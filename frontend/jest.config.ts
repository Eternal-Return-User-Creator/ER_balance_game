import type { Config } from 'jest';

const config: Config = {
  testEnvironment: 'jest-environment-jsdom',
  transform: {
    // TypeScript 파일과 JS 파일을 변환하도록 설정
    '^.+\\.tsx?$': 'ts-jest',
    // 이미지 파일을 무시하도록 설정
    '^.+\\.(png|jpg|jpeg|gif|svg|bmp|webp)$': 'jest-transform-stub',
  },
  moduleNameMapper: {
    // 이미지 파일을 import할 때 빈 객체로 대체
    '\\.(png|jpg|jpeg|gif|svg|bmp|webp)$': '<rootDir>/__mocks__/fileMock.ts',
  },
  testMatch: ['**/*.test.tsx'], // *.test.tsx 파일만 실행
  moduleFileExtensions: ['js', 'jsx', 'ts', 'tsx'],
  setupFilesAfterEnv: ['<rootDir>/jest.setup.ts'],
};

module.exports = {
  transform: {
    '^.+\\.(png|jpg|jpeg|gif|svg)$': 'jest-transform-stub',
  },
};

export default config;
