import React from "react";
// import { useSelector } from "react-redux";
import { Route, Routes } from "react-router-dom";
import LoginForm from "./Components/LoginForm";
import HomePage from "./Components/HomePage";
import Header from "./Components/Header";
import EditProfile from "./Components/EditProfile";
import PrivateRoute from "./Components/PrivateRoute";
// import Header from './Components/Header';
import Join from './Components/Join';
import Result from './Components/Result';
import Question from './Components/Question';
import Write from './Components/Write'
import styled from "styled-components"
// "proxy": "https://6a4c-175-213-102-16.ngrok-free.app/"

const MainContent = styled.div`
    position: relative;
    top: 50px;
    height: calc(100vh - 50px);
    `;

function App() {
  
  // const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
  
  return (
    <>
      <Header />
      <MainContent>
        <Routes>
          <Route path="/write" element={<PrivateRoute />} />
          <Route index element={<Write />} />
          <Route path="/question" element={<PrivateRoute />} />
          <Route index element={<Question />} />
          <Route path="/join" element={<Join />} />
          <Route path="/result" element={<Result />} />
          <Route path="/EditProfile" element={<PrivateRoute />} />
          <Route index element={<EditProfile />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/" element={<PrivateRoute />} />
          <Route index element={<HomePage />} />
        </Routes>
      </MainContent>
    </>
  );
};

export default App;
