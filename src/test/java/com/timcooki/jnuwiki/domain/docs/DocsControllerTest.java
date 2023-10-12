package com.timcooki.jnuwiki.domain.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.request.ContentEditReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.*;
import com.timcooki.jnuwiki.domain.docs.controller.DocsController;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.service.DocsReadService;
import com.timcooki.jnuwiki.domain.docs.service.DocsWriteService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    private DocsReadService docsReadService;
    @MockBean
    private DocsWriteService docsWriteService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;
    @MockBean
    private MemberSecurityService memberSecurityService;

    @Test
    @DisplayName("로그인 안한 유저도 문서 조회를 할 수 있다.")
    public void findAll_security_filter_test() throws Exception {
        // given

        // QueryParmas
        MultiValueMap<String, String> map  = new LinkedMultiValueMap<>();
        map.add("leftLng", "30.0");
        map.add("leftLat", "30.0");
        map.add("rightLng", "40.0");
        map.add("rightLat", "40.0");

        List<OneOfListReadResDTO> oneOfListReadResDTOS = new ArrayList<>();
        OneOfListReadResDTO oneOfListReadResDTO1 = OneOfListReadResDTO.builder()
                .docsId(1L)
                .docsLocation(DocsLocation.builder()
                        .lng(35.0)
                        .lat(35.0)
                        .build())
                .docsCategory(DocsCategory.CAFE.getCategory())
                .docsName("test")
                .scrap(false)
                .build();
        oneOfListReadResDTOS.add(oneOfListReadResDTO1);
        ListReadResDTO docsList = ListReadResDTO.builder()
                .docsList(oneOfListReadResDTOS)
                .totalPages(1)
                .build();


        Mockito.when(docsReadService.getDocsList(any(), any())).thenReturn(docsList);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/docs")
                        .queryParams(map)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 결과 : " + res);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.docsList[0].docsName").value("test"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.docsList[0].docsCategory").value("카페"));

    }

    @Test
    @DisplayName("문서 상세 조회")
    @WithMockUser(username = "mminl@naver.cm", roles = "USER")
    public void findById_test() throws Exception {
        // given
        Long docsId = 1L;

        Member member = Member.builder()
                .email("mminl@naver.cm")
                .nickName("mmmm")
                .password("1235!dasd")
                .role(MemberRole.USER)
                .build();

        // stub
        Mockito.when(docsReadService.getOneDocs(eq(docsId))).thenReturn(
                new ReadResDTO(1L, "내용1", DocsCategory.CONV.getCategory(), new DocsLocation(13.1, 34.3), "1nuu", member.getNickName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),true)
        );

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/docs/1", docsId)
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("문서 수정")
    @WithMockUser(username = "testUser@naver.com",roles = "USER")
    @Test
    public void docs_update_test() throws Exception{
        // given
        Long docsId = 1L;
        String docsContent = "수정된 문서내용";
        String dateTimeFormmat = "yyyy/MM/dd HH:mm:ss";
        ContentEditReqDTO dto = ContentEditReqDTO.builder()
                .docsContent(docsContent)
                .build();
        ContentEditResDTO res = ContentEditResDTO.builder()
                .id(docsId)
                .docsContent(docsContent)
                .docsModifiedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormmat)))
                .build();
        String requestBody = om.writeValueAsString(dto);

        // stub
        Mockito.when(
                docsWriteService.updateDocs(any(), any())
        ).thenReturn(res);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/docs/{docs_id}", 1)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String expectedRes = om.writeValueAsString(res);
        String responseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$.success").value("true")
        );
    }

    @DisplayName("문서 검색")
    @WithMockUser(username = "testUser@naver.com",roles = "USER")
    @Test
    public void docs_search_test() throws Exception{
        // given
        String search = "검색";
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("search", "검색");
        List<SearchReadResDTO> dtoList = new ArrayList<>();
        SearchReadResDTO dto = SearchReadResDTO.builder()
                .docsId(1L)
                .docsName("검색 테스트 제목")
                .docsContent("검색 테스트 내용")
                .build();
        dtoList.add(dto);

        // stub
        Mockito.when(
                docsReadService.searchLike(search)
        ).thenReturn(
            dtoList
        );

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/docs/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(queryParam)
                        .characterEncoding(StandardCharsets.UTF_8)
        );



        // then
        String res = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + res);

        resultActions.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$.success").value("true")
        );

    }


}