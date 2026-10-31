import { useState } from 'react'

function TouristRow({ tourist, onEdit, onDelete, onBudget }) {
  const [budget, setBudget] = useState('')

  const submitBudget = async (e) => {
    e.preventDefault()
    if (await onBudget(tourist.id, budget)) setBudget('')
  }

  return (
    <tr>
      <td>{tourist.id}</td>
      <td>{tourist.name}</td>
      <td>{tourist.city}</td>
      <td>{tourist.packageType}</td>
      <td>{tourist.budget}</td>
      <td>
        <form className="inline" onSubmit={submitBudget}>
          <input
            type="number"
            min="0"
            step="0.01"
            placeholder="New budget"
            value={budget}
            onChange={(e) => setBudget(e.target.value)}
            required
          />
          <button type="submit" className="secondary">Set</button>
        </form>
      </td>
      <td className="row-actions">
        <button type="button" className="secondary" onClick={() => onEdit(tourist)}>
          Edit
        </button>
        <button type="button" className="danger" onClick={() => onDelete(tourist.id)}>
          Delete
        </button>
      </td>
    </tr>
  )
}

function TouristTable({ tourists, onEdit, onDelete, onBudget }) {
  if (tourists.length === 0) return <p className="muted">No tourists to show.</p>

  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>City</th>
            <th>Package</th>
            <th>Budget</th>
            <th>Update budget</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {tourists.map((t) => (
            <TouristRow
              key={t.id}
              tourist={t}
              onEdit={onEdit}
              onDelete={onDelete}
              onBudget={onBudget}
            />
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default TouristTable
