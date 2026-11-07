import { useState } from 'react'
import { useStore } from '../store/store'
import { money, stockInfo } from '../lib/format'
import Icon from './Icon'
import ProductImage from './ProductImage'

export default function ProductCard({ product: p, index = 0 }) {
  const { addToCart, toggleWish, wishlist } = useStore()
  const [added, setAdded] = useState(false)
  const stock = stockInfo(p)
  const saved = wishlist.includes(p.id)

  const onAdd = () => {
    if (!addToCart(p, 1)) return
    setAdded(true)
    setTimeout(() => setAdded(false), 1400)
  }

  return (
    <article
      className="group relative flex animate-fade-up flex-col"
      style={{ animationDelay: `${Math.min(index, 11) * 45}ms` }}
    >
      <div className="relative aspect-4/5 overflow-hidden rounded-3xl bg-stage">
        <ProductImage
          product={p}
          className={`size-full object-contain p-7 transition duration-500 ease-out group-hover:scale-[1.07] ${
            stock.key === 'out' ? 'opacity-50 grayscale' : ''
          }`}
        />

        <div className="absolute top-3 left-3 flex flex-col items-start gap-1.5">
          {p.isNew && stock.key !== 'out' && (
            <span className="rounded-full bg-volt px-2.5 py-1 text-[11px] font-bold tracking-wide text-volt-ink uppercase">
              New
            </span>
          )}
          {stock.key === 'low' && (
            <span className="rounded-full bg-coral px-2.5 py-1 text-[11px] font-bold text-white">
              {stock.label}
            </span>
          )}
          {stock.key === 'out' && (
            <span className="rounded-full bg-ink px-2.5 py-1 text-[11px] font-bold text-bg">
              Sold out
            </span>
          )}
        </div>

        <button
          onClick={() => toggleWish(p)}
          className="absolute top-3 right-3 z-10 grid size-9 cursor-pointer place-items-center rounded-full bg-white/80 text-neutral-900 backdrop-blur transition hover:scale-110 hover:bg-white active:scale-95"
          aria-label={saved ? `Remove ${p.name} from saved` : `Save ${p.name}`}
          aria-pressed={saved}
        >
          <Icon name="heart" fill={saved} className={`size-4.5 ${saved ? 'text-coral' : ''}`} />
        </button>
      </div>

      <div className="mt-3 flex flex-1 flex-col px-1">
        <p className="truncate text-xs font-semibold tracking-wider text-muted uppercase">
          {p.brand || p.category}
        </p>
        <h3 className="mt-0.5 line-clamp-2 leading-snug font-semibold">
          <a href={`#/product/${p.id}`} className="after:absolute after:inset-0 after:rounded-3xl">
            {p.name}
          </a>
        </h3>
        <div className="mt-auto flex items-center justify-between pt-3">
          <span className="font-display text-xl font-bold tracking-tight">{money(p.price)}</span>
          <button
            onClick={onAdd}
            disabled={stock.key === 'out'}
            className={`relative z-10 grid size-10 cursor-pointer place-items-center rounded-full transition duration-300 active:scale-90 disabled:pointer-events-none disabled:opacity-30 ${
              added ? 'bg-volt text-volt-ink' : 'bg-ink text-bg hover:scale-110'
            }`}
            aria-label={`Add ${p.name} to bag`}
          >
            <Icon name={added ? 'check' : 'plus'} className="size-4.5" strokeWidth={2.4} />
          </button>
        </div>
      </div>
    </article>
  )
}

export function ProductSkeleton() {
  return (
    <div aria-hidden="true">
      <div className="skeleton aspect-4/5 rounded-3xl" />
      <div className="skeleton mt-3 h-3 w-1/3 rounded-full" />
      <div className="skeleton mt-2 h-4 w-4/5 rounded-full" />
      <div className="skeleton mt-3 h-6 w-1/3 rounded-full" />
    </div>
  )
}
