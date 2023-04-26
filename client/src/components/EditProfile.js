import React, { useState } from "react";
import styled from "styled-components";
import TextEdit from "./TextEdit.js";

const EditProfile = () => {
  const [profileImage, setProfileImage] = useState("");
  const [displayName, setDisplayName] = useState("");
  const [location, setLocation] = useState("");
  const [title, setTitle] = useState("");
  const [aboutMe, setAboutMe] = useState("");
  const [websiteLink, setWebsiteLink] = useState("");
  const [githubLink, setGithubLink] = useState("");
  const [twitterLink, setTwitterLink] = useState("");

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

  return (

    <Wrapper>
      <h1>Edit your profile</h1>
      <p>Public information</p>
      <BoxContainer>
        <ProfileImage>
          <img src={profileImage} alt="" />
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
          <TextEdit
            placeholder="About Me"
            value={aboutMe}
            onChange={(e) => setAboutMe(e.target.value)}
          />
        </InputContainer>
      </BoxContainer>

      <p>Links</p>
      <BoxContainer>

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
      </BoxContainer>

      <BoxContainer>

        <ButtonContainer>
          <SaveButton>Save Profile</SaveButton>
          <CancelButton onClick={handleCancel}>Cancel</CancelButton>
        </ButtonContainer>
      </BoxContainer>
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
      border: none;
      border-radius: 5px;
      padding: 10px 20px;
      font-size: 16px;
      margin-right: 10px;
      cursor: pointer;
      &:hover {
        background - color: #0074cc;
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