package com.undefined14.pre.slice.quesetion;

import com.google.gson.Gson;
import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.board.question.controller.QuestionController;
import com.undefined14.pre.board.question.dto.QuestionPatchDto;
import com.undefined14.pre.board.question.dto.QuestionPostDto;
import com.undefined14.pre.board.question.dto.QuestionResponseAllDto;
import com.undefined14.pre.board.question.dto.QuestionResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.link.LinkServiceImpl;
import com.undefined14.pre.board.question.mapper.QuestionMapper;
import com.undefined14.pre.board.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    @WithMockUser
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
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
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
    @WithMockUser
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
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
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
    @WithMockUser
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
    @WithMockUser
    public void deleteQuestionTest() throws Exception {
        // given
        Long questionId = 1L;

        doNothing().when(questionService).deleteQuestionById(questionId, "Bearer " + "test-token");

        // when & then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/board/questions/{question-id}", questionId)
                                .header("Authorization", "Bearer " + "test-token")

                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-questions",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("question-id").description("질문 ID")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        )
                ));
    }

    @Test
    @DisplayName("질문 목록 페이지네이션")
    @WithMockUser
    public void getQuestionsPagesTest() throws Exception {
        // given
        List<QuestionResponseAllDto> questionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            QuestionResponseAllDto questionResponseAllDto = new QuestionResponseAllDto();
            questionResponseAllDto.setQuestionId((long) (i + 1));
            questionResponseAllDto.setMemberId((long) (i + 1));
            questionResponseAllDto.setTitle("title" + (i + 1));
            questionResponseAllDto.setProblem("problem" + (i + 1));
            questionResponseAllDto.setExpecting("expecting" + (i + 1));
            questionResponseAllDto.setCreate_at(LocalDateTime.now().minusDays(i + 1));
            questionResponseAllDto.setQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE);
            questionList.add(questionResponseAllDto);
        }
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("questionId").descending());
        Page<QuestionResponseAllDto> questionPage = new PageImpl<>(questionList, pageRequest, questionList.size());

        given(questionService.findQuestions(any(Pageable.class))).willReturn(questionPage);

        // when & then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/board/questions/")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "questionId,desc")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].questionId").value(1L))
                .andExpect(jsonPath("$.content[0].memberId").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("title1"))
                .andExpect(jsonPath("$.content[0].problem").value("problem1"))
                .andExpect(jsonPath("$.content[0].expecting").value("expecting1"))
                .andExpect(jsonPath("$.content[0].questionStatus").value("QUESTION_ACTIVE"))
                .andExpect(jsonPath("$.content[9].questionId").value(10L))
                .andExpect(jsonPath("$.content[9].memberId").value(10L))
                .andExpect(jsonPath("$.content[9].title").value("title10"))
                .andExpect(jsonPath("$.content[9].problem").value("problem10"))
                .andExpect(jsonPath("$.content[9].expecting").value("expecting10"))
                .andExpect(jsonPath("$.content[9].questionStatus").value("QUESTION_ACTIVE"))
                .andDo(print())
                .andDo(
                        document(
                                "get-questions-pages-success",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 크기"),
                                        parameterWithName("sort").description("정렬 기준 (필드명,asc/desc)")
                                ),
                                responseFields(
                                        fieldWithPath("content[].questionId").description("질문 ID"),
                                        fieldWithPath("content[].memberId").description("멤버 ID"),
                                        fieldWithPath("content[].title").description("질문 제목"),
                                        fieldWithPath("content[].problem").description("질문 내용"),
                                        fieldWithPath("content[].expecting").description("예상 원인"),
                                        fieldWithPath("content[].create_at").description("질문 작성일"),
                                        fieldWithPath("content[].questionStatus").description("질문 상태"),
                                        fieldWithPath("pageable").description("페이지 정보"),
                                        fieldWithPath("pageable.sort").description("페이지 정렬 정보"),
                                        fieldWithPath("pageable.sort.empty").description("정렬 정보가 비어 있는지 여부"),
                                        fieldWithPath("pageable.sort.sorted").description("정렬 정보가 정렬되어 있는지 여부"),
                                        fieldWithPath("pageable.sort.unsorted").description("정렬 정보가 정렬되지 않았는지 여부?"),
                                        fieldWithPath("pageable.offset").description("페이지 오프셋"),
                                        fieldWithPath("pageable.pageNumber").description("페이지 번호"),
                                        fieldWithPath("pageable.pageSize").description("페이지 크기"),
                                        fieldWithPath("pageable.paged").description("페이징 가능 여부"),
                                        fieldWithPath("pageable.unpaged").description("페이징 불가능 여부"),
                                        fieldWithPath("last").description("마지막 페이지 여부"),
                                        fieldWithPath("totalElements").description("전체 요소 수"),
                                        fieldWithPath("totalPages").description("전체 페이지 수"),
                                        fieldWithPath("size").description("페이지 크기"),
                                        fieldWithPath("number").description("페이지 번호"),
                                        fieldWithPath("sort").description("정렬 정보"),
                                        fieldWithPath("sort.empty").description("정렬 정보가 비어 있는지 여부"),
                                        fieldWithPath("sort.sorted").description("정렬 정보가 정렬되어 있는지 여부"),
                                        fieldWithPath("sort.unsorted").description("정렬 정보가 정렬되어 있지 않은지 여부"),
                                        fieldWithPath("first").description("첫 번째 페이지 여부"),
                                        fieldWithPath("numberOfElements").description("현재 페이지 요소 수"),
                                        fieldWithPath("empty").description("현재 페이지가 비어있는지 여부")
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.LINK)
                                                .description("페이징 링크 정보(Web Linking (RFC5988) format)")
                                                .optional()
                                ),
                                links(
                                        linkWithRel("first").description("첫 페이지 결과에 대한 링크").optional(),
                                        linkWithRel("prev").description("이전 페이지 결과에 대한 링크").optional(),
                                        linkWithRel("self").description("현재 페이지 결과에 대한 링크").optional(),
                                        linkWithRel("next").description("다음 페이지 결과에 대한 링크").optional(),
                                        linkWithRel("last").description("마지막 페이지 결과에 대한 링크").optional()
                                )
                        )
                );
    }






}
