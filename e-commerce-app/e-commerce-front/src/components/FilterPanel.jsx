import { money } from '../lib/format'

function Section({ title, children }) {
  return (
    <fieldset className="border-t border-line py-5 first:border-t-0 first:pt-0">
      <legend className="mb-3 text-xs font-bold tracking-widest text-muted uppercase">{title}</legend>
      {children}
    </fieldset>
  )
}

export default function FilterPanel({
  brands,
  selectedBrands,
  onToggleBrand,
  priceCeil,
  maxPrice,
  onMaxPrice,
  inStock,
  onInStock,
  onClear,
  activeCount,
}) {
  return (
    <div className="rounded-3xl border border-line bg-surface p-5">
      <div className="mb-5 flex items-center justify-between">
        <h3 className="font-display text-lg font-bold">Filters</h3>
        {activeCount > 0 && (
          <button
            onClick={onClear}
            className="cursor-pointer text-sm font-semibold text-coral hover:underline"
          >
            Clear all
          </button>
        )}
      </div>

      <Section title="Availability">
        <label className="flex cursor-pointer items-center justify-between gap-3 text-sm font-medium">
          In stock only
          <span className="relative">
            <input
              type="checkbox"
              checked={inStock}
              onChange={(e) => onInStock(e.target.checked)}
              className="peer sr-only"
            />
            <span className="block h-6 w-11 rounded-full bg-surface-2 ring-1 ring-line transition peer-checked:bg-volt peer-focus-visible:ring-2 peer-focus-visible:ring-ink" />
            <span className="absolute top-0.5 left-0.5 size-5 rounded-full bg-ink transition peer-checked:translate-x-5 peer-checked:bg-volt-ink" />
          </span>
        </label>
      </Section>

      {priceCeil > 0 && (
        <Section title="Max price">
          <input
            type="range"
            min={0}
            max={priceCeil}
            step={Math.max(1, Math.round(priceCeil / 100))}
            value={maxPrice}
            onChange={(e) => onMaxPrice(Number(e.target.value))}
            className="w-full"
            aria-label="Maximum price"
          />
          <div className="mt-1 flex justify-between text-sm">
            <span className="text-muted">{money(0)}</span>
            <span className="font-bold">{money(maxPrice)}</span>
          </div>
        </Section>
      )}

      {brands.length > 0 && (
        <Section title="Brand">
          <div className="flex flex-wrap gap-2">
            {brands.map((b) => (
              <button
                key={b}
                className="chip h-8 px-3 text-xs"
                aria-pressed={selectedBrands.includes(b)}
                onClick={() => onToggleBrand(b)}
              >
                {b}
              </button>
            ))}
          </div>
        </Section>
      )}
    </div>
  )
}
