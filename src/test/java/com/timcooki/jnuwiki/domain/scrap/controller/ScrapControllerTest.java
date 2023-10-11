package com.timcooki.jnuwiki.domain.scrap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.service.ScrapWriteService;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        JwtFilter.class,
})
@WebMvcTest(controllers = ScrapController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ScrapControllerTest {

    @MockBean
    private ScrapWriteService scrapWriteService;
    @MockBean
    private MemberSecurityService memberSecurityService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("스크랩 생성")
    @WithMockUser
     void create_test() throws Exception {
        // given
        NewScrapReqDTO dto = NewScrapReqDTO.builder()
                .memberId(0L)
                .docsId(0L)
                .build();

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/scrap")
                        .content(om.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("스크랩 삭제")
    @WithMockUser
     void delete_test() throws Exception {
        // given
        DeleteScrapReqDTO dto = DeleteScrapReqDTO.builder()
                .memberId(1L)
                .docsId(1L)
                .build();

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/scrap")
                        .content(om.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + res);

        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

}