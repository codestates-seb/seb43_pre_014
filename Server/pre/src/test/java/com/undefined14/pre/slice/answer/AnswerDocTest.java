package com.undefined14.pre.slice.answer;

import com.google.gson.Gson;
import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.board.anwser.controller.AnswerController;
import com.undefined14.pre.board.anwser.dto.AnswerDto;
import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.anwser.mapper.AnswerMapper;
import com.undefined14.pre.board.anwser.service.AnswerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private AnswerMapper answerMapper;

    // Jwt 적용 시 아래 객체를 Bean으로 등록
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;

    @Test
    @DisplayName("답변 등록")
    @WithMockUser
    public void postAnswer() throws Exception {
        AnswerDto.Post post = new AnswerDto.Post();
        post.setQuestionId(1L);
        post.setBody("test-script");
        String content = gson.toJson(post);

        AnswerResponseDto responseDto = new AnswerResponseDto();
        responseDto.setMemberId(1L);
        responseDto.setAnswerId(1L);
        responseDto.setBody("test-script");
        responseDto.setAnswerStatus(Answer.AnswerStatus.ANSWER_POSTED.getStatus());
        responseDto.setComments(null);

        given(answerMapper.answerPostDtoToAnswer(any())).willReturn(new Answer());

        given(answerService.createAnswer(any(),any())).willReturn(new Answer());

        given(answerMapper.answerToAnswerResponseDto(any())).willReturn(responseDto);

        mockMvc.perform(
                        post("/board/answers")
                                .header("Authorization", "Bearer " + "test-token")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
        )
                .andExpect(status().isCreated())
                .andDo(print()) // 응답 요청 내용을 보기 위함
                .andDo(document("post-answer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용")
                                )
                        )
                        ));
    }

    @Test
    @DisplayName("답변 수정")
    @WithMockUser
    public void updateAnswer() throws Exception {

        AnswerDto.Patch patch = new AnswerDto.Patch();
        patch.setAnswerId(1L);
        patch.setBody("test-script");
        String content = gson.toJson(patch);

        AnswerResponseDto responseDto = new AnswerResponseDto();
        responseDto.setMemberId(1L);
        responseDto.setAnswerId(1L);
        responseDto.setBody("test-script");
        responseDto.setCreate_at(LocalDateTime.now());
        responseDto.setAnswerStatus(Answer.AnswerStatus.ANSWER_POSTED.getStatus());
        responseDto.setComments(null);

        given(answerMapper.answerPatchDtoToAnswer(any())).willReturn(new Answer());
        given(answerService.updateAnswer(any(),any())).willReturn(new Answer());
        given(answerMapper.answerToAnswerResponseDto(any())).willReturn(responseDto);

        mockMvc.perform(
                patch("/board/answers/{answer-id}", 1L)
                        .header("Authorization", "Bearer " + "test-token")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(status().isOk())
                // patch dto 에 들어있는 데이터가 제대로 리턴되는지만 체크
                .andExpect(jsonPath("$.answerId").value(patch.getAnswerId()))
                .andExpect(jsonPath("$.body").value(patch.getBody()))
                .andDo(print())
                .andDo(document("patch-answer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용"),
                                        fieldWithPath("create_at").type(JsonFieldType.STRING).description("작성 일자"),
                                        fieldWithPath("answerStatus").type(JsonFieldType.STRING).description("답변 삭제 유무"),
                                        fieldWithPath("comments").type(JsonFieldType.ARRAY).description("답변에 달린 댓글 리스트").optional()
                                )
                        )
                        ));
    }

    @Test
    @DisplayName("답변 삭제")
    @WithMockUser
    public void deleteAnswer() throws Exception {

        long answerId = 1L;

        doNothing().when(answerService).deleteAnswer(answerId, eq(any()));

        mockMvc.perform(
                        delete("/board/answers/{answer-id}", answerId)
                                .header("Authorization", "Bearer " + "test-token") // mock 토큰을 사용합니다.
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("delete-answer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        )
                ));
    }
}
