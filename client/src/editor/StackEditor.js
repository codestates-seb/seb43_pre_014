import styled from "styled-components";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const EditorWrapper = styled.div`
  height: 400px;
  box-sizing: border-box;
`;

const StyledReactQuill = styled(ReactQuill)`
  margin: 10px 0;
  height: 350px;
  box-sizing: border-box;
`;

const StackEditor = ({ text, setText }) => {
  const handleChange = (content, delta, source, editor) => {
    setText(content);
  };

  return (
    <EditorWrapper>
      <StyledReactQuill
        value={text}
        onChange={handleChange}
        modules={{
          clipboard: { matchVisual: false },
        }}
      />
    </EditorWrapper>
  );
};

export default StackEditor;