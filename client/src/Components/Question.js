import axios from "axios";
import styled from "styled-components"
import { useEffect } from "react";
import { useState } from "react";
import Viewer from "../editor/Viewer";

// react-syntax-highlighter 와 관련된 내용. js 코드를 표시할 때 쓰는 부분
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { prism } from 'react-syntax-highlighter/dist/esm/styles/prism';

const DisplayFlex = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;
`;

const MainBox = styled.div`
    width: 1204px;
    display: flex;
    align-items: stretch;
    justify-content: center;
`;

// 반응형으로 특정 창 이상 줄이게 되면 없어지게끔 구현해야 함.
const Sidemenu = styled.div`
    width: 185px;
    border-right: 1px solid lightgray;
    font-size: 10pt;
    margin-right: 25px;
    height: 100%;

    >ul {
        list-style: none;
        padding: 0;
        margin: 0;

        >li {            
            margin: 20px;
            width: calc(100% - 20px);
            
            >ul {
            list-style: none;
            padding: 0;

            >li:first-child {
                margin-top: 5px;
                font-weight: 500;
                color: black;
                background-color: #F1F2F3;
                border-right: 3px solid #F48225;
            }

            li {
                padding: 7px 0 7px 20px;
            }
            }
        }
    }

    @media only screen and (max-width: 1024px) {
        display: none;
    }
`;

const PostComponent = styled.div`
    display: flex;
    align-items: center;
    flex-direction: column;

    width: 100%;
    overflow: hidden;
`;

const PostHeader = styled.div`
    width: 100%;
    border-bottom: 1px solid lightgray;
    padding-top: 25px;

    div {
        display: flex;
        
        h2 {
            width: 100%;
            padding: 0;
            margin: 0;
            font-weight: 400;

            a {
                color: black;
                text-decoration: none;
            }
        }
        
        button {
            width: 140px;
            height: 40px;
            margin: 0 15px;
            background-color: #0A95FF;
            border: 1px solid #0A95FF;
            color: white;
            border-radius: 3px;
            box-shadow: inset 0px 1px rgba(255, 255, 255, .5);

            :hover {
                box-shadow: inset 50px 50px rgba(0, 0, 0, .2);
            }
        }
    }

    ul {
        display: flex;
        list-style: none;
        margin: 15px 0 15px 0;
        padding: 0;
        font-size: 10pt;
        color: gray;
        
        li {
            padding-right: 20px;
        }
    }

    .black {
        color: black;
    }

`;

const PostMain = styled.div`
    padding: 25px 0;
    display: flex;
    width: 100%;
    font-size: 11pt;

    ul {
        padding: 0;
        margin: 0 10px;
        width: 60px;
        text-align: center;
        font-size: 18pt;
        color: lightgray;
        cursor: pointer;

        list-style: none;

        li {
            padding-bottom: 5px;

            :nth-child(2) {
                cursor: text !important;
            }

            :last-child svg:hover{
                fill: #0A95FF;
            }
        }
    }

    div {
        flex: 1;
    }

    .black {
        color: black;
    }
`;

const PostFooter = styled.div`
    width: 100%;
    
    >ul {
        margin: 0;
        padding: 0;
        display: flex;
        list-style: none;
        margin-bottom: 30px;
        
        li {
            font-size: 8pt;
            background-color : #D9EAF7;
            border-radius: 3px;
            color: #39739D;

           padding: 3px 7px;
           margin-right: 5px;

           cursor: pointer;

           :hover {
            background-color: #D0E3F1;
            color: #2C5877;
           }
        }
    }

    >div {
        display: flex;
        justify-content: space-between;

        >ul {
            display: flex;
            margin: 0;
            padding: 0;
            list-style: none;
            font-size: 10pt;
            color: gray;

            >li {
                margin-right: 10px;

                a{
                    :link {
                        text-decoration: none;
                        color: #646464;
                    }

                    :visited {
                        color: #646464;
                    }
                }
            }
        }

        >div {
            background-color: #D9EAF7;
            color: gray;
            width: 180px;
            font-size: 8pt;
            padding: 5px;
            display: flex;
            flex-direction: column;

            >div {
                padding-bottom: 3px;

                >ul {
                    display: flex;
                    list-style: none;
                    margin: 0;
                    padding: 0;

                    >li:first-child {
                        background-color: blue;
                        width: 30px;
                        height: 30px;
                        border-radius: 3px;
                    }

                    >li ul {
                        margin: 0;
                        padding: 0 5px;
                        display: flex;
                        flex-direction: column;
                        list-style: none;

                        >li:first-child {
                            color: #0074CC;
                        }

                        span {
                            padding: 0 5px;
                            color: #D1A684;
                        }
                    }
                }
            }
        }
    }
