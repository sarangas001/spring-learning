import { useStore } from '../store/store'
import Icon from './Icon'

const STYLE = {
  success: { icon: 'check', dot: 'bg-volt text-volt-ink' },
  error: { icon: 'alert', dot: 'bg-coral text-white' },
  info: { icon: 'heart', dot: 'bg-surface-2 text-ink' },
}

export default function Toasts() {
  const { toasts, dismissToast } = useStore()
  return (
    <div
      className="pointer-events-none fixed inset-x-0 bottom-5 z-[60] flex flex-col items-center gap-2 px-4"
      role="status"
      aria-live="polite"
    >
      {toasts.map((t) => {
        const s = STYLE[t.kind] ?? STYLE.info
        return (
          <div
            key={t.id}
            className="pointer-events-auto flex max-w-md animate-toast items-center gap-3 rounded-3xl bg-ink py-2 pr-3 pl-2 text-sm font-medium text-bg shadow-2xl"
          >
            <span className={`grid size-7 shrink-0 place-items-center rounded-full ${s.dot}`}>
              <Icon name={s.icon} className="size-3.5" strokeWidth={2.4} />
            </span>
            <span className="line-clamp-4 break-words">{t.message}</span>
            <button
              onClick={() => dismissToast(t.id)}
              className="grid size-6 shrink-0 cursor-pointer place-items-center rounded-full opacity-60 hover:opacity-100"
              aria-label="Dismiss"
            >
              <Icon name="x" className="size-3.5" />
            </button>
          </div>
        )
      })}
    </div>
  )
}
