import styled from "styled-components"
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux"
import { setId } from "./store/idSlice";
import axiosInstance from "./axiosConfig";

const DisplayFlex = styled.div`
    background-color: #F1F2F3;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
`;

const LoginText = styled.div`
    width: 450px;
    margin-right: 20px;

    h2 {
        font-weight: 400;
    }

    ul {
        padding: 0;
        margin: 0;
        list-style: none;

        li {
            display: flex;
            align-items: center;
            margin-bottom: 25px;

            svg {
                padding-right: 10px;
                fill: #0A95FF;
            }

            span {
                margin-right: 5px;
            }
        }
    }

    p:last-child {
        font-size: 10pt;
    }

    a {
            color: #0074CC;
    }

    @media only screen and (max-width: 824px) {
        display: none;
    }
`;

const LoginContainer = styled.div`
    width: 300px;
    margin-left: 20px;

    >p {
        font-size: 10pt;
        text-align: center;
        margin: 5px;
    }

    form {
        display: flex;
        justify-content: center;
        flex-direction: column ;
        
        margin: 10px 0 30px 0;
        padding: 10px 20px 20px 20px;
        background-color: white;
        border-radius: 7px;
        box-shadow: 0px 0px 30px rgba(0, 0, 0, .15);
        font-weight: 500;
        overflow: hidden;

        >input {
            width: 248px;
            height: 28px;
            border: 1px solid lightgray;
            border-radius: 2px;
            padding-left: 10px;
            outline: none; 

            :focus {
                outline: 4px solid #D9EAF8;
                border: 1px solid #59A4DE;
            }
        }
        
        .invalid {
            border-color: #DE4F54;
            
            :focus {
                border-color: #DE4F54;
                outline: 4px solid #F6E0E0;
            }
        }

        label {
            display: block;
            margin: 10px 0 5px 0;
        }

        >p {
            margin: 0;
            font-size: 8.5pt;
            font-weight: 400;
        }

        p:last-child {
            margin-top: 30px;
            color: #a0a0a0;
        }

        .check {
            display: flex;
            align-items: center;
            justify-content: center;
            color: #c0c0c0;
            font-size: 10pt;

            background-color: #f0f0f0;
            width: 258px;
            height: 150px;
            border-radius: 5px;
            border: 1px solid #cfcfcf;
        }

        .warning {
            margin: 20px 0 15px 0;
            display: flex;
            align-items: flex-start;
            
            label {
                margin: 0;
                padding: 0;
                font-size: 8.5pt;
                font-weight: 400;
            }
        }

        button {
            background-color: #0A95FF;
            border: 1px solid #0A95FF;
            color: white;
            width: 258px;
            height: 38px;
            border-radius: 3px;
            box-shadow: inset 0px 1px rgba(255, 255, 255, .5);

            :hover {
            box-shadow: inset 50px 50px rgba(0, 0, 0, .2);
            cursor: pointer;
            }
            
        }

    }

    .hide {
        display: none;
    }

    .text-red {
        display: block;
        color: #DE4F54;
        font-size: 8.5pt;
        font-weight: 400;
    }

    a {
            color: #0074CC;
    }

    div svg {
        padding-right: 7px;
    }
`;

const SocialBtn = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;

    background-color: ${(props) => props.bg || "white"};
    width: 300px;
    height: 36px;
    border-radius: 5px;
    margin-bottom: 10px;
    color: ${(props) => props.color || "white"};
    border: ${props => props.border ? '1px solid gray' : 'none'};

    :hover {
        box-shadow: inset 50px 50px rgba(0, 0, 0, .08);
    }
