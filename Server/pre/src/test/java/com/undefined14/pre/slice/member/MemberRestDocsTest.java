package com.undefined14.pre.slice.member;

import com.google.gson.Gson;
import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.member.controller.MemberController;
import com.undefined14.pre.member.dto.MemberPatchDto;
import com.undefined14.pre.member.dto.MemberPostDto;
import com.undefined14.pre.member.dto.MemberResponseDto;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.mapper.MemberMapper;
import com.undefined14.pre.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @MockBean
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;

    @Test
    @DisplayName("멤버 등록")
    public void postMemberTest() throws Exception {
        //  given
        MemberPostDto post = new MemberPostDto("홍길동", "asd@gmail.com", "qwer1234",true);
        String content = gson.toJson(post);

        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setMemberId(1L);
        responseDto.setEmail("asd@gmail.com");
        responseDto.setName("홍길동");
        responseDto.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        responseDto.setNews(true);

        given(mapper.memberPostDtoToMember(Mockito.any(MemberPostDto.class))).willReturn(new Member());
        Member mockResultMember = new Member();
        mockResultMember.setMemberId(1L);
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(mockResultMember);

        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/members/")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/members/"))))
                .andDo(print())
                .andDo(document("post-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("news").type(JsonFieldType.BOOLEAN).description("뉴스레터")
                                )
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )
                ));
    }

    @Test
    @DisplayName("멤버 수정")
    @WithMockUser
    public void patchMemberTest() throws Exception {
        //given
        MemberPatchDto patchDto = new MemberPatchDto("홍홍홍", "asd@gmail.com","qwer1234", true);
        String content = gson.toJson(patchDto);

        given(mapper.memberPatchDtoToMember(any())).willReturn(new Member());

        given(memberService.updateMember(any(),any())).willReturn(new Member());

        //when
        ResultActions actions =
                mockMvc.perform(
                        patch("/members")
                                .header("Authorization", "Bearer " + "test-token")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional(),
                                        fieldWithPath("news").type(JsonFieldType.BOOLEAN).description("뉴스레터")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("멤버 조회")
    @WithMockUser
    public void getMemberTest() throws Exception {
        // given
        MemberResponseDto responseDto =
                new MemberResponseDto();
        responseDto.setMemberId(1L);
        responseDto.setEmail("asd@gmail.com");
        responseDto.setName("홍홍홍");
        responseDto.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        responseDto.setNews(true);

        given(memberService.findMember(any())).willReturn(new Member());

        given(mapper.memberToMemberResponseDto(any(Member.class))).willReturn(responseDto);

        mockMvc.perform(
                        get("/members")
                                .header("Authorization", "Bearer " + "test-token")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(responseDto.getMemberId()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.email").value(responseDto.getEmail()))
                .andDo(print())
                .andDo(document("get-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 탈퇴 상태"),
                                        fieldWithPath("news").type(JsonFieldType.BOOLEAN).description("뉴스레터"),
                                        fieldWithPath("create_at").type(JsonFieldType.NULL).description("가입 시기")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("멤버 삭제")
    @WithMockUser
    public void deleteMemberTest() throws Exception {
        doNothing().when(memberService).deleteMember(any());

        mockMvc.perform(
                delete("/members")
                        .header("Authorization", "Bearer " + "test-token")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andDo(document("delete-members",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt-token")
                        )
                ));
    }
}

