import { useEffect, useRef, useState } from 'react'
import { useStore } from '../store/store'
import { navigate, scrollToCatalog, useRoute } from '../router'
import Icon from './Icon'

function IconButton({ label, onClick, href, active = false, children }) {
  const cls = `relative grid size-9 cursor-pointer place-items-center rounded-full transition hover:bg-surface-2 sm:size-10 ${
    active ? 'bg-surface-2' : ''
  }`
  return href ? (
    <a href={href} className={cls} aria-label={label} title={label}>
      {children}
    </a>
  ) : (
    <button onClick={onClick} className={cls} aria-label={label} title={label} aria-pressed={active}>
      {children}
    </button>
  )
}

export default function Header() {
  const {
    query,
    setQuery,
    count,
    setCartOpen,
    theme,
    toggleTheme,
    wishlist,
    savedOnly,
    setSavedOnly,
  } = useStore()
  const route = useRoute()
  const inputRef = useRef(null)
  const [scrolled, setScrolled] = useState(false)

  useEffect(() => {
    const onScroll = () => setScrolled(window.scrollY > 8)
    window.addEventListener('scroll', onScroll, { passive: true })
    return () => window.removeEventListener('scroll', onScroll)
  }, [])

  // Press "/" anywhere to jump into search.
  useEffect(() => {
    const onKey = (e) => {
      const tag = document.activeElement?.tagName
      if (e.key === '/' && !/INPUT|TEXTAREA|SELECT/.test(tag ?? '')) {
        e.preventDefault()
        inputRef.current?.focus()
      }
    }
    window.addEventListener('keydown', onKey)
    return () => window.removeEventListener('keydown', onKey)
  }, [])

  const onSearch = (value) => {
    setQuery(value)
    if (route.name !== 'home') navigate('/')
  }

  const toggleSaved = () => {
    setSavedOnly(!savedOnly)
    if (!savedOnly) scrollToCatalog()
  }

  return (
    <header
      className={`sticky top-0 z-40 border-b backdrop-blur-xl transition-colors ${
        scrolled ? 'border-line bg-bg/85' : 'border-transparent bg-bg/60'
      }`}
    >
      <div className="mx-auto flex max-w-7xl flex-wrap items-center gap-x-3 gap-y-2 px-4 py-2.5 sm:px-6 md:flex-nowrap">
        <a
          href="#/"
          onClick={() => {
            setQuery('')
            setSavedOnly(false)
          }}
          className="mr-auto flex items-center gap-2 font-display text-xl font-extrabold tracking-tight md:mr-0"
          aria-label="Orbit home"
        >
          <span className="grid size-9 place-items-center rounded-full bg-volt text-volt-ink">
            <svg viewBox="0 0 24 24" className="size-5" fill="none" stroke="currentColor" strokeWidth="2.6">
              <circle cx="11" cy="13" r="6.5" />
              <circle cx="18" cy="6" r="2.2" fill="currentColor" stroke="none" />
            </svg>
          </span>
          <span className="max-[380px]:hidden">Orbit</span>
        </a>

        {/* Search — second row on phones, centred on desktop */}
        <div className="relative order-last w-full md:order-none md:mx-auto md:w-auto md:flex-1 md:max-w-xl">
          <Icon name="search" className="pointer-events-none absolute top-1/2 left-4 size-4 -translate-y-1/2 text-muted" />
          <input
            ref={inputRef}
            type="search"
            value={query}
            onChange={(e) => onSearch(e.target.value)}
            placeholder="Search products, brands, categories…"
            aria-label="Search products"
            className="h-11 w-full rounded-full border border-line bg-surface pr-12 pl-11 text-sm outline-none transition placeholder:text-muted focus:border-ink focus:ring-4 focus:ring-volt/50 [&::-webkit-search-cancel-button]:appearance-none"
          />
          {query ? (
            <button
              onClick={() => setQuery('')}
              className="absolute top-1/2 right-2 grid size-7 -translate-y-1/2 cursor-pointer place-items-center rounded-full hover:bg-surface-2"
              aria-label="Clear search"
            >
              <Icon name="x" className="size-4" />
            </button>
          ) : (
            <kbd className="pointer-events-none absolute top-1/2 right-3 hidden -translate-y-1/2 rounded-md border border-line px-1.5 py-0.5 text-[11px] font-semibold text-muted md:block">
              /
            </kbd>
          )}
        </div>

        <nav className="flex items-center gap-0.5" aria-label="Primary">
          <IconButton label="My orders" href="#/orders" active={route.name === 'orders'}>
            <Icon name="receipt" />
          </IconButton>
          <IconButton
            label="Manage products"
            href="#/admin"
            active={route.name === 'admin' || route.name === 'admin-form'}
          >
            <Icon name="layout" />
          </IconButton>
          <IconButton label="Saved items" onClick={toggleSaved} active={savedOnly}>
            <Icon name="heart" fill={savedOnly} className={`size-5 ${savedOnly ? 'text-coral' : ''}`} />
            {wishlist.length > 0 && (
              <span className="absolute top-1 right-1 size-2 rounded-full bg-coral ring-2 ring-bg" />
            )}
          </IconButton>
          <IconButton
            label={theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode'}
            onClick={toggleTheme}
          >
            <Icon name={theme === 'dark' ? 'sun' : 'moon'} />
          </IconButton>
          <button
            onClick={() => setCartOpen(true)}
            className="btn btn-primary ml-1 h-10 gap-2 px-3 sm:ml-1.5 sm:px-4"
            aria-label={`Open bag, ${count} items`}
          >
            <Icon name="bag" className="size-[18px]" />
            <span className="hidden sm:inline">Bag</span>
            <span
              key={count}
              className={`grid min-w-5 place-items-center rounded-full px-1.5 text-xs font-bold ${
                count ? 'animate-bump bg-volt text-volt-ink' : 'bg-bg/20'
              }`}
            >
              {count}
            </span>
          </button>
        </nav>
      </div>
    </header>
  )
}
