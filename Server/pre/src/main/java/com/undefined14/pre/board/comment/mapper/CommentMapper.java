package com.undefined14.pre.board.comment.mapper;

import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.comment.dto.CommentDto.Response;
import com.undefined14.pre.board.comment.dto.CommentDto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment commentPostDto_to_Comment(CommentDto.Post commentPostDto);
    Comment commentPatchDto_to_Comment(CommentDto.Patch commentPostDto);

    List<CommentDto.Response> comments_to_CommentResponseDtos(List<Comment> comments);

    default CommentDto.Response comment_to_CommentResponseDto(Comment comment){
        CommentDto.Response commentResponseDto;
        if(comment.isInheritQuestion()) {
            commentResponseDto = new Response(
//                    comment.getQuestion().getQuestionId(),
                    comment.getCommentId(),
                    comment.getMember().getMemberId(),
                    comment.getBody(),
                    comment.getCreatedAt()
            );
        }else{
            commentResponseDto = new Response(
//                    comment.getAnswer().getAnswerId(),
                    comment.getCommentId(),
                    comment.getMember().getMemberId(),
                    comment.getBody(),
                    comment.getCreatedAt()
            );
        }
        return commentResponseDto;
    }
}