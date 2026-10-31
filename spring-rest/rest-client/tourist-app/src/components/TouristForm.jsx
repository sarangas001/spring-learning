import { useState } from 'react'

const PACKAGE_TYPES = ['Budget', 'Standard', 'Premium', 'Luxury']

const empty = { name: '', city: '', packageType: PACKAGE_TYPES[0], budget: '' }

// `editing` is the tourist being edited (PUT), or null when registering a new one (POST).
// The parent passes key={editing?.id} so the form resets when the target changes.
function TouristForm({ editing, onSubmit, onCancel }) {
  const [form, setForm] = useState(editing ?? empty)

  const handleChange = (e) =>
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    const ok = await onSubmit({ ...form, budget: Number(form.budget) })
    if (ok && !editing) setForm(empty)
  }

  return (
    <form className="card form" onSubmit={handleSubmit}>
      <h2>{editing ? `Update tourist #${editing.id}` : 'Register tourist'}</h2>

      <label>
        Name
        <input name="name" value={form.name} onChange={handleChange} required />
      </label>
      <label>
        City
        <input name="city" value={form.city} onChange={handleChange} required />
      </label>
      <label>
        Package type
        <select name="packageType" value={form.packageType} onChange={handleChange}>
          {PACKAGE_TYPES.map((p) => (
            <option key={p}>{p}</option>
          ))}
        </select>
      </label>
      <label>
        Budget
        <input
          name="budget"
          type="number"
          min="0"
          step="0.01"
          value={form.budget}
          onChange={handleChange}
          required
        />
      </label>

      <div className="actions">
        <button type="submit">{editing ? 'Save changes' : 'Register'}</button>
        {editing && (
          <button type="button" className="secondary" onClick={onCancel}>
            Cancel
          </button>
        )}
      </div>
    </form>
  )
}

export default TouristForm
