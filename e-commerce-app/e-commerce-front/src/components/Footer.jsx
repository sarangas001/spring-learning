import { scrollToCatalog } from '../router'

export default function Footer() {
  return (
    <footer className="mt-24 border-t border-line">
      <div className="mx-auto flex max-w-7xl flex-col gap-8 px-4 py-12 sm:px-6 md:flex-row md:items-end md:justify-between">
        <div className="max-w-sm">
          <p className="font-display text-3xl font-extrabold tracking-tight">
            Orbit<span className="ml-0.5 inline-block size-2.5 rounded-full bg-volt" />
          </p>
          <p className="mt-2 text-sm text-muted">
            A curated shop for gear you&apos;ll actually use. Live stock, instant order confirmation,
            zero fuss.
          </p>
        </div>
        <nav className="flex flex-wrap gap-x-8 gap-y-2 text-sm font-medium" aria-label="Footer">
          <button onClick={scrollToCatalog} className="cursor-pointer hover:underline">
            Shop all
          </button>
          <a href="#/orders" className="hover:underline">
            My orders
          </a>
          <a href="#/admin" className="hover:underline">
            Manage products
          </a>
          <button
            onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}
            className="cursor-pointer hover:underline"
          >
            Back to top ↑
          </button>
        </nav>
      </div>
      <div className="border-t border-line py-5 text-center text-xs text-muted">
        © {new Date().getFullYear()} Orbit. Built with React, Tailwind &amp; Spring Boot.
      </div>
    </footer>
  )
}
