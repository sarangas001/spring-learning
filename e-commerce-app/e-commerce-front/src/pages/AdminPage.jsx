import { useMemo, useState } from 'react'
import { useStore } from '../store/store'
import { deleteProduct } from '../api'
import { money, moneyCompact, stockInfo } from '../lib/format'
import Icon from '../components/Icon'
import ProductImage from '../components/ProductImage'
import ConfirmDialog from '../components/ConfirmDialog'
import ErrorState from '../components/ErrorState'

const PILL = {
  in: 'bg-volt/60 text-volt-ink dark:bg-volt/20 dark:text-volt',
  low: 'bg-coral/15 text-coral',
  out: 'bg-ink/10 text-muted',
}

function Stat({ label, value, title, tone = '' }) {
  return (
    <div className="min-w-0 rounded-3xl border border-line bg-surface p-5">
      <p className="truncate text-xs font-bold tracking-widest text-muted uppercase">{label}</p>
      <p
        title={title}
        className={`mt-1 truncate font-display text-3xl font-extrabold tracking-tight ${tone}`}
      >
        {value}
      </p>
    </div>
  )
}

function friendlyDeleteError(message) {
  return /constraint|violates|foreign key/i.test(message)
    ? "This product is part of existing orders, so it can't be deleted. Set its stock to 0 or mark it unavailable instead."
    : message
}

