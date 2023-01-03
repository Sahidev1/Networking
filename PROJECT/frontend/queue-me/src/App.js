import { Routes, Route, Navigate } from 'react-router-dom';
import LoginPresenter from './presenters/loginpresenter';
import Home from './views/page/home';
import React, { useState, useEffect } from 'react';
import Topbar from './presenters/topbar';
import { useWebSocket } from 'react-use-websocket/dist/lib/use-websocket';
import { ReadyState } from 'react-use-websocket';
import { getPostOptions, getURL } from './util/apihelpers';
import { checkAuth } from './util/helpers';

async function mapsocketToSession (){
  const retURL = getURL('map/');
  const keyval = sessionStorage.getItem('wskey');
  const options = getPostOptions ({
    'wskey': keyval
  });

  try {
    const resp = await fetch(retURL, options);
    const data = await resp.json();
    console.log(data);
    return data;
  } catch (error){
    console.log(error);
  }
}

function App() {
  const auth = checkAuth();
  const result = sessionStorage.getItem('wskey');
  const [waiting, setWaiting] = useState(false);
  console.log(waiting);
  // const [messageHistory, setMessageHistory] = useState([]);
  const { sendMessage, lastMessage } = useWebSocket('ws://localhost:8080', {share:true});
  if (!result && !waiting) {
    sendMessage('GETKEY');
    setWaiting(true);
  }
  else if (!waiting){
    sendMessage('GETKEY');
    setWaiting(true);
  }

  useEffect(() => {
    if (lastMessage !== null && lastMessage.data.includes("wskey")) {
      console.log("HAS");
      const myobj = JSON.parse(lastMessage.data);
      console.log(myobj.wskey);
      sessionStorage.setItem('wskey', myobj.wskey);
      mapsocketToSession();
    }
    else if (lastMessage !== null){
      console.log(lastMessage.data);
    }
  }, [lastMessage]);

  /*useEffect(() => {
    const send = () => {
      sendMessage("hello");
    }
    send();
  },[])*/

  return (
    <>
      <Topbar />
      <Routes>
        <Route exact path='/' element={auth?<Home />:<Navigate to="/login"/>}  />
        <Route exact path='/login' element={auth?<Navigate to="/"/>:<LoginPresenter />} />
      </Routes>
    </>
  )
}

export default App;
