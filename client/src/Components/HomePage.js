import axiosInstance from "../axiosConfig";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import QuestionBox from "./QuestionBox";
import SideMenu from "./Sidemenu";
import { useParams } from "react-router";

const DisplayFlex = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
`;

const MainBox = styled.div`
  width: 1204px;
  display: flex;
  align-items: stretch;
  justify-content: center;
`;

const Questions = styled.div`
  width: 100%;
`;

const QuestionsHeader = styled.div`
  height: 120px;
  border-bottom: 1px solid lightgray;
  padding-top: 25px;

  >div:first-child {
      display: flex;
      margin: 0 0 20px 25px;
      
      h2 {
          width: 100%;
          padding: 0;
          margin: 0;
          font-weight: 400;

          a {
              color: black;
              text-decoration: none;
          }
      }
      
      button {
          width: 140px;
          height: 40px;
          margin: 0 0 0 15px;
          background-color: #0A95FF;
          border: 1px solid #0A95FF;
          color: white;
          border-radius: 3px;
          box-shadow: inset 0px 1px rgba(255, 255, 255, .5);

          :hover {
              box-shadow: inset 50px 50px rgba(0, 0, 0, .2);
          }
      }
  }

  .black {
      color: black;
  }

  .menu {
    display: flex;
    justify-content: space-between;
    margin-left: 25px;

    >div{
      :first-child {
      width: 250px;
      font-size: 13pt;
      }

      :nth-child(2) ul {
        display: flex;

        cursor: pointer;
        list-style: none;
        color: #6A737C;
        margin: 0;
        padding: 0;
        font-size: 9pt;
        border-radius: 3px;
        border: 1px solid #9FA6AD;
        
        li {
          border-left: 1px solid #9FA6AD;
          margin-left: -1px;
          padding: 10px 9px;

          :nth-child(4) {
            background-color: #E3E6E8;
          }

          :hover {
            background-color: #F8F9F9;
          }

          span {
            width: 20px;
            height: 15px;
            color: white;
            text-align: center;
            margin-left: 5px;
            padding: 1px 5px;
            border-radius: 3px;
            font-size: 8pt;
            background-color: #0074CC;
            display: inline-block;
          }
        }
      }
    }

    >div:last-child
      {
        display: flex;
        cursor: pointer;
        
        >div:last-child {
        display: flex;
        align-items: center;
        justify-content: center;

        width: 50px;
        height: 17px;
        border-radius: 3px;
        text-align: center;
        font-size: 9pt;
        padding: 10px 9px;
        background-color: #E1ECF4;
        border: 1px solid #7AA7C7;
        margin-left: 20px;
        color: #39739D;
        box-shadow: inset 0px 1px rgba(255, 255, 255, .5);

        :hover {
          background-color: #B3D3EA;
        }

        svg {fill: #39739D}
      }
    }
  }
`;

const QuestionContainer = styled.div`
  width: 100%;
`

const HomePage = () => { // homepage 파트는 메인페이지가 나왔을 때 바꿔주자.

  const [questions, setQuestions] = useState(null);
  const {id} = useParams();

  useEffect(() => {
    // baseURL과 axiosInstance 설정을 사용하여 API를 호출합니다.
    axiosInstance
      .get(`/board/questions/${id}`, {
        params: {
          page: 0,
          size: 10,
          sort: "questionId,desc",
        },
      })
      .then((res) => {
        setQuestions(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [id]);

  return (
  <>
  {questions &&
    <DisplayFlex>
      <MainBox>
        <SideMenu />
        <Questions>
          <QuestionsHeader>
            <div>
                <h2>All Questions</h2>
                <button onClick={() => window.location.href = '/write'}>Ask Question</button>
            </div>
            <div className="menu">
              <div>{questions.length.toLocaleString()} questions with no upvoted or accepted answers</div>
              <div>
                <div>
                  <ul>
                    <li>Newest</li>
                    <li>Active</li>
                    <li>Bountied<span>223</span></li>
                    <li>Unanswered</li>
                    <li>More ▼</li>
                  </ul>
                </div>
                <div>
                <svg aria-hidden="true" className="svg-icon iconFilter" width="18" height="18" viewBox="0 0 18 18"><path d="M2 4h14v2H2V4Zm2 4h10v2H4V8Zm8 4H6v2h6v-2Z"></path></svg>Filter
                </div>
              </div>
            </div>
          </QuestionsHeader>
          <QuestionContainer>
                <QuestionBox questions={questions}/>
          </QuestionContainer>
        </Questions>
      </MainBox>
    </DisplayFlex>
  }
  </>
  );
};

export default HomePage;