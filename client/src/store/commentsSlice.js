import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosInstance from '../axiosConfig';

const initialState = {
  items: [],
  status: 'idle',
  error: null,
}

export const fetchCommentAsync = createAsyncThunk(
  'comments/fetchComments',
  async (parentId, parentType, thunkAPI) => {
    try {
      const response = await axiosInstance.get(
        `comments/${parentId}?type=${parentType}`
      );
      return response.data;
    } catch (error){
      return thunkAPI.rejectWithValue(error.response.data);
    }
  }
);

const commentsSlice = createSlice({
  name: 'comments',
  initialState,
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
  extraReducers: (builder) => {
    builder
    .addCase(fetchCommentAsync.pending, (state) => {
      state.status = 'loading';
    })
    .addCase(fetchCommentAsync.fulfilled, (state,action)=>{
      state.status = 'success';
      state.items = action.payload;
    })
    .addCase(fetchCommentAsync.rejected, (state, action)=>{
      state.status = 'failed';
      state.error = action.payload;
    })
  }
});

export const { addComment, updateComment, deleteComment } = commentsSlice.actions;

export default commentsSlice.reducer;