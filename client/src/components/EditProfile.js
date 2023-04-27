import React, { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import styled from "styled-components";
import axiosInstance from "../axiosConfig";

const EditProfile = () => {
  const [profileImage, setProfileImage] = useState("");
  const [displayName, setDisplayName] = useState("");
  const [location, setLocation] = useState("");
  const [title, setTitle] = useState("");
  const [aboutMe, setAboutMe] = useState("");
  const [websiteLink, setWebsiteLink] = useState("");
  const [githubLink, setGithubLink] = useState("");
  const [twitterLink, setTwitterLink] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleCancel = () => {
    // Cancel 버튼 클릭 시, 상태 초기화
    setProfileImage("");
    setDisplayName("");
    setLocation("");
    setTitle("");
    setAboutMe("");
    setWebsiteLink("");
    setGithubLink("");
    setTwitterLink("");
  };

  const navigate = useNavigate();

  const saveProfile = async () => {
    try {
      const response = await axiosInstance.post("/user/profile?이 맞나?", {
        profileImage,
        displayName,
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
    <Wrapper>
      <h1>Edit Profile</h1>
      <ProfileImage>
        <img src={profileImage} alt="Profile" />
        <input
          type="text"
          placeholder="Profile Image URL"
          value={profileImage}
          onChange={(e) => setProfileImage(e.target.value)}
        />
      </ProfileImage>
      <InputContainer>
        <label>Display Name</label>
        <input
          type="text"
          placeholder="Display Name"
          value={displayName}
          onChange={(e) => setDisplayName(e.target.value)}
        />
      </InputContainer>
      <InputContainer>
        <label>Location</label>
        <input
          type="text"
          placeholder="Location"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        />
      </InputContainer>
      <InputContainer>
        <label>Title</label>
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </InputContainer>
      <InputContainer>
        <label>About Me</label>
        <textarea
          placeholder="About Me"
          value={aboutMe}
          onChange={(e) => setAboutMe(e.target.value)}
        />
      </InputContainer>
      <LinkContainer>
        <LinkInputContainer>
          <label>Website Link</label>
          <input
            type="text"
            placeholder="Website Link"
            value={websiteLink}
            onChange={(e) => setWebsiteLink(e.target.value)}
          />
        </LinkInputContainer>
        <LinkInputContainer>
          <label>Github Link</label>
          <input
            type="text"
            placeholder="Github Link"
            value={githubLink}
            onChange={(e) => setGithubLink(e.target.value)}
          />
        </LinkInputContainer>
        <LinkInputContainer>
          <label>Twitter Link</label>
          <input
            type="text"
            placeholder="Twitter Link"
            value={twitterLink}
            onChange={(e) => setTwitterLink(e.target.value)}
          />
        </LinkInputContainer>
      </LinkContainer>
      {success && <Success>{success}</Success>}
      {error && <Error>{error}</Error>}
      <ButtonContainer>
        <SaveButton onClick={saveProfile}>Save Profile</SaveButton>
        <CancelButton onClick={handleCancel}>Cancel</CancelButton>
      </ButtonContainer>
    </Wrapper>
  );
};

const Wrapper = styled.div`
      display: flex;
      flex-direction: column;
      /* align-items: center; */
      padding: 100px;
      p {
        font-size : 25px;
      }
      `;

const BoxContainer = styled.div`
      display: flex;
      flex-direction: column;
      padding: 30px 20px;
      border : 1px; solid #ccc;
      p {
        margin-top: 60px;
        padding-top: 20px;
        font-size : 20px;
      }
      `

const ProfileImage = styled.div`
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      align-items: right;
      img {
        width: 200px;
      height: 200px;
      object-fit: cover;
      border-radius: 50%;
      margin-bottom: 10px;
  }
      input {
        margin - top: 10px;
  }
      `;

const InputContainer = styled.div`
      display: flex;
      flex-direction: column;
      margin-top: 20px;
      width: 40%;
      label {
        margin - bottom: 5px;
  }
      input,
      textarea {
        border: 1px solid #ccc;
      border-radius: 5px;
      padding: 10px;
      margin-bottom: 10px;
      font-size: 16px;
      width: 60%;
  }
      TextEdit {

      }
      `;

const LinkContainer = styled.div`
      display: flex;
      flex-direction: row;
      /* margin-top: 20px; */
      width: 40%;
      justify-content: space-between;
      `;

const LinkInputContainer = styled.div`
      display: flex;
      flex-direction: column;
      width: 25%;
      label {
        margin - bottom: 5px;
  }
      input {
        border: 1px solid #ccc;
      border-radius: 5px;
      padding: 10px;
      margin-bottom: 10px;
      font-size: 16px;
      width: 100%;
  }
      `;

const ButtonContainer = styled.div`
      display: flex;
      margin-top: 20px;
      `;

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
        background - color: #999;
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


// const ProfileImage = styled.div`
//   margin-top: 20px;
//   display: flex;
//   flex-direction: column;
//   align-items: center;
//   img {
//     width: 200px;
//     height: 200px;
//     object-fit: cover;
//     border-radius: 50%;
//     margin-bottom: 10px;
//   }
//   input {
//     margin-top: 10px;
//   }
// `;
// const LinkContainer = styled.div`
//   display: flex;
//   flex-direction: row;
//   margin-top: 20px;
//   width: 100%;
//   justify-content: space-between;
// `;

// const LinkInputContainer = styled.div`
//   display: flex;
//   flex-direction: column;
//   width: 30%;
//   label {
//     margin-bottom: 5px;
//   }
//   input {
//     border: 1px solid #ccc;
//     border-radius: 5px;
//     padding: 10px;
//     margin-bottom: 10px;
//     font-size: 16px;
//     width: 100%;
//   }
// `;

// const InputContainer = styled.div`
//   display: flex;
//   flex-direction: column;
//   margin-top: 20px;
//   label {
//     margin-bottom: 5px;
//   }
//   input,
//   textarea {
//     border: 1px solid #ccc;
//     border-radius: 5px;
//     padding: 10px;
//     margin-bottom: 10px;
//     font-size: 16px;
//     width: 100%;
//     /* border: none;
//     border-bottom: 1px solid #ccc;
//     padding: 5px;
//     margin-bottom: 10px;
//     font-size: 16px;
//     width: 100%; */
//   }
// `;

// const Button = styled.button`
//   margin-top: 20px;
//   background-color: #0a95ff;
//   color: white;
//   border: none;
//   border-radius: 5px;
//   padding: 10px 20px;
//   font-size: 16px;
//   cursor: pointer;
// `;

export default EditProfile;