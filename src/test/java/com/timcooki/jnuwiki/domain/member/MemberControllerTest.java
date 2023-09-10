package com.timcooki.jnuwiki.domain.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.DTO.request.JoinReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapResDTO;
import com.timcooki.jnuwiki.domain.member.controller.MemberController;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.errors.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({
        AuthenticationConfig.class,
        JwtFilter.class,
        GlobalExceptionHandler.class,
        RefreshTokenService.class
})
@WebMvcTest(controllers = MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MemberControllerTest {

    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberSecurityService memberSecurityService;
    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;

    @BeforeEach
    void setUp() {
        UserDetails userDetails = memberSecurityService.loadUserByUsername("test@test.com");
//        this.mvc = MockMvcBuilders.standaloneSetup(new MemberController).build();
    }

    @Test
    @DisplayName("마이페이지 내가 스크랩한 글 조회")
    @WithMockUser(username = "test@test.com", roles = "USER")
    public void get_scrapped_docsList_test() throws Exception {
        // given
        Member member = Member.builder()
                .email("test@test.com")
                .nickName("hihi")
                .role(MemberRole.USER)
                .password("1234!asdf")
                .build();

        UserDetails userDetails = memberSecurityService.loadUserByUsername("test@test.com");

        List<ScrapResDTO> list = new ArrayList<>();
        list.add(new ScrapResDTO(1L, "내용1", DocsCategory.CONV.getCategory()));
        list.add(new ScrapResDTO(2L, "내용2", DocsCategory.CAFE.getCategory()));
        list.add(new ScrapResDTO(3L, "내용3", DocsCategory.SCHOOL.getCategory()));

        // stub
        Mockito.when(memberService.getScrappedDocs(any(), any())).thenReturn(list);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/members/scrap")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}