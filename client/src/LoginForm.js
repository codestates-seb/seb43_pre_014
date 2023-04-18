import React, { useState } from 'react';
import './LoginForm.css';

function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const validateEmail = (email) => {
    //이메일 유효성 검사
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);  
  };

  const validatePassword = (password) => {
    return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(password)
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    if (email === ''){
      setError('Email cannot be empty.');
    }
    if (!validateEmail(email)) {
      setError('The email is not a valid email address.');
      return;
    }

    if (password.length < 8) {
      setError('비밀번호는 최소 8자 이상이어야 합니다. -> 영어로');
      return;
    }
    if (!validatePassword(password)) {
      setError('올바른 비밀번호 형식이 아님')
      return;
    }

    // 로그인 로직을 여기에 구현하세요.
    // 예: 백엔드 협업 로그인을 처리합니다.
    // 입력한 이메일과 비밀번호가 서버에 저장돼 있는 데이터와 다르다면? => 'The email or password is incorrect.. 추가하기.

    setError('');

  };

  return (
    <div className="container">

    <img className='mainLogo' alt='stackoverflowLogo' src='logo/Stack_Overflow_icon.png'>
    </img>
    <button className="googleLogin">
    <img className="googleLogo" alt='googleLogoG' src='logo/Google_G_Logo.png'/>
      Log in with Google
      </button>
      <button className="githubLogin">
    <img className="githubLogo" alt='githubLogoWhite' src='logo/github_white.png'/>
      Log in with GitHub
      </button>
      <button className="facebookLogin">
    <img className="facebookLogo" alt='facebookLogo' src='logo/facebookLogo.png'/>
      Log in with Facebook
      </button>      
    <form className="login-form" onSubmit={handleSubmit}>   
      <span className='title1'>Email</span>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <div className='passwordAndForgot'>
        <label className='title2'>Password
          <a className='forgotpwd' href='d'>Forgot password?</a>
        </label>
      </div>
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {error && <div className="error-message">{error}</div>}
      <button type="submit">Log In</button>
    </form>
    </div>
  );
}

export default LoginForm;