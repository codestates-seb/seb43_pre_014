import React from "react";
import { useSelector } from "react-redux";
import Comments from "./Comments";
import LogOutBtn from "./LogoutForm";




const HomePage = () => { // homepage 파트는 메인페이지가 나왔을 때 바꿔주자.
  const user = useSelector((state) => state.user.userInfo);

  return (
    <div>
      <h1>Welcome, {user.username}!</h1>
      <LogOutBtn />
      <Comments />
      </div>
  );
};

export default HomePage;