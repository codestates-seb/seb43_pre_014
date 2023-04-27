import React from "react";
import { useSelector } from "react-redux";
import { Route, Routes } from "react-router-dom";
import styled from "styled-components"
import Header from "./Components/Header/Header";
import LoginHeader from "./Components/Header/LoginHeader";
import HomePage from "./Components/HomePage";
import LoginForm from "./Components/Login/LoginForm";
import Join from './Components/Join/Join';
import Result from './Components/Join/Result';
import Question from './Components/Question/Questions';
import Comments from "./Components/Question/Comments";
import Write from './Components/Question/Write'
import Modify from './Components/Question/Modify'
import EditProfile from "./Components/EditProfile";
import PrivateRoute from "./Components/PrivateRoute";
import Answer from "./Components/Answer";
import AnswerList from "./Components/AnswerList";
import SubmittedAnswer from "./Components/SubmittedAnswer";
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
          <Route path="/" element={<LoginForm />} />
          <Route path="/write" element={<PrivateRoute />} />
          <Route index element={<Write />} />
          <Route path="/question" element={<PrivateRoute />} />
          <Route index element={<Question />} />
          <Route path="/write" element={<Write />} />
          <Route path="/modify/:id" element={<Modify />} />
          <Route path="/question" element={<HomePage />} />
          <Route path="/question/:id" element={<Question />} />
          <Route path="/comments" element={<Comments />} />

          <Route path="/join" element={<Join />} />
          <Route path="/result" element={<Result />} />
          <Route path="/editProfile" element={<PrivateRoute />} />
          <Route index element={<EditProfile />} />
          {/* 추후 메인 페이지가 작성되면 LoginForm을 /login으로 바꾸고 매안페이지를 /로 설정하기 */}
          {/* <Route path="/" element={<PrivateRoute />} /> */}
          <Route index element={<HomePage />} />
        </Routes>
      </MainContent>
    </>
  );
};

export default App;
