package com.timcooki.jnuwiki.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.member.DTO.request.CheckEmailReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.CheckNicknameReqDTO;
import com.timcooki.jnuwiki.domain.member.service.MemberReadService;
import com.timcooki.jnuwiki.domain.member.service.MemberWriteService;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.errors.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

@Import({
        AuthenticationConfig.class,
})
@WebMvcTest(controllers = MemberCheckController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MemberCheckControllerTest {

    @MockBean private MemberSecurityService memberSecurityService;
    @MockBean private MemberWriteService memberWriteService;

    @Autowired
    private MockMvc mvc;
    @Autowired private ObjectMapper om;

    @Test
    @DisplayName("닉네임 중복 검사")
    public void checkNickname() throws Exception {
        // given
        String nickname = "aa";
        CheckNicknameReqDTO checkNicknameReqDTO = new CheckNicknameReqDTO(nickname);

        // stub
        Mockito.when(memberWriteService.isPresentNickName(checkNicknameReqDTO)).thenReturn(false);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/members/check/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(checkNicknameReqDTO))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
    @Test
    @DisplayName("닉네임 중복 검사 실패")
    public void checkNickname_fail() throws Exception {
        // given
        String nickname = "aa";
        CheckNicknameReqDTO checkNicknameReqDTO = new CheckNicknameReqDTO(nickname);

        // stub
        Mockito.when(memberWriteService.isPresentNickName(checkNicknameReqDTO)).thenReturn(true);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/members/check/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(checkNicknameReqDTO))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 닉네임이 존재합니다.:" + nickname));
    }
    @Test
    @DisplayName("이메일 중복 검사")
    public void checkEmail() throws Exception {
        // given
        String email = "aa@naver.com";
        CheckEmailReqDTO checkEmailReqDTO = new CheckEmailReqDTO(email);

        // stub
        Mockito.when(memberWriteService.isPresentEmail(checkEmailReqDTO)).thenReturn(false);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/members/check/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(checkEmailReqDTO))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("이메일 중복 검사 실패")
    public void checkEmail_fail() throws Exception {
        // given
        String email = "aa@naver.com";
        CheckEmailReqDTO checkEmailReqDTO = new CheckEmailReqDTO(email);

        // stub
        Mockito.when(memberWriteService.isPresentEmail(checkEmailReqDTO)).thenReturn(true);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/members/check/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(checkEmailReqDTO))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 이메일이 존재합니다.:" + email));
    }
}
