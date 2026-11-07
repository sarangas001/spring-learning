import { useMemo, useState } from 'react'
import { useStore } from '../store/store'
import { createProduct, updateProduct } from '../api'
import { money, stockInfo } from '../lib/format'
import { dataUrlToFile, formatBytes, prepareImage, readDataUrl } from '../lib/image'
import { navigate } from '../router'
import Icon from '../components/Icon'
import ErrorState from '../components/ErrorState'

const pad = (n) => String(n).padStart(2, '0')
const toInputDate = (d) => (d ? `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}` : '')
// <input type="date"> gives yyyy-mm-dd; the backend wants dd-MM-yyyy.
const toBackendDate = (v) => (v ? v.split('-').reverse().join('-') : null)

const DESC_MAX = 255 // Product.description is a plain String → varchar(255)

function Field({ label, hint, error, children, className = '' }) {
  return (
    <label className={`block ${className}`}>
      <span className="mb-1.5 flex items-baseline justify-between gap-2 text-sm font-semibold">
        {label}
        {hint && <span className="text-xs font-normal text-muted">{hint}</span>}
      </span>
      {children}
      {error && (
        <span className="mt-1.5 block text-sm font-medium text-coral" role="alert">
          {error}
        </span>
      )}
    </label>
  )
}

function PreviewCard({ form, imageUrl }) {
  const stock = stockInfo({
    stockQuantity: parseInt(form.stock, 10) || 0,
    productAvailable: form.available,
  })
  return (
    <div className="w-full max-w-xs">
      <div className="relative aspect-4/5 overflow-hidden rounded-3xl bg-stage">
        {imageUrl ? (
          <img
            src={imageUrl}
            alt=""
            className="size-full object-contain p-7 mix-blend-multiply"
          />
        ) : (
          <div className="grid size-full place-items-center text-ink/25">
            <Icon name="image" className="size-16" strokeWidth={1.2} />
          </div>
        )}
        <div className="absolute top-3 left-3 flex flex-col items-start gap-1.5">
          {stock.key === 'low' && (
            <span className="rounded-full bg-coral px-2.5 py-1 text-[11px] font-bold text-white">
              {stock.label}
            </span>
          )}
          {stock.key === 'out' && (
            <span className="rounded-full bg-ink px-2.5 py-1 text-[11px] font-bold text-bg">Sold out</span>
          )}
        </div>
      </div>
      <div className="mt-3 px-1">
        <p className="truncate text-xs font-semibold tracking-wider text-muted uppercase">
          {form.brand || 'Brand'}
        </p>
        <h3 className="mt-0.5 line-clamp-2 leading-snug font-semibold">{form.name || 'Product name'}</h3>
        <p className="mt-2 font-display text-xl font-bold tracking-tight">
          {money(Number(form.price) || 0)}
        </p>
      </div>
    </div>
  )
}

