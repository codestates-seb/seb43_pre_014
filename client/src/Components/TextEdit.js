import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import styled from 'styled-components';

const QuillWrapper = styled.div`
  .ql-container {
    border: 1px solid #ccc;
    border-radius: 4px;
    height: 150px;
    width: 100%;
    margin-bottom: 16px;
    transition: border-color 0.2s ease-in-out;
  }

  .ql-container:focus-within {
    border-color: #0077cc;
    box-shadow: 0 0 0 3px #0077cc33;
  }
`;

function TextEdit({ text, setText }) {
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
    const textChange = (contents) => { setText(contents) }
    return (

        <QuillWrapper>
            <ReactQuill value={text} onChange={textChange} modules={modules} />
        </QuillWrapper>
    );
}

export default TextEdit;