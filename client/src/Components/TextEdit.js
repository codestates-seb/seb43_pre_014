import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import styled from 'styled-components';

const QuillWrapper = styled.div`
  .ql-container {
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    height: 150px;
    margin-bottom: 16px;
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
                // [{ header: [1, 2, false] }],
                ['bold', 'italic'],
                // 'underline', 'strike', ],
                ['link', 'blockquote', 'code-block', 'image'],
                [{ list: 'ordered' }, { list: 'bullet' }, { 'align': [] }],
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