import React from "react";
import { useSelector } from "react-redux";
import { Route, Routes } from "react-router-dom";
import styled from "styled-components"
import Header from "./Components/Header/Header";
import LoginHeader from "./Components/Header/LoginHeader";
import HomePage from "./Components/HomePage";
import Join from './Components/Join/Join';
import Result from './Components/Join/Result';
import Question from './Components/Question/Questions';
import Comments from "./Components/Question/Comments";
import Write from './Components/Question/Write'
import Modify from './Components/Question/Modify'
import PrivateRoute from "./Components/PrivateRoute";
import LoginForm from "./Components/Login/LoginForm";
import EditProfile from "./Components/EditProfile";
import Answer from "./Components/Answer/Answer";
import SubmittedAnswer from "./Components/Answer/SubmittedAnswer";
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
      {isAuthenticated ? <Header /> : <LoginHeader />}
      <MainContent>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/join" element={<Join />} />
          <Route path="/question" element={<PrivateRoute />} />
          <Route path="/write" element={<Write />} />
          <Route path="/modify/:id" element={<Modify />} />
          <Route path="/question" element={<HomePage />} />
          <Route path="/question/:id" element={<Question />} />
          <Route path="/comments" element={<Comments />} />
          <Route path="/result" element={<Result />} />
          <Route path="/editProfile" element={<PrivateRoute />} />
        </Routes>
      </MainContent>
    </>
  );
};

export default App;
