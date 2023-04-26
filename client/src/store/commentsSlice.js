import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosInstance from '../axiosConfig';
const initialState = {
  items: [],
  status: 'idle',
  error: null,
}

export const fetchCommentAsync = createAsyncThunk(
  'comments/fetchComment',
  async ({ parentId, parentType }, thunkAPI) => {
    const accessToken = axiosInstance.defaults.headers.common['Authorization'];
    try {
      let response;
      if (parentType === 'question') {
        response = await axiosInstance.get(`board/questions/${parentId}/comments`, {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Authorization: accessToken,
            Accept: 'application/json',
          },
        });
      } else if (parentType === 'answer') {
        response = await axiosInstance.get(`board/answers/${parentId}/comments`, {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            Authorization: accessToken,
            Accept: 'application/json',
          },
        });
      }
      return response.data;
    } catch (error) {
      return thunkAPI.rejectWithValue(error.response.data);
    }
  }
);

export const postQuestionCommentAsync = createAsyncThunk(
  'comments/postQuestionComment',
  async ({ parentId, body }, thunkAPI) => {
    const accessToken = axiosInstance.defaults.headers.common['Authorization'];
    try {
      const response = await axiosInstance.post(`board/questions/${parentId}/comments`, { body }, {
        headers: { 'Content-Type': 'application/json;charset=UTF-8', Authorization: accessToken, Accept: 'application/json' },
      });
      return response.data;
    } catch (error){
      return thunkAPI.rejectWithValue(error.response.data);
    }
  }
);

export const postAnswerCommentAsync = createAsyncThunk(
  'comments/postAnswerComment',
  async ({ parentId, body }, thunkAPI) => {
    const accessToken = axiosInstance.defaults.headers.common['Authorization'];
    try {
      const response = await axiosInstance.post(`board/answers/${parentId}/comments`, { body }, {
        headers: { 'Content-Type': 'application/json;charset=UTF-8', Authorization: accessToken, Accept: 'application/json' },
      });
      return response.data;
    } catch (error){
      return thunkAPI.rejectWithValue(error.response.data);
    }
  }
);

export const updateCommentAsync = createAsyncThunk(
  'comments/updateComment',
  async ({ commentId, body }, thunkAPI) => {
    const accessToken = axiosInstance.defaults.headers.common['Authorization'];
    try {
      const response = await axiosInstance.patch(`board/comments/${commentId}`, { commentId, body }, {
        headers: { 'Content-Type': 'application/json;charset=UTF-8', Authorization: accessToken, Accept: 'application/json' },
      });
      return response.data;
    } catch (error){
      return thunkAPI.rejectWithValue(error.response.data);
    }
  }
);

export const deleteCommentAsync = createAsyncThunk(
  'comments/deleteComment',
  async (commentId, thunkAPI) => {
    const accessToken = axiosInstance.defaults.headers.common['Authorization'];
    try {
      await axiosInstance.delete(`board/comments/${commentId}`, {
        headers: { 'Content-Type': 'application/json;charset=UTF-8', Authorization: accessToken, Accept: 'application/json' },
      });
      return commentId;
    } catch (error){
      return thunkAPI.rejectWithValue(error.response.data);
    }
  }
);

const commentsSlice = createSlice({
  name: 'comments',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
    .addCase(postQuestionCommentAsync.pending, (state) => {
      state.status = 'loading';
    })
    .addCase(postQuestionCommentAsync.fulfilled, (state, action) => {
      state.status = 'idle';
      state.items.push(action.payload);
    })
    .addCase(postQuestionCommentAsync.rejected, (state, action) => {
      state.status = 'failed';
      state.error = action.payload;
    })
    .addCase(postAnswerCommentAsync.pending, (state) => {
      state.status = 'loading';
    })
    .addCase(postAnswerCommentAsync.fulfilled, (state, action) => {
      state.status = 'idle';
      state.items.push(action.payload);
    })
    .addCase(postAnswerCommentAsync.rejected, (state, action) => {
      state.status = 'failed';
      state.error = action.payload;
    })
    .addCase(updateCommentAsync.pending, (state) => {
      state.status = 'loading';
    })
    .addCase(updateCommentAsync.fulfilled, (state, action) => {
      state.status = 'idle';
      const index = state.items.findIndex((comment) => comment.commentId === action.payload.commentId);
      if (index !== -1) {
        state.items[index] = action.payload;
      }
    })
    .addCase(updateCommentAsync.rejected, (state, action) => {
      state.status = 'failed';
      state.error = action.payload;
    })
    .addCase(deleteCommentAsync.pending, (state) => {
      state.status = 'loading';
    })
    .addCase(deleteCommentAsync.fulfilled, (state, action) => {
      state.status = 'idle';
      state.items = state.items.filter((comment) => comment.commentId !== action.payload);
    })
    .addCase(deleteCommentAsync.rejected, (state, action) => {
      state.status = 'failed';
      state.error = action.payload;
    });
  },
});

export default commentsSlice.reducer;

