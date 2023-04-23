import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { addComment, updateComment, deleteComment } from '../store/commentsSlice';
import './Comments.css';

const Comments = () => {
  const comments = useSelector((state) => state.comments.items);
  const user = useSelector((state) => state.user.userInfo);
  const dispatch = useDispatch();

  const [newComment, setNewComment] = useState('');
  const [editingComment, setEditingComment] = useState(null);
  const [editedComment, setEditedComment] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const newCommentObject = {
      id: Date.now(),
      text: newComment,
      author: user.username,
      timestamp: Date.now(),
    };
    dispatch(addComment(newCommentObject));
    setNewComment('');
  };

  const handleEdit = (comment) => {
    setEditingComment(comment.id);
    setEditedComment(comment.text);
  };

  const handleUpdate = (e) => {
    e.preventDefault();
    dispatch(
      updateComment({
        id: editingComment,
        text: editedComment,
        author: user.username, // 실제 작성자 이름으로 변경해야 함
        timestamp: Date.now(),
      })
    );
    setEditingComment(null);
    setEditedComment('');
  };


  const handleDelete = (id) => {
    dispatch(deleteComment(id));
  };

  return (<div className="comments">
  <h2>Comments</h2>
  <ul className="comments-list">
    {comments.map((comment) => (
      <li key={comment.id} className="comment">
        {editingComment === comment.id ? (
          <form onSubmit={handleUpdate}>
            <input
              type="text"
              value={editedComment}
              onChange={(e) => setEditedComment(e.target.value)}
            />
            <button type="submit">Update</button>
          </form>
        ) : (
          <>
            <p>{comment.text}</p>
            <div className="comment-footer">
              <a href="#">{comment.author}</a> 
              <span className="comment-timestamp">{new Date(comment.timestamp).toLocaleString()}</span>
              <button onClick={() => handleEdit(comment)}>Edit</button>
              <button onClick={() => handleDelete(comment.id)}>Delete</button>
            </div>
          </>
        )}
      </li>
    ))}
  </ul>
  <form onSubmit={handleSubmit} className="comment-form">
    <input
      type="text"
      value={newComment}
      onChange={(e) => setNewComment(e.target.value)}
      placeholder="Write a comment..."
    />
    <button type="submit">Submit</button>
  </form>
</div>
);
};

export default Comments;