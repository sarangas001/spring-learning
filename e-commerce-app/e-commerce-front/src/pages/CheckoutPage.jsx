import { useState } from 'react'
import { useStore } from '../store/store'
import { placeOrder } from '../api'
import { formatDate, money, parseOrderDate } from '../lib/format'
import { scrollToCatalog } from '../router'
import Icon from '../components/Icon'
import ProductImage from '../components/ProductImage'

const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/

const readSavedEmail = () => {
  try {
    return localStorage.getItem('orbit-email') ?? ''
  } catch {
    return ''
  }
}

// Deterministic confetti so render stays pure.
const CONFETTI = Array.from({ length: 32 }, (_, i) => ({
  left: (i * 37) % 100,
  x: ((i * 53) % 160) - 80,
  r: ((i * 97) % 720) - 360,
  delay: (i % 8) * 0.07,
  color: ['bg-volt', 'bg-coral', 'bg-ink', 'bg-stage'][i % 4],
  size: 6 + (i % 4) * 2,
}))

function Field({ label, icon, error, children }) {
  return (
    <label className="block">
      <span className="mb-1.5 flex items-center gap-2 text-sm font-semibold">
        <Icon name={icon} className="size-4 text-muted" /> {label}
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

function Success({ order }) {
  const total = order.items.reduce((n, i) => n + Number(i.totalPrice), 0)
  const date = parseOrderDate(order.orderDate)
  return (
    <div className="relative mx-auto max-w-xl overflow-hidden px-4 py-16 text-center sm:px-6">
      <div className="pointer-events-none absolute inset-x-0 top-0 h-full" aria-hidden="true">
        {CONFETTI.map((c, i) => (
          <span
            key={i}
            className={`absolute top-0 animate-confetti rounded-[2px] ${c.color}`}
            style={{
              left: `${c.left}%`,
              width: c.size,
              height: c.size * 1.6,
              animationDelay: `${c.delay}s`,
              '--x': `${c.x}px`,
              '--r': `${c.r}deg`,
            }}
          />
        ))}
      </div>

      <div className="relative mx-auto grid size-24 animate-pop place-items-center rounded-full bg-volt text-volt-ink">
        <svg viewBox="0 0 24 24" className="size-12" fill="none" stroke="currentColor" strokeWidth="2.6" strokeLinecap="round" strokeLinejoin="round">
          <path d="M20 6 9 17l-5-5" strokeDasharray="24" strokeDashoffset="24" className="animate-draw" />
        </svg>
      </div>

      <h1 className="mt-8 animate-fade-up font-display text-5xl font-extrabold tracking-tight">
        Order confirmed!
      </h1>
      <p className="mt-3 animate-fade-up text-lg text-muted" style={{ animationDelay: '100ms' }}>
        Thanks, {order.customerName.split(' ')[0]}. We&apos;ve got your order and a confirmation is on
        its way to <strong className="text-ink">{order.email}</strong>.
      </p>

      <div
        className="mt-8 animate-fade-up rounded-3xl border border-line bg-surface p-6 text-left"
        style={{ animationDelay: '200ms' }}
      >
        <div className="flex flex-wrap items-center justify-between gap-2 border-b border-line pb-4">
          <div>
            <p className="text-xs font-bold tracking-widest text-muted uppercase">Order number</p>
            <p className="font-mono text-xl font-bold">{order.orderId}</p>
          </div>
          <div className="text-right">
            <p className="text-xs font-bold tracking-widest text-muted uppercase">Placed</p>
            <p className="font-semibold">{formatDate(date)}</p>
          </div>
        </div>
        <ul className="divide-y divide-line">
          {order.items.map((i, idx) => (
            <li key={idx} className="flex items-center justify-between gap-4 py-3">
              <span className="min-w-0 truncate">
                <span className="font-semibold">{i.productName}</span>{' '}
                <span className="text-muted">× {i.quantity}</span>
              </span>
              <span className="shrink-0 font-semibold">{money(i.totalPrice)}</span>
            </li>
          ))}
        </ul>
        <div className="flex items-baseline justify-between border-t border-line pt-4">
          <span className="text-muted">Total</span>
          <span className="font-display text-3xl font-extrabold">{money(total)}</span>
        </div>
      </div>

      <div className="mt-8 flex flex-wrap justify-center gap-3">
        <button className="btn btn-primary h-12 px-6" onClick={scrollToCatalog}>
          Keep shopping
        </button>
        <a href="#/orders" className="btn btn-ghost h-12 px-6">
          View my orders
        </a>
      </div>
    </div>
  )
}

export default function CheckoutPage() {
  const { lines, subtotal, count, clearCart, refresh, setQty, removeFromCart } = useStore()
  const [form, setForm] = useState(() => ({ name: '', email: readSavedEmail() }))
  const [touched, setTouched] = useState({})
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)
  const [placed, setPlaced] = useState(null)

  if (placed) return <Success order={placed} />

  const errors = {
    name: form.name.trim().length < 2 ? 'Please enter your full name' : '',
    email: EMAIL_RE.test(form.email.trim()) ? '' : 'Enter a valid email address',
  }
  const shown = (k) => (touched[k] ? errors[k] : '')

  const onSubmit = async (e) => {
    e.preventDefault()
    setTouched({ name: true, email: true })
    if (errors.name || errors.email || lines.length === 0) return

    setSubmitting(true)
    setError(null)
    try {
      const order = await placeOrder({
        customerName: form.name.trim(),
        email: form.email.trim(),
        items: lines.map((l) => ({ productId: l.product.id, quantity: l.qty })),
      })
      try {
        localStorage.setItem('orbit-email', form.email.trim())
      } catch {
        /* ignore */
      }
      setPlaced(order)
      clearCart()
      window.scrollTo({ top: 0 })
    } catch (err) {
      setError(err.message)
    } finally {
      refresh() // pick up new stock levels either way
      setSubmitting(false)
    }
  }

  if (lines.length === 0) {
    return (
      <div className="mx-auto max-w-md animate-fade-up px-4 py-24 text-center">
        <div className="mx-auto grid size-24 place-items-center rounded-full bg-surface-2">
          <Icon name="bag" className="size-10 text-muted" strokeWidth={1.4} />
        </div>
        <h1 className="mt-6 font-display text-4xl font-extrabold tracking-tight">Your bag is empty</h1>
        <p className="mt-2 text-muted">Add something you love and come back to check out.</p>
        <button className="btn btn-volt mt-8 h-12 px-6" onClick={scrollToCatalog}>
          Browse products <Icon name="arrow-right" className="size-4" />
        </button>
      </div>
    )
  }

  return (
    <div className="mx-auto max-w-6xl px-4 pt-8 sm:px-6">
      <a href="#/" className="inline-flex items-center gap-1.5 text-sm font-medium text-muted hover:text-ink">
        <Icon name="arrow-left" className="size-4" /> Continue shopping
      </a>
      <h1 className="mt-3 font-display text-5xl font-extrabold tracking-tight">Checkout</h1>

      <div className="mt-8 grid gap-8 lg:grid-cols-[1fr_26rem]">
        <form onSubmit={onSubmit} noValidate className="space-y-6">
          <section className="rounded-3xl border border-line bg-surface p-6 sm:p-8">
            <div className="mb-6 flex items-center gap-3">
              <span className="grid size-8 place-items-center rounded-full bg-ink text-sm font-bold text-bg">1</span>
              <h2 className="font-display text-2xl font-bold">Your details</h2>
            </div>
            <div className="grid gap-5 sm:grid-cols-2">
              <Field label="Full name" icon="user" error={shown('name')}>
                <input
                  className="input"
                  autoComplete="name"
                  placeholder="Alex Johnson"
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                  onBlur={() => setTouched((t) => ({ ...t, name: true }))}
                  aria-invalid={Boolean(shown('name'))}
                />
              </Field>
              <Field label="Email" icon="mail" error={shown('email')}>
                <input
                  className="input"
                  type="email"
                  autoComplete="email"
                  placeholder="alex@example.com"
                  value={form.email}
                  onChange={(e) => setForm({ ...form, email: e.target.value })}
                  onBlur={() => setTouched((t) => ({ ...t, email: true }))}
                  aria-invalid={Boolean(shown('email'))}
                />
              </Field>
            </div>
            <p className="mt-5 flex items-center gap-2 text-sm text-muted">
              <Icon name="lock" className="size-4" /> We only use your details to confirm and look up
              this order.
            </p>
          </section>

          {error && (
            <div
              className="flex animate-fade-up items-start gap-3 rounded-2xl border border-coral/40 bg-coral/10 p-4 text-sm"
              role="alert"
            >
              <Icon name="alert" className="mt-0.5 size-5 shrink-0 text-coral" />
              <div>
                <p className="font-semibold text-coral">We couldn&apos;t place your order</p>
                <p className="mt-0.5">{error}</p>
              </div>
            </div>
          )}

          <button type="submit" className="btn btn-volt h-14 w-full text-base" disabled={submitting}>
            {submitting ? (
              <>
                <span className="size-5 animate-spin rounded-full border-2 border-volt-ink/30 border-t-volt-ink" />
                Placing your order…
              </>
            ) : (
              <>
                Place order · {money(subtotal)} <Icon name="arrow-right" className="size-5" />
              </>
            )}
          </button>
        </form>

        {/* summary */}
        <aside className="lg:sticky lg:top-28 lg:self-start">
          <div className="rounded-3xl border border-line bg-surface p-6">
            <h2 className="font-display text-xl font-bold">
              Order summary <span className="text-muted">({count})</span>
            </h2>
            <ul className="mt-4 divide-y divide-line">
              {lines.map(({ product: p, qty, clamped, lineTotal }) => (
                <li key={p.id} className="flex gap-3 py-4">
                  <div className="relative size-16 shrink-0 overflow-hidden rounded-xl bg-stage">
                    <ProductImage product={p} className="size-full object-contain p-1.5" />
                    <span className="absolute -top-0 -right-0 grid min-w-5 place-items-center rounded-bl-lg bg-ink px-1 text-[11px] font-bold text-bg">
                      {qty}
                    </span>
                  </div>
                  <div className="min-w-0 flex-1">
                    <p className="line-clamp-2 text-sm leading-snug font-semibold">{p.name}</p>
                    <p className="text-xs text-muted">{money(p.price)} each</p>
                    {clamped && (
                      <p className="text-xs font-medium text-coral">Quantity adjusted to available stock</p>
                    )}
                    <div className="mt-1 flex gap-3 text-xs font-medium text-muted">
                      <button type="button" className="cursor-pointer hover:text-ink" onClick={() => setQty(p, qty - 1)}>
                        − One
                      </button>
                      <button type="button" className="cursor-pointer hover:text-coral" onClick={() => removeFromCart(p)}>
                        Remove
                      </button>
                    </div>
                  </div>
                  <span className="text-sm font-bold">{money(lineTotal)}</span>
                </li>
              ))}
            </ul>
            <div className="mt-2 flex items-baseline justify-between border-t border-line pt-4">
              <span className="font-semibold">Total</span>
              <span className="font-display text-3xl font-extrabold tracking-tight">{money(subtotal)}</span>
            </div>
          </div>
        </aside>
      </div>
    </div>
  )
}
