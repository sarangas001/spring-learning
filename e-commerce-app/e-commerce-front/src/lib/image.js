// The Spring Boot default multipart limit is 1 MB per file, and the backend
// config doesn't raise it. So we shrink big photos in the browser first.
const MAX_BYTES = 900 * 1024
const MAX_DIM = 1400

const loadImage = (file) =>
  new Promise((resolve, reject) => {
    const url = URL.createObjectURL(file)
    const img = new Image()
    img.onload = () => {
      URL.revokeObjectURL(url)
      resolve(img)
    }
    img.onerror = () => {
      URL.revokeObjectURL(url)
      reject(new Error("That file isn't a readable image."))
    }
    img.src = url
  })

const toBlob = (canvas, type, quality) =>
  new Promise((resolve) => canvas.toBlob(resolve, type, quality))

/**
 * Returns a File that fits the backend's upload limit. Small images are
 * passed through untouched; large ones are resized and re-encoded as JPEG
 * on a white background (which also suits the product-photo panels).
 */
export async function prepareImage(file) {
  if (!file.type.startsWith('image/')) {
    throw new Error('Please choose an image file (JPG, PNG, WebP…).')
  }
  const img = await loadImage(file)
  const w = img.naturalWidth || 1000 // SVGs may report 0
  const h = img.naturalHeight || 1000

  const isSvg = file.type === 'image/svg+xml'
  if (file.size <= MAX_BYTES && (isSvg || Math.max(w, h) <= 2000)) return file

  const base = file.name.replace(/\.[^.]+$/, '') || 'product'
  let scale = Math.min(1, MAX_DIM / Math.max(w, h))

  for (let round = 0; round < 4; round++) {
    const canvas = document.createElement('canvas')
    canvas.width = Math.max(1, Math.round(w * scale))
    canvas.height = Math.max(1, Math.round(h * scale))
    const ctx = canvas.getContext('2d')
    ctx.fillStyle = '#fff'
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height)

    for (const quality of [0.88, 0.78, 0.68, 0.55]) {
      const blob = await toBlob(canvas, 'image/jpeg', quality)
      if (blob && blob.size <= MAX_BYTES) {
        return new File([blob], `${base}.jpg`, { type: 'image/jpeg' })
      }
    }
    scale *= 0.75
  }
  throw new Error('That image is too large to shrink automatically. Try a smaller one.')
}

export const readDataUrl = (file) =>
  new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = () => resolve(reader.result)
    reader.onerror = () => reject(reader.error)
    reader.readAsDataURL(file)
  })

/** Turn an existing data-URI back into a File (the PUT endpoint requires an image part). */
export async function dataUrlToFile(dataUrl, name = 'image') {
  const blob = await (await fetch(dataUrl)).blob()
  return new File([blob], name, { type: blob.type })
}

export const formatBytes = (n) =>
  n >= 1024 * 1024 ? `${(n / 1024 / 1024).toFixed(1)} MB` : `${Math.round(n / 1024)} KB`
