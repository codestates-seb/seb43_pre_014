import styled from 'styled-components';

import 'react-quill/dist/quill.snow.css';

const AnswerWrapper = styled.div`
  border: 1px solid #ccc;
  padding: 16px;
  margin-top: 16px;
`;

const AnswerTitle = styled.h3`
  margin-top: 0;
`;

const SubmittedAnswer = ({ answer }) => {
  return (
    <AnswerWrapper>
      <AnswerTitle>Submitted Answer:</AnswerTitle>
      <div dangerouslySetInnerHTML={{ __html: answer }} />
    </AnswerWrapper>
  );
};

export default SubmittedAnswer;