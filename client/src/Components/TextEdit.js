import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

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
        <div>
            <ReactQuill modules={modules} />
        </div>
    );
}

export default TextEdit;