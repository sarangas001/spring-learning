import { useMemo } from 'react'
import { useStore } from '../store/store'
import { money, stockInfo } from '../lib/format'
import { scrollToCatalog } from '../router'
import Icon from './Icon'
import ProductImage from './ProductImage'

const SLOTS = [
  'left-[2%] top-[10%] w-[46%] -rotate-6',
  'right-[0%] top-[0%] w-[44%] rotate-6',
  'left-[26%] bottom-[2%] w-[46%] rotate-2',
]

export default function Hero() {
  const { catalog } = useStore()
  const items = catalog.items

  const featured = useMemo(() => {
    const withImage = items.filter((p) => p.image)
    const inStock = withImage.filter((p) => stockInfo(p).max > 0)
    return (inStock.length >= 3 ? inStock : withImage).slice(0, 3)
  }, [items])

  const stats = useMemo(
    () => [
      { label: 'Products', value: items.length },
      { label: 'Brands', value: new Set(items.map((p) => p.brand).filter(Boolean)).size },
      { label: 'Categories', value: new Set(items.map((p) => p.category).filter(Boolean)).size },
    ],
    [items],
  )

  const categories = useMemo(
    () => [...new Set(items.map((p) => p.category).filter(Boolean))],
    [items],
  )

  return (
    <>
      <section className="relative overflow-hidden">
        {/* ambient colour */}
        <div className="pointer-events-none absolute -top-40 -right-32 size-[34rem] rounded-full bg-volt/40 blur-3xl dark:bg-volt/20" />
        <div className="pointer-events-none absolute top-40 -left-40 size-[28rem] rounded-full bg-coral/20 blur-3xl dark:bg-coral/10" />

        <div className="relative mx-auto grid max-w-7xl items-center gap-10 px-4 pt-10 pb-16 sm:px-6 lg:grid-cols-[1.1fr_0.9fr] lg:pt-16 lg:pb-24">
          <div>
            <span className="inline-flex animate-fade-up items-center gap-2 rounded-full border border-line bg-surface px-3.5 py-1.5 text-xs font-semibold">
              <span className="size-2 animate-pulse-dot rounded-full bg-volt ring-1 ring-volt-ink/20" />
              Live stock · Fresh arrivals weekly
            </span>

            <h1
              className="mt-6 animate-fade-up font-display text-[2.9rem] leading-[0.95] font-extrabold tracking-tighter sm:text-7xl lg:text-[5.4rem]"
              style={{ animationDelay: '80ms' }}
            >
              Gear that makes{' '}
              <span className="relative inline-block">
                <span className="absolute -inset-x-2 inset-y-1 -z-10 -rotate-1 rounded-2xl bg-volt" />
                <span className="px-1 text-volt-ink">every day</span>
              </span>{' '}
              feel brand new.
            </h1>

            <p
              className="mt-6 max-w-lg animate-fade-up text-lg text-muted"
              style={{ animationDelay: '160ms' }}
            >
              Hand-picked tech and lifestyle essentials from brands we trust. Find it, bag it, and
              we&apos;ll confirm your order in seconds.
            </p>

            <div
              className="mt-8 flex animate-fade-up flex-wrap gap-3"
              style={{ animationDelay: '240ms' }}
            >
              <button className="btn btn-primary h-14 px-7 text-base" onClick={scrollToCatalog}>
                Shop the collection <Icon name="arrow-right" className="size-5" />
              </button>
              <a href="#/orders" className="btn btn-ghost h-14 px-7 text-base">
                Track an order
              </a>
            </div>

            {items.length > 0 && (
              <dl
                className="mt-10 flex animate-fade-up gap-8 sm:gap-12"
                style={{ animationDelay: '320ms' }}
              >
                {stats.map((s) => (
                  <div key={s.label}>
                    <dd className="font-display text-3xl font-extrabold tracking-tight">{s.value}</dd>
                    <dt className="text-sm text-muted">{s.label}</dt>
                  </div>
                ))}
              </dl>
            )}
          </div>

          {/* product stage */}
          <div className="relative mx-auto aspect-square w-full max-w-md lg:max-w-none">
            <div className="absolute inset-[8%] animate-pop rounded-full bg-volt" />
            <svg
              viewBox="0 0 100 100"
              className="absolute inset-0 size-full text-ink/15"
              fill="none"
              stroke="currentColor"
              strokeWidth="0.25"
              aria-hidden="true"
            >
              <circle cx="50" cy="50" r="49" strokeDasharray="1 2" />
              <circle cx="50" cy="50" r="40" />
            </svg>

            {featured.length === 0 ? (
              <div className="absolute inset-0 grid place-items-center text-volt-ink/70">
                <Icon name="bag" className="size-24" strokeWidth={1} />
              </div>
            ) : (
              featured.map((p, i) => (
                <a
                  key={p.id}
                  href={`#/product/${p.id}`}
                  className={`group absolute ${SLOTS[i]} animate-float`}
                  style={{ animationDelay: `${i * -2}s` }}
                >
                  <div className="overflow-hidden rounded-3xl bg-stage p-2 shadow-[0_24px_50px_-20px_rgba(0,0,0,0.45)] transition duration-300 group-hover:-translate-y-1 group-hover:rotate-0 group-hover:scale-105">
                    <div className="aspect-square overflow-hidden rounded-2xl">
                      <ProductImage
                        product={p}
                        className="size-full object-contain p-3 transition duration-500 group-hover:scale-110"
                      />
                    </div>
                    <div className="flex items-center justify-between gap-2 px-2 pt-2 pb-1 text-neutral-900">
                      <span className="truncate text-xs font-semibold">{p.name}</span>
                      <span className="shrink-0 text-xs font-bold">{money(p.price)}</span>
                    </div>
                  </div>
                </a>
              ))
            )}
          </div>
        </div>
      </section>

      {categories.length > 0 && (
        <div className="overflow-hidden border-y border-ink bg-ink py-3.5 text-bg" aria-hidden="true">
          <div className="flex w-max animate-marquee gap-10 pr-10 font-display text-lg font-bold tracking-tight">
            {[...categories, ...categories, ...categories, ...categories].map((c, i) => (
              <span key={i} className="flex items-center gap-10 whitespace-nowrap">
                {c}
                <Icon name="star" fill className="size-3.5 text-volt" />
              </span>
            ))}
          </div>
        </div>
      )}
    </>
  )
}
