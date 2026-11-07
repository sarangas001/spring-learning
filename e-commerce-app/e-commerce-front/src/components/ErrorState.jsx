import Icon from './Icon'

export default function ErrorState({ title = 'Something went wrong', message, onRetry }) {
  return (
    <div className="mx-auto flex max-w-md animate-fade-up flex-col items-center py-16 text-center">
      <div className="mb-5 grid size-16 place-items-center rounded-full bg-coral/15 text-coral">
        <Icon name="alert" className="size-8" />
      </div>
      <h3 className="font-display text-2xl font-bold">{title}</h3>
      {message && <p className="mt-2 text-muted">{message}</p>}
      {onRetry && (
        <button className="btn btn-primary mt-6" onClick={onRetry}>
          <Icon name="refresh" className="size-4" /> Try again
        </button>
      )}
    </div>
  )
}
