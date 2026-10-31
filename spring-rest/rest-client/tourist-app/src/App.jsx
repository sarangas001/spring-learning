import { useCallback, useEffect, useState } from 'react'
import {
  deleteTourist,
  errorMessage,
  getAllTourists,
  getTourist,
  registerTourist,
  updateBudget,
  updateTourist,
} from './api/touristApi'
import TouristForm from './components/TouristForm'
import TouristTable from './components/TouristTable'
import './App.css'

// Backend replies with either a plain string message or an object; show whichever we get.
const toText = (data, fallback) => (typeof data === 'string' && data ? data : fallback)

function App() {
  const [tourists, setTourists] = useState([])
  const [editing, setEditing] = useState(null)
  const [searchId, setSearchId] = useState('')
  const [searching, setSearching] = useState(false)
  const [loading, setLoading] = useState(true) // true until the first fetch settles
  const [status, setStatus] = useState(null) // { type: 'success' | 'error', text }

  const success = (text) => setStatus({ type: 'success', text })
  const failure = (err) => setStatus({ type: 'error', text: errorMessage(err) })

  const loadAll = useCallback(async () => {
    try {
      const { data } = await getAllTourists()
      setTourists(Array.isArray(data) ? data : [])
      setSearching(false)
    } catch (err) {
      failure(err)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    getAllTourists()
      .then(({ data }) => setTourists(Array.isArray(data) ? data : []))
      .catch((err) => setStatus({ type: 'error', text: errorMessage(err) }))
      .finally(() => setLoading(false))
  }, [])

  const handleSubmit = async (tourist) => {
    try {
      if (editing) {
        const { data } = await updateTourist({ ...tourist, id: editing.id })
        success(toText(data, 'Tourist updated'))
        setEditing(null)
      } else {
        const { data } = await registerTourist(tourist)
        success(toText(data, 'Tourist registered'))
      }
      await loadAll()
      return true
    } catch (err) {
      failure(err)
      return false
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm(`Delete tourist #${id}?`)) return
    try {
      const { data } = await deleteTourist(id)
      success(toText(data, `Tourist #${id} deleted`))
      if (editing?.id === id) setEditing(null)
      await loadAll()
    } catch (err) {
      failure(err)
    }
  }

  const handleBudget = async (id, budget) => {
    try {
      const { data } = await updateBudget(id, budget)
      success(toText(data, `Budget updated for tourist #${id}`))
      await loadAll()
      return true
    } catch (err) {
      failure(err)
      return false
    }
  }

  const handleSearch = async (e) => {
    e.preventDefault()
    try {
      const { data } = await getTourist(searchId)
      setTourists(data ? [data] : [])
      setSearching(true)
      setStatus(null)
    } catch (err) {
      failure(err)
    }
  }

  return (
    <main>
      <header>
        <h1>Tourist App</h1>
        <p className="muted">Manage tourists through the Spring Boot backend.</p>
      </header>

      {status && (
        <div className={`alert ${status.type}`} role="status">
          <span>{status.text}</span>
          <button type="button" aria-label="Dismiss" onClick={() => setStatus(null)}>
            ×
          </button>
        </div>
      )}

      <div className="layout">
        <TouristForm
          key={editing?.id ?? 'new'}
          editing={editing}
          onSubmit={handleSubmit}
          onCancel={() => setEditing(null)}
        />

        <section className="card">
          <div className="list-header">
            <h2>{searching ? 'Search result' : 'All tourists'}</h2>
            <form className="inline" onSubmit={handleSearch}>
              <input
                type="number"
                min="1"
                placeholder="Find by ID"
                value={searchId}
                onChange={(e) => setSearchId(e.target.value)}
                required
              />
              <button type="submit">Find</button>
              <button type="button" className="secondary" onClick={loadAll}>
                {searching ? 'Show all' : 'Refresh'}
              </button>
            </form>
          </div>

          {loading ? (
            <p className="muted">Loading…</p>
          ) : (
            <TouristTable
              tourists={tourists}
              onEdit={setEditing}
              onDelete={handleDelete}
              onBudget={handleBudget}
            />
          )}
        </section>
      </div>
    </main>
  )
}

export default App
