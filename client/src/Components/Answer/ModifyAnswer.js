import TextEdit from '../TextEdit';
import axiosInstance from '../../axiosConfig';

const ModifyAnswer = ({ answer, setAnswer }) => {

  const handleUpdate = () => {
    axiosInstance.patch(`/board/answers/${answer.id}`, {
        answer,
      })
      .then((response) => {
        console.log(response);
        // 이후 상태 업데이트 및 UI 변경을 처리하는 코드
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <TextEdit value={answer} handleChange={setAnswer} />
      <button onClick={handleUpdate}>Save Changes</button>
    </div>
  );
};

export default ModifyAnswer;