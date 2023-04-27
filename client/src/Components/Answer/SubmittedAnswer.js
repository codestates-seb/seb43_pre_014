import styled from 'styled-components';
import axiosInstance from '../../axiosConfig';

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

const SubmittedAnswer = ({ answer}) => {
  const handleDelete = () => {
    axiosInstance.delete(`board/answers/${answer.id}`)
      .then((response) => {
        console.log(response);
        // 이후 상태 업데이트 및 UI 변경을 처리하는 코드
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <AnswerWrapper>

      <div dangerouslySetInnerHTML={{ __html: answer }} />
      <DeleteButton onClick={handleDelete}>Delete Answer</DeleteButton>
    </AnswerWrapper>
  );
};

export default SubmittedAnswer;