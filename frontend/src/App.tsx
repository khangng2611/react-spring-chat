import React from 'react';
import Login from './component/authentication/Login';
import Container from './component/container';
import SessionContextProvider from './context/SessionContext';
import { Route, Routes, BrowserRouter as Router } from 'react-router-dom';
import './App.css';
import WebsocketsContextProvider from './context/WebsocketsContext';

function App() {
  return (
    <Router>
      <SessionContextProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/chat" element={(
            <WebsocketsContextProvider>
              <Container />
            </WebsocketsContextProvider>
          )}
          />
        </Routes>
      </SessionContextProvider>
    </Router>
  );
}

export default App;
