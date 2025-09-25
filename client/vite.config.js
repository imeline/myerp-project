/* eslint-env node */
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

const API_PREFIX = process.env.VITE_API_PREFIX || '/api'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    host: true, // 0.0.0.0
    proxy: {
      [API_PREFIX]: {
        target: 'http://server:8080',
        changeOrigin: true,
      },
    },
  },
  define: {
    __API_PREFIX__: JSON.stringify(API_PREFIX),
  },
})
