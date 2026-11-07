import { useEffect, useMemo, useState } from 'react'
import { getOrders } from '../api'
import { formatDate, money, parseOrderDate } from '../lib/format'
import { scrollToCatalog } from '../router'
import Icon from '../components/Icon'
import ErrorState from '../components/ErrorState'

const STEPS = ['Placed', 'Processing', 'Shipped', 'Delivered']

const readSavedEmail = () => {
  try {
    return localStorage.getItem('orbit-email') ?? ''
  } catch {
    return ''
  }
}

function Timeline({ status }) {
  const current = Math.max(0, STEPS.findIndex((s) => s.toLowerCase() === String(status).toLowerCase()))
  return (
    <ol className="flex items-center" aria-label={`Order status: ${status}`}>
      {STEPS.map((s, i) => (
        <li key={s} className={`flex items-center ${i < STEPS.length - 1 ? 'flex-1' : ''}`}>
          <span className="flex flex-col items-center gap-1.5">
            <span
              className={`grid size-7 place-items-center rounded-full text-xs font-bold ${
                i <= current ? 'bg-volt text-volt-ink' : 'bg-surface-2 text-muted'
              }`}
            >
              {i < current ? <Icon name="check" className="size-4" strokeWidth={3} /> : i + 1}
            </span>
            <span className={`text-[11px] font-semibold ${i <= current ? '' : 'text-muted'}`}>{s}</span>
          </span>
          {i < STEPS.length - 1 && (
            <span className={`mx-2 mb-5 h-0.5 flex-1 rounded-full ${i < current ? 'bg-volt' : 'bg-line'}`} />
          )}
        </li>
      ))}
    </ol>
  )
}

export default function OrdersPage() {
  const [state, setState] = useState({ status: 'loading', items: [], error: null })
  const [input, setInput] = useState(readSavedEmail)
  const [email, setEmail] = useState(readSavedEmail)

  useEffect(() => {
    const ac = new AbortController()
    getOrders(ac.signal)
      .then((items) => setState({ status: 'ready', items: items ?? [], error: null }))
      .catch((err) => {
        if (err.name !== 'AbortError') setState({ status: 'error', items: [], error: err.message })
      })
    return () => ac.abort()
  }, [])

  const retry = () => {
    setState({ status: 'loading', items: [], error: null })
    getOrders()
      .then((items) => setState({ status: 'ready', items: items ?? [], error: null }))
      .catch((err) => setState({ status: 'error', items: [], error: err.message }))
  }

  const mine = useMemo(() => {
    const target = email.trim().toLowerCase()
    if (!target) return []
    return state.items
      .map((o, idx) => ({ ...o, _idx: idx, _date: parseOrderDate(o.orderDate) }))
      .filter((o) => o.email?.toLowerCase() === target)
      .sort((a, b) => (b._date?.getTime() ?? 0) - (a._date?.getTime() ?? 0) || b._idx - a._idx)
  }, [state.items, email])

  const onSubmit = (e) => {
    e.preventDefault()
    setEmail(input)
    try {
      localStorage.setItem('orbit-email', input.trim())
    } catch {
      /* ignore */
    }
  }

  return (
    <div className="mx-auto max-w-3xl px-4 pt-10 sm:px-6">
      <h1 className="font-display text-5xl font-extrabold tracking-tight">My orders</h1>
      <p className="mt-2 text-muted">Enter the email you checked out with to see your order history.</p>

      <form onSubmit={onSubmit} className="mt-6 flex flex-col gap-3 sm:flex-row">
        <div className="relative flex-1">
          <Icon name="mail" className="pointer-events-none absolute top-1/2 left-4 size-4 -translate-y-1/2 text-muted" />
          <input
            type="email"
            required
            className="input pl-11"
            placeholder="you@example.com"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            aria-label="Email address"
          />
        </div>
        <button className="btn btn-primary h-12 px-7" type="submit">
          Find my orders
        </button>
      </form>

      <div className="mt-10">
        {state.status === 'loading' ? (
          <div className="space-y-4">
            {[0, 1].map((i) => (
              <div key={i} className="skeleton h-56 rounded-3xl" />
            ))}
          </div>
        ) : state.status === 'error' ? (
          <ErrorState title="Couldn't load orders" message={state.error} onRetry={retry} />
        ) : !email.trim() ? (
          <div className="rounded-3xl border border-dashed border-line py-16 text-center">
            <Icon name="receipt" className="mx-auto size-12 text-muted" strokeWidth={1.2} />
            <p className="mt-3 font-semibold">Your orders will show up here</p>
          </div>
        ) : mine.length === 0 ? (
          <div className="animate-fade-up py-12 text-center">
            <Icon name="package" className="mx-auto size-14 text-muted" strokeWidth={1.2} />
            <h2 className="mt-4 font-display text-2xl font-bold">No orders found</h2>
            <p className="mt-1 text-muted">
              Nothing for <strong className="text-ink">{email.trim()}</strong>. Double-check the spelling,
              or start a new order.
            </p>
            <button className="btn btn-volt mt-6" onClick={scrollToCatalog}>
              Start shopping
            </button>
          </div>
        ) : (
          <ul className="space-y-5">
            {mine.map((o, idx) => {
              const total = o.items.reduce((n, i) => n + Number(i.totalPrice), 0)
              return (
                <li
                  key={o.orderId}
                  className="animate-fade-up rounded-3xl border border-line bg-surface p-6"
                  style={{ animationDelay: `${Math.min(idx, 6) * 60}ms` }}
                >
                  <div className="flex flex-wrap items-start justify-between gap-3">
                    <div>
                      <p className="font-mono text-lg font-bold">{o.orderId}</p>
                      <p className="text-sm text-muted">Placed {formatDate(o._date)}</p>
                    </div>
                    <span className="rounded-full bg-volt px-3 py-1 text-xs font-bold tracking-wide text-volt-ink uppercase">
                      {o.status}
                    </span>
                  </div>

                  <div className="my-6">
                    <Timeline status={o.status} />
                  </div>

                  <ul className="divide-y divide-line border-t border-line">
                    {o.items.map((i, k) => (
                      <li key={k} className="flex items-center justify-between gap-4 py-3 text-sm">
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
                    <span className="font-display text-2xl font-extrabold">{money(total)}</span>
                  </div>
                </li>
              )
            })}
          </ul>
        )}
      </div>
    </div>
  )
}
