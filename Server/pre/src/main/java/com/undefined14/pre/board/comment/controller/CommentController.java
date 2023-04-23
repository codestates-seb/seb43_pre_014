package com.undefined14.pre.board.comment.controller;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.board.comment.dto.CommentDto;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.comment.mapper.CommentMapper;
import com.undefined14.pre.board.comment.service.CommentService;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Validated
@RequestMapping
@AllArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping("/questions/{quest-id}/comments")
    public ResponseEntity postCommentToQuestion(@RequestHeader(name = "Authorization") String token,
                                                @PathVariable("quest-id") @Positive long questId,
                                                @Valid @RequestBody CommentDto.Post commentPostDto){
        Comment comment = commentService.createCommentToQuestion(
                commentMapper.commentPostDto_to_Comment(commentPostDto),
                questId,jwtTokenizer.getMemberId(token));
        CommentDto.Response response = commentMapper.comment_to_CommentResponseDto(comment);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @PostMapping("/answers/{answer-id}/comments")
    public ResponseEntity postCommentToAnswer(@RequestHeader(name = "Authorization") String token,
                                              @PathVariable("answer-id") @Positive long questId,
                                              @Valid @RequestBody CommentDto.Post commentPostDto){
        Comment comment = commentService.createCommentToAnswer(
                commentMapper.commentPostDto_to_Comment(commentPostDto),
                questId,jwtTokenizer.getMemberId(token));
        CommentDto.Response response = commentMapper.comment_to_CommentResponseDto(comment);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity patchComment(@RequestHeader(name = "Authorization") String token,
                                       @PathVariable("comment-id") @Positive long commentId,
                                       @Valid @RequestBody CommentDto.Patch commentPatchDto) {
        commentPatchDto.setCommentId(commentId);
        Comment comment = commentService.updateComment(
                commentMapper.commentPatchDto_to_Comment(commentPatchDto),
                jwtTokenizer.getMemberId(token));
        CommentDto.Response response = commentMapper.comment_to_CommentResponseDto(comment);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @GetMapping("/questions/{quest-id}/comments")
    public ResponseEntity getCommentFromQuestion(@PathVariable("quest-id") @Positive long questId){
        List<Comment> comments = commentService.findCommentByQuestionId(questId);

        List<CommentDto.Response> responses = commentMapper.comments_to_CommentResponseDtos(comments);

        return new ResponseEntity<>(responses,
                HttpStatus.OK);
    }

    @GetMapping("/answers/{answer-id}/comments")
    public ResponseEntity getCommentFromAnswer(@PathVariable("answer-id") @Positive long answerId){
        List<Comment> comments = commentService.findCommentByAnswerId(answerId);

        List<CommentDto.Response> responses = commentMapper.comments_to_CommentResponseDtos(comments);

        return new ResponseEntity<>(responses,
                HttpStatus.OK);
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteComment(@RequestHeader(name = "Authorization") String token,
                                        @PathVariable("comment-id") @Positive long commentId){
        commentService.deleteComment(commentId,jwtTokenizer.getMemberId(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

