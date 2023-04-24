import { Route, Routes } from 'react-router-dom';
import './App.css';
import Header from './component/Header';
import Join from './component/Join';
import Result from './component/Result';
import Question from './component/Question';
import Write from './component/Write'
import styled from "styled-components"

const MainContent = styled.div`
    position: relative;
    top: 50px;
    height: calc(100vh - 50px);
`;

function App() {
  return (
    <>
      <Header />
      <MainContent>
        <Routes>
            <Route path="/write" element={<Write />} />
            <Route path="/question" element={<Question />} />
            <Route path="/join" element={<Join />} />
            <Route path="/result" element={<Result />} />
        </Routes>
      </MainContent>
    </>
  );
}

export default App;
