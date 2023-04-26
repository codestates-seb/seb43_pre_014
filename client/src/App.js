import React from "react";
// import { useSelector } from "react-redux";
import { Route, Routes } from "react-router-dom";
import LoginForm from "./Components/LoginForm";
import HomePage from "./Components/HomePage";
import LoginHeader from "./Components/Header";
import Header from "./Components/Header";
import EditProfile from "./Components/EditProfile";
import PrivateRoute from "./Components/PrivateRoute";
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
  // 질문 글 작성하고 그 질문글 페이지로 이동하기 위해 작성
  // const rand = Math.random().toString(16).substr(2, 8);

  return (
    <>
      {isAuthenticated ? <Header /> : <LoginHeader />}
      <MainContent>
        <Routes>
          <Route path="/" element={<LoginForm />} />
          <Route path="/write" element={<PrivateRoute />} />
          <Route index element={<Write />} />
          <Route path="/question" element={<PrivateRoute />} />
          <Route index element={<Question />} />
          <Route path="/join" element={<Join />} />
          <Route path="/result" element={<Result />} />
          <Route path="/editProfile" element={<PrivateRoute />} />
          <Route index element={<EditProfile />} />
          {/* 추후 메인 페이지가 작성되면 LoginForm을 /login으로 바꾸고 매안페이지를 /로 설정하기 */}
          {/* <Route path="/" element={<PrivateRoute />} /> */}
          <Route index element={<HomePage />} />
          {/* <Route path="/write" element={<Write />} />
          <Route path="/question" element={<Question />} />
          <Route path="/join" element={<Join />} />
          <Route path="/result" element={<Result />} />
          <Route path="/EditProfile" element={<EditProfile></EditProfile>} />
          <Route path="/login" element={isAuthenticated ? <Navigate to="/" /> : <LoginForm />} />
          <Route path="/" element={isAuthenticated ? <HomePage /> : <Navigate to="/login" />} />
          <Route path="/Answer" element={<Answer />} />
          <Route path="/AnswerList" element={<AnswerList />} />
          <Route path="/ AnswerForm" element={<AnswerForm />} /> */}
        </Routes>
      </MainContent>
    </>
  );
};

export default App;
