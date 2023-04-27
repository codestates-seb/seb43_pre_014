import { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router";
import axios from "axios";
import styled from "styled-components"
import Viewer from "../../editor/Viewer";
import Comments from "./Comments";
import SideMenu from "../Sidemenu";
import Answer from "../Answer/Answer";
import AnswerViewer from "../Answer/AnswerViewer";

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

const PostComponent = styled.div`
    display: flex;
    align-items: center;
    flex-direction: column;
    margin-left: 25px;

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
            margin: 0 0 0 15px;
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

const PostMemu = styled.div`
    padding: 25px 0;
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

    .black {
        color: black;
    }
`;

const PostMain = styled.div`
    width: 100%;
    display: flex;
`;

const PostContent = styled.div`
    width: 100%;
    
    >div {
        flex: 1;
    }

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

    .submenu {
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
`

const CommentBox = styled.div`
    width: 100%;
    font-size: 10pt;
    color: #6F6F6F;
`;

const Question = () => {

    const [question, setQuestion] = useState(null);
    const [commnet, setCommnet] = useState(false);

    const { id } = useParams(); // URL에서 id값을 가져오기

    useEffect(() => {

        axios.get(`http://localhost:3001/write/${id}`, { withCredentials: true })
            .then((res) => {
                setQuestion(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, [id]);

    const handleDelete = () => {
        axios.delete(`http://localhost:3001/write/${id}`, { withCredentials: true })
            .then((res) => {
                window.location.href = `/`;
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return (
        <>
            {question &&
                <DisplayFlex>
                    <MainBox>
                        <SideMenu />
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
                                <PostMemu>
                                    <ul>
                                        <li><svg aria-hidden="true" className="svg-icon iconArrowUpLg" width="36" height="36" fill="#c0c0c0" viewBox="0 0 36 36"><path d="M2 25h32L18 9 2 25Z"></path></svg></li>
                                        <li className="black">0</li>
                                        <li><svg aria-hidden="true" className="svg-icon iconArrowDownLg" width="36" height="36" fill="#c0c0c0" viewBox="0 0 36 36"><path d="M2 11h32L18 27 2 11Z"></path></svg></li>
                                        <li><svg aria-hidden="true" className="js-saves-btn-unselected svg-icon iconBookmarkAlt" fill="#c0c0c0" width="18" height="18" viewBox="0 0 18 18"><path d="m9 10.6 4 2.66V3H5v10.26l4-2.66ZM3 17V3c0-1.1.9-2 2-2h8a2 2 0 0 1 2 2v14l-6-4-6 4Z"></path></svg></li>
                                        <li><svg aria-hidden="true" className="mln2 mr0 svg-icon iconHistory" fill="#c0c0c0" width="19" height="18" viewBox="0 0 19 18"><path d="M3 9a8 8 0 1 1 3.73 6.77L8.2 14.3A6 6 0 1 0 5 9l3.01-.01-4 4-4-4h3L3 9Zm7-4h1.01L11 9.36l3.22 2.1-.6.93L10 10V5Z"></path></svg></li>
                                    </ul>
                                </PostMemu>
                                <PostContent>
                                    <div>
                                        <Viewer problemText={question.problem} expectingText={question.expecting} />
                                    </div>
                                    <ul>
                                        {question.tags.map((tag) => (
                                            <li key={tag}>{tag}</li>
                                        ))}
                                    </ul>
                                    <div className="submenu">
                                        <ul>
                                            <li><a href="https://www.naver.com/">Share</a></li>
                                            <li><a href={`/modify/${id}`}>Edit</a></li>
                                            <li><a onClick={handleDelete}>Delete</a></li>
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
                                    <CommentBox>
                                        <span onClick={() => setCommnet(!commnet)}>Add a comment</span>
                                        {commnet ? <Comments /> : null}
                                    </CommentBox>
                                </PostContent>
                            </PostMain>
                            <Answer>
                                <AnswerViewer problemText={question.problem} expectingText={question.expecting} />
                            </Answer>
                        </PostComponent>
                    </MainBox>
                </DisplayFlex>
            }
        </>
    )
}

export default Question