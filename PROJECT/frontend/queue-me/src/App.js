import {Routes, Route} from 'react-router-dom';
import LoginPresenter from './presenters/loginpresenter';
import Home from './views/page/home';
import React, { createContext } from 'react';
import Topbar from './presenters/topbar';

function App() {
  return (
    <>
      <Topbar/>
      <Routes>
        <Route exact path='/' element={<Home/>} />
        <Route exact path='/login' element={<LoginPresenter/>}/>
      </Routes>
    </>
  )
}

export default App;
