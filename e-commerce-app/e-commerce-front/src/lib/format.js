const CURRENCY = import.meta.env.VITE_CURRENCY || 'USD'

const currency = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: CURRENCY,
  maximumFractionDigits: 2,
})

export const money = (n) => currency.format(Number(n) || 0)

const compact = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: CURRENCY,
  notation: 'compact',
  maximumFractionDigits: 1,
})

/** "$96.1K" — for big overview numbers where exact cents don't matter. */
export const moneyCompact = (n) => compact.format(Number(n) || 0)

/** Backend sends Product.releaseDate as "dd-MM-yyyy". */
export function parseRelease(value) {
  if (!value) return null
  const m = /^(\d{2})-(\d{2})-(\d{4})$/.exec(value)
  if (m) return new Date(Number(m[3]), Number(m[2]) - 1, Number(m[1]))
  const d = new Date(value)
  return Number.isNaN(d.getTime()) ? null : d
}

/** Backend LocalDate can arrive as "YYYY-MM-DD" or as [y, m, d]. */
export function parseOrderDate(value) {
  if (Array.isArray(value)) return new Date(value[0], value[1] - 1, value[2])
  if (typeof value === 'string') {
    const m = /^(\d{4})-(\d{2})-(\d{2})/.exec(value)
    if (m) return new Date(Number(m[1]), Number(m[2]) - 1, Number(m[3]))
  }
  return null
}

export const formatDate = (d) =>
  d
    ? d.toLocaleDateString(undefined, { day: 'numeric', month: 'short', year: 'numeric' })
    : '—'

/** Normalised stock state used across the whole UI. */
export function stockInfo(product) {
  const n = product?.stockQuantity ?? 0
  if (product?.productAvailable === false || n <= 0) {
    return { key: 'out', label: 'Out of stock', max: 0 }
  }
  if (n <= 5) return { key: 'low', label: `Only ${n} left`, max: n }
  return { key: 'in', label: 'In stock', max: n }
}
