import { configureStore, createSlice } from '@reduxjs/toolkit'

let id = createSlice({
  name : 'id',
  initialState : '',
  reducers: {
    setId: (state, action) => action.payload,
  },
})

export const { setId } = id.actions;

export default configureStore({
  reducer: {
    id : id.reducer
  }
}) 
