import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './assets/css/index.css'
import './assets/css/Header.css'
import Main from './component/Main.tsx'
import Header from './component/Header.tsx'
import Footer from './component/Footer.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Header />
    <Main />
    <Footer />
  </StrictMode>,
)