`;

const CommentBox = styled.div`
    
`;

const Question = () => {

const [question, setQuestion] = useState(null);
const [filteredContent, setFilteredContent] = useState("");

useEffect(() => {
    axios.get("http://localhost:3001/write/1", { withCredentials: true })
      .then((res) => {
        setQuestion(res.data);
      })
      .catch((err) => {
        console.log(err);
        });
    }, []);

    return (
    <>
    {question &&
        <DisplayFlex>
            <MainBox>
                <Sidemenu>
                    <ul>
                        <li>Home</li>
                        <li>
                            <ul>PUBLIC
                                <li>Questions</li>
                                <li>Users</li>
                            </ul>
                        </li>
                    </ul>
                </Sidemenu>
                <PostComponent>
                    <PostHeader>
                        <div>
                            <h2><a href="">{question.title}</a></h2>
                            <button onClick={() => window.location.href = '/write'}>Ask Question</button>
                        </div>
                        <ul>
                            <li>Asked <span className="black">today</span></li>
                            <li>Modified <span className="black">today</span></li>
                            <li>Viewed <span className="black">15 times</span></li>
                        </ul>
                    </PostHeader>
                    <PostMain>
                        <ul>
                            <li><svg aria-hidden="true" className="svg-icon iconArrowUpLg" width="36" height="36" fill="#c0c0c0" viewBox="0 0 36 36"><path d="M2 25h32L18 9 2 25Z"></path></svg></li>
                            <li className="black">0</li>
                            <li><svg aria-hidden="true" className="svg-icon iconArrowDownLg" width="36" height="36" fill="#c0c0c0" viewBox="0 0 36 36"><path d="M2 11h32L18 27 2 11Z"></path></svg></li>
                            <li><svg aria-hidden="true" className="js-saves-btn-unselected svg-icon iconBookmarkAlt" fill="#c0c0c0" width="18" height="18" viewBox="0 0 18 18"><path d="m9 10.6 4 2.66V3H5v10.26l4-2.66ZM3 17V3c0-1.1.9-2 2-2h8a2 2 0 0 1 2 2v14l-6-4-6 4Z"></path></svg></li>
                            <li><svg aria-hidden="true" className="mln2 mr0 svg-icon iconHistory" fill="#c0c0c0" width="19" height="18" viewBox="0 0 19 18"><path d="M3 9a8 8 0 1 1 3.73 6.77L8.2 14.3A6 6 0 1 0 5 9l3.01-.01-4 4-4-4h3L3 9Zm7-4h1.01L11 9.36l3.22 2.1-.6.93L10 10V5Z"></path></svg></li>
                        </ul>
                        <div>
                            <Viewer problemText={question.problem} expectingText={question.expecting} />
                        </div>
                    </PostMain>
                    <PostFooter>
                        <ul>
                            {question.tags.map((tag) => (
                                <li key={tag}>{tag}</li>
                            ))}
                        </ul>
                        <div>
                            <ul>
                                <li><a href="https://www.naver.com/">Share</a></li>
                                <li><a href="https://www.naver.com/">Improve this question</a></li>
                                <li><a href="https://www.naver.com/">Follow</a></li>
                            </ul>
                            <div>
                                <div>asked 16 mins ago</div>
                                <div>
                                    <ul>
                                        <li></li>
                                        <li><ul>
                                            <li>Pathorn Teng</li>
                                            <li>346<span>♥</span>6</li>
                                            </ul></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </PostFooter>
                    <CommentBox>
                        {/* 위쪽으로 코멘트 일부가 보이는 거 같은데 이건 어떻게 해야할지 여쭤보기 */}
                        <span>Add a comment</span>
                    </CommentBox>
                </PostComponent>
            </MainBox>
        </DisplayFlex>
    }
    </>
)}

export default Question