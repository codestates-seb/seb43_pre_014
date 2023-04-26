import React from "react";
import { useSelector } from "react-redux";
import { Route, Routes, Navigate } from "react-router-dom";
import LoginForm from "./Components/LoginForm";
import HomePage from "./Components/HomePage";
import Header from "./Components/Header";
import EditProfile from "./Components/EditProfile";
// import Header from './Components/Header';
import Join from './Components/Join';
import Result from './Components/Result';
import Question from './Components/Question';
import Write from './Components/Write'
import styled from "styled-components"

import Answer from "./Components/Answer";
import AnswerList from "./Components/AnswerList";
import AnswerForm from "./Components/AnswerForm";
// "proxy": "https://6a4c-175-213-102-16.ngrok-free.app/"

const MainContent = styled.div`
    position: relative;
    top: 50px;
    height: calc(100vh - 50px);
    `;

function App() {

  const isAuthenticated = useSelector((state) => state.user.isAuthenticated);

  return (
    <>
      <Header />
      <MainContent>
        <Routes>
          <Route path="/write" element={<Write />} />
          <Route path="/question" element={<Question />} />
          <Route path="/join" element={<Join />} />
          <Route path="/result" element={<Result />} />
          <Route path="/EditProfile" element={<EditProfile></EditProfile>} />
          <Route path="/login" element={isAuthenticated ? <Navigate to="/" /> : <LoginForm />} />
          <Route path="/" element={isAuthenticated ? <HomePage /> : <Navigate to="/login" />} />
          <Route path="/Answer" element={<Answer />} />
          <Route path="/AnswerList" element={<AnswerList />} />
          <Route path="/ AnswerForm" element={<AnswerForm />} />
        </Routes>
      </MainContent>
    </>
  );
};

export default App;
