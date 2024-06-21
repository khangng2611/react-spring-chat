import React, { useEffect, useState } from 'react';
import ChatContainer from './component/container/Container';
import Login from './component/authentication/Login';
import './App.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const userLoggedIn = localStorage.getItem('session');
    setIsLoggedIn(!!userLoggedIn);
  }, []);

  // if (!isLoggedIn) {
  //     return <Login />;
  // }
  return (
    <>
      {!isLoggedIn && <Login setIsLoggedIn={setIsLoggedIn} />}
      {isLoggedIn && <ChatContainer />}
    </>
  );
}

export default App;
