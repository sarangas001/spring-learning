import { useMemo, useState } from 'react'
import { useStore } from '../store/store'
import { stockInfo } from '../lib/format'
import Hero from '../components/Hero'
import FilterPanel from '../components/FilterPanel'
import ProductCard, { ProductSkeleton } from '../components/ProductCard'
import ErrorState from '../components/ErrorState'
import Icon from '../components/Icon'

const SORTS = [
  { value: 'featured', label: 'Featured' },
  { value: 'newest', label: 'Newest' },
  { value: 'price-asc', label: 'Price: low to high' },
  { value: 'price-desc', label: 'Price: high to low' },
  { value: 'name', label: 'Name A–Z' },
]

const matches = (p, q) =>
  [p.name, p.brand, p.category, p.description].some((s) => s?.toLowerCase().includes(q))

export default function ShopPage() {
  const { catalog, reload, query, setQuery, savedOnly, setSavedOnly, wishlist } = useStore()
  const [category, setCategory] = useState('All')
  const [brands, setBrands] = useState([])
  const [maxPrice, setMaxPrice] = useState(null)
  const [inStock, setInStock] = useState(false)
  const [sort, setSort] = useState('featured')
  const [filtersOpen, setFiltersOpen] = useState(false)

  const items = catalog.items

  const categories = useMemo(
    () => ['All', ...[...new Set(items.map((p) => p.category).filter(Boolean))].sort()],
    [items],
  )
  const allBrands = useMemo(
    () => [...new Set(items.map((p) => p.brand).filter(Boolean))].sort(),
    [items],
  )
  const priceCeil = useMemo(() => Math.ceil(Math.max(0, ...items.map((p) => p.price))), [items])
  const priceLimit = maxPrice ?? priceCeil

  const visible = useMemo(() => {
    const q = query.trim().toLowerCase()
    const list = items.filter(
      (p) =>
        (category === 'All' || p.category === category) &&
        (brands.length === 0 || brands.includes(p.brand)) &&
        p.price <= priceLimit &&
        (!inStock || stockInfo(p).max > 0) &&
        (!savedOnly || wishlist.includes(p.id)) &&
        (!q || matches(p, q)),
    )
    switch (sort) {
      case 'newest':
        return list.sort((a, b) => (b.releaseDate?.getTime() ?? 0) - (a.releaseDate?.getTime() ?? 0))
      case 'price-asc':
        return list.sort((a, b) => a.price - b.price)
      case 'price-desc':
        return list.sort((a, b) => b.price - a.price)
      case 'name':
        return list.sort((a, b) => a.name.localeCompare(b.name))
      default:
        return list
    }
  }, [items, category, brands, priceLimit, inStock, savedOnly, wishlist, query, sort])

  const activeCount =
    brands.length + (inStock ? 1 : 0) + (maxPrice !== null && maxPrice < priceCeil ? 1 : 0)

  const clearFilters = () => {
    setBrands([])
    setMaxPrice(null)
    setInStock(false)
    setCategory('All')
    setQuery('')
    setSavedOnly(false)
  }

  const toggleBrand = (b) =>
    setBrands((cur) => (cur.includes(b) ? cur.filter((x) => x !== b) : [...cur, b]))

  const title = savedOnly
    ? 'Saved items'
    : query.trim()
      ? `Results for “${query.trim()}”`
      : category === 'All'
        ? 'All products'
        : category

  const loading = catalog.status === 'loading'

  return (
    <>
      <Hero />

      <section id="catalog" className="mx-auto max-w-7xl scroll-mt-24 px-4 pt-14 sm:px-6">
        <div className="flex flex-wrap items-end justify-between gap-4">
          <div>
            <h2 className="font-display text-4xl font-extrabold tracking-tight sm:text-5xl">{title}</h2>
            {catalog.status === 'ready' && (
              <p className="mt-1 text-muted">
                {visible.length} {visible.length === 1 ? 'product' : 'products'}
              </p>
            )}
          </div>

          <div className="flex items-center gap-2">
            <button
              className="btn btn-ghost h-10 lg:hidden"
              onClick={() => setFiltersOpen((o) => !o)}
              aria-expanded={filtersOpen}
            >
              <Icon name="sliders" className="size-4" />
              Filters
              {activeCount > 0 && (
                <span className="grid size-5 place-items-center rounded-full bg-volt text-xs font-bold text-volt-ink">
                  {activeCount}
                </span>
              )}
            </button>
            <label className="relative">
              <span className="sr-only">Sort products</span>
              <select
                value={sort}
                onChange={(e) => setSort(e.target.value)}
                className="h-10 cursor-pointer appearance-none rounded-full border border-line bg-surface pr-9 pl-4 text-sm font-medium outline-none focus:border-ink focus:ring-4 focus:ring-volt/50"
              >
                {SORTS.map((s) => (
                  <option key={s.value} value={s.value}>
                    {s.label}
                  </option>
                ))}
              </select>
              <Icon
                name="chevron"
                className="pointer-events-none absolute top-1/2 right-3 size-4 -translate-y-1/2"
              />
            </label>
          </div>
        </div>

        {categories.length > 2 && (
          <div
            className="no-scrollbar -mx-4 mt-6 flex gap-2 overflow-x-auto px-4 pb-1 sm:mx-0 sm:px-0"
            role="group"
            aria-label="Categories"
          >
            {categories.map((c) => (
              <button key={c} className="chip" aria-pressed={category === c} onClick={() => setCategory(c)}>
                {c}
              </button>
            ))}
          </div>
        )}

        <div className="mt-8 grid gap-8 lg:grid-cols-[16rem_1fr]">
          <aside className={`${filtersOpen ? 'block' : 'hidden'} animate-fade-in lg:block`}>
            <div className="lg:sticky lg:top-28">
              <FilterPanel
                brands={allBrands}
                selectedBrands={brands}
                onToggleBrand={toggleBrand}
                priceCeil={priceCeil}
                maxPrice={priceLimit}
                onMaxPrice={setMaxPrice}
                inStock={inStock}
                onInStock={setInStock}
                onClear={clearFilters}
                activeCount={activeCount}
              />
            </div>
          </aside>

          <div>
            {catalog.status === 'error' ? (
              <ErrorState title="The shelves are out of reach" message={catalog.error} onRetry={reload} />
            ) : loading ? (
              <div className="grid grid-cols-2 gap-x-3 gap-y-8 sm:gap-x-5 md:grid-cols-3 xl:grid-cols-4">
                {Array.from({ length: 8 }, (_, i) => (
                  <ProductSkeleton key={i} />
                ))}
              </div>
            ) : items.length === 0 ? (
              <div className="py-20 text-center">
                <Icon name="package" className="mx-auto size-14 text-muted" strokeWidth={1.2} />
                <h3 className="mt-4 font-display text-2xl font-bold">No products yet</h3>
                <p className="mt-1 text-muted">Add your first product and it&apos;ll appear here.</p>
                <a href="#/admin/new" className="btn btn-volt mt-6 h-12 px-6">
                  <Icon name="plus" className="size-5" strokeWidth={2.4} /> Add a product
                </a>
              </div>
            ) : visible.length === 0 ? (
              <div className="animate-fade-up py-20 text-center">
                <div className="mx-auto grid size-20 place-items-center rounded-full bg-surface-2">
                  <Icon name={savedOnly ? 'heart' : 'search'} className="size-9 text-muted" strokeWidth={1.4} />
                </div>
                <h3 className="mt-5 font-display text-2xl font-bold">
                  {savedOnly && wishlist.length === 0 ? 'Nothing saved yet' : 'No matches found'}
                </h3>
                <p className="mt-1 text-muted">
                  {savedOnly && wishlist.length === 0
                    ? 'Tap the heart on any product to keep it here.'
                    : 'Try a different search or loosen your filters.'}
                </p>
                <button className="btn btn-primary mt-6" onClick={clearFilters}>
                  Reset everything
                </button>
              </div>
            ) : (
              <div className="grid grid-cols-2 gap-x-3 gap-y-8 sm:gap-x-5 md:grid-cols-3 xl:grid-cols-4">
                {visible.map((p, i) => (
                  <ProductCard key={p.id} product={p} index={i} />
                ))}
              </div>
            )}
          </div>
        </div>
      </section>
    </>
  )
}
