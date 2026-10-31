import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    // Forward /client/* to the Spring Boot backend so the browser sees a
    // same-origin request and CORS doesn't need to be configured on the server.
    proxy: {
      '/client': 'http://localhost:9090',
    },
  },
})
