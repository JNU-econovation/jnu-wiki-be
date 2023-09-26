package com.timcooki.jnuwiki.scrap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.controller.ScrapController;
import com.timcooki.jnuwiki.domain.scrap.service.ScrapWriteService;
import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.util.ApiUtils;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;


@Import({
        AuthenticationConfig.class,
        JwtFilter.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = ScrapController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ScrapControllerTest {

    @MockBean
    private ScrapWriteService scrapWriteService;

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private MemberSecurityService memberSecurityService;

    @Test
    @DisplayName("스크랩 생성 컨트롤러 단위테스트")
    @WithMockUser
    public void create_test() throws Exception{
        // given
        NewScrapReqDTO dto = NewScrapReqDTO.builder()
                .memberId(1L)
                .docsId(1L)
                .build();

        Mockito.when(scrapWriteService.create(any(NewScrapReqDTO.class))).thenReturn(ResponseEntity.ok().body(ApiUtils.success(null)));
        String request = om.writeValueAsString(dto);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/scrap/create")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("스크랩 삭제 컨트롤러 단위테스트")
    @WithMockUser
    public void delete_test() throws Exception{
        // given
        DeleteScrapReqDTO dto = DeleteScrapReqDTO.builder()
                .memberId(1L)
                .docsId(1L)
                .build();

        Mockito.when(scrapWriteService.delete(any(DeleteScrapReqDTO.class))).thenReturn(ResponseEntity.ok().body(ApiUtils.success(null)));
        String request = om.writeValueAsString(dto);
        System.out.println("request : " + request);


        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/scrap")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

}
