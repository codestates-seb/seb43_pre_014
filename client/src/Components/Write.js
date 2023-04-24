import React, { useRef } from 'react';
import { useState } from "react";
import { useForm } from "react-hook-form";
import styled from "styled-components"
import StackEditor from "../editor/StackEditor";
import axios from "axios";

// import { Editor } from '@toast-ui/editor';
// import '@toast-ui/editor/dist/toastui-editor.css';

const DisplayFlex = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
    box-sizing: border-box;
    background-color: #F8F9F9;
`;

const WriteMain = styled.div`
    width: 1204px;

    .disabled {
        opacity: 0.5;
        pointer-events: none;
        cursor: not-allowed;

        button {
            pointer-events: none;
        }
    }

    .hide {
        display: none;
    }
`;

const WriteText = styled.div`

    h1{
        font-weight: 500;
        padding: 45px 0 65px 0;
        margin: 0;
        font-size: 20pt;
        background-image: url(./bg.PNG);
    }

    h5 {
        font-weight: 500;
        padding: 0;
        margin: 12px 0 5px 0;
    }

    div {
        width: 70%;
        background-color: #EBF4FB;
        padding: 20px;
        border-radius: 5px;
        border: 1px solid #A6CEED;
        box-sizing: border-box;
        color: #3B4045;

        h3 {
            margin: 0;
            padding: 0;
            font-weight: 400;
            font-size: 16pt;
        }

        p {
            margin: 10px 0 0 0;
            padding: 0;
        }

        ul {
            font-size: 10pt;
            padding: 0;
            margin: 0;

            li {
                margin-left: 30px;
                padding: 0;
            }
        }

        a{
            :link {
                text-decoration: none;
                color: #0074CC;
            }

            :visited {
                color: #0074CC;
            }
        }
    }
`;

const MainForm = styled.form`
    width: 100%;
`;

const TitleBox = styled.div`
    display: flex;

    >div:first-child {    
        width: 70%;

        >div:first-child {
            padding: 20px;
            border-radius: 5px;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            border: 1px solid #E3E6E8;
            margin: 15px 0;
            box-sizing: border-box;

            >label {
                font-weight: 500;
            }

            >p {
                margin: 0;
                padding: 0;
                font-size: 9pt;
            }

            >div { 
            display: flex;
            align-items: center;
            
                >div {
                    background-color: red;
                    display: inline-block;
                    padding: 5px;
                }
            }

            input {
                width: 100%;
                height: 30px;
                margin: 10px 0px;
                border-radius: 3px;
                border: 1px solid #BABFC4;

                ::placeholder {
                    color: #c0c0c0;
                }
            }
        }
    }

    button {
        background-color: #0A95FF;
        border: 1px solid #0A95FF;
        color: white;
        width: 50px;
        height: 40px;
        border-radius: 3px;
        box-shadow: inset 0px 1px rgba(255, 255, 255, .5);

        :hover {
        box-shadow: inset 50px 50px rgba(0, 0, 0, .2);
        }
    }

    >div:last-child {
        width: calc(30% - 15px);
        height: 20%;
        margin: 15px 0 0 15px;
        box-sizing: border-box;
        border-radius: 5px;
        border: 1px solid #D6D9DC;
        background-color: white;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, .15);

        >div:first-child {
            background-color: lightblue;
            padding: 10px;
            border-bottom: 1px solid #D6D9DC;
            background-color: #F8F9F9;
            font-size: 11pt;
        }

    }

    .subFlex {
        display: flex;
        padding: 20px;

        >div:first-child {
            width: 20%;
        }

        >div:last-child {
            width: 80%;
            font-size: 9pt;
        }

        a{
            :link {
                text-decoration: none;
                color: #0074CC;
            }

            :visited {
                color: #0074CC;
            }
        }
    }