export default function AdminPage() {
  const { catalog, reload, refresh, toast } = useStore()
  const [q, setQ] = useState('')
  const [target, setTarget] = useState(null)
  const [busy, setBusy] = useState(false)
  const items = catalog.items

  const stats = useMemo(
    () => ({
      total: items.length,
      value: items.reduce((n, p) => n + p.price * p.stockQuantity, 0),
      low: items.filter((p) => stockInfo(p).key === 'low').length,
      out: items.filter((p) => stockInfo(p).key === 'out').length,
    }),
    [items],
  )

  const rows = useMemo(() => {
    const s = q.trim().toLowerCase()
    return items
      .filter((p) => !s || [p.name, p.brand, p.category].some((x) => x?.toLowerCase().includes(s)))
      .sort((a, b) => b.id - a.id) // newest first
  }, [items, q])

  const confirmDelete = async () => {
    setBusy(true)
    try {
      await deleteProduct(target.id)
      toast(`Deleted ${target.name}`, 'success')
      setTarget(null)
      await refresh()
    } catch (err) {
      setTarget(null)
      toast(friendlyDeleteError(err.message), 'error')
    } finally {
      setBusy(false)
    }
  }

  return (
    <div className="mx-auto max-w-6xl px-4 pt-10 sm:px-6">
      <div className="flex flex-wrap items-end justify-between gap-4">
        <div>
          <h1 className="font-display text-5xl font-extrabold tracking-tight">Products</h1>
          <p className="mt-2 text-muted">Add, edit and remove what&apos;s on your shelves.</p>
        </div>
        <a href="#/admin/new" className="btn btn-volt h-12 px-6">
          <Icon name="plus" className="size-5" strokeWidth={2.4} /> Add product
        </a>
      </div>

      {catalog.status === 'error' ? (
        <ErrorState title="Couldn't load products" message={catalog.error} onRetry={reload} />
      ) : catalog.status === 'loading' ? (
        <div className="mt-8 space-y-3">
          {[0, 1, 2, 3].map((i) => (
            <div key={i} className="skeleton h-24 rounded-3xl" />
          ))}
        </div>
      ) : items.length === 0 ? (
        <div className="mt-10 animate-fade-up rounded-3xl border border-dashed border-line py-20 text-center">
          <div className="mx-auto grid size-20 place-items-center rounded-full bg-volt text-volt-ink">
            <Icon name="package" className="size-9" strokeWidth={1.6} />
          </div>
          <h2 className="mt-5 font-display text-3xl font-bold">Your shelves are empty</h2>
          <p className="mt-1 text-muted">Add your first product and it&apos;ll appear in the shop instantly.</p>
          <a href="#/admin/new" className="btn btn-primary mt-6 h-12 px-6">
            <Icon name="plus" className="size-5" /> Add your first product
          </a>
        </div>
      ) : (
        <>
          <div className="mt-8 grid grid-cols-2 gap-3 md:grid-cols-4">
            <Stat label="Products" value={stats.total} />
            <Stat label="Stock value" value={moneyCompact(stats.value)} title={money(stats.value)} />
            <Stat label="Low stock" value={stats.low} tone={stats.low ? 'text-coral' : ''} />
            <Stat label="Sold out" value={stats.out} />
          </div>

          <div className="relative mt-8">
            <Icon name="search" className="pointer-events-none absolute top-1/2 left-4 size-4 -translate-y-1/2 text-muted" />
            <input
              className="input pl-11"
              placeholder="Filter by name, brand or category…"
              value={q}
              onChange={(e) => setQ(e.target.value)}
              aria-label="Filter products"
            />
          </div>

          {rows.length === 0 ? (
            <p className="py-16 text-center text-muted">No products match “{q}”.</p>
          ) : (
            <ul className="mt-4 divide-y divide-line overflow-hidden rounded-3xl border border-line bg-surface">
              {rows.map((p) => {
                const stock = stockInfo(p)
                return (
                  <li
                    key={p.id}
                    className="flex flex-wrap items-center gap-x-4 gap-y-3 p-4 transition hover:bg-surface-2/50 md:flex-nowrap"
                  >
                    <div className="size-16 shrink-0 overflow-hidden rounded-2xl bg-stage">
                      <ProductImage product={p} className="size-full object-contain p-1.5" />
                    </div>
                    <div className="min-w-0 flex-1 basis-40">
                      <a href={`#/product/${p.id}`} className="line-clamp-1 font-semibold hover:underline">
                        {p.name}
                      </a>
                      <p className="line-clamp-1 text-sm text-muted">
                        {[p.brand, p.category].filter(Boolean).join(' · ')}
                      </p>
                    </div>
                    <p className="w-24 font-display font-bold md:text-right">{money(p.price)}</p>
                    <span
                      className={`inline-flex w-28 justify-center rounded-full px-3 py-1 text-xs font-semibold ${PILL[stock.key]}`}
                    >
                      {stock.key === 'in' ? `${stock.max} in stock` : stock.label}
                    </span>
                    <div className="ml-auto flex gap-1">
                      <a
                        href={`#/product/${p.id}`}
                        className="grid size-10 place-items-center rounded-full transition hover:bg-surface-2"
                        aria-label={`View ${p.name}`}
                        title="View in shop"
                      >
                        <Icon name="eye" className="size-[18px]" />
                      </a>
                      <a
                        href={`#/admin/edit/${p.id}`}
                        className="grid size-10 place-items-center rounded-full transition hover:bg-surface-2"
                        aria-label={`Edit ${p.name}`}
                        title="Edit"
                      >
                        <Icon name="edit" className="size-[18px]" />
                      </a>
                      <button
                        onClick={() => setTarget(p)}
                        className="grid size-10 cursor-pointer place-items-center rounded-full text-muted transition hover:bg-coral/10 hover:text-coral"
                        aria-label={`Delete ${p.name}`}
                        title="Delete"
                      >
                        <Icon name="trash" className="size-[18px]" />
                      </button>
                    </div>
                  </li>
                )
              })}
            </ul>
          )}
        </>
      )}

      {target && (
        <ConfirmDialog
          danger
          busy={busy}
          title={`Delete “${target.name}”?`}
          message="This removes it from the shop for good. This can't be undone."
          confirmLabel="Delete product"
          onConfirm={confirmDelete}
          onCancel={() => setTarget(null)}
        />
      )}
    </div>
  )
}
