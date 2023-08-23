package com.timcooki.jnuwiki.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.controller.DocsController;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.service.DocsService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

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
    public void findAll_security_filter_test() throws Exception{
        // given
        List<ListReadResDTO> docsList = new ArrayList<>();
        docsList.add(ListReadResDTO.builder()
                .docsId(1L)
                .docsCategory("cafe")
                .docsLocation(DocsLocation.builder()
                        .lng(3.14).lat(3.123).build())
                .docsContent("dd")
                .docsCreatedBy("dd")
                .docsCreatedAt("1234")
                .docsModifiedAt("123")
                .docsName("dd")
                .build());

        Mockito.when(docsService.getDocsList(any())).thenReturn(docsList);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/docs")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 결과 : " +res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

    }
}
