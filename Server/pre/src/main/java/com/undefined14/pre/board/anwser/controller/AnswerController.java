package com.undefined14.pre.board.anwser.controller;

import com.undefined14.pre.board.anwser.dto.AnswerDto;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.anwser.mapper.AnswerMapper;
import com.undefined14.pre.board.anwser.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@CrossOrigin(origins = "http://localhost:3000") // CORS 정책 허용
@RestController
@RequestMapping("/board/answer")
@Validated
@AllArgsConstructor
@Slf4j
public class AnswerController {
    private final AnswerMapper mapper;
    private final AnswerService service;

    // 답변 등록
    @PostMapping
    public ResponseEntity postAnswer(@RequestHeader(name = "Authorization") String token,
                                     @Validated @RequestBody AnswerDto.Post answerPostDto) {
        log.info(String.valueOf(answerPostDto));

        Answer response = service.createAnswer(
                mapper.answerPostDtoToAnswer(answerPostDto), token);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 답변 수정
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@RequestHeader(name = "Authorization") String token,
                                      @PathVariable("answer-id") @Positive long answerId,
                                      @Validated @RequestBody AnswerDto.Patch answerPatchDto) {
        answerPatchDto.setAnswerId(answerId);

        log.info(String.valueOf(answerPatchDto));

        Answer response = service.updateAnswer(mapper.answerPatchDtoToAnswer(answerPatchDto),token);

        return new ResponseEntity<>(mapper.answerToAnswerResponseDto(response),HttpStatus.OK);
    }

    // 답변 조회
//    @GetMapping("/{answer-id}")
//    public ResponseEntity getAnswer(@PathVariable("answer-id") @Positive long answerId) {
//
//        log.info(String.valueOf(answerId));
//
//        Answer response = service.findAnswer(answerId);
//
//        return new ResponseEntity<>(mapper.answerToAnswerResponseDto(response),HttpStatus.OK);
//    }

    // 답변 삭제
    @DeleteMapping("/{answer-id}")
    public void deleteAnswer(@RequestHeader(name = "Authorization") String token,
                             @PathVariable("answer-id") @Positive long answerId) {

        log.info(String.valueOf(answerId));

        service.deleteAnswer(answerId,token);
    }
}
