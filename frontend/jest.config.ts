import type { Config } from 'jest';

const config: Config = {
  testEnvironment: 'jsdom',
  transform: {
    // TypeScript 파일과 JS 파일을 변환하도록 설정
    '^.+\\.tsx?$': 'ts-jest',
    // 이미지 파일을 무시하도록 설정
    '^.+\\.(png|jpg|jpeg|gif|svg|bmp|webp)$': 'jest-transform-stub',
  },
  moduleNameMapper: {
    // 이미지 파일을 import할 때 빈 객체로 대체
    '\\.(png|jpg|jpeg|gif|svg|bmp|webp)$': '<rootDir>/__mocks__/fileMock.ts',
    // 스타일 파일을 모킹
    '\\.(css|less|sass|scss)$': 'identity-obj-proxy',
  },
  testMatch: ["**/?(*.)+(test).tsx"], // *.test.tsx 파일만 실행
  moduleDirectories: ['node_modules', '<rootDir>/src'], // Jest가 `node_modules`를 제대로 찾도록 설정
  moduleFileExtensions: ["ts", "tsx", "js", "jsx", "json", "node"],
  setupFilesAfterEnv: ['<rootDir>/jest.setup.ts'],
};

export default config;
