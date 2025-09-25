import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import { defineConfig, globalIgnores } from 'eslint/config'

export default defineConfig([
  globalIgnores(['dist', 'build', 'coverage']),
  // 1) 브라우저(소스 코드)
  {
    files: ['src/**/*.{js,jsx}', '**/*.{js,jsx}'],
    ignores: ['node_modules/**'],
    extends: [
      js.configs.recommended,
      reactHooks.configs['recommended-latest'],
      reactRefresh.configs.vite,
    ],
    languageOptions: {
      ecmaVersion: 'latest',
      globals: globals.browser,
      parserOptions: { ecmaFeatures: { jsx: true }, sourceType: 'module' },
    },
    rules: {
      'no-unused-vars': ['error', { varsIgnorePattern: '^[A-Z_]' }],
    },
  },
  // 2) Node 환경 파일(설정 스크립트들)
  {
    files: [
      'vite.config.{js,ts,mjs,cjs}',
      'tailwind.config.{js,ts}',
      'postcss.config.{js,ts}',
      'eslint.config.{js,ts}',
      '*.config.{js,ts}', // 기타 config들
    ],
    languageOptions: {
      ecmaVersion: 'latest',
      globals: {
        ...globals.node,
      },
      sourceType: 'module',
    },
  },
])
