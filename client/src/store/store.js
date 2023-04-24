import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./userSlice";
import commentsReducer from './commentsSlice'
import idReducer from "./idSlice";

const store = configureStore({
  reducer: {
    user: userReducer,
    comments: commentsReducer,
    id: idReducer,
  },
});

export default store;