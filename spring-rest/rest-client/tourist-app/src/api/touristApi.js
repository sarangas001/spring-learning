import axios from 'axios'

// Requests go to /client/tourist on the dev server, which proxies them to
// http://localhost:9090/client/tourist (see vite.config.js).
const api = axios.create({
  baseURL: '/client/tourist',
  headers: { 'Content-Type': 'application/json' },
})

export const registerTourist = (tourist) => api.post('/register', tourist)

export const getTourist = (id) => api.get(`/${id}`)

export const getAllTourists = () => api.get('/all')

export const deleteTourist = (id) => api.delete(`/${id}`)

export const updateTourist = (tourist) => api.put('/', tourist)

export const updateBudget = (id, budget) => api.patch(`/${id}/${budget}`)

// Pull a readable message out of an axios error (Spring error body or network failure).
export const errorMessage = (err) => {
  if (err.response) {
    const data = err.response.data
    if (typeof data === 'string' && data) return data
    if (data?.message) return data.message
    return `Request failed (${err.response.status})`
  }
  return 'Cannot reach the backend at localhost:9090. Is it running?'
}
