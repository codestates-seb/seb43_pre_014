package com.undefined14.pre.board.question.controller;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.board.question.dto.QuestionPatchDto;
import com.undefined14.pre.board.question.dto.QuestionPostDto;
import com.undefined14.pre.board.question.dto.QuestionResponseAllDto;
import com.undefined14.pre.board.question.dto.QuestionResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.mapper.QuestionMapper;
import com.undefined14.pre.board.question.service.QuestionService;
import com.undefined14.pre.board.question.link.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "X-AUTH-TOKEN")
@RestController
@RequestMapping("/board/questions")
@AllArgsConstructor
public class QuestionController {
    private final static String QUESTION_DEFAULT_URL = "/questions";
    private final QuestionMapper mapper;
    private final QuestionService service;
    private final LinkService<QuestionResponseAllDto> linkService;
    private final JwtTokenizer jwtTokenizer;

    // 질문 작성
    @PostMapping
    public ResponseEntity postQuestion(@RequestHeader(name = "Authorization") String token,
                                       @Validated @RequestBody QuestionPostDto questionPostDto) {

        Question question = service.save(mapper.questionPostDtoToQuestion(questionPostDto), token);
        QuestionResponseDto responseDto = mapper.questionToQuestionResponseDto(question);

        URI location = UriComponentsBuilder.fromUriString(QUESTION_DEFAULT_URL)
                .path("/{id}")
                .buildAndExpand(responseDto.getQuestionId())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    // 질문 전체 조회 (QuestionStatus QUESTION_DELETED("삭제 완료")도 조회 됨)
    @GetMapping("/all")
    public ResponseEntity<List<QuestionResponseDto>> getQuestions() {
        List<Question> questions = service.findAll();
        List<QuestionResponseDto> responseDtos = mapper.questionListToResponseDtoList(questions);

        return ResponseEntity.ok().body(responseDtos);
    }

    // 질문 한 건 조회
    @GetMapping("/{question-id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable ("question-id") @Positive Long questionId) {
        Question question = service.findQuestionById(questionId);
        QuestionResponseDto questionResponseDto = mapper.questionToQuestionResponseDto(question);
        return ResponseEntity.ok(questionResponseDto);
    }


    // 질문 목록 페이지네이션
    @GetMapping
    public ResponseEntity<Page<QuestionResponseAllDto>> getQuestionsPages(
            Pageable pageable,
            HttpServletRequest request) {
        Page<QuestionResponseAllDto> questions = service.findQuestions(pageable);

        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());

        return ResponseEntity.ok()
                .header(HttpHeaders.LINK, linkService.createLinkHeader(questions, request, baseUrl))
                .body(questions);
    }

    // 질문 수정
    @PatchMapping("/{question-id}")
    public ResponseEntity<QuestionResponseDto> patchQuestion(@RequestHeader(name = "Authorization") String token,
                                                             @PathVariable("question-id") @Positive Long questionId,
                                                             @RequestBody QuestionPatchDto questionPatchDto) {
        questionPatchDto.setQuestionId(questionId);
        Question question = service.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto), token);
        QuestionResponseDto responseDto = mapper.questionToQuestionResponseDto(question);
        return ResponseEntity.ok().body(responseDto);
    }

    // 질문 삭제 -> questionStatus만 변경됨
    @DeleteMapping("/{question-id}")
    public ResponseEntity<Void> deleteQuesetion(@RequestHeader(name = "Authorization") String token,
                                                @PathVariable("question-id") @Positive Long questionId) {
        service.deleteQuestionById(questionId, token);
        return ResponseEntity.noContent().build();
    }


}
