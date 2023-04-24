import axios from "axios";
import styled from "styled-components"
import DOMPurify from 'dompurify';

// react-syntax-highlighter 와 관련된 내용. js 코드를 표시할 때 쓰는 부분
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { prism } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { useEffect } from "react";
import { useState } from "react";

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

        list-style: none;

        li {
            padding-bottom: 5px;
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

const Question = () => {

const [question, setQuestion] = useState(null);
const [filteredContent, setFilteredContent] = useState("");

useEffect(() => {
    axios
      .get("http://localhost:3001/write/1", { withCredentials: true })
      .then((res) => {
        setQuestion(res.data);
  
        const test = res.data.text;
  
        let extractedContent = test.match(/`([^`]+)`/)[1];
  
        if (extractedContent.startsWith('</p><p>') || extractedContent.startsWith('<p>')) {
          extractedContent = extractedContent.replace(/^<\/p><p>|^<p>/, '');
        }
  
        if (extractedContent.endsWith('</p><p>') || extractedContent.endsWith('</p>')) {
          extractedContent = extractedContent.replace(/<\/p><p>$|<\/p>$/, '');
        }
  
        const newFilteredContent = extractedContent.replace(/<\/p><p>/g, '\n').replace(/<ul><\/p><p><\/li><\/p><p><\/li><\/p><p><\/ul>/g, '<ul>\n<li>\n</li>\n<li>\n</li>\n</ul>');
  
        setFilteredContent(newFilteredContent);
  
        console.log(newFilteredContent);
  
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  


// 코드블럭으로 바뀌는 부분.. 이걸 어떻게 처리할지 모르겠음.
const codeString = 
`const test = adsad

<ul>
<li>Asked <span className="black">today</span></li>
<li>Modified <span className="black">today</span></li>
<li>Viewed <span className="black">15 times</span></li>
</ul>`
    
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
                            <h2>{question.title}</h2>
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
                            <li>▲</li>
                            <li className="black">0</li>
                            <li>▼</li>
                            <li>●</li>
                            <li>●</li>
                        </ul>
                        <div>
                            <div dangerouslySetInnerHTML={{__html: DOMPurify.sanitize([question.text, question.expecting])}}></div>
                            <SyntaxHighlighter language="javascript" style={prism}>
                                {filteredContent}
                                {console.log(filteredContent)}
                            </SyntaxHighlighter>
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
                </PostComponent>
            </MainBox>
        </DisplayFlex>
    }
    </>
)}

export default Question