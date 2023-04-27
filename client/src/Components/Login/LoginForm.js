import styled from "styled-components";
import { useDispatch } from "react-redux";
import { login } from "../../store/userSlice";
import axiosInstance from "../../axiosConfig";
import { useState } from "react";
import { useNavigate } from "react-router-dom";



const Container = styled.div`
  background-color: #F1F2F3;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
`;
const SocialLoginButton = styled.button`
  height: 40px;
  width: 337px;
  margin: 5px;
  padding: 11px;
  display: flex;
  font-size: 15px;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  border: 1px solid rgb(176, 173, 173);
  border-radius: 5px;
  cursor: pointer;
`;
const GoogleLogin = styled(SocialLoginButton)`
  background-color: #ffffff;
  color: black;
  
  &:hover {
    background-color: #f2f3f4;
  }
`;

const GithubLogin = styled(SocialLoginButton)`
  background-color: #151515;
  color: rgb(247, 243, 243);

  &:hover {
    background-color: #000000c8;
  }
`;

const FacebookLogin = styled(SocialLoginButton)`
  background-color: #3b5998;
  color: rgb(247, 243, 243);
  &:hover {
    background-color: #2a4074;
  }
`;

const LoginFormContainer = styled.form`
  display: flex;
  background-color: white;
  flex-direction: column;
  width: 270px;
  margin: 23px;
  padding: 2rem;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const Input = styled.input`
  padding: 10px;
  margin-bottom: ${props => props.error ? 'none' : '1rem'};
  border-radius: 5px;
  border: 1px solid #ccc;
`;

const Button = styled.button`
  padding: 10px;
  background-color: #0A95FF;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.2s;
  &:hover {
    background-color: #005a9c;
  }
`;

const Logo = styled.img`
  display: flex;
  width: 18px;
  height: 18px;
`;

const Title1 = styled.span`
  text-align:left;
  font-weight: bold;
`;

const PasswordAndForgot = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Title2 = styled.label`
  font-weight: bold;
`;

const ForgotPwd = styled.a`
  text-decoration-line: none;
  font-size: small;
`;

const MainLogo = styled.img`
  width: 50px;
  height: 57px;
  cursor: pointer;
`;
const ContainerFooter = styled.p`
display: flex;
flex-direction: column;
justify-content: spapce-around;
align-items: center;
`;

const ContainerFooterContents = styled.a`
display: flex;
font-size: small;
margin-top: 10px;
text-decoration: none;
flex-direction: column;
`

const Error = styled.p`
  color: red;
  font-size: 12px;
  margin-top: 3px;
`;

const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;


const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const validateEmail = (email) => {
    return emailRegex.test(email);
  };

  const validatePassword = (password) => {
    return passwordRegex.test(password);
  };


const loginUser = async(email, password) => {
  try{
    const response = await axiosInstance.post('/members/login', {
      email,
      password,
    });
    if (response.headers.authorization) {
      localStorage.setItem("jwt", response.headers.authorization);
      dispatch(login(response.headers.user));
      
      navigate("/")
    } else {
      setError("Invalid email or password");
    }
  } catch (error) {
    setError("Error fetching data")
  }
};
  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (!validateEmail(email)) {
      setError("The email is not a valid email address.");
      return null;
    }

    if (!validatePassword(password)) {
      setError("Password must be at least 8 characters");
      return null;
    }

    await loginUser(email, password);
  };

  return (
    <Container>
      <MainLogo alt='stackoverflowLogo' src='logo/Stack_Overflow_icon.png' />
      <GoogleLogin>
        <Logo className="googleLogo" alt='googleLogoG' src='logo/Google_G_Logo.png' />
          Log in with Google
      </GoogleLogin>
      <GithubLogin>
      <svg aria-hidden="true" className="svg-icon iconGitHub" width="18" height="18" viewBox="0 0 18 18">
        <path fill="#ffffff" 
        d="M9 1a8 8 0 0 0-2.53 15.59c.4.07.55-.17.55-.38l-.01-1.49c-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 
        0 .67-.21 2.2.82a7.42 7.42 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48l-.01 2.2c0 .21.15.46.55.38A8.01 8.01 0 0 0 9 1Z">
        </path>
      </svg>
          Log in with GitHub
      </GithubLogin>
      <FacebookLogin>
      <svg aria-hidden="true" className="svg-icon iconFacebook" width="18" height="18" viewBox="0 0 18 18">
        <path fill="#ffffff" 
        d="M3 1a2 2 0 0 0-2 2v12c0 1.1.9 2 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H3Zm6.55 16v-6.2H7.46V8.4h2.09V6.61c0-2.07
        1.26-3.2 3.1-3.2.88 0 1.64.07 1.87.1v2.16h-1.29c-1 0-1.19.48-1.19 1.18V8.4h2.39l-.31 2.42h-2.08V17h-2.5Z">
        </path>
      </svg>
          Log in with Facebook
      </FacebookLogin>
      <LoginFormContainer onSubmit={handleSubmit}>
      <Title1>Email</Title1>
        <Input
          error={error}
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        {error ? <Error>{error}</Error> : null}
        <PasswordAndForgot>
          <Title2>Password</Title2>
          <ForgotPwd href='#'>Forgot password?</ForgotPwd>
        </PasswordAndForgot>
        <Input
          error={error}
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        {error ? <Error>{error}</Error> : null}
        <Button type="submit">Login</Button>
        </LoginFormContainer>
        <ContainerFooter>
          <ContainerFooterContents>
          Don’t have an account?
          </ContainerFooterContents>
          <ContainerFooterContents href="#">
          Sign up
          </ContainerFooterContents>
          <ContainerFooterContents>
          Are you an employer?
          </ContainerFooterContents>
          <ContainerFooterContents href="#">
          Sign up on Talent
          </ContainerFooterContents>
          </ContainerFooter>
    </Container>
  );
};

export default LoginForm;