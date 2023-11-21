//package com.timcooki.jnuwiki.domain.member.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//
//import com.timcooki.jnuwiki.domain.member.DTO.request.CheckEmailReqDTO;
//import com.timcooki.jnuwiki.domain.member.DTO.request.CheckNicknameReqDTO;
//import com.timcooki.jnuwiki.domain.member.service.MemberCheckService;
//import com.timcooki.jnuwiki.domain.member.service.MemberWriteService;
//import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
//import com.timcooki.jnuwiki.testutil.CommonApiTest;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.nio.charset.Charset;
//
//@Import(MemberCheckController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//class MemberCheckControllerTest extends CommonApiTest {
//
//    @MockBean
//    private MemberCheckService memberCheckService;
//
//    @Test
//    @DisplayName("닉네임 중복 검사")
//    public void checkNickname() throws Exception {
//        // given
//        String nickname = "aa";
//        CheckNicknameReqDTO checkNicknameReqDTO = new CheckNicknameReqDTO(nickname);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/members/check/nickname")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(checkNicknameReqDTO))
//        );
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
//    }
//
//    @Test
//    @DisplayName("닉네임 중복 검사 실패")
//    void checkNickname_fail() throws Exception {
//        // given
//        String nickname = "aa";
//        CheckNicknameReqDTO checkNicknameReqDTO = new CheckNicknameReqDTO(nickname);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/members/check/nickname")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(checkNicknameReqDTO))
//        );
//
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
//        System.out.println("테스트 : " + responseBody);
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 닉네임이 존재합니다.:" + nickname));
//    }
//
//    @Test
//    @DisplayName("이메일 중복 검사")
//     void checkEmail() throws Exception {
//        // given
//        String email = "aa@naver.com";
//        CheckEmailReqDTO checkEmailReqDTO = new CheckEmailReqDTO(email);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/members/check/email")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(checkEmailReqDTO))
//        );
//
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
//        System.out.println("테스트 : " + responseBody);
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
//    }
//
//    @Test
//    @DisplayName("이메일 중복 검사 실패")
//    void checkEmail_fail() throws Exception {
//        // given
//        String email = "aa@naver.com";
//        CheckEmailReqDTO checkEmailReqDTO = new CheckEmailReqDTO(email);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .post("/members/check/email")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(om.writeValueAsString(checkEmailReqDTO))
//        );
//
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
//        System.out.println("테스트 : " + responseBody);
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("동일한 이메일이 존재합니다.:" + email));
//    }
//}
