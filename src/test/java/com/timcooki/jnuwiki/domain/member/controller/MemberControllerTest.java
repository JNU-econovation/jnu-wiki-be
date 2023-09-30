package com.timcooki.jnuwiki.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.DTO.request.EditReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapListResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapResDTO;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Import({
        AuthenticationConfig.class,
        JwtFilter.class,
        GlobalExceptionHandler.class,
        RefreshTokenService.class
})
@WebMvcTest(controllers = MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MemberControllerTest {

    @MockBean private MemberReadService memberReadService;
    @MockBean private RefreshTokenService refreshTokenService;
    @MockBean private MemberSecurityService memberSecurityService;
    @MockBean private MemberWriteService memberWriteService;

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    // TODO: 로그인 리팩토링 후 테스트 코드 작성
    @Test
    @DisplayName("리프레시 토큰 재발급")
    @WithMockUser
    public void refreshToken() throws Exception {
        // given
        String refreshToken = "token";

        // stub
        Mockito.when(refreshTokenService.renewToken(refreshToken)).thenReturn(refreshToken);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/members/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("set-cookie", refreshToken)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then

    }

    @Test
    @DisplayName("회원가입")
    public void join() throws Exception {
        // given
        JoinReqDTO joinReqDTO = new JoinReqDTO("minl7@fd.fos", "mm", "edfwf741!");

        // when
        ResultActions resultActions = mvc.perform(
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
    public void info() throws Exception {
        // given
        Long id = 0L;
        String nickname = "mmm";
        String password = "mmm1234!";

        // stub
        Mockito.when(memberReadService.getInfo()).thenReturn(new ReadResDTO(id, nickname, password));

        // when
        ResultActions resultActions = mvc.perform(
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
    @DisplayName("내 정보 수정")
    @WithMockUser
    public void modifyInfo() throws Exception {
        // given
        String nickname = "mmm";
        String password = "mmm1234!";

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/members/modify/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new EditReqDTO(nickname, password)))
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("마이페이지 내가 스크랩한 글 조회")
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void get_scrapped_docsList_test() throws Exception {
        // given
        List<ScrapResDTO> list = new ArrayList<>();
        list.add(new ScrapResDTO(1L, "내용1", DocsCategory.CONV.getCategory(), new DocsLocation(213.34, 3423.32), "12"));
        list.add(new ScrapResDTO(2L, "내용2", DocsCategory.CAFE.getCategory(), new DocsLocation(213.34, 3423.32), "12"));
        list.add(new ScrapResDTO(3L, "내용3", DocsCategory.SCHOOL.getCategory(), new DocsLocation(213.34, 3423.32), "12"));

        ScrapListResDTO listResDTO = new ScrapListResDTO(list, 3);

        // stub
        Mockito.when(memberReadService.getScrappedDocs(any())).thenReturn(listResDTO);

        // when
        ResultActions resultActions = mvc.perform(
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