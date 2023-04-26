package com.undefined14.pre.board.comment.mapper;

import com.undefined14.pre.board.comment.dto.CommentDto;
import com.undefined14.pre.board.comment.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-25T12:29:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDto_to_Comment(CommentDto.Post commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setBody( commentPostDto.getBody() );

        return comment;
    }

    @Override
    public Comment commentPatchDto_to_Comment(CommentDto.Patch commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( commentPostDto.getCommentId() );
        comment.setBody( commentPostDto.getBody() );

        return comment;
    }

    @Override
    public List<CommentDto.Response> comments_to_CommentResponseDtos(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDto.Response> list = new ArrayList<CommentDto.Response>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( comment_to_CommentResponseDto( comment ) );
        }

        return list;
    }
}
