import React from "react";
import { useSelector } from "react-redux";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import LoginForm from "./Components/LoginForm";
import HomePage from "./Components/HomePage";
import Header from "./Components/Header";
// "proxy": "https://6a4c-175-213-102-16.ngrok-free.app/"

import EditProfile from "./components/EditProfile";

function App() {
  return (
    <>
      <Header />
      <BrowserRouter>
        <Routes>
          <Route
            path="/EditProfile"
            element={<EditProfile></EditProfile>} >

          </Route>
          <Route
            path="/login"
            element={isAuthenticated ? <Navigate to="/" /> : <LoginForm />}
          />
          <Route
            path="/"
            element={isAuthenticated ?
              <HomePage>
              </HomePage> : <Navigate to="/login" />}
          />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default App;
