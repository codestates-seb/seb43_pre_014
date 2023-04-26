import styled from 'styled-components';
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const ViewerWrapper = styled.div`
    padding: 0;
    margin: 0;

    .ql-container.ql-snow {
    border: none;
    }
`;

const Viewer = ({ problemText, expectingText }) => {
  const modules = {
    toolbar: false,
  };

  const formats = [
    "header",
    "bold",
    "italic",
    "underline",
    "strike",
    "blockquote",
    "list",
    "bullet",
    "indent",
    "link",
    "image",
    "code-block",
  ];

  return (
    <ViewerWrapper>
      <ReactQuill value={problemText} readOnly={true} modules={modules} formats={formats} />
      <ReactQuill value={expectingText} readOnly={true} modules={modules} formats={formats} />
    </ViewerWrapper>
  );
};

export default Viewer;
