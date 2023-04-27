import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  postQuestionCommentAsync,
  postAnswerCommentAsync,
  updateCommentAsync,
  deleteCommentAsync,
  fetchCommentAsync,
} from '../../store/commentsSlice';
import styled from 'styled-components';
import { useEffect } from 'react';

const StyledComments = styled.div`
  width: 100%;
  margin: 0 auto;
  padding: 1rem 0 1rem 1rem;
  box-sizing: border-box;
`;

const CommentsList = styled.ul`
  list-style-type: none;
  padding: 0;
`;

const CommentItem = styled.li`
  background-color: #f5f5f5;
  border-radius: 4px;
  padding: 1rem;
  margin-bottom: 1rem;
`;

const CommentFooter = styled.div`
  display: flex;
  align-items: center;
  font-size: 0.8rem;
  color: #666;
`;

const CommentFooterLink = styled.a`
  color: #0079ff;
  text-decoration: none;
  margin-right: 0.5rem;
`;

const CommentFooterButton = styled.button`
  background: none;
  border: none;
  color: #0079ff;
  cursor: pointer;
  font-size: 0.8rem;
  padding: 0;
  margin-left: 0.5rem;
`;

const CommentTimestamp = styled.span`
  margin-left: 0.5rem;
`;

const CommentForm = styled.form`
  display: flex;
  margin-top: 1rem;
`;

const CommentFormInput = styled.input`
  flex-grow: 1;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 0.5rem;
  margin-right: 0.5rem;
`;

const CommentFormButton = styled.button`
  background-color: #0A95FF;
  color: #fff;
  border: none;
  border-radius: 3px;
  box-shadow: inset 0px 1px rgba(255, 255, 255, .5);
  padding: 0.5rem 1rem;
  cursor: pointer;
`;

const Comments = ({ parentId, parentType }) => {
  const dispatch = useDispatch();
  const comments = useSelector((state) => state.comments.items);
  const user = useSelector((state) => state.user.userInfo);
  const status = useSelector((state) => state.comments.status);
  const error = useSelector((state) => state.comments.error);

  useEffect(() => {
    if (status === 'idle') {
      dispatch(fetchCommentAsync({ parentId, parentType }));
    }
  }, [dispatch, parentId, parentType, status]);

  const [newComment, setNewComment] = useState('');
  const [editingComment, setEditingComment] = useState(null);
  const [editedComment, setEditedComment] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (parentType === 'question') {
      dispatch(postQuestionCommentAsync({ parentId, body: newComment }));
    } else if (parentType === 'answer') {
      dispatch(postAnswerCommentAsync({ parentId, body: newComment }));
    }
    setNewComment('');
  };

  const handleEdit = (comment) => {
    if (comment.author === user.name) {
      setEditingComment(comment.commentId);
      setEditedComment(comment.body);
    } else {
      alert("You can only edit your own comments.");
    }
  };

  const handleUpdate = (e) => {
    e.preventDefault();
    dispatch(
      updateCommentAsync({
        commentId: editingComment,
        body: editedComment,
      })
    );
    setEditingComment(null);
    setEditedComment('');
  };

  const handleDelete = (commentId, commentAuthor) => {
    if (commentAuthor === user.name) {
      dispatch(deleteCommentAsync(commentId));
    } else {
      alert("You can only delete your own comments.");
    }
  };

  return (
    <StyledComments>
      <h2>Comments</h2>
      <CommentsList>
        {status === 'loading' && <p>Loading...</p>}
        {status === 'success' &&
          comments.map((comment) => (
            <CommentItem key={comment.commentId}>
              {editingComment === comment.commentId ? (
                <CommentForm onSubmit={handleUpdate}>
                  <CommentFormInput
                    type="text"
                    value={editedComment}
                    onChange={(e) => setEditedComment(e.target.value)}
                  />
                  <CommentFormButton type="submit">Update</CommentFormButton>
                </CommentForm>
              ) : (
                <>
                  <p>{comment.body}</p>
                  <CommentFooter>
                    <CommentFooterLink href="#">{comment.author}</CommentFooterLink>
                    <CommentTimestamp>
                      {new Date(comment.timestamp).toLocaleString()}
                    </CommentTimestamp>
                    <CommentFooterButton onClick={() => handleEdit(comment)}>
                      Edit
                    </CommentFooterButton>
                    <CommentFooterButton onClick={() => handleDelete(comment.commentId, comment.author)}>
                      Delete
                    </CommentFooterButton>
                  </CommentFooter>
                </>
              )}
            </CommentItem>
          ))}
        {status === 'failed' && <p>Error: {error}</p>}
      </CommentsList>
      <CommentForm onSubmit={handleSubmit}>
        <CommentFormInput
          type="text"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="Write a comment..."
        />
        <CommentFormButton type="submit">Submit</CommentFormButton>
      </CommentForm>
    </StyledComments>
  );
};

export default Comments;