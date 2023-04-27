import React, { useState } from 'react';
import styled from 'styled-components';

import 'react-quill/dist/quill.snow.css';

const AnswerWrapper = styled.div`
  border: 1px solid #ccc;
  padding: 16px;
  margin-top: 16px;
`;



const DeleteButton = styled.button`
  margin-top: 8px;
  padding: 8px;
  background-color: #fff;
  color: #ff4444;
  border: 1px solid #ff4444;
  border-radius: 3px;
  box-shadow: rgba(255, 255, 255, 0.5) 0px 1px inset;
  cursor: pointer;
  &:hover {
    background-color: #ff4444;
    color: #fff;
  }
`;

const SubmittedAnswer = ({ answer, handleDelete }) => {
  return (
    <AnswerWrapper>

      <div dangerouslySetInnerHTML={{ __html: answer }} />
      <DeleteButton onClick={handleDelete}>Delete Answer</DeleteButton>
    </AnswerWrapper>
  );
};

export default SubmittedAnswer;