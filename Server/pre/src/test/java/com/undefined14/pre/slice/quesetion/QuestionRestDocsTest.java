package com.undefined14.pre.slice.quesetion;

import com.google.gson.Gson;
import com.undefined14.pre.auth.config.SecurityConfiguration;
import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.board.question.controller.QuestionController;
import com.undefined14.pre.board.question.dto.QuestionPatchDto;
import com.undefined14.pre.board.question.dto.QuestionPostDto;
import com.undefined14.pre.board.question.dto.QuestionResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.link.LinkService;
import com.undefined14.pre.board.question.link.LinkServiceImpl;
import com.undefined14.pre.board.question.mapper.QuestionMapper;
import com.undefined14.pre.board.question.service.QuestionService;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.exception.ExceptionCode;
import com.undefined14.pre.member.dto.MemberPatchDto;
import com.undefined14.pre.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class QuestionRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private LinkServiceImpl linkServiceImpl;
    @MockBean
    private QuestionMapper mapper;
    @Autowired
    private Gson gson;
    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;
    @MockBean
    private JwtTokenizer jwtTokenizer;

    @Test
    @DisplayName("질문 작성")
    public void postQuestionTest() throws Exception {
        // given
        QuestionPostDto post = new QuestionPostDto("title", "problem", "expecting");
        String content = gson.toJson(post);

        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setQuestionId(1L);
        responseDto.setMemberId(1L);
        responseDto.setTitle("title");
        responseDto.setProblem("problem");
        responseDto.setExpecting("expecting");
        responseDto.setCreate_at(LocalDateTime.now());
        responseDto.setQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE);
        responseDto.setComments(new ArrayList<>());
        responseDto.setAnswerResponseDto(new ArrayList<>());

        given(mapper.questionPostDtoToQuestion(any(QuestionPostDto.class))).willReturn(new Question());
        Question mockResultQuestion = new Question();
        mockResultQuestion.setQuestionId(1L);
        given(questionService.save(any(Question.class), any(String.class))).willReturn(mockResultQuestion);

        given(mapper.questionToQuestionResponseDto(any(Question.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/board/questions")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + "test-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/questions/1"))))
                .andDo(print())
                .andDo(document("post-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("problem").type(JsonFieldType.STRING).description("문제"),
                                        fieldWithPath("expecting").type(JsonFieldType.STRING).description("예상한 원인")
                                )
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )
                ));
    }

    @Test
    @DisplayName("질문 수정")
    public void patchQuestionTest() throws Exception {
        // given
        QuestionPatchDto patchDto = new QuestionPatchDto(1L, "제목", "문제", "원인");
        String content = gson.toJson(patchDto);

        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setQuestionId(1L);
        responseDto.setMemberId(1L);
        responseDto.setTitle("수정된 제목");
        responseDto.setProblem("수정된 문제");
        responseDto.setExpecting("수정된 원인");
        responseDto.setCreate_at(LocalDateTime.now());
        responseDto.setQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE);
        responseDto.setComments(new ArrayList<>());
        responseDto.setAnswerResponseDto(new ArrayList<>());

        given(mapper.questionPatchDtoToQuestion(any(QuestionPatchDto.class))).willReturn(new Question());
        given(questionService.updateQuestion(any(Question.class), any(String.class))).willReturn(new Question());
        given(mapper.questionToQuestionResponseDto(any(Question.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/board/questions/{question-id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "bearer " + "test-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.problem").value(responseDto.getProblem()))
                .andExpect(jsonPath("$.expecting").value(responseDto.getExpecting()))
                .andDo(print())
                .andDo(document("patch-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("problem").type(JsonFieldType.STRING).description("문제"),
                                fieldWithPath("expecting").type(JsonFieldType.STRING).description("예상 원인")
                        ),
                        responseFields(
                                fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 ID"),
                                fieldWithPath("memberId").description("회원 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("problem").type(JsonFieldType.STRING).description("문제"),
                                fieldWithPath("expecting").type(JsonFieldType.STRING).description("예상 원인"),
                                fieldWithPath("create_at").type(JsonFieldType.STRING).description("작성 시기"),
                                fieldWithPath("questionStatus").type(JsonFieldType.STRING).description("질문 상태"),
                                fieldWithPath("comments").type(JsonFieldType.ARRAY).description("질문 댓글 목록"),
                                fieldWithPath("answerResponseDto").type(JsonFieldType.ARRAY).description("질문에 달린 답변 목록")
                        )
                ));

    }

    @Test
    @DisplayName("질문 조회")
    public void getQuestionTest() throws Exception {
        // given
        Long questionId = 1L;
        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setQuestionId(1L);
        responseDto.setMemberId(1L);
        responseDto.setTitle("제목");
        responseDto.setProblem("문제");
        responseDto.setExpecting("원인");
        responseDto.setCreate_at(LocalDateTime.now());
        responseDto.setQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE);
        responseDto.setComments(new ArrayList<>());
        responseDto.setAnswerResponseDto(new ArrayList<>());
        String content = gson.toJson(responseDto);

        given(mapper.questionPostDtoToQuestion(Mockito.any(QuestionPostDto.class))).willReturn(new Question());
        given(questionService.findQuestionById(anyLong()))
                .willReturn(new Question());
        given(mapper.questionToQuestionResponseDto(any(Question.class))).willReturn(responseDto);

        // when
        MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .get("/board/questions/{question-id}", questionId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer test-token")
                .accept(MediaType.APPLICATION_JSON);

// then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(responseDto.getQuestionId()))
                .andExpect(jsonPath("$.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.problem").value(responseDto.getProblem()))
                .andExpect(jsonPath("$.expecting").value(responseDto.getExpecting()))
                .andDo(document("get-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("question-id").description("질문 ID")
                        ),
                        responseFields(
                                fieldWithPath("questionId").description("질문 ID"),
                                fieldWithPath("memberId").description("회원 ID"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("problem").description("문제"),
                                fieldWithPath("expecting").description("예상 원인"),
                                fieldWithPath("create_at").description("질문 작성 시간"),
                                fieldWithPath("questionStatus").description("질문 상태"),
                                fieldWithPath("comments").description("질문의 댓글 목록"),
                                fieldWithPath("answerResponseDto").description("질문의 답변 목록")
                        )
                ));
    }
    @Test
    @DisplayName("질문 삭제")
    public void deleteQuestionTest() throws Exception {
        // given
        Long questionId = 1L;

//        Question question = new Question();
//        question.setQuestionId(questionId);
//        question.setTitle("제목");
//        question.setProblem("문제");
//        question.setExpecting("익스펙팅");
//        question.setQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE);
//        question.setMember(member);
//        question.

        doNothing().when(questionService).deleteQuestionById(questionId, "Bearer " + "test-token");

        // when & then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/board/questions/{question-id}", questionId)
                                .header("Authorization", "Bearer " + "test-token")

                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
//                .andExpect(header().string("Location", is(startsWith("/questions/1"))))
                .andDo(
                        document(
                                "delete-questions-success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("question-id").description("질문 ID")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("인증 토큰")
                        )
                ));
    }



}
