import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8080",
        changeOrigin: true,
        secure: false,
        ws: true,
        rewrite: (path) => path.replace(/^\/api/, "api/"),
      },
      "/ws": {
        target: "ws://127.0.0.1:8080",
        secure: false,
        ws: true,
        changeOrigin: true,
      },
    },
    port: 5173,
  },
  plugins: [react()],
});
