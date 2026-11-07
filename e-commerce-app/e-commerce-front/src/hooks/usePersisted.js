import { useEffect, useState } from 'react'

/** useState that survives reloads. Safe when storage is blocked. */
export default function usePersisted(key, initial) {
  const [value, setValue] = useState(() => {
    try {
      const raw = localStorage.getItem(key)
      return raw ? JSON.parse(raw) : initial
    } catch {
      return initial
    }
  })

  useEffect(() => {
    try {
      localStorage.setItem(key, JSON.stringify(value))
    } catch {
      /* storage unavailable — keep working in memory */
    }
  }, [key, value])

  return [value, setValue]
}
