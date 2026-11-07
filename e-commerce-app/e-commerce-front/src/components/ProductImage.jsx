import Icon from './Icon'

/** Product photo, or a tasteful placeholder when the product has no image. */
export default function ProductImage({ product, className = '', blend = true }) {
  if (product.image) {
    return (
      <img
        src={product.image}
        alt={product.name}
        loading="lazy"
        decoding="async"
        draggable="false"
        className={`${blend ? 'mix-blend-multiply' : ''} ${className}`}
      />
    )
  }
  return (
    <div className={`grid place-items-center text-ink/25 ${className}`} role="img" aria-label={product.name}>
      <Icon name="package" className="size-1/3" strokeWidth={1.2} />
    </div>
  )
}
