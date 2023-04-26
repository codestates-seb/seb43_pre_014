import React from 'react';
import styled from 'styled-components';
import TextEdit from './TextEdit';
const AnswerWrapper = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  padding: 16px;
`;

const AnswerText = styled.p`
  margin: 0;
`;

const AnswerButtonWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 16px;
`;

const AnswerButton = styled.button`
  padding: 8px;
  background-color: ${(props) => props.color};
  color: #fff;
  border: none;
  cursor: pointer;
  &:hover {
    opacity: 0.8;
  }
`;

const Answer = ({ id, text, onDelete }) => {
  const handleDeleteClick = () => {
    onDelete(id);
  };

  return (
    <AnswerWrapper>
      <AnswerText>{text}</AnswerText>
      <TextEdit

      />
      <AnswerButtonWrapper>
        <AnswerButton color="#0077cc">Post Your Answer</AnswerButton>
        {/* <AnswerButton color="#cc0000" onClick={handleDeleteClick}>

          Delete
        </AnswerButton> */}
      </AnswerButtonWrapper>
    </AnswerWrapper>
  );
};

export default Answer;