
// import React, { useState } from 'react';
// import Answer from '../Answer/Answer';
// import styled from 'styled-components';

// const answers = [
//   { id: 1, text: 'Answer 1' },
//   { id: 2, text: 'Answer 2' },
//   { id: 3, text: 'Answer 3' },
// ];

// const AnswerListWrapper = styled.div`
//   display: flex;
//   flex-direction: column;
//   gap: 16px;
//   margin-top: 16px;
// `;

// const AnswerList = () => {
//   const [answerList, setAnswerList] = useState(answers);

//   const handleAddAnswer = () => {
//     const newAnswerList = [
//       ...answerList,
//       { id: answerList.length + 1, text: 'New Answer' },
//     ];
//     setAnswerList(newAnswerList);
//   };

//   const handleDeleteAnswer = (id) => {
//     const newAnswerList = answerList.filter((answer) => answer.id !== id);
//     setAnswerList(newAnswerList);
//   };

//   return (
//     <>
//       <button onClick={handleAddAnswer}>Add Answer</button>
//       <AnswerListWrapper>
//         {answerList.map((answer) => (
//           <Answer
//             key={answer.id}
//             id={answer.id}
//             text={answer.text}
//             onDelete={handleDeleteAnswer}
//           />
//         ))}
//       </AnswerListWrapper>
//     </>
//   );
// };

// export default AnswerList;