`;

const TagBox = styled(TitleBox)`

    >div:first-child {

        #submit {
            width: 130px;
            height: 35px;
            margin-right: 20px;
        }

        #discard {
        background-color: transparent;
        color: #C22E32;
        width: 100px;
        height: 35px;
        border-radius: 3px;
        border: none;

            #discard:hover {
                background-color: #FDF2F2;
            }
        }


        >div:first-child >div {
        border-radius: 5px;
        border: 1px solid #A6CEED;
        max-height: 200px;

        display: flex;
        flex-wrap : wrap;

    
        >div {
            display: flex;
            align-items: center;
            justify-content: center;

            padding: 5px;
            font-size: 9pt;
            background-color: #E1ECF4;
            border-radius: 3px;
            box-sizing: border-box;
            margin: 5px;
            color: #39739D;

            svg {
                margin: 0 0 0 5px;
                fill: #39739D;
            }
        }
    }
    }

    #tag {
        width: unset;
        flex-grow: 1;
        max-width: 100%;
        border: none;
        margin: 0;

        :focus {
            outline: none;
        }
    }
    
`

const EditorBox = styled.div`
    display: flex;

    >div:first-child {
        width: 70%;
        padding: 20px;
        border-radius: 5px;
        background-color: white;
        padding: 20px;
        border-radius: 5px;
        border: 1px solid #E3E6E8;
        margin: 15px 0;
        box-sizing: border-box;

        >label {
            font-weight: 500;
        }

        >p {
            margin: 0;
            padding: 0;
            font-size: 9pt;
        }

        input {
            width: 100%;
            height: 300px;
            margin: 10px 0px;
            border-radius: 3px;
            border: 1px solid #BABFC4;

            ::placeholder {
                color: #c0c0c0;
            }
        
        }
    }

    button {
        background-color: #0A95FF;
        border: 1px solid #0A95FF;
        color: white;
        width: 50px;
        height: 40px;
        border-radius: 3px;
        box-shadow: inset 0px 1px rgba(255, 255, 255, .5);

        :hover {
        box-shadow: inset 50px 50px rgba(0, 0, 0, .2);
        }
    }

    >div:last-child {
        width: calc(30% - 15px);
        height: 20%;
        margin: 15px 0 0 15px;
        box-sizing: border-box;
        border-radius: 5px;
        border: 1px solid #D6D9DC;
        background-color: white;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, .15);

        >div:first-child {
            background-color: lightblue;
            padding: 10px;
            border-bottom: 1px solid #D6D9DC;
            background-color: #F8F9F9;
            font-size: 11pt;
        }

    }

    .subFlex {
        display: flex;
        padding: 20px;

        >div:first-child {
            width: 20%;
        }

        >div:last-child {
            width: 80%;
            font-size: 9pt;
        }

        a{
            :link {
                text-decoration: none;
                color: #0074CC;
            }

            :visited {
                color: #0074CC;
            }
        }
    }
