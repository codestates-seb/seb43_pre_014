import React, { useState } from 'react';
import styled from 'styled-components';
import TextEdit from './TextEdit';
import SubmittedAnswer from './SubmittedAnswer';
const AnswerWrapper = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  padding: 16px;
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

  return (
    <AnswerWrapper>
      <TextEdit value={answer} handleChange={handleAnswerChange} />
      <AnswerButtonWrapper>
        <AnswerButton onClick={handleSubmit} >Post Your Answer</AnswerButton>
        <ClearButton onClick={handleClear}>Clear</ClearButton>

      </AnswerButtonWrapper>
      {submittedAnswer && <SubmittedAnswer answer={submittedAnswer} handleDelete={handleDelete} />}
    </AnswerWrapper>
  );
};

export default Answer;