import Icon from './Icon'

export default function QtyStepper({ value, max, onChange, small = false, label = 'Quantity' }) {
  const size = small ? 'size-8' : 'size-11'
  const btn = `${size} grid cursor-pointer place-items-center rounded-full transition hover:bg-surface-2 disabled:pointer-events-none disabled:opacity-30`
  return (
    <div
      className="inline-flex items-center rounded-full border border-line bg-surface p-0.5"
      role="group"
      aria-label={label}
    >
      <button
        type="button"
        className={btn}
        onClick={() => onChange(value - 1)}
        aria-label="Decrease quantity"
      >
        <Icon name="minus" className={small ? 'size-3.5' : 'size-4'} />
      </button>
      <span
        className={`${small ? 'w-7 text-sm' : 'w-10'} text-center font-semibold tabular-nums`}
        aria-live="polite"
      >
        {value}
      </span>
      <button
        type="button"
        className={btn}
        onClick={() => onChange(value + 1)}
        disabled={value >= max}
        aria-label="Increase quantity"
      >
        <Icon name="plus" className={small ? 'size-3.5' : 'size-4'} />
      </button>
    </div>
  )
}
