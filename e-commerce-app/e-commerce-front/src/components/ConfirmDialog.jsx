import { useEffect, useRef } from 'react'

/** Mount it to show it; unmount to hide it. */
export default function ConfirmDialog({
  title,
  message,
  confirmLabel = 'Confirm',
  danger = false,
  busy = false,
  onConfirm,
  onCancel,
}) {
  const cancelRef = useRef(null)

  useEffect(() => {
    const onKey = (e) => e.key === 'Escape' && !busy && onCancel()
    document.addEventListener('keydown', onKey)
    const prev = document.body.style.overflow
    document.body.style.overflow = 'hidden'
    cancelRef.current?.focus()
    return () => {
      document.removeEventListener('keydown', onKey)
      document.body.style.overflow = prev
    }
  }, [busy, onCancel])

  return (
    <div className="fixed inset-0 z-[55] grid place-items-center p-4">
      <div
        className="absolute inset-0 animate-fade-in bg-black/55 backdrop-blur-sm"
        onClick={() => !busy && onCancel()}
      />
      <div
        role="alertdialog"
        aria-modal="true"
        aria-labelledby="confirm-title"
        className="relative w-full max-w-sm animate-pop rounded-3xl border border-line bg-bg p-6 shadow-2xl"
      >
        <h2 id="confirm-title" className="font-display text-2xl font-bold tracking-tight">
          {title}
        </h2>
        <p className="mt-2 text-muted">{message}</p>
        <div className="mt-6 flex justify-end gap-2">
          <button ref={cancelRef} className="btn btn-ghost" onClick={onCancel} disabled={busy}>
            Cancel
          </button>
          <button
            className={`btn ${danger ? 'bg-coral text-white hover:brightness-95' : 'btn-primary'}`}
            onClick={onConfirm}
            disabled={busy}
          >
            {busy ? 'Working…' : confirmLabel}
          </button>
        </div>
      </div>
    </div>
  )
}