`;

const Header = () => {
    // 버튼 활성화, 
    const [part, setPart] = useState([0, 1, 2, 3])

    // 내용 들어가는 부분
    const [title, setTitle] = useState('');
    const [text, setText] = useState('');
    const [expecting, setExpecting] = useState('');
    // 태그는 최대 5개까지, 그리고 최대 35글자까지 됨.
    const [tags, setTags] = useState([]);

    const { handleSubmit, reset } = useForm();

    // 태그 추가, 삭제
    const addTags = (event) => {
        if(!tags.includes(event.target.value) && event.target.value !== ''){
          setTags([...tags, event.target.value]);
          event.target.value = '';
        } else if(event.target.value === ''){
          event.target.value = '';
        }
    };

    const removeTags = (indexToRemove) => {
        setTags(tags.filter((el) => {
          return el !== tags[indexToRemove]
        }))
      };

    // 자꾸 에디터에 내가 쓰는 태그들이 이상하게 출력되서 우선 적어보는 함수..
    // {parseText(text)} 얘를 post 요청해서 서버로 보내야 함.
    function parseText(text) {
        return text.replace(/&lt;/g, '<').replace(/&gt;/g, '>');
      }

    // post로 보내는 부분
    const onSubmit = () => {
        console.log('테스트용')
        axios.post("http://localhost:3001/write", {
            title,
            problem : parseText(text),
            expecting : parseText(expecting),
            tags
          }, {headers: {
            'Content-Type': `application/json`,
            'ngrok-skip-browser-warning': '69420',
          }
        })
          .then((response) => {
            console.log(response);
            reset();
          })
          .catch((error) => {
            console.log(error);
          });
    };

    const handleEnter = (event) => {
        if (event.key === 'Enter') {
          event.preventDefault();
        }
      };

    const pencilIcon = `<svg aria-hidden="true" className="svg-spot spotPencil" width="48" height="48" viewBox="0 0 48 48"><path d="M31.52 5.2a.34.34 0 0 0-.46.08L7 39.94a.34.34 0 0 0-.06.16l-.54 5.21c-.03.26.24.45.48.34l4.77-2.29c.05-.02.1-.06.13-.1L35.83 8.58a.34.34 0 0 0-.09-.47l-4.22-2.93Z" opacity=".2"></path><path d="M28.53 2.82c.4-.58 1.2-.73 1.79-.32l4.22 2.92c.58.4.72 1.2.32 1.79L10.82 41.87c-.13.18-.3.33-.5.43l-4.77 2.28c-.9.44-1.93-.29-1.83-1.29l.55-5.2c.02-.22.1-.43.22-.6L28.53 2.81Zm4.43 3.81L29.74 4.4 28.2 6.6l3.22 2.24 1.53-2.21Zm-2.6 3.76-3.23-2.24-20.32 29.3 3.22 2.24 20.32-29.3ZM5.7 42.4 8.62 41l-2.57-1.78-.34 3.18Zm35.12.3a1 1 0 1 0-.9-1.78 35 35 0 0 1-7.94 3.06c-1.93.43-3.8.3-5.71-.04-.97-.17-1.93-.4-2.92-.64l-.3-.07c-.9-.21-1.81-.43-2.74-.62-2.9-.58-6.6-.49-9.43.65a1 1 0 0 0 .74 1.86c2.4-.96 5.68-1.07 8.3-.55.88.18 1.77.4 2.66.6l.3.08c1 .24 2 .48 3.03.66 2.07.37 4.22.53 6.5.02 3-.67 5.77-1.9 8.41-3.22Z"></path></svg>`

    return (
        <DisplayFlex>
            <WriteMain>
                <WriteText>
                    <h1>Ask a public question</h1>
                    <div>
                        <h3>Writing a good question</h3>
                        <p>You’re ready to <a href="https://stackoverflow.com/questions/ask">ask a programming-related question</a> and this form will help guide you through the process.
                        <br />
                        Looking to ask a non-programming question? See <a href="https://stackoverflow.com/questions/ask">the topics here</a> to find a relevant site.</p>

                        <h5>Steps</h5>

                        <ul>
                        <li>Summarize your problem in a one-line title.</li>
                        <li>Describe your problem in more detail.</li>
                        <li>Describe what you tried and what you expected to happen.</li>
                        <li>Add “tags” which help surface your question to members of the community.</li>
                        <li>Review your question and post it to the site.</li>
                        </ul>
                    </div>
                </WriteText>
                <MainForm onSubmit={handleSubmit(onSubmit)}>
                    <TitleBox>
                        <div>
                            <div>
                                <label>Title</label>
                                <p>Be specific and imagine you’re asking a question to another person.</p>
                                <input type="text" placeholder="e.g. Is there an R function for finding the index of an element in a vector" value={title} onChange={
                                    (e)=>setTitle(e.target.value)
                                } />
                                <button 
                                type="button"
                                className={part[0] === 0 ? "" : "hide"}
                                onClick={() => {
                                    let copy = [...part];
                                    copy[0] += 1;
                                    setPart(copy);
                                }}>Next
                            </button>
                            </div>
                        </div>
                        
                        <div className={part[0] === 0 ? "" : "hide"}>
                            <div>Writing a good title</div>
                            <div className="subFlex">
                                <div><div dangerouslySetInnerHTML={{ __html: pencilIcon }} /></div>
                                <div>Your title should summarize the problem.<br/>
                                <br/>
                                You might find that you have a better idea of your title after writing out the rest of the question.</div>
                            </div>
                        </div>
                    </TitleBox>
                    <EditorBox className={part[0] === 0 ? "disabled" : ""}>
                        <div>
                            <label>What are the details of your problem?</label>
                            <p>Introduce the problem and expand on what you put in the title. Minimum 20 characters.</p>
                             <div><StackEditor text={text} setText={setText} /></div>
                             <p>{parseText(text)}</p>
                             <button 
                                type="button"
                                className={part[0] === 0 || (part[0] !== 0 && part[1] !== 1) ? "hide" : ""}
                                onClick={() => {
                                    let copy = [...part];
                                    copy[1] += 1;
                                    setPart(copy)
                                }}>Next
                            </button>
                        </div>

                        <div className={part[0] === 0 || part[1] !== 1 ? "hide" : ""}>
                            <div>Expand on the problem</div>
                            <div className="subFlex">
                            <div><div dangerouslySetInnerHTML={{ __html: pencilIcon }} /></div>
                                <div>Show what you’ve tried, tell us what happened, and why it didn’t meet your needs.<br />
                                <br />
                                Not all questions benefit from including code, but if your problem is better understood with code you’ve written, you should include a <a href="https://stackoverflow.com/questions/ask">minimal, reproducible example.</a><br />
                                <br />
                                Please make sure to post code and errors as text directly to the question (and <a href="https://stackoverflow.com/questions/ask">not as images</a>), and <a href="https://stackoverflow.com/questions/ask">format them appropriately.</a></div>
                            </div>
                        </div>
                    </EditorBox>
                    <EditorBox className={part[1] === 1 ? "disabled" : ""}>
                        <div>
                            <label>What did you try and what were you expecting?</label>
                            <p>Describe what you tried, what you expected to happen, and what actually resulted. Minimum 20 characters.</p>
                            <div><StackEditor text={expecting} setText={setExpecting} /></div>
                            <p>{expecting}</p>
                            <button 
                                type="button"
                                className={part[1] === 1 || (part[1] !== 1 && part[2] !== 2) ? "hide" : ""}
                                onClick={() => {
                                    let copy = [...part];
                                    copy[2] += 1;
                                    setPart(copy)
                                }}>Next
                            </button>
                        </div>
                        <div className={part[1] === 1 || part[2] !== 2 ? "hide" : ""}>
                            <div>Introduce the problem</div>
                            <div className="subFlex">
                            <div><div dangerouslySetInnerHTML={{ __html: pencilIcon }} /></div>
                                <div>Explain how you encountered the problem you’re trying to solve, and any difficulties that have prevented you from solving it yourself.</div>
                            </div>
                        </div>
                    </EditorBox>
                    <TagBox className={part[2] === 2 ? "disabled" : ""}>
                        <div>
                            <div>
                                <label>Tags</label>
                                <p>Add up to 5 tags to describe what your question is about. Start typing to see suggestions.</p>
                                <div>
                                {tags.map((tag, index) => (
                                    <div key={index}>{tag}
                                        <svg className="svg-icon iconClearSm pe-none" width="14" height="14" viewBox="0 0 14 14" onClick={() => { removeTags(index) }}>
                                            <path d="M12 3.41L10.59 2 7 5.59 3.41 2 2 3.41 5.59 7 2 10.59 3.41 12 7 8.41 10.59 12 12 10.59 8.41 7z"></path>
                                        </svg>
                                    </div>
                                ))}
                                <input type="text"
                                placeholder="e.g. (asp.net-mvc objective-c ruby-on-rails)"
                                id="tag"
                                onKeyDown={handleEnter}
                                onKeyUp={(e)=>{
                                    if(e.key === 'Enter'){
                                        e.preventDefault();
                                        addTags(e);
                                    }
                                  }}/>
                                </div>
                            </div>
                        <button type="submit" id="submit">Post your question</button>
                        <button id="discard">Discard draft</button>
                        </div>

                        <div className={part[2] === 2 || part[3] !== 3 ? "hide" : ""}>
                            <div>Adding tags</div>
                            <div className="subFlex">
                                <div><div dangerouslySetInnerHTML={{ __html: pencilIcon }} /></div>
                                <div>ags help ensure that your question will get attention from the right people.<br/>
                                <br/>
                                Tag things in more than one way so people can find them more easily. Add tags for product lines, projects, teams, and the specific technologies or languages used.
                                <br/>
                                <br/>
                                <a href="https://stackoverflow.com/questions/ask">Learn more about tagging</a></div>
                            </div>
                        </div>
                    </TagBox>
                    {/* <div>
                            <label>Review questions already on Stack Overflow to see if your question is a duplicate.</label>
                            <p>Clicking on these questions will open them in a new tab for you to review. Your progress here will be saved so you can come back and continue.</p>
                            <input type="text" />
                            <button>Next</button>
                    </div> */}
                </MainForm>
            </WriteMain>
        </DisplayFlex>
)}

export default Header