`;

const Login = () => {

    const dispatch = useDispatch();
    
    const name = useSelector(state => state.id);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [news, setNews] = useState(false);
    const [img, setImg] = useState(null);
    
    const { register, handleSubmit, watch, formState: { errors }, reset } = useForm();
    const newInfo = {
        name,
        email,
        password,
        news,
        img,
    }
    const onSubmit = () => {
        axiosInstance
            .post('/members',newInfo)
            .then((response) => {
            console.log(response);
            reset();
            window.location.href = `/result/${response.data.id}`;
        })
        .catch((error) => {
            console.log(error);
        });
    };

    function handleChange(e) {
        const id = e.target.value;
        dispatch(setId(id));
        }    

    return (
    <DisplayFlex>

        <LoginText>
        <h2>Join the Stack Overflow community</h2>
        <ul>
            <li><svg width="26" height="26" className="svg-icon mtn2"><path opacity=".5" d="M4.2 4H22a2 2 0 012 2v11.8a3 3 0 002-2.8V5a3 3 0 00-3-3H7a3 3 0 00-2.8 2z"></path><path d="M1 7c0-1.1.9-2 2-2h18a2 2 0 012 2v12a2 2 0 01-2 2h-2v5l-5-5H3a2 2 0 01-2-2V7zm10.6 11.3c.7 0 1.2-.5 1.2-1.2s-.5-1.2-1.2-1.2c-.6 0-1.2.4-1.2 1.2 0 .7.5 1.1 1.2 1.2zm2.2-5.4l1-.9c.3-.4.4-.9.4-1.4 0-1-.3-1.7-1-2.2-.6-.5-1.4-.7-2.4-.7-.8 0-1.4.2-2 .5-.7.5-1 1.4-1 2.8h1.9v-.1c0-.4 0-.7.2-1 .2-.4.5-.6 1-.6s.8.1 1 .4a1.3 1.3 0 010 1.8l-.4.3-1.4 1.3c-.3.4-.4 1-.4 1.6 0 0 0 .2.2.2h1.5c.2 0 .2-.1.2-.2l.1-.7.5-.7.6-.4z"></path></svg>
            Get unstuck — ask a question</li>
            <li><svg width="26" height="26" className="svg-icon mtn2"><path d="M12 .7a2 2 0 013 0l8.5 9.6a1 1 0 01-.7 1.7H4.2a1 1 0 01-.7-1.7L12 .7z"></path><path opacity=".5" d="M20.6 16H6.4l7.1 8 7-8zM15 25.3a2 2 0 01-3 0l-8.5-9.6a1 1 0 01.7-1.7h18.6a1 1 0 01.7 1.7L15 25.3z"></path></svg>
            Unlock new privileges like voting and commenting</li>
            <li><svg width="26" height="26" className="svg-icon mtn2"><path d="M14.8 3a2 2 0 00-1.4.6l-10 10a2 2 0 000 2.8l8.2 8.2c.8.8 2 .8 2.8 0l10-10c.4-.4.6-.9.6-1.4V5a2 2 0 00-2-2h-8.2zm5.2 7a2 2 0 110-4 2 2 0 010 4z"></path><path opacity=".5" d="M13 0a2 2 0 00-1.4.6l-10 10a2 2 0 000 2.8c.1-.2.3-.6.6-.8l10-10a2 2 0 011.4-.6h9.6a2 2 0 00-2-2H13z"></path></svg>
            Save your favorite questions, answers, watch tags, and more</li>
            <li><svg width="26" height="26" className="svg-icon mtn2"><path d="M21 4V2H5v2H1v5c0 2 2 4 4 4v1c0 2.5 3 4 7 4v3H7s-1.2 2.3-1.2 3h14.4c0-.6-1.2-3-1.2-3h-5v-3c4 0 7-1.5 7-4v-1c2 0 4-2 4-4V4h-4zM5 11c-1 0-2-1-2-2V6h2v5zm11.5 2.7l-3.5-2-3.5 1.9L11 9.8 7.2 7.5h4.4L13 3.8l1.4 3.7h4L15.3 10l1.4 3.7h-.1zM23 9c0 1-1 2-2 2V6h2v3z"></path></svg>
            Earn reputation and badges</li>
        </ul>
        <p>Collaborate and share knowledge with a private group for FREE.<br />
        <a href="https://www.naver.com/">Get Stack Overflow for Teams free for up to 50 users.</a></p>
        </LoginText>
        <LoginContainer>
            <div className="social">
                <SocialBtn color="#c0c0c0" border="true"><svg aria-hidden="true" className="native svg-icon iconGoogle" width="18" height="18" viewBox="0 0 18 18"><path fill="#4285F4" d="M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 0 0 2.38-5.88c0-.57-.05-.66-.15-1.18Z"></path><path fill="#34A853" d="M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 0 1-7.18-2.54H1.83v2.07A8 8 0 0 0 8.98 17Z"></path><path fill="#FBBC05" d="M4.5 10.52a4.8 4.8 0 0 1 0-3.04V5.41H1.83a8 8 0 0 0 0 7.18l2.67-2.07Z"></path><path fill="#EA4335" d="M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 0 0 1.83 5.4L4.5 7.49a4.77 4.77 0 0 1 4.48-3.3Z"></path></svg>
                sign up with Google</SocialBtn>
                <SocialBtn bg="#2F3337"><svg aria-hidden="true" className="svg-icon iconGitHub" width="18" height="18" viewBox="0 0 18 18"><path fill="#ffffff" d="M9 1a8 8 0 0 0-2.53 15.59c.4.07.55-.17.55-.38l-.01-1.49c-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.42 7.42 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48l-.01 2.2c0 .21.15.46.55.38A8.01 8.01 0 0 0 9 1Z"></path></svg>
                sign up with GitHub</SocialBtn>
                <SocialBtn bg="#385499"><svg aria-hidden="true" className="svg-icon iconFacebook" width="18" height="18" viewBox="0 0 18 18"><path fill="#ffffff" d="M3 1a2 2 0 0 0-2 2v12c0 1.1.9 2 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H3Zm6.55 16v-6.2H7.46V8.4h2.09V6.61c0-2.07 1.26-3.2 3.1-3.2.88 0 1.64.07 1.87.1v2.16h-1.29c-1 0-1.19.48-1.19 1.18V8.4h2.39l-.31 2.42h-2.08V17h-2.5Z"></path></svg>
                sign up with Facebook</SocialBtn>
            </div>
            <form onSubmit={handleSubmit(onSubmit)}>

                <label htmlFor="id">Display name</label>
                <input
                    type="text" 
                    name="id" id="id"
                    onChange={handleChange}
                />

                <label htmlFor="email">Email</label>
                <input 
                    type="text" 
                    name="email" 
                    id="email" 
                    {...register("email", {
                        required: "Email cannot be empty.",
                        pattern: {
                        value: /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
                        message: "Please enter a valid email address.",
                        }
                    })}
                    onChange={(e) => setEmail(e.target.value)}
                    className={errors.email ? "invalid" : ""}
                />
                {errors.email && <p className="text-red">{errors.email.message}</p>}

                <label htmlFor="password">Password</label>
                <input 
                    type="password" 
                    id="password" 
                    // formState 객체 반환
                    {...register('password', {
                        required: "Password cannot be empty.", // 빈 문자열 확인
                        minLength: { // 8글자 이하인지 확인
                        value: 8,
                        message: "Must contain at least * more characters.",
                        },
                        validate: { // 커스텀 유효성 검사 규칙을 정의할 때 사용
                            // 아래 hasNumberr와 hasLetter은 함수, .test()는 js문법으로 정규표현식에 따라 true/fasle 반환.
                            // 아래의 value는 별도로 함수를 부르지 않아도 register의 기능상 password값이 실시간으로 추적됨.
                        hasNumber: value => /\d/.test(value),
                        hasLetter: value => /[a-zA-Z]/.test(value),
                        }
                    })}
                    onChange={(e)=>{
                        setPassword(e.target.value)
                    }}
                    className={errors.password ? "invalid" : ""}
                />
                {/* errors.password 객체에 값이 존재하는 경우 실행됨. */}
                {errors.password && (
                <p className="text-red">
                    {errors.password.message}
                </p>
                )}
                {errors.password && (
                <div className="text-red">
                    {/* password에 문자와 숫자가 하나라도 있는지 확인 */}
                    {!/(?=.*[A-Za-z])(?=.*\d)/.test(watch('password')) && (
                    <ul>
                        {/* 없다면 1. 숫자만 있는 경우 2. 문자로만 이루어져 있는 경우로 나눠서 문구를 출력함. */}
                        {/^\d+$/.test(watch('password')) && <li>letters</li>}
                        {/^[A-Za-z]+$/.test(watch('password')) && <li>number</li>}
                    </ul>
                    )}
                </div>
                )}

                <p>Passwords must contain at least eight characters, including at least 1 letter and 1 number.</p>
                <div className="check">우선은 비워두기(로봇확인)</div>
                <div className="warning">
                    <input 
                        type="checkbox" 
                        id="check" 
                        checked={news}
                        onChange={()=>{
                            setNews(!news)
                        }}
                        />
                    <label htmlFor="check">Opt-in to receive occasional product<br />updates, user research invitations, company announcements, and digests.</label>
                    <span><svg aria-hidden="true" className="svg-icon iconHelpSm" width="14" height="14" viewBox="0 0 14 14"><path d="M7 1C3.74 1 1 3.77 1 7c0 3.26 2.77 6 6 6 3.27 0 6-2.73 6-6s-2.73-6-6-6Zm1.06 9.06c-.02.63-.48 1.02-1.1 1-.57-.02-1.03-.43-1.01-1.06.02-.63.5-1.04 1.08-1.02.6.02 1.05.45 1.03 1.08Zm.73-3.07-.47.3c-.2.15-.36.36-.44.6a3.6 3.6 0 0 0-.08.65c0 .04-.03.14-.16.14h-1.4c-.14 0-.16-.09-.16-.13-.01-.5.11-.99.36-1.42A4.6 4.6 0 0 1 7.7 6.07c.15-.1.21-.21.3-.33.18-.2.28-.47.28-.74.01-.67-.53-1.14-1.18-1.14-.9 0-1.18.7-1.18 1.46H4.2c0-1.17.31-1.92.98-2.36a3.5 3.5 0 0 1 1.83-.44c.88 0 1.58.16 2.2.62.58.42.88 1.02.88 1.82 0 .5-.17.9-.43 1.24-.15.2-.44.47-.86.79h-.01Z"></path></svg></span>
                </div>
                <button type="submit">Sign up</button>
                <p>By clicking “Sign up”, you agree to our <a href="https://www.naver.com/">terms of service, privacy policy</a> and <a href="https://www.naver.com/">cookie policy</a></p>
            </form>
            <p>Already have an account? <a href="https://www.naver.com/">Log in</a></p>
            <p>Are you an employer? <a href="https://www.naver.com/">Sign up on Talent</a><svg aria-hidden="true" className="va-text-bottom sm:d-none svg-icon iconShareSm" width="14" height="14" viewBox="0 0 14 14" fill="#0A95FF"><path d="M5 1H3a2 2 0 0 0-2 2v8c0 1.1.9 2 2 2h8a2 2 0 0 0 2-2V9h-2v2H3V3h2V1Zm2 0h6v6h-2V4.5L6.5 9 5 7.5 9.5 3H7V1Z"></path></svg></p>
        </LoginContainer>
    </DisplayFlex>
)}

export default Login