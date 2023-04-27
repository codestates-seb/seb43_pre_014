import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axiosInstance from "../axiosConfig";
import TextEdit from './TextEdit';
import Sidemenu from "./Sidemenu";

const MainContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const MainContents = styled.div`
  display: flex;
  margin: 25px 0 0 25px;
  flex-direction: column;
`;

const Wrapper = styled.div`
      width: 100%;

      h1 {
        margin: 0;
        padding: 0 0 15px 0;
        font-weight: 400;
        border-bottom: 1px solid #D6D9DC;
      }

      h2 {
        font-weight: 400;
        margin-bottom: 10px;
      }

      p {
        font-size : 25px;
      }
  `;

const PublicInfo = styled.div`
  border-radius: 5px;
  border: 1px solid #E3E6E8;
  padding: 20px;
  `;


const Profile = styled.div`
      width: 1204px;
      display: flex;

      >div:first-child img {
        width: 140px;
        height: 140px;
        background-color: black;
        display: block;
        margin-right: 10px;
      }

      >div:last-child >div:first-child h1 {
        color: black;
        font-weight: 400;
        margin-bottom: 10px;
      }

      >div:last-child >div:last-child ul {
        display: flex;
        list-style: none;
        margin: 0;
        padding: 0;
        font-size: 10pt;
        color: #606060;

        li {
          margin-right: 10px;
          display: flex;
          align-items: center;

          svg {
            fill: #606060;
            margin-right: 5px;
          }
        }
      }
  `;

const Submenu = styled.div`
      margin: 20px 0;
      width: 1204px;
      font-size: 10pt;

      ul {
        list-style: none;
        display: flex;
        margin: 0;
        padding: 0;
        cursor: pointer;
        
        li {
          padding: 7px 12px;
          border-radius: 50px;
          margin-right: 20px;
        }

        .active {
          background-color: #F48225;
          color: white;
        }
      }
  `;

const ProfileImage = styled.div`
      display: flex;
      flex-direction: column;
      align-items: right;

      p {
        margin: 0 0 5px 0;
        padding: 0;
        font-size: 12pt;
        font-weight: 500;
      }

      img {
        width: 200px;
        height: 200px;
        object-fit: cover;
        margin-bottom: 10px;

        background-color: red;
  }
      input {
        margin-top: 10px;
  }
`;

const InputContainer = styled.div`
      display: flex;
      flex-direction: column;

      label {
        margin: 0 0 5px 0;
        padding: 0;
        font-size: 12pt;
        font-weight: 500;
      }

      input {
      width: 60%;
      height: 10px;
      font-size: 10pt !important;
      border: 1px solid #ccc;
      border-radius: 5px;
      padding: 10px;
      margin-bottom: 10px;
      width: 60%;
}
`;

const ButtonContainer = styled.div`
      display: flex;
      margin-top: 20px;
`

const SaveButton = styled.button`
      background-color: #0a95ff;
      color: white;
      padding: 10px 20px;
      font-size: 16px;
      margin-right: 10px;
      border: 1px solid #0a95ff;
      border-radius: 3px;
      box-shadow: rgba(255, 255, 255, 0.5) 0px 1px inset;
      cursor: pointer;
      &:hover {
        background-color: #0074cc;
  }
`;

const CancelButton = styled.button`
      background-color: #ccc;
      color: white;
      border: none;
      border-radius: 5px;
      padding: 10px 20px;
      font-size: 16px;
      cursor: pointer;
      &:hover {
        background-color: #999;
  }
`;
const Error = styled.p`
  color: red;
  font-size: 12px;
  margin-top: 3px;
`;
const Success = styled.p`
  color: white;
  background-color: #94c0de;
  font-size: 14px;
  padding: 10px;
  border-radius: 5px;
  margin-top: 10px;
`;

const EditProfile = () => {
  const [name, setName] = useState("");
  const [location, setLocation] = useState("");
  const [title, setTitle] = useState("");
  const [aboutMe, setAboutMe] = useState("");
  const [websiteLink, setWebsiteLink] = useState("");
  const [githubLink, setGithubLink] = useState("");
  const [twitterLink, setTwitterLink] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [user, setUser] = useState(null);
  const [img, setImg] = useState("")

  const handleCancel = () => {
    // Cancel 버튼 클릭 시, 상태 초기화
    setName("");
    setLocation("");
    setTitle("");
    setAboutMe("");
    setWebsiteLink("");
    setGithubLink("");
    setTwitterLink("");
  };

  const navigate = useNavigate();

  useEffect(() => {
    axiosInstance.get("/members/")
        .then((res) => {
        setUser(res.data);
        })
        .catch((err) => {
        console.log(err);
        });
    }, []);

  const saveProfile = async () => {
    try {
      const response = await axiosInstance.post("members", {
        img,
        name,
        location,
        title,
        aboutMe,
        websiteLink,
        githubLink,
        twitterLink,
      });
      if (response.status === 200) {
        // 성공시 응답처리 뭘로할까
        setSuccess("Your profile has been saved successfully.");
        navigate("/");
      } else {
        //성공 못했을 때 에러 표시 -> error 관련 css check하기
        setError
          ("Oops! There was a problem updating your profile: Display name may only be changed once every 30 days")
      }
    } catch (error) {
      // error 메시지
      setError("Fail fetching data")
    }
  };

  return (
    <>
    {user &&
  <MainContainer>
    <Sidemenu />
    <MainContents>
      <Profile>
        <div>
          <img src={img} alt="Profile" />
        </div>
        <div>
          <div><h1>haneul</h1></div>
          <div>
            <ul>
              <li><svg aria-hidden="true" className="svg-icon iconCake" width="18" height="18" viewBox="0 0 18 18"><path d="M9 4.5a1.5 1.5 0 0 0 1.28-2.27L9 0 7.72 2.23c-.14.22-.22.48-.22.77 0 .83.68 1.5 1.5 1.5Zm3.45 7.5-.8-.81-.81.8c-.98.98-2.69.98-3.67 0l-.8-.8-.82.8c-.49.49-1.14.76-1.83.76-.55 0-1.3-.17-1.72-.46V15c0 1.1.9 2 2 2h10a2 2 0 0 0 2-2v-2.7c-.42.28-1.17.45-1.72.45-.69 0-1.34-.27-1.83-.76Zm1.3-5H10V5H8v2H4.25C3 7 2 8 2 9.25v.9c0 .81.91 1.47 1.72 1.47.39 0 .77-.14 1.03-.42l1.61-1.6 1.6 1.6a1.5 1.5 0 0 0 2.08 0l1.6-1.6 1.6 1.6c.28.28.64.43 1.03.43.81 0 1.73-.67 1.73-1.48v-.9C16 8.01 15 7 13.75 7Z"></path></svg>Member for 15 days</li>
              <li><svg aria-hidden="true" className="svg-icon iconClock" width="18" height="18" viewBox="0 0 18 18"><path d="M9 17c-4.36 0-8-3.64-8-8 0-4.36 3.64-8 8-8 4.36 0 8 3.64 8 8 0 4.36-3.64 8-8 8Zm0-2c3.27 0 6-2.73 6-6s-2.73-6-6-6-6 2.73-6 6 2.73 6 6 6ZM8 5h1.01L9 9.36l3.22 2.1-.6.93L8 10V5Z"></path></svg>Last seen this week</li>
              <li><svg aria-hidden="true" className="svg-icon iconCalendar" width="18" height="18" viewBox="0 0 18 18"><path d="M14 2h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4c0-1.1.9-2 2-2h1V0h2v2h6V0h2v2ZM3 6v9h12V6H3Zm2 2h2v2H5V8Zm0 3h2v2H5v-2Zm3 0h2v2H8v-2Zm3 0h2v2h-2v-2Zm0-3h2v2h-2V8ZM8 8h2v2H8V8Z"></path></svg>Visited 10 days, 8 consecutive</li>
            </ul>
          </div>
        </div>
      </Profile>
      <Submenu>
        <ul>
          <li>Questions List</li>
          <li className="active">Edit profile</li>
        </ul>
      </Submenu>
      <Wrapper>
        <h1>Edit Profile</h1>
        <h2>Public infomation</h2>
        <PublicInfo>
        <ProfileImage>
          <p>Profile image</p>
          <img src={img} alt="Profile" />
        </ProfileImage>
        <InputContainer>
          <label>Display Name</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </InputContainer>
        <InputContainer>
          <label>Email</label>
          <input
            type="text"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />
        </InputContainer>
        <InputContainer>
          <label>Title</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </InputContainer>
        <InputContainer>
          <label>About Me</label>
          <TextEdit />
        </InputContainer>
        {success && <Success>{success}</Success>}
        {error && <Error>{error}</Error>}
        <ButtonContainer>
          <SaveButton onClick={saveProfile}>Save Profile</SaveButton>
          <CancelButton onClick={handleCancel}>Cancel</CancelButton>
        </ButtonContainer>
        </PublicInfo>
      </Wrapper>
    </MainContents>
  </MainContainer>
      }
    </>
  );
};

export default EditProfile;