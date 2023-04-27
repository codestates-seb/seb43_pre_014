import styled from "styled-components";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { useState } from "react";

const EditorWrapper = styled.div`
  box-sizing: border-box;
  
  .quill {
    border: 1px solid #BABFC4;
    border-radius: 5px;
  }
  
  .outline{  
    outline: 4px solid #D9EAF8;
    border: 1px solid #59A4DE;
    box-sizing: border-box;
  }
  
  .ql-toolbar {
    border: none;
    border-bottom: 1px solid #BABFC4;
  }

  .ql-container {
    border: none;
    height: 400px;
    box-sizing: border-box;
    overflow: auto;

    :focus {
      outline: none;
    }
  }
`;

const StyledReactQuill = styled(ReactQuill)`
  margin: 10px 0;
  box-sizing: border-box;
`;

const StackEditor = ({ text, setText }) => {
  // css 구현을 위해 만든 state와 함수
  const [isOutlineActive, setIsOutlineActive] = useState(false);

  const handleOutlineClick = () => {
    setIsOutlineActive(!isOutlineActive);
  };
  
  const handleBlur = () => {
    setIsOutlineActive(false);
  };

  // 안의 내용물이 전달 받은 state에 저장되게끔 + CSS 조정
  const handleChange = (content, delta, source, editor) => {
    setText(content);
  };

  // 모듈 안의 옵션, code-block을 위해 작성해둠.
  const modules = {
    toolbar: {
      container: [
        [{ header: [1, 2, false] }],
        ['underline', 'strike', 'blockquote'],
        [{ list: 'ordered' }, { list: 'bullet' }, 'link'],
        ['image', 'code-block'],
        ['clean'],
      ],
    },
  };

  return (
    <EditorWrapper onClick={handleOutlineClick} onBlur={handleBlur}>
      <StyledReactQuill className={isOutlineActive ? 'outline' : ''}
        value={text}
        onChange={handleChange}
        modules={modules}
      />
    </EditorWrapper>
  );
};

export default StackEditor;
