import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { localBackendURL } from "./src/common/constants.ts";

// https://vite.dev/config/
export default defineConfig({
  plugins: [ react() ],
  server: {
    proxy: {
      '/api': {
        target: localBackendURL,
        changeOrigin: true,
      }
    }
  }
});
