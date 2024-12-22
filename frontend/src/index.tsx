import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './assets/css/index.css';
import './assets/css/Header.css';
import './assets/css/Main.css';
import './assets/css/Footer.css';
import Header from './component/Header.tsx';
import Footer from './component/Footer.tsx';
import CreateQuestion from './page/CreateQuestion.tsx';
import Game from "./page/Game.tsx";
import MainPage from "./page/MainPage.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Header/>
      <main>
        <Routes>
          <Route path="/" element={ <MainPage/> }/>
          <Route path="/create" element={ <CreateQuestion/> }/>
          <Route path="/game" element={ <Game/> }/>
        </Routes>
      </main>
      <Footer/>
    </BrowserRouter>
  </StrictMode>,
);
