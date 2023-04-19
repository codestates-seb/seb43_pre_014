package com.undefined14.pre.slice.member;

import com.google.gson.Gson;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("멤버 등록")
    public void postMemberTest() throws Exception {
        //  given
        MemberPostDto post = new MemberPostDto("홍길동", "hgd@gmail.com", "qwer1234");

        Member member = new Member();
        member.setMemberId(1L);

        given(memberService.createMember(Mockito.any()))
                .willReturn(member);

        String content = gson.toJson(post);

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
                .andExpect(header().string("Location", is(startsWith("/members/"))));
    }

    @Test
    @DisplayName("멤버 수정")
    public void patchMemberTest() throws Exception {
        //given
        long memberId = 1L;
        MemberPatchDto patchDto = new MemberPatchDto(memberId, "홍홍홍", "asd@gmail.com","qwer1234");
        String content = gson.toJson(patchDto);

        MemberResponseDto responseDto =
                new MemberResponseDto();
        responseDto.setMemberId(1L);
        responseDto.setEmail("ccc@gmail.com");
        responseDto.setName("총총총");
        responseDto.setPassword("qwer1234");
        responseDto.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        responseDto.setCreate_at(LocalDateTime.now());

        given(mapper.memberPatchDtoToMember(Mockito.any(MemberPatchDto.class))).willReturn(new Member());
        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        patch("/members/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.memberId").value(patchDto.getMemberId()))
//                .andExpect(jsonPath("$.data.name").value(patchDto.getName()))
//                .andExpect(jsonPath("$.data.phone").value(patchDto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(header().string("location", is(startsWith("/members/"))));
//                .andExpect(jsonPath("$.data.name").value(patchDto.getName()));

    }

    @Test
    @DisplayName("멤버 조회")
    public void getMemberTest() throws Exception {
        MemberPostDto postDto = new MemberPostDto("홍홍홍",
                "asd@gmail.com","qwer1234");
        String content = gson.toJson(postDto);

        //given
        MemberResponseDto responseDto =
                new MemberResponseDto();
        responseDto.setMemberId(1L);
        responseDto.setEmail("asd@gmail.com");
        responseDto.setName("홍홍홍");
        responseDto.setPassword("qwer1234");
        responseDto.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        responseDto.setCreate_at(LocalDateTime.now());
//
//        ResultActions postActions =
//                mockMvc.perform(
//                        post("/members/{member-id}", 1L)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .contentType(content)
//                );

//        String location = postActions.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(
                        get("/members/{member-id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)   /** 중복 */
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 삭제")
    public void deleteMemberTest() throws Exception {
        doNothing().when(memberService).deleteMember(1L);

        mockMvc.perform(
                delete("/members/{member-id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }
}

