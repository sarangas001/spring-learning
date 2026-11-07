import { useEffect, useMemo, useState } from 'react'
import { useStore } from '../store/store'
import { getProduct } from '../api'
import { formatDate, money, stockInfo } from '../lib/format'
import { navigate } from '../router'
import Icon from '../components/Icon'
import ProductImage from '../components/ProductImage'
import QtyStepper from '../components/QtyStepper'
import ProductCard from '../components/ProductCard'
import ErrorState from '../components/ErrorState'

function Spec({ label, children }) {
  return (
    <div className="flex items-center justify-between gap-4 py-3 text-sm">
      <dt className="text-muted">{label}</dt>
      <dd className="text-right font-semibold">{children}</dd>
    </div>
  )
}

export default function ProductPage({ id }) {
  const { catalog, reload, addToCart, toggleWish, wishlist, setCartOpen } = useStore()
  const [fresh, setFresh] = useState(null)
  const [qty, setQty] = useState(1)
  const [zoom, setZoom] = useState({ on: false, x: 50, y: 50 })

  // Show the cached product instantly, then quietly refresh stock from the API.
  useEffect(() => {
    const ac = new AbortController()
    getProduct(id, ac.signal)
      .then(setFresh)
      .catch(() => {})
    return () => ac.abort()
  }, [id])

  const listed = catalog.items.find((p) => p.id === id)
  const p = fresh && fresh.id === id ? fresh : listed

  const related = useMemo(() => {
    if (!p) return []
    const others = catalog.items.filter((x) => x.id !== p.id)
    const same = others.filter((x) => x.category === p.category)
    return [...same, ...others.filter((x) => x.category !== p.category)].slice(0, 4)
  }, [catalog.items, p])

  if (!p) {
    if (catalog.status === 'loading') {
      return (
        <div className="mx-auto grid max-w-7xl gap-10 px-4 py-10 sm:px-6 lg:grid-cols-2">
          <div className="skeleton aspect-square rounded-[2rem]" />
          <div className="space-y-4 pt-4">
            <div className="skeleton h-4 w-24 rounded-full" />
            <div className="skeleton h-12 w-4/5 rounded-2xl" />
            <div className="skeleton h-10 w-32 rounded-2xl" />
            <div className="skeleton h-32 w-full rounded-2xl" />
          </div>
        </div>
      )
    }
    return (
      <div className="mx-auto max-w-7xl px-4 sm:px-6">
        <ErrorState
          title={catalog.status === 'error' ? "Couldn't load this product" : 'Product not found'}
          message={
            catalog.status === 'error'
              ? catalog.error
              : 'It may have been removed or the link is out of date.'
          }
          onRetry={catalog.status === 'error' ? reload : () => navigate('/')}
        />
      </div>
    )
  }

  const stock = stockInfo(p)
  const saved = wishlist.includes(p.id)
  const quantity = Math.min(qty, Math.max(stock.max, 1))

  const buyNow = () => {
    if (addToCart(p, quantity, { silent: true })) navigate('/checkout')
  }

  return (
    <div className="mx-auto max-w-7xl px-4 pt-6 sm:px-6">
      <nav className="mb-6 flex flex-wrap items-center gap-2 text-sm text-muted" aria-label="Breadcrumb">
        <a href="#/" className="inline-flex items-center gap-1.5 font-medium hover:text-ink">
          <Icon name="arrow-left" className="size-4" /> Shop
        </a>
        {p.category && (
          <>
            <span aria-hidden="true">/</span>
            <span>{p.category}</span>
          </>
        )}
        <span aria-hidden="true">/</span>
        <span className="line-clamp-1 font-medium text-ink">{p.name}</span>
      </nav>

      <div className="grid gap-8 lg:grid-cols-2 lg:gap-14">
        {/* ---------- image stage ---------- */}
        <div className="lg:sticky lg:top-28 lg:self-start">
          <div
            className="relative aspect-square cursor-zoom-in overflow-hidden rounded-[2rem] bg-stage"
            onPointerMove={(e) => {
              if (e.pointerType !== 'mouse' || !p.image) return
              const r = e.currentTarget.getBoundingClientRect()
              setZoom({
                on: true,
                x: ((e.clientX - r.left) / r.width) * 100,
                y: ((e.clientY - r.top) / r.height) * 100,
              })
            }}
            onPointerLeave={() => setZoom((z) => ({ ...z, on: false }))}
          >
            <ProductImage
              product={p}
              className="size-full object-contain p-10 transition-transform duration-200 ease-out"
            />
            {p.image && (
              <img
                src={p.image}
                alt=""
                aria-hidden="true"
                draggable="false"
                className="pointer-events-none absolute inset-0 size-full object-contain p-10 mix-blend-multiply transition duration-200 ease-out"
                style={{
                  opacity: zoom.on ? 1 : 0,
                  transform: zoom.on ? 'scale(2)' : 'scale(1)',
                  transformOrigin: `${zoom.x}% ${zoom.y}%`,
                }}
              />
            )}
            <div className="absolute top-5 left-5 flex gap-2">
              {p.isNew && stock.key !== 'out' && (
                <span className="rounded-full bg-volt px-3 py-1 text-xs font-bold tracking-wide text-volt-ink uppercase">
                  New
                </span>
              )}
              {stock.key === 'out' && (
                <span className="rounded-full bg-ink px-3 py-1 text-xs font-bold text-bg">Sold out</span>
              )}
            </div>
          </div>
        </div>

        {/* ---------- details ---------- */}
        <div className="animate-fade-up">
          <p className="text-sm font-bold tracking-widest text-muted uppercase">{p.brand}</p>
          <h1 className="mt-2 font-display text-4xl leading-[1.02] font-extrabold tracking-tight sm:text-5xl">
            {p.name}
          </h1>

          <div className="mt-5 flex flex-wrap items-center gap-4">
            <span className="font-display text-4xl font-extrabold tracking-tight">{money(p.price)}</span>
            <span
              className={`inline-flex items-center gap-2 rounded-full px-3 py-1.5 text-sm font-semibold ${
                stock.key === 'out'
                  ? 'bg-ink/10'
                  : stock.key === 'low'
                    ? 'bg-coral/15 text-coral'
                    : 'bg-volt/60 text-volt-ink dark:bg-volt/20 dark:text-volt'
              }`}
            >
              <span
                className={`size-2 rounded-full ${
                  stock.key === 'out' ? 'bg-ink/40' : stock.key === 'low' ? 'bg-coral' : 'bg-current'
                }`}
              />
              {stock.key === 'in' ? `In stock · ${stock.max} available` : stock.label}
            </span>
          </div>

          {p.description && <p className="mt-6 text-lg leading-relaxed text-muted">{p.description}</p>}

          {/* purchase box */}
          <div className="mt-8 rounded-3xl border border-line bg-surface p-5 sm:p-6">
            <div className="flex flex-wrap items-center gap-4">
              <QtyStepper value={quantity} max={stock.max} onChange={(n) => setQty(Math.max(1, n))} />
              <p className="text-sm text-muted">
                Total{' '}
                <strong className="font-display text-xl text-ink">{money(p.price * quantity)}</strong>
              </p>
            </div>
            <div className="mt-5 flex flex-wrap gap-3">
              <button
                className="btn btn-volt h-14 flex-1 px-6 text-base"
                disabled={stock.key === 'out'}
                onClick={() => {
                  if (addToCart(p, quantity)) setCartOpen(true)
                }}
              >
                <Icon name="bag" className="size-5" />
                {stock.key === 'out' ? 'Sold out' : 'Add to bag'}
              </button>
              <button
                className="btn btn-primary h-14 flex-1 px-6 text-base"
                disabled={stock.key === 'out'}
                onClick={buyNow}
              >
                Buy now <Icon name="zap" className="size-5" />
              </button>
              <button
                onClick={() => toggleWish(p)}
                className="btn btn-ghost size-14 px-0"
                aria-label={saved ? 'Remove from saved' : 'Save for later'}
                aria-pressed={saved}
              >
                <Icon name="heart" fill={saved} className={`size-5 ${saved ? 'text-coral' : ''}`} />
              </button>
            </div>
          </div>

          <ul className="mt-6 grid gap-3 text-sm sm:grid-cols-3">
            {[
              ['zap', 'Instant order confirmation'],
              ['shield', 'Live stock — no surprises'],
              ['package', 'Reserved when you order'],
            ].map(([icon, text]) => (
              <li key={text} className="flex items-center gap-2.5 rounded-2xl bg-surface-2/70 px-4 py-3 font-medium">
                <Icon name={icon} className="size-5 shrink-0" />
                {text}
              </li>
            ))}
          </ul>

          <dl className="mt-8 divide-y divide-line border-y border-line">
            <Spec label="Brand">{p.brand || '—'}</Spec>
            <Spec label="Category">{p.category || '—'}</Spec>
            <Spec label="Released">{formatDate(p.releaseDate)}</Spec>
            <Spec label="Product ID">#{p.id}</Spec>
          </dl>
        </div>
      </div>

      {related.length > 0 && (
        <section className="mt-24">
          <h2 className="font-display text-3xl font-extrabold tracking-tight">You might also like</h2>
          <div className="mt-6 grid grid-cols-2 gap-x-3 gap-y-8 sm:gap-x-5 md:grid-cols-4">
            {related.map((r, i) => (
              <ProductCard key={r.id} product={r} index={i} />
            ))}
          </div>
        </section>
      )}
    </div>
  )
}
