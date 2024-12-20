import type { Config } from 'jest';

const config: Config = {
  testEnvironment: 'jsdom',
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
  },
  testMatch: ['**/*.test.tsx'], // *.test.tsx 파일만 실행
  moduleFileExtensions: ['js', 'jsx', 'ts', 'tsx'],
};

export default config;
