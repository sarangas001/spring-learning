import { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { StoreContext } from './store'
import { getProducts } from '../api'
import { stockInfo } from '../lib/format'
import usePersisted from '../hooks/usePersisted'

export default function StoreProvider({ children }) {
  /* ---------- catalogue ---------- */
  const [catalog, setCatalog] = useState({ status: 'loading', items: [], error: null })

  const load = useCallback(
    (signal) =>
      getProducts(signal)
        .then((items) => setCatalog({ status: 'ready', items, error: null }))
        .catch((err) => {
          if (err.name === 'AbortError') return
          // A failed background refresh shouldn't wipe a catalogue we already have.
          setCatalog((c) => (c.items.length ? c : { ...c, status: 'error', error: err.message }))
        }),
    [],
  )

  useEffect(() => {
    const ac = new AbortController()
    load(ac.signal)
    return () => ac.abort()
  }, [load])

  /** Retry with a visible loading state. */
  const reload = useCallback(() => {
    setCatalog((c) => ({ ...c, status: 'loading', error: null }))
    return load()
  }, [load])

  /** Silent refresh (e.g. to pick up new stock levels after an order). */
  const refresh = useCallback(() => load(), [load])

  /* ---------- toasts ---------- */
  const [toasts, setToasts] = useState([])
  const nextToastId = useRef(1)
  const dismissToast = useCallback((id) => setToasts((t) => t.filter((x) => x.id !== id)), [])
  const toast = useCallback(
    (message, kind = 'info') => {
      const id = nextToastId.current++
      setToasts((t) => [...t.slice(-2), { id, message, kind }])
      setTimeout(() => dismissToast(id), 3400)
    },
    [dismissToast],
  )

  /* ---------- theme ---------- */
  const [theme, setTheme] = useState(() =>
    document.documentElement.classList.contains('dark') ? 'dark' : 'light',
  )
  useEffect(() => {
    document.documentElement.classList.toggle('dark', theme === 'dark')
    try {
      localStorage.setItem('orbit-theme', theme)
    } catch {
      /* ignore */
    }
  }, [theme])
  const toggleTheme = useCallback(() => setTheme((t) => (t === 'dark' ? 'light' : 'dark')), [])

  /* ---------- search / saved filter ---------- */
  const [query, setQuery] = useState('')
  const [savedOnly, setSavedOnly] = useState(false)

  /* ---------- wishlist ---------- */
  const [wishlist, setWishlist] = usePersisted('orbit-wishlist', [])
  const toggleWish = useCallback(
    (product) => {
      const has = wishlist.includes(product.id)
      setWishlist(has ? wishlist.filter((id) => id !== product.id) : [...wishlist, product.id])
      toast(has ? 'Removed from saved' : 'Saved for later', 'info')
    },
    [wishlist, setWishlist, toast],
  )

  /* ---------- cart ---------- */
  const [cart, setCart] = usePersisted('orbit-cart', []) // [{ id, qty }]
  const [cartOpen, setCartOpen] = useState(false)

  const byId = useMemo(() => new Map(catalog.items.map((p) => [p.id, p])), [catalog.items])

  // Join cart entries with live product data and clamp to current stock.
  const lines = useMemo(
    () =>
      cart
        .map((entry) => {
          const product = byId.get(entry.id)
          if (!product) return null
          const qty = Math.min(entry.qty, stockInfo(product).max)
          if (qty < 1) return null
          return { product, qty, clamped: qty < entry.qty, lineTotal: product.price * qty }
        })
        .filter(Boolean),
    [cart, byId],
  )

  const count = useMemo(() => lines.reduce((n, l) => n + l.qty, 0), [lines])
  const subtotal = useMemo(() => lines.reduce((n, l) => n + l.lineTotal, 0), [lines])

  const addToCart = useCallback(
    (product, qty = 1, { silent = false } = {}) => {
      const max = stockInfo(product).max
      if (max < 1) {
        if (!silent) toast(`${product.name} is out of stock`, 'error')
        return false
      }
      const current = cart.find((l) => l.id === product.id)?.qty ?? 0
      const next = Math.min(current + qty, max)
      if (next === current) {
        if (!silent) toast(`You already have all ${max} in your bag`, 'info')
        return true
      }
      setCart(
        current
          ? cart.map((l) => (l.id === product.id ? { ...l, qty: next } : l))
          : [...cart, { id: product.id, qty: next }],
      )
      if (!silent) toast(`Added ${product.name} to your bag`, 'success')
      return true
    },
    [cart, setCart, toast],
  )

  const setQty = useCallback(
    (product, qty) => {
      const max = stockInfo(product).max
      const next = Math.min(qty, max)
      setCart((c) =>
        next < 1
          ? c.filter((l) => l.id !== product.id)
          : c.map((l) => (l.id === product.id ? { ...l, qty: next } : l)),
      )
    },
    [setCart],
  )

  const removeFromCart = useCallback(
    (product) => setCart((c) => c.filter((l) => l.id !== product.id)),
    [setCart],
  )
  const clearCart = useCallback(() => setCart([]), [setCart])

  const value = useMemo(
    () => ({
      catalog,
      reload,
      refresh,
      toasts,
      toast,
      dismissToast,
      theme,
      toggleTheme,
      query,
      setQuery,
      savedOnly,
      setSavedOnly,
      wishlist,
      toggleWish,
      cartOpen,
      setCartOpen,
      lines,
      count,
      subtotal,
      addToCart,
      setQty,
      removeFromCart,
      clearCart,
    }),
    [
      catalog,
      reload,
      refresh,
      toasts,
      toast,
      dismissToast,
      theme,
      toggleTheme,
      query,
      savedOnly,
      wishlist,
      toggleWish,
      cartOpen,
      lines,
      count,
      subtotal,
      addToCart,
      setQty,
      removeFromCart,
      clearCart,
    ],
  )

  return <StoreContext.Provider value={value}>{children}</StoreContext.Provider>
}
