import { parseRelease } from './lib/format'

// Empty base = same origin (Vite proxies /api to Spring Boot in dev).
// Set VITE_API_URL=https://your-host to call a deployed backend directly.
const BASE = import.meta.env.VITE_API_URL ?? ''

export class ApiError extends Error {
  constructor(message, status) {
    super(message)
    this.name = 'ApiError'
    this.status = status
  }
}

async function request(path, { method = 'GET', body, signal } = {}) {
  let res
  // FormData sets its own multipart boundary header, so only JSON gets one.
  const isForm = typeof FormData !== 'undefined' && body instanceof FormData
  try {
    res = await fetch(`${BASE}/api${path}`, {
      method,
      signal,
      headers: body && !isForm ? { 'Content-Type': 'application/json' } : undefined,
      body: body ? (isForm ? body : JSON.stringify(body)) : undefined,
    })
  } catch (err) {
    if (err.name === 'AbortError') throw err
    throw new ApiError(
      "We can't reach the store right now. Make sure the backend is running on port 8080.",
      0,
    )
  }

  const text = await res.text()
  let data
  try {
    data = text ? JSON.parse(text) : null
  } catch {
    data = text
  }

  if (!res.ok) {
    const message =
      (data && typeof data === 'object' && (data.message || data.error)) ||
      (typeof data === 'string' && data) ||
      `Something went wrong (${res.status})`
    throw new ApiError(message, res.status)
  }
  return data
}

/** Turns the raw entity into what the UI needs (image data-URI, real dates…). */
export function normalizeProduct(p) {
  const image = p.imageData && p.imageType ? `data:${p.imageType};base64,${p.imageData}` : null
  const releaseDate = parseRelease(p.releaseDate)
  const out = {
    ...p,
    price: Number(p.price) || 0,
    stockQuantity: p.stockQuantity ?? 0,
    releaseDate,
    image,
    isNew: releaseDate ? Date.now() - releaseDate.getTime() < 90 * 24 * 3600 * 1000 : false,
  }
  delete out.imageData // keep memory low — the data-URI above is all we need
  return out
}

export const getProducts = (signal) =>
  request('/products', { signal }).then((list) => (list ?? []).map(normalizeProduct))

export const getProduct = (id, signal) =>
  request(`/product/${id}`, { signal }).then(normalizeProduct)

// The backend expects multipart: a JSON "product" part plus an "image" file part.
function productForm(product, image) {
  const fd = new FormData()
  fd.append('product', new Blob([JSON.stringify(product)], { type: 'application/json' }))
  fd.append('image', image)
  return fd
}

export const createProduct = (product, image) =>
  request('/product', { method: 'POST', body: productForm(product, image) })

export const updateProduct = (product, image) =>
  request('/product', { method: 'PUT', body: productForm(product, image) })

export const deleteProduct = (id) => request(`/product/${id}`, { method: 'DELETE' })

export const placeOrder =(payload) => request('/orders/place', { method: 'POST', body: payload })

export const getOrders = (signal) => request('/orders/orders', { signal })
