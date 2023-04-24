import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../store/userSlice";
import Comments from "./Comments";




const HomePage = () => {
  const user = useSelector((state) => state.user.userInfo);
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };


  return (
    <div>
      <h1>Welcome, {user.username}!</h1>
      <button onClick={handleLogout}>Logout</button>
      <Comments />
      </div>
  );
};

export default HomePage;