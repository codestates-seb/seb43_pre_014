import React, { useState } from 'react';
import styled from 'styled-components';
import TextEdit from '../TextEdit';
import SubmittedAnswer from '../Answer/SubmittedAnswer';

const AnswerWrapper = styled.div`
  display: flex;
  flex-direction: column;
  /* border: 1px solid #ccc; */
  padding: 16px;
  margin : 20px;
  width: 80%;
  p {
    display: block;
    font-size: 1.5em;
    margin-block-start: 0.83em;
    margin-block-end: 0.83em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    /* font-weight: bold; */
  }
  h2 {
    display: block;
    font-size: 1.5em;
    margin-block-start: 0.83em;
    margin-block-end: 0.83em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    font-weight: bold;
}
`;

const AnswerListWrapper = styled.div`
display: flex;
flex-direction: column;
align : right;
`

const EditButton = styled.button`
  background: none;
  border: none;
  color: #aaa;
  cursor: pointer;
  font-size: 0.8rem;
  padding: 0;
  margin-left: 0.5rem;
`;



const AnswerButtonWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 16px;
`;

const AnswerButton = styled.button`
  padding: 8px;
  background-color: #0a95ff;
  color: #fff;
  border: 1px solid #0a95ff;
  border-radius: 3px;
  box-shadow: rgba(255, 255, 255, 0.5) 0px 1px inset;
  cursor: pointer;
  &:hover {
    background-color: #0074cc;
  }
`;

const ClearButton = styled.button`
  padding: 8px;
  background-color: #fff;
  color: #0a95ff;
  border: 1px solid #0a95ff;
  border-radius: 3px;
  box-shadow: rgba(255, 255, 255, 0.5) 0px 1px inset;
  cursor: pointer;
  &:hover {
    background-color: #0074cc;
    color: #fff;
  }
`;


const Answer = () => {
  const [answer, setAnswer] = useState('');
  const [submittedAnswer, setSubmittedAnswer] = useState('');

  const handleAnswerChange = (value) => {
    setAnswer(value);
  };

  const handleClear = () => {
    setAnswer('');
  };



  const handleSubmit = () => {
    // 답변 작성을 완료하고, 작성된 답변을 저장하거나 다른 작업을 수행하는 함수
    setSubmittedAnswer(answer);
    setAnswer('');

    console.log(answer);
  };

  const handleDelete = () => {
    setSubmittedAnswer('');
  };

  const handleEdit = () => {
    setAnswer(submittedAnswer);
    setSubmittedAnswer('');
  }

  return (
    <AnswerWrapper>
      <AnswerListWrapper>
        {submittedAnswer && <SubmittedAnswer answer={submittedAnswer} handleDelete={handleDelete} />}
        <EditButton onClick={handleEdit}> Edit </EditButton>
      </AnswerListWrapper>
      <div><p> Your Answer</p></div>
      <TextEdit text={answer} setText={setAnswer} handleChange={handleAnswerChange} />
      <AnswerButtonWrapper>
        <AnswerButton onClick={handleSubmit} >Post Your Answer</AnswerButton>
        <ClearButton onClick={handleClear}>Clear</ClearButton>

      </AnswerButtonWrapper>
    </AnswerWrapper>
  );
};
