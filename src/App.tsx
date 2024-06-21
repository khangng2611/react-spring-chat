import React from 'react';
import Login from './component/authentication/Login';
import Container from './component/container/Container';
import SessionContext from './context/SessionContext';
import { Route, Routes, BrowserRouter as Router} from 'react-router-dom';
import './App.css';

function App() {
  return (
    <Router>
      <SessionContext>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/chat" element={<Container />} />
        </Routes>
      </SessionContext>
    </Router>
  );
}

export default App;
