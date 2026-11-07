import { useEffect, useRef } from 'react'
import { useStore } from '../store/store'
import { navigate, scrollToCatalog } from '../router'
import { money, stockInfo } from '../lib/format'
import Icon from './Icon'
import ProductImage from './ProductImage'
import QtyStepper from './QtyStepper'

export default function CartDrawer() {
  const { cartOpen, setCartOpen, lines, count, subtotal, setQty, removeFromCart } = useStore()
  const closeRef = useRef(null)

  useEffect(() => {
    if (!cartOpen) return
    const onKey = (e) => e.key === 'Escape' && setCartOpen(false)
    document.addEventListener('keydown', onKey)
    const prevOverflow = document.body.style.overflow
    document.body.style.overflow = 'hidden'
    closeRef.current?.focus()
    return () => {
      document.removeEventListener('keydown', onKey)
      document.body.style.overflow = prevOverflow
    }
  }, [cartOpen, setCartOpen])

  const checkout = () => {
    setCartOpen(false)
    navigate('/checkout')
  }

  return (
    <div className={`fixed inset-0 z-50 ${cartOpen ? '' : 'pointer-events-none'}`}>
      <div
        onClick={() => setCartOpen(false)}
        className={`absolute inset-0 bg-black/55 backdrop-blur-sm transition-opacity duration-300 ${
          cartOpen ? 'opacity-100' : 'opacity-0'
        }`}
      />
      <aside
        role="dialog"
        aria-modal="true"
        aria-label="Shopping bag"
        inert={!cartOpen}
        className={`absolute top-0 right-0 flex h-full w-full max-w-md flex-col bg-bg shadow-2xl transition-transform duration-500 ease-[cubic-bezier(0.2,0.8,0.2,1)] ${
          cartOpen ? 'translate-x-0' : 'translate-x-full'
        }`}
      >
        <div className="flex items-center justify-between border-b border-line px-6 py-5">
          <h2 className="font-display text-2xl font-bold tracking-tight">
            Your bag <span className="text-muted">({count})</span>
          </h2>
          <button
            ref={closeRef}
            onClick={() => setCartOpen(false)}
            className="grid size-10 cursor-pointer place-items-center rounded-full hover:bg-surface-2"
            aria-label="Close bag"
          >
            <Icon name="x" />
          </button>
        </div>

        {lines.length === 0 ? (
          <div className="flex flex-1 flex-col items-center justify-center px-8 text-center">
            <div className="mb-5 grid size-24 place-items-center rounded-full bg-surface-2">
              <Icon name="bag" className="size-10 text-muted" strokeWidth={1.4} />
            </div>
            <h3 className="font-display text-2xl font-bold">Your bag is empty</h3>
            <p className="mt-2 text-muted">Looks like you haven&apos;t found your next favourite yet.</p>
            <button
              className="btn btn-volt mt-6"
              onClick={() => {
                setCartOpen(false)
                scrollToCatalog()
              }}
            >
              Start shopping <Icon name="arrow-right" className="size-4" />
            </button>
          </div>
        ) : (
          <>
            <ul className="flex-1 divide-y divide-line overflow-y-auto px-6">
              {lines.map(({ product: p, qty, clamped, lineTotal }) => (
                <li key={p.id} className="flex gap-4 py-5">
                  <a
                    href={`#/product/${p.id}`}
                    onClick={() => setCartOpen(false)}
                    className="size-24 shrink-0 overflow-hidden rounded-2xl bg-stage"
                  >
                    <ProductImage product={p} className="size-full object-contain p-2" />
                  </a>
                  <div className="flex min-w-0 flex-1 flex-col">
                    <p className="truncate text-xs font-semibold tracking-wider text-muted uppercase">
                      {p.brand}
                    </p>
                    <a
                      href={`#/product/${p.id}`}
                      onClick={() => setCartOpen(false)}
                      className="line-clamp-2 leading-snug font-semibold hover:underline"
                    >
                      {p.name}
                    </a>
                    {clamped && (
                      <p className="mt-1 text-xs font-medium text-coral">
                        Only {stockInfo(p).max} available — quantity adjusted
                      </p>
                    )}
                    <div className="mt-auto flex items-center justify-between pt-2">
                      <QtyStepper
                        small
                        value={qty}
                        max={stockInfo(p).max}
                        onChange={(n) => setQty(p, n)}
                        label={`Quantity of ${p.name}`}
                      />
                      <span className="font-display font-bold">{money(lineTotal)}</span>
                    </div>
                  </div>
                  <button
                    onClick={() => removeFromCart(p)}
                    className="-mt-1 -mr-2 grid size-8 shrink-0 cursor-pointer place-items-center rounded-full text-muted transition hover:bg-coral/10 hover:text-coral"
                    aria-label={`Remove ${p.name}`}
                  >
                    <Icon name="trash" className="size-4" />
                  </button>
                </li>
              ))}
            </ul>

            <div className="border-t border-line bg-surface px-6 py-5">
              <div className="flex items-baseline justify-between">
                <span className="text-muted">Subtotal</span>
                <span className="font-display text-3xl font-extrabold tracking-tight">
                  {money(subtotal)}
                </span>
              </div>
              <button className="btn btn-volt mt-4 h-14 w-full text-base" onClick={checkout}>
                Checkout <Icon name="arrow-right" className="size-5" />
              </button>
              <button
                className="mt-2 w-full cursor-pointer py-2 text-sm font-medium text-muted hover:text-ink"
                onClick={() => setCartOpen(false)}
              >
                Continue shopping
              </button>
            </div>
          </>
        )}
      </aside>
    </div>
  )
}