function ProductForm({ existing }) {
  const { catalog, refresh, toast } = useStore()
  const editing = Boolean(existing)

  const [form, setForm] = useState(() => ({
    name: existing?.name ?? '',
    brand: existing?.brand ?? '',
    category: existing?.category ?? '',
    price: existing ? String(existing.price) : '',
    stock: existing ? String(existing.stockQuantity) : '',
    releaseDate: toInputDate(existing ? existing.releaseDate : new Date()),
    available: existing ? existing.productAvailable !== false : true,
    description: existing?.description ?? '',
  }))
  // file === null with a url means "keep the image that's already saved".
  const [image, setImage] = useState(() =>
    existing?.image ? { file: null, url: existing.image, note: null } : null,
  )
  const [imgBusy, setImgBusy] = useState(false)
  const [imgError, setImgError] = useState('')
  const [dragging, setDragging] = useState(false)
  const [touched, setTouched] = useState({})
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)

  const categories = useMemo(
    () => [...new Set(catalog.items.map((p) => p.category).filter(Boolean))].sort(),
    [catalog.items],
  )

  const set = (k) => (e) => setForm((f) => ({ ...f, [k]: e.target.value }))
  const blur = (k) => () => setTouched((t) => ({ ...t, [k]: true }))

  const price = Number(form.price)
  const stockNum = Number(form.stock)
  const errors = {
    name: form.name.trim().length < 2 ? 'Give your product a name' : '',
    brand: form.brand.trim() ? '' : 'Brand is required',
    category: form.category.trim() ? '' : 'Pick or type a category',
    price: form.price !== '' && price > 0 ? '' : 'Enter a price above 0',
    stock:
      form.stock !== '' && Number.isInteger(stockNum) && stockNum >= 0
        ? ''
        : 'Enter a whole number, 0 or more',
    image: image ? '' : 'Add a product photo',
  }
  const show = (k) => (touched[k] ? errors[k] : '')

  const handleFile = async (file) => {
    if (!file) return
    setImgBusy(true)
    setImgError('')
    try {
      const prepared = await prepareImage(file)
      const url = await readDataUrl(prepared)
      setImage({
        file: prepared,
        url,
        note:
          prepared !== file
            ? `Optimised ${formatBytes(file.size)} → ${formatBytes(prepared.size)} to fit the upload limit`
            : null,
      })
      setTouched((t) => ({ ...t, image: true }))
    } catch (err) {
      setImgError(err.message)
    } finally {
      setImgBusy(false)
    }
  }

  const onSubmit = async (e) => {
    e.preventDefault()
    setTouched({ name: true, brand: true, category: true, price: true, stock: true, image: true })
    if (Object.values(errors).some(Boolean)) return

    setSubmitting(true)
    setError(null)
    try {
      const payload = {
        name: form.name.trim(),
        brand: form.brand.trim(),
        category: form.category.trim(),
        description: form.description.trim(),
        price,
        stockQuantity: stockNum,
        productAvailable: form.available,
        releaseDate: toBackendDate(form.releaseDate),
      }
      // The PUT endpoint requires an image part, so resend the saved one when unchanged.
      const file = image.file ?? (await dataUrlToFile(image.url, existing?.imageName || 'image'))

      if (editing) {
        await updateProduct({ ...payload, id: existing.id }, file)
      } else {
        await createProduct(payload, file)
      }
      await refresh()
      toast(editing ? `Saved changes to ${payload.name}` : `${payload.name} is now in the shop`, 'success')
      navigate('/admin')
    } catch (err) {
      setError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div className="mx-auto max-w-6xl px-4 pt-8 sm:px-6">
      <a
        href="#/admin"
        className="inline-flex items-center gap-1.5 text-sm font-medium text-muted hover:text-ink"
      >
        <Icon name="arrow-left" className="size-4" /> All products
      </a>
      <h1 className="mt-3 font-display text-5xl font-extrabold tracking-tight">
        {editing ? 'Edit product' : 'Add a product'}
      </h1>

      <form onSubmit={onSubmit} noValidate className="mt-8 grid gap-8 lg:grid-cols-[1fr_20rem]">
        <div className="space-y-6">
          {/* ---------- photo ---------- */}
          <section className="rounded-3xl border border-line bg-surface p-6 sm:p-8">
            <h2 className="mb-5 font-display text-2xl font-bold">Photo</h2>
            <label
              onDragOver={(e) => {
                e.preventDefault()
                setDragging(true)
              }}
              onDragLeave={() => setDragging(false)}
              onDrop={(e) => {
                e.preventDefault()
                setDragging(false)
                handleFile(e.dataTransfer.files?.[0])
              }}
              className={`group flex min-h-56 cursor-pointer flex-col items-center justify-center gap-3 rounded-2xl border-2 border-dashed p-6 text-center transition ${
                dragging
                  ? 'border-ink bg-volt/30'
                  : show('image')
                    ? 'border-coral bg-coral/5'
                    : 'border-line hover:border-ink hover:bg-surface-2/50'
              }`}
            >
              <input
                type="file"
                accept="image/*"
                className="sr-only"
                onChange={(e) => {
                  handleFile(e.target.files?.[0])
                  e.target.value = ''
                }}
              />
              {imgBusy ? (
                <>
                  <span className="size-8 animate-spin rounded-full border-2 border-line border-t-ink" />
                  <span className="text-sm font-medium">Optimising image…</span>
                </>
              ) : image ? (
                <>
                  <div className="size-40 overflow-hidden rounded-2xl bg-stage">
                    <img src={image.url} alt="Selected product" className="size-full object-contain p-3 mix-blend-multiply" />
                  </div>
                  <span className="btn btn-ghost h-9 text-xs">
                    <Icon name="upload" className="size-4" /> Replace photo
                  </span>
                </>
              ) : (
                <>
                  <span className="grid size-14 place-items-center rounded-full bg-volt text-volt-ink transition group-hover:scale-110">
                    <Icon name="upload" className="size-6" />
                  </span>
                  <span className="font-semibold">Drop a photo here, or click to browse</span>
                  <span className="text-sm text-muted">
                    JPG, PNG, WebP — large images are shrunk automatically
                  </span>
                </>
              )}
            </label>
            {image?.note && <p className="mt-3 text-sm text-muted">{image.note}</p>}
            {(imgError || show('image')) && (
              <p className="mt-3 text-sm font-medium text-coral" role="alert">
                {imgError || show('image')}
              </p>
            )}
          </section>

          {/* ---------- details ---------- */}
          <section className="rounded-3xl border border-line bg-surface p-6 sm:p-8">
            <h2 className="mb-5 font-display text-2xl font-bold">Details</h2>
            <div className="grid gap-5 sm:grid-cols-2">
              <Field label="Product name" error={show('name')} className="sm:col-span-2">
                <input
                  className="input"
                  maxLength={120}
                  placeholder="Aurora X1 Smartphone"
                  value={form.name}
                  onChange={set('name')}
                  onBlur={blur('name')}
                  aria-invalid={Boolean(show('name'))}
                />
              </Field>
              <Field label="Brand" error={show('brand')}>
                <input
                  className="input"
                  maxLength={80}
                  placeholder="Nova"
                  value={form.brand}
                  onChange={set('brand')}
                  onBlur={blur('brand')}
                  aria-invalid={Boolean(show('brand'))}
                />
              </Field>
              <Field label="Category" error={show('category')}>
                <input
                  className="input"
                  list="category-options"
                  maxLength={80}
                  placeholder="Mobiles"
                  value={form.category}
                  onChange={set('category')}
                  onBlur={blur('category')}
                  aria-invalid={Boolean(show('category'))}
                />
                <datalist id="category-options">
                  {categories.map((c) => (
                    <option key={c} value={c} />
                  ))}
                </datalist>
              </Field>
              <Field label="Price" error={show('price')}>
                <input
                  className="input"
                  type="number"
                  inputMode="decimal"
                  min="0"
                  step="0.01"
                  placeholder="799.00"
                  value={form.price}
                  onChange={set('price')}
                  onBlur={blur('price')}
                  aria-invalid={Boolean(show('price'))}
                />
              </Field>
              <Field label="Stock quantity" error={show('stock')}>
                <input
                  className="input"
                  type="number"
                  inputMode="numeric"
                  min="0"
                  step="1"
                  placeholder="25"
                  value={form.stock}
                  onChange={set('stock')}
                  onBlur={blur('stock')}
                  aria-invalid={Boolean(show('stock'))}
                />
              </Field>
              <Field label="Release date" hint="optional">
                <input className="input" type="date" value={form.releaseDate} onChange={set('releaseDate')} />
              </Field>
              <div className="flex items-end">
                <label className="flex h-12 w-full cursor-pointer items-center justify-between gap-3 rounded-2xl border border-line px-4 text-sm font-semibold">
                  Available for sale
                  <span className="relative">
                    <input
                      type="checkbox"
                      className="peer sr-only"
                      checked={form.available}
                      onChange={(e) => setForm((f) => ({ ...f, available: e.target.checked }))}
                    />
                    <span className="block h-6 w-11 rounded-full bg-surface-2 ring-1 ring-line transition peer-checked:bg-volt peer-focus-visible:ring-2 peer-focus-visible:ring-ink" />
                    <span className="absolute top-0.5 left-0.5 size-5 rounded-full bg-ink transition peer-checked:translate-x-5 peer-checked:bg-volt-ink" />
                  </span>
                </label>
              </div>
              <Field
                label="Description"
                hint={`${form.description.length}/${DESC_MAX}`}
                className="sm:col-span-2"
              >
                <textarea
                  className="input h-28 resize-none py-3"
                  maxLength={DESC_MAX}
                  placeholder="What makes this product worth owning?"
                  value={form.description}
                  onChange={set('description')}
                />
              </Field>
            </div>
          </section>

          {error && (
            <div
              className="flex animate-fade-up items-start gap-3 rounded-2xl border border-coral/40 bg-coral/10 p-4 text-sm"
              role="alert"
            >
              <Icon name="alert" className="mt-0.5 size-5 shrink-0 text-coral" />
              <div>
                <p className="font-semibold text-coral">
                  We couldn&apos;t {editing ? 'save' : 'add'} this product
                </p>
                <p className="mt-0.5 break-words">{error}</p>
              </div>
            </div>
          )}

          <div className="flex flex-wrap gap-3">
            <button type="submit" className="btn btn-volt h-14 flex-1 px-8 text-base" disabled={submitting || imgBusy}>
              {submitting ? (
                <>
                  <span className="size-5 animate-spin rounded-full border-2 border-volt-ink/30 border-t-volt-ink" />
                  {editing ? 'Saving…' : 'Adding…'}
                </>
              ) : (
                <>
                  <Icon name="check" className="size-5" strokeWidth={2.4} />
                  {editing ? 'Save changes' : 'Add to shop'}
                </>
              )}
            </button>
            <a href="#/admin" className="btn btn-ghost h-14 px-8 text-base">
              Cancel
            </a>
          </div>
        </div>

        {/* ---------- live preview ---------- */}
        <aside className="lg:sticky lg:top-28 lg:self-start">
          <p className="mb-3 text-xs font-bold tracking-widest text-muted uppercase">Live preview</p>
          <div className="rounded-3xl border border-line bg-surface p-5">
            <PreviewCard form={form} imageUrl={image?.url} />
          </div>
        </aside>
      </form>
    </div>
  )
}

export default function ProductFormPage({ id }) {
  const { catalog, reload } = useStore()

  if (id == null) return <ProductForm />

  const existing = catalog.items.find((p) => p.id === id)
  if (existing) return <ProductForm existing={existing} />

  if (catalog.status === 'loading') {
    return (
      <div className="mx-auto max-w-6xl space-y-4 px-4 pt-10 sm:px-6">
        <div className="skeleton h-14 w-72 rounded-2xl" />
        <div className="skeleton h-64 rounded-3xl" />
        <div className="skeleton h-96 rounded-3xl" />
      </div>
    )
  }
  return (
    <ErrorState
      title={catalog.status === 'error' ? "Couldn't load this product" : 'Product not found'}
      message={catalog.status === 'error' ? catalog.error : 'It may have been deleted.'}
      onRetry={catalog.status === 'error' ? reload : () => navigate('/admin')}
    />
  )
}
