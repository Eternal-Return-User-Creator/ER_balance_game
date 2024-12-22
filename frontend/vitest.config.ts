import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// vitest 설정을 추가합니다.
export default defineConfig({
  plugins: [vue()],
  test: {
    globals: true, // 글로벌 변수로 jest와 같은 API 사용
    environment: 'jsdom', // jsdom 환경에서 테스트 실행
    setupFiles: './vitest.setup.ts', // 테스트 환경 설정 파일
    include: ['src/**/*.{test,spec}.ts', 'src/**/*.{test,spec}.tsx'], // 테스트 파일의 패턴 지정
  },
})
