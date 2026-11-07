import { useEffect } from 'react'
import StoreProvider from './store/StoreProvider'
import { useRoute } from './router'
import Header from './components/Header'
import Footer from './components/Footer'
import CartDrawer from './components/CartDrawer'
import Toasts from './components/Toasts'
import ShopPage from './pages/ShopPage'
import ProductPage from './pages/ProductPage'
import CheckoutPage from './pages/CheckoutPage'
import OrdersPage from './pages/OrdersPage'
import AdminPage from './pages/AdminPage'
import ProductFormPage from './pages/ProductFormPage'

function Routes() {
  const route = useRoute()
  const key = route.id != null ? `${route.name}-${route.id}` : route.name

  // Every page change starts at the top.
  useEffect(() => {
    window.scrollTo({ top: 0 })
  }, [key])

  return (
    <main id="main" tabIndex={-1} key={key} className="flex-1 animate-fade-in outline-none">
      {route.name === 'product' && <ProductPage id={route.id} />}
      {route.name === 'checkout' && <CheckoutPage />}
      {route.name === 'orders' && <OrdersPage />}
      {route.name === 'admin' && <AdminPage />}
      {route.name === 'admin-form' && <ProductFormPage id={route.id} />}
      {route.name === 'home' && <ShopPage />}
    </main>
  )
}

export default function App() {
  return (
    <StoreProvider>
      <div className="flex min-h-svh flex-col">
        <button
          onClick={() => document.getElementById('main')?.focus()}
          className="sr-only z-70 rounded-full bg-ink px-4 py-2 text-bg focus:not-sr-only focus:fixed focus:top-3 focus:left-3"
        >
          Skip to content
        </button>
        <Header />
        <Routes />
        <Footer />
      </div>
      <CartDrawer />
      <Toasts />
    </StoreProvider>
  )
}
