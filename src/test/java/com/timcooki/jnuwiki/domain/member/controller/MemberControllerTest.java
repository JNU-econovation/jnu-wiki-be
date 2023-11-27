package com.timcooki.jnuwiki.domain.member.controller;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.DTO.request.EditNicknameReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.EditPasswordReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.AccessTokenResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapListResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.WrapAccessTokenResDTO;
import com.timcooki.jnuwiki.domain.member.service.MemberReadService;
import com.timcooki.jnuwiki.domain.member.service.MemberWriteService;
import com.timcooki.jnuwiki.domain.security.config.JwtProvider;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.testutil.CommonApiTest;
import com.timcooki.jnuwiki.util.TimeFormatter;
import java.time.Instant;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Import(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest extends CommonApiTest {

    @MockBean
    private MemberReadService memberReadService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private MemberSecurityService memberSecurityService;
    @MockBean
    private MemberWriteService memberWriteService;

    @Test
    @DisplayName("엑세스 토큰 재발급")
    @WithMockUser
    void refreshToken() throws Exception {
        // given
        String refreshToken = "lhkjtoke.dsda.fgfn";
        Instant expiration = Instant.ofEpochSecond(170123032L);

        // stub
        Mockito.when(refreshTokenService.renewAccessToken(refreshToken)).thenReturn(
                WrapAccessTokenResDTO.builder()
                        .accessTokenResDTO(AccessTokenResDTO.builder()
                                .accessTokenFormattedExpiration(TimeFormatter.format(expiration))
                                .accessTokenExpiration(expiration.toEpochMilli())
                                .build())
                        .accessToken(refreshToken)
                        .build());

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/members/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("refresh-token", refreshToken))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then

    }

    @Test
    @DisplayName("회원가입")
    void join() throws Exception {
        // given
        JoinReqDTO joinReqDTO = new JoinReqDTO("minl7@fd.fos", "mm", "edfwf741!");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(joinReqDTO))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("내 정보 보기")
    @WithMockUser
    void info() throws Exception {
        // given
        Long id = 0L;
        String nickname = "mmm";
        String password = "mmm1234!";

        // stub
        Mockito.when(memberReadService.getInfo()).thenReturn(new ReadResDTO(id, nickname, password));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members/info")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(id));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.nickName").value(nickname));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.password").value(password));
    }

    @Test
    @DisplayName("내 비밀번호 수정")
    @WithMockUser
    void modifyInfo() throws Exception {
        // given
        String password = "mmm1234!";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new EditPasswordReqDTO(password)))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").value("비밀번호가 변경되었습니다." + password));
    }

    @Test
    @DisplayName("내 닉네임 수정")
    @WithMockUser
    void modifyNickname() throws Exception {
        // given
        String nickname = "mmm";

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/members/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new EditNicknameReqDTO(nickname)))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").value("닉네임이 변경되었습니다." + nickname));
    }

    @Test
    @DisplayName("마이페이지 내가 스크랩한 글 조회")
    @WithMockUser(username = "test@test.com", roles = "USER")
    void get_scrapped_docsList_test() throws Exception {
        // given
        List<ScrapResDTO> list = new ArrayList<>();
        list.add(new ScrapResDTO(1L, "내용1", DocsCategory.CONV.getCategory(), new DocsLocation(213.34, 3423.32), "12"));
        list.add(new ScrapResDTO(2L, "내용2", DocsCategory.CAFE.getCategory(), new DocsLocation(213.34, 3423.32), "12"));
        list.add(
                new ScrapResDTO(3L, "내용3", DocsCategory.SCHOOL.getCategory(), new DocsLocation(213.34, 3423.32), "12"));

        ScrapListResDTO listResDTO = new ScrapListResDTO(list, 3);

        // stub
        Mockito.when(memberReadService.getScrappedDocs(any())).thenReturn(listResDTO);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/members/scrap")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}