import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './assets/css/index.css';
import './assets/css/Header.css';
import './assets/css/Main.css';
import './assets/css/Footer.css';
import Main from './component/Main.tsx';
import Header from './component/Header.tsx';
import Footer from './component/Footer.tsx';
import CreateQuestion from './page/CreateQuestion.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Header/>
      <main>
        <Routes>
          <Route path="/" element={ <Main/> }/>
          <Route path="/create" element={ <CreateQuestion/> }/>
        </Routes>
      </main>
      <Footer/>
    </BrowserRouter>
  </StrictMode>,
);
