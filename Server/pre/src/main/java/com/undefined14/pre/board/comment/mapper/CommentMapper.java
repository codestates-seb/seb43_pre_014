package com.undefined14.pre.board.comment.mapper;

import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.comment.dto.CommentDto.Response;
import com.undefined14.pre.board.comment.dto.CommentDto;

import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDto_to_Comment(CommentDto.Post commentPostDto);
    Comment commentPatchDto_to_Comment(CommentDto.Patch commentPostDto);

    List<CommentDto.Response> comments_to_CommentResponseDtos(List<Comment> comments);
    default CommentDto.Response comment_to_CommentResponseDto(Comment comment){
        CommentDto.Response commentResponseDto;
        // 만약 comment.isInheritComment()가 ture이면 질문에 달린 댓글이므로 question에 대한 로직 작성
        if(comment.isInheritComment()) {
            commentResponseDto = new CommentDto.Response(
                    comment.getQuestion().getQuestionId(),
                    comment.getCommentId(),
                    comment.getMemberId().getMemberId(),
                    comment.getMemberId().getName(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
            // 반대
        }else{
            commentResponseDto = new Response(
                    comment.getAnswer().getAnswerId(),
                    comment.getCommentId(),
                    comment.getMemberId().getMemberId(),
                    comment.getMemberId().getName(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
        }
        return commentResponseDto;
    }
}