import TextEdit from '../TextEdit';

const ModifyAnswer = ({ answer, setAnswer, handleUpdate }) => {
  return (
    <div>
      <TextEdit value={answer} handleChange={setAnswer} />
      <button onClick={handleUpdate}>Save Changes</button>
    </div>
  );
};

export default ModifyAnswer;