import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
// import axios from 'axios';

// 나중에 백엔드와 통신할 코드
// export const fetchComments = createAsyncThunk('comments/fetchComments', async () => {
//   const response = await axios.get('/api/comments');
//   return response.data;
// });

const commentsSlice = createSlice({
  name: 'comments',
  initialState: {
    items: [],
  },
  reducers: {
    addComment: (state, action) => {
      state.items.push(action.payload);
    },
    updateComment: (state, action) => {
      const index = state.items.findIndex((comment) => comment.id === action.payload.id); 
      if (index !== -1) {
        state.items[index] = action.payload;
      }
    },
    deleteComment: (state, action) => {
      state.items = state.items.filter((comment) => comment.id !== action.payload);
    },
  },
});

export const { addComment, updateComment, deleteComment } = commentsSlice.actions;

export default commentsSlice.reducer;