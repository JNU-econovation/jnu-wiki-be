package com.timcooki.jnuwiki.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.docs.controller.DocsController;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.service.DocsService;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@Import({
        AuthenticationConfig.class,
        JwtFilter.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = DocsController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class DocsControllerTest {

    @MockBean
    private DocsService docsService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;
    @MockBean
    private MemberSecurityService memberSecurityService;

    @Test
    @DisplayName("문서 조회 권한 테스트")
    public void findAll_security_filter_test() throws Exception {
        // given
        List<ListReadResDTO> docsList = new ArrayList<>();
        docsList.add(ListReadResDTO.builder()
                .docsId(1L)
                .docsCategory("cafe")
                .build());

        Mockito.when(docsService.getDocsList(null, any())).thenReturn(docsList);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/docs")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 결과 : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

    }

    @Test
    @DisplayName("문서 목록 조회")
    @WithMockUser(username = "mminl@naver.cm", roles = "USER")
    public void findAll_test() throws Exception {
        // given
        int page = 1;
        int size = 10;

        Member member = Member.builder()
                .email("momo@naver.com")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        List<ListReadResDTO> list = new ArrayList<>();
        list.add(new ListReadResDTO(1L, "내용1", DocsCategory.CONV.getCategory(), true));
        list.add(new ListReadResDTO(2L, "내용2", DocsCategory.CAFE.getCategory(), false));
        list.add(new ListReadResDTO(3L, "내용3", DocsCategory.SCHOOL.getCategory(), false));

        list.forEach(System.out::println);

        // stub
        Mockito.when(docsService.getDocsList(eq("mminl@naver.cm"), any())).thenReturn(list);
//        given(docsService.getDocsList(Mockito.any(PageRequest.class))).willReturn(list);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/docs")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}