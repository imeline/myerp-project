import React, { Suspense, lazy } from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Layout from './layout/Layout'

const Home = lazy(() => import('./pages/Home'))
const About = lazy(() => import('./pages/About'))
const Reserve = lazy(() => import('./pages/Reserve'))

export default function App() {
  return (
    <BrowserRouter>
      <Suspense fallback={<div className="p-6 text-slate-600 dark:text-slate-300">로딩…</div>}>
        <Routes>
          <Route element={<Layout />}>
            <Route index element={<Navigate to="/home" replace />} />
            <Route path="/home" element={<Home />} />
            <Route path="/about" element={<About />} />
            <Route path="/reserve" element={<Reserve />} />
          </Route>
          <Route path="*" element={<div className="p-6">404</div>} />
        </Routes>
      </Suspense>
    </BrowserRouter>
  )
}
