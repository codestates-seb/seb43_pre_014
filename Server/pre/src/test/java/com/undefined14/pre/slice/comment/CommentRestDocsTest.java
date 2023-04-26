package com.undefined14.pre.slice.comment;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;

import com.undefined14.pre.PreApplication;
import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.board.anwser.service.AnswerService;
import com.undefined14.pre.board.comment.controller.CommentController;
import com.undefined14.pre.board.comment.dto.CommentDto;
import com.undefined14.pre.board.comment.dto.CommentDto.*;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.comment.mapper.CommentMapper;
import com.undefined14.pre.board.comment.service.CommentService;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.service.QuestionService;
import com.undefined14.pre.member.controller.MemberController;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.mapper.MemberMapper;
import com.undefined14.pre.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.undefined14.pre.board.comment.entity.Comment.CommentStatus.COMMENT_POSTED;
import static com.undefined14.pre.board.comment.entity.Comment.PostType.QUESTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = CommentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class CommentRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private Comment comment;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;
    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private MemberService memberService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private Member member;

    @Autowired
    private Gson gson;


    @Test
    @DisplayName("질문 댓글 작성")
    @WithMockUser
    public void postCommentToQuestionTest() throws Exception{
        long questionId = 1L;

        CommentDto.Post post = new Post("질문에 달리는 댓글 내용입니다");

        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();

        CommentDto.Response response =
                new Response(1L,
                        1L,
                        "질문에 달리는 댓글 내용입니다",
                        createdAt);

        given(commentMapper.commentPostDto_to_Comment(Mockito.any(CommentDto.Post.class)))
                .willReturn(new Comment());
        given(commentService.createCommentToQuestion(Mockito.any(Comment.class),
                Mockito.anyLong(),Mockito.any())).willReturn(new Comment());
        given(commentMapper.comment_to_CommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        post("/board/questions/{quest-id}/comments",questionId)
                                .header("Authorization", "Bearer (accessToken)")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body").value(post.getBody()))
                .andDo(document(
                        "post-comment-to-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        ),
                        pathParameters(
                                parameterWithName("quest-id").description("질문 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("댓글 내용")
                                )
                        ),
                        responseFields(
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                        )
                ));
    }
//
    @Test
    @DisplayName("답변 댓글 작성")
    @WithMockUser
    public void postCommentToAnswerTest() throws Exception {
        long answerId = 1L;

        CommentDto.Post post = new Post("답변에 달리는 댓글 내용입니다");

        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();

        CommentDto.Response response =
                new Response(1L,
                        1L,
                        "답변에 달리는 댓글 내용입니다",
                        createdAt);

        given(commentMapper.commentPostDto_to_Comment(Mockito.any(CommentDto.Post.class)))
                .willReturn(new Comment());
        given(commentService.createCommentToAnswer(Mockito.any(Comment.class),
                Mockito.anyLong(), Mockito.any())).willReturn(new Comment());
        given(commentMapper.comment_to_CommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        post("/board/answers/{answer-id}/comments", answerId)
                                .header("Authorization", "Bearer (accessToken)")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body").value(post.getBody()))
                .andDo(document(
                        "post-comment-to-answers",
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        ),
                        pathParameters(
                                parameterWithName("answer-id").description("답변 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("댓글 내용")
                                )
                        ),
                        responseFields(
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                        )
                ));
    }

    @Test
    @DisplayName("댓글 수정")
    @WithMockUser
    public void patchCommentTest() throws Exception{
        long commentId = 1L;
        CommentDto.Patch patch = new Patch(commentId,"수정된 댓글내용입니다");
        String content = gson.toJson(patch);

        LocalDateTime createdAt = LocalDateTime.now();

        CommentDto.Response response =
                new Response(1L,
                        1L,
                        "수정된 댓글내용입니다",
                        createdAt);

        given(commentMapper.commentPatchDto_to_Comment(Mockito.any(CommentDto.Patch.class)))
                .willReturn(new Comment());
        given(commentService.updateComment(Mockito.any(Comment.class),Mockito.any()))
                .willReturn(new Comment());
        given(commentMapper.comment_to_CommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(response);

        ResultActions actions =
                mockMvc.perform(
                        patch("/board/comments/{comment-id}",commentId)
                                .header("Authorization", "Bearer (accessToken)")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value(patch.getBody()))
                .andDo(document("patch-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer (accessToken)")
                        ),
                        pathParameters(
                                parameterWithName("comment-id").description("댓글 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("수정될 댓글 식별자"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("수정된 댓글 내용")
                                )
                        ),
                        responseFields(
                                fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("body").type(JsonFieldType.STRING).description("수정된 답변 내용"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                        )
                ));
    }

    @Test
    @DisplayName("댓글 삭제")
    @WithMockUser
    public void deleteCommentTest() throws Exception {
        LocalDateTime createdAt = LocalDateTime.of(2022, 11, 11, 11, 11, 11);

        Question question = new Question("질문 제목", "질문 내용", "질문 익스펙팅", member);

        Member member = new Member();
        member.setName("John Doe");
        member.setEmail("johndoe@example.com");
        member.setPassword("mypassword");
        member.setNews(true);
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.addAnswer(null);
        member.addQuestion(question);
        member.getRoles().add("ROLE_USER");

        comment = new Comment();
        comment.setCommentId(1L);
        comment.setBody("댓글 내용");
        comment.setCommentStatus(Comment.CommentStatus.COMMENT_POSTED);
        comment.setCreatedAt(createdAt);
        comment.setPostType(Comment.PostType.QUESTION);
        comment.setMember(member);
        comment.setQuestion(question);
        comment.setAnswer(null);

        doNothing().when(commentService).deleteComment(comment.getCommentId(), eq(any()));

        ResultActions resultActions = mockMvc.perform(
                delete("/board/comments/{comment-id}", comment.getCommentId())
                        .header("Authorization", "Bearer (accessToken)")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.
                andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-comment",
                                pathParameters(parameterWithName("comment-id").description("댓글 식별자")
                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer (accessToken)")
                                )
                        )
                );
    }
}