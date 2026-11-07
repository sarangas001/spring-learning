# Orbit — e-commerce frontend

React 19 + Vite + Tailwind CSS 4 storefront for the Spring Boot `e-commerce-back` API.

## Run

```bash
# 1. start the backend (port 8080, needs PostgreSQL as configured in application.properties)
# 2. then:
npm install
npm run dev        # http://localhost:5173
```

In dev, Vite proxies `/api/*` to `http://localhost:8080`, so no backend/CORS changes are needed.

| Env var            | Purpose                                                          | Default                 |
| ------------------ | ---------------------------------------------------------------- | ----------------------- |
| `VITE_BACKEND_URL` | Dev-proxy target                                                 | `http://localhost:8080` |
| `VITE_API_URL`     | Call a deployed API directly instead of proxying (build-time)    | *(same origin)*         |
| `VITE_CURRENCY`    | ISO currency code used for prices                                | `USD`                   |

## Features

- Product catalogue with live search (press `/`), category chips, brand / price / in-stock filters, sorting
- Product detail page with hover-zoom, stock status, quantity picker, "Buy now", related products
- Slide-in bag with stock-aware quantity steppers, persisted in `localStorage`
- Saved items (wishlist), light / dark theme, skeleton loading and friendly error states
- Checkout with validation → `POST /api/orders/place`, animated confirmation
- Order history lookup by email with a status timeline
- **Product management** at `#/admin` — stats, filter, add / edit / delete
  - `#/admin/new` and `#/admin/edit/:id`: photo drag-and-drop, validation, live preview card
  - Big photos are resized/recompressed in the browser, because Spring Boot's default upload limit is 1 MB
  - Deleting a product that's already in an order is rejected by the database; the UI explains why

## Structure

```
src/
  api.js            fetch wrapper + endpoints
  router.js         tiny hash router
  store/            catalogue, cart, wishlist, theme, toasts (React context)
  components/       Header, Hero, ProductCard, CartDrawer, …
  pages/            ShopPage, ProductPage, CheckoutPage, OrdersPage, AdminPage, ProductFormPage
  lib/              format helpers, client-side image optimiser
```
