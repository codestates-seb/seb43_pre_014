import styled from "styled-components";
import ReactPaginate from 'react-paginate';
import { useState } from "react";

const PaginateContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;

  ul {
    display: flex;
    justify-content: center;
    align-items: center;
    list-style: none;

    li {
      margin: 0 5px;
      cursor: pointer;

      border: 1px solid #D6D9DC;
      padding: 5px 10px;
      border-radius: 5px;
      box-sizing: border-box;
      font-size: 9pt;

      &.previousPage,
      &.nextPage {
        color: #fff;
        background-color: #007bff;
        border-color: #007bff;
        padding: 5px 10px;
        border-radius: 5px;

        :hover {
        background-color : #D6D9DC !important;
        }
      }

      &.paginationDisabled {
        color: #6c757d;
        pointer-events: none;
      }

      &.paginationActive {
        color: #fff;
        background-color: #F48225 !important;
        border-color: #007bff;
        border: 1px solid #F48225 !important;
      }

      :hover {
        background-color : #D6D9DC;
      }
    }
  }
`;

const Question = styled.div`
  width: 100%;
  display: flex;
  border-bottom: 1px solid #DFE2E4;

  .sub {
    width: 190px;
    margin: 10px 15px 0 0px;

      ul {
      list-style: none;
      font-size: 10pt;
      margin: 0;
      padding: 0;
      text-align: right;

      li {
        margin: 10px;

        :first-child { color: black; }
        :nth-child(2) { color : #6A737C; }
        :last-child { color : #83690B; }
      }
    }
  }

  .main {
    width: 100%;

    >ul {
    margin-top: 15px;
    list-style: none;
    padding: 0;

    >li h3 {
      font-weight: 400;
      margin: 0 0 5px 0;
      padding: 0;
      cursor: pointer;

      a, a:visited {
        text-decoration: none;
        color: #0063BF;
      }

      :hover { color : #0A95FF; }
    }

    >li:nth-child(2) {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      font-size: 10pt;
      color: #3B4045;
      margin: 0;
      max-width: 100%;
      height: 35px;
      overflow: hidden;
      text-overflow: ellipsis;
      -webkit-line-clamp: 2;
    }

    >li:nth-child(3) {
      display: flex;
      margin: 10px 0 5px 0;

      div {
      font-size: 8pt;
      background-color : #D9EAF7;
      border-radius: 3px;
      color: #39739D;

      padding: 3px 7px;
      margin-right: 5px;

      cursor: pointer;

      :hover {
      background-color: #D0E3F1;
      color: #2C5877;
      }
    }}

    >li:last-child {
      display: flex;
      justify-content: end;
      
      >ul {
        display: flex;
        list-style: none;
        margin: 0;
        padding: 0;
        font-size: 9pt;

        >li:first-child {
          color: #0074CC;
          display: flex;
          margin-right: 5px;

          >div {
            width: 16px;
            height: 16px;
            background-color: red;
            display: inline-block;
            margin-right: 5px;
          }
        }

        >li:nth-child(2) {
          color: #525960;
          margin-right: 5px;

          >span {
            font-weight: 600;
            margin-right: 5px;
          }
        }

        >li:last-child { color : #808A94; }
    }}
  }}
`;



const QuestionBox = ({questions}) => { 
  const [pageNumber, setPageNumber] = useState(0);
  const questionsPerPage = 10;
  const pagesVisited = pageNumber * questionsPerPage;
  
  const displayQuestions = questions
    .slice(pagesVisited, pagesVisited + questionsPerPage)
    .map((question, index) => (
      <Question key={index}>
      <div className="sub">
          <ul>
              <li><span>80</span>votes</li>
              {/* 혹시 질문글에 답글이 달리면 상태가 어떻게 변하는지 물어보고 삼항연산자로 바꾸기 */}
              <li><span>0</span>answers</li>
              <li><span>3k</span>views</li>
          </ul>
      </div>
      <div className="main">
          <ul>
            <li><h3><a href={`http://localhost:3000/question/${question.id}`}>{question.title}</a></h3></li>
            <li>{question.problem.replace(/(<([^>]+)>)/gi, '')}</li>
            <li> {question.tags.map((tag) => (
                              <div key={tag}>{tag}</div>
                  ))}</li>
            <li><ul>
              <li><div className="userImg"></div>casolorz</li>
              <li><span>8,206</span>asked</li>
              <li>Jun 6, 2017 at 18:54</li>
            </ul></li>
          </ul>
      </div>
    </Question>
    ));

  const pageCount = Math.ceil(questions.length / questionsPerPage);

  const handlePageClick = ({selected}) => {
    setPageNumber(selected);
  };

return (
<>
      {displayQuestions}
      <PaginateContainer>
      <ReactPaginate
        previousLabel={"Prev"}
        nextLabel={"Next"}
        pageCount={pageCount}
        onPageChange={handlePageClick}
        containerClassName={"pagination"}
        previousLinkClassName={"previousPage"}
        nextLinkClassName={"nextPage"}
        disabledClassName={"paginationDisabled"}
        activeClassName={"paginationActive"}
      />
    </PaginateContainer>
</>
);
}

export default QuestionBox;