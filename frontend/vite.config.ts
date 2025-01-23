import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/

export default (mode: string) => {
  // Load app-level env vars to node-level env vars.

  process.env = {
    ...process.env,
    ...loadEnv(mode, process.cwd())
  };

  return defineConfig({
    plugins: [ react() ],
    server: {
      proxy: {
        '/api': {
          target: process.env.VITE_BACKEND_URL,
          changeOrigin: true,
        }
      }
    }
    // To access env vars here use process.env.TEST_VAR
  });
}
