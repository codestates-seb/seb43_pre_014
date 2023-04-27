import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import styled from 'styled-components';

const QuillWrapper = styled.div`
  .ql-container {
    border: 1px solid #ccc;
    border-radius: 4px;
    height: 150px;
    transition: border-color 0.2s ease-in-out;
  }

  .ql-container:focus-within {
    border-color: #0077cc;
    box-shadow: 0 0 0 3px #0077cc33;
  }
`;

function TextEdit() {
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
        <QuillWrapper>
            <ReactQuill modules={modules} />
        </QuillWrapper>
    );
}

export default TextEdit;