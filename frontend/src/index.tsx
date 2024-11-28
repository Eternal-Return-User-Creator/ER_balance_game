import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './assets/css/index.css';
import './assets/css/Header.css';
import './assets/css/Footer.css';
import Main from './component/Main.tsx';
import Header from './component/Header.tsx';
import Footer from './component/Footer.tsx';
import CreateQuestion from './page/CreateQuestion.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/create" element={<CreateQuestion />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  </StrictMode>,
);
