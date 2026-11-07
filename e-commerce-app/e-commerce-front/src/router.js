import { useSyncExternalStore } from 'react'

// Tiny hash router — no dependency, works on any static host.
function subscribe(callback) {
  window.addEventListener('hashchange', callback)
  return () => window.removeEventListener('hashchange', callback)
}

const getSnapshot = () => window.location.hash || '#/'

export function parseRoute(hash) {
  const path = hash.replace(/^#/, '').split('?')[0] || '/'
  const seg = path.split('/').filter(Boolean)
  if (seg[0] === 'product' && seg[1]) return { name: 'product', id: Number(seg[1]) }
  if (seg[0] === 'checkout') return { name: 'checkout' }
  if (seg[0] === 'orders') return { name: 'orders' }
  if (seg[0] === 'admin') {
    if (seg[1] === 'new') return { name: 'admin-form', id: null }
    if (seg[1] === 'edit' && seg[2]) return { name: 'admin-form', id: Number(seg[2]) }
    return { name: 'admin' }
  }
  return { name: 'home' }
}

export function useRoute() {
  const hash = useSyncExternalStore(subscribe, getSnapshot, () => '#/')
  return parseRoute(hash)
}

export const navigate = (path) => {
  window.location.hash = path
}

export function scrollToCatalog() {
  const go = () => document.getElementById('catalog')?.scrollIntoView({ behavior: 'smooth' })
  if (parseRoute(getSnapshot()).name === 'home') go()
  else {
    navigate('/')
    setTimeout(go, 120)
  }
}
