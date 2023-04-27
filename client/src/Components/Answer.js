// import React, { useState } from 'react';
// import styled from 'styled-components';
// import TextEdit from './TextEdit';
// import SubmittedAnswer from './SubmittedAnswer';
// import axiosInstance from '../axiosConfig';

// const AnswerWrapper = styled.div`
//   display: flex;
//   flex-direction: column;
//   border: 1px solid #ccc;
//   padding: 16px;
// `;

// const AnswerButtonWrapper = styled.div`
//   display: flex;
//   justify-content: space-between;
//   margin-top: 16px;
// `;

// const AnswerButton = styled.button`
//   padding: 8px;
//   background-color: #0a95ff;
//   color: #fff;
//   border: 1px solid #0a95ff;
//   border-radius: 3px;
//   box-shadow: rgba(255, 255, 255, 0.5) 0px 1px inset;
//   cursor: pointer;
//   &:hover {
//     background-color: #0074cc;
//   }
// `;

// const ClearButton = styled.button`
//   padding: 8px;
//   background-color: #fff;
//   color: #0a95ff;
//   border: 1px solid #0a95ff;
//   border-radius: 3px;
//   box-shadow: rgba(255, 255, 255, 0.5) 0px 1px inset;
//   cursor: pointer;
//   &:hover {
//     background-color: #0074cc;
//     color: #fff;
//   }
// `;


// const Answer = () => {
//   const [answer, setAnswer] = useState('');
//   const [submittedAnswer, setSubmittedAnswer] = useState('');
//   const [isEditing, setIsEditing] = useState(false);

//   const handleAnswerChange = (value) => {
//     setAnswer(value);
//   };

//   const handleClear = () => {
//     setAnswer('');
//   };

//   const handleEdit = () => {
//     setAnswer(submittedAnswer.answerText);
//     setIsEditing(true)
//   }



//   const handleSubmit = async () => {
//     try {
//       const response = await axiosInstance.post('board/answers', {
//         answerText: answer,
//         // 필요한 다른 필드를 추가하세요
//       });
//       setSubmittedAnswer(response.data);
//       setAnswer('');
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   try {
//     const response = await axiosInstance.put(`board/answers/1/${submittedAnswer.id}`, {
//       answerText: answer,
//       // 필요한 필드
//     });
//     setSubmittedAnswer(response.data);
//     setAnswer('');
//     setIsEditing(false);
//   } catch (error) {
//     console.log(error);
//   }
// };

// const handleDelete = async () => {
//   try {
//     await axiosInstance.delete(`board/answers/${submittedAnswer.id}`);
//     setSubmittedAnswer('');
//   } catch (error) {
//     console.log(error);
//   }
// };

//   return (
//     <AnswerWrapper>
//       <TextEdit value={answer} handleChange={handleAnswerChange} />
//       <AnswerButtonWrapper>
//         <AnswerButton onClick={handleSubmit} >Post Your Answer</AnswerButton>
//         <ClearButton onClick={handleClear}>Clear</ClearButton>

//       </AnswerButtonWrapper>
//       {submittedAnswer && <SubmittedAnswer answer={submittedAnswer} handleDelete={handleDelete} />}
//     </AnswerWrapper>
//   );
// };

// export default Answer;