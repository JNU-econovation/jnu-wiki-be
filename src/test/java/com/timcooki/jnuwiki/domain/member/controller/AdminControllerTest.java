package com.timcooki.jnuwiki.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.response.InfoEditResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.NewApproveResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestReadService;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewListReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import com.timcooki.jnuwiki.domain.member.service.AdminWriteService;
import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
import com.timcooki.jnuwiki.util.errors.GlobalExceptionHandler;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Import({
//        AuthenticationConfig.class,
        JwtFilter.class,
        GlobalExceptionHandler.class,
})
@WebMvcTest(controllers = AdminController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
@MockBean(JpaMetamodelMappingContext.class)
public class AdminControllerTest {

    @MockBean
    private DocsRequestReadService docsRequestReadService;
    @MockBean
    private MemberSecurityService memberSecurityService;
    @MockBean
    private AdminWriteService adminWriteService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("문서 수정 요청 목록 조회")
    @WithMockUser
    public void getModifiedRequests_test() throws Exception {
        // given
        EditListReadResDTO editListReadResDTO = new EditListReadResDTO(
                Arrays.asList(
                        new EditReadResDTO(1L, 1L, DocsRequestType.MODIFIED, DocsCategory.CAFE.getCategory(), "1이름", new DocsLocation(34.3, 43.3)),
                        new EditReadResDTO(2L, 2L, DocsRequestType.MODIFIED, DocsCategory.SCHOOL.getCategory(), "2이름", new DocsLocation(34.3, 43.3)),
                        new EditReadResDTO(3L, 3L, DocsRequestType.MODIFIED, DocsCategory.SCHOOL.getCategory(), "3이름", new DocsLocation(34.3, 43.3))
                ), 3);

        // stub
        Mockito.when(docsRequestReadService.getModifiedRequestList(any())).thenReturn(editListReadResDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/requests/update")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("새 문서 생성 요청 목록 조회")
    @WithMockUser
    public void getCreatedRequests_test() throws Exception {
        // given
        NewListReadResDTO newListReadResDTO = new NewListReadResDTO(
                Arrays.asList(
                        new NewReadResDTO(1L, DocsRequestType.MODIFIED, DocsCategory.CAFE.getCategory(), "1이름", new DocsLocation(34.3, 43.3)),
                        new NewReadResDTO(2L, DocsRequestType.MODIFIED, DocsCategory.SCHOOL.getCategory(), "2이름", new DocsLocation(34.3, 43.3)),
                        new NewReadResDTO(3L, DocsRequestType.MODIFIED, DocsCategory.SCHOOL.getCategory(), "3이름", new DocsLocation(34.3, 43.3))
                ), 3);

        // stub
        Mockito.when(docsRequestReadService.getCreatedRequestList(any())).thenReturn(newListReadResDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/requests/new")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
    }


    @Test
    @DisplayName("문서 기본 정보 수정 요청 상세 조회")
    @WithMockUser
    public void getOneModifiedRequest_test() throws Exception {
        // given
        Long docsRequestId = 1L;

        // stub
        Mockito.when(docsRequestReadService.getOneModifiedRequest(docsRequestId)).thenReturn(
                new EditReadResDTO(docsRequestId, 1L, DocsRequestType.MODIFIED, DocsCategory.CAFE.getCategory(), "자고 싶어..", new DocsLocation(23.4, 532.4))
        );

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/requests/update/{docs_request_id}", docsRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("새 문서 신청 요청 상세 조회")
    @WithMockUser
    public void getOneCreatedRequest_test() throws Exception {
        // given
        Long docsRequestId = 1L;

        // stub
        Mockito.when(docsRequestReadService.getOneCreatedRequest(docsRequestId)).thenReturn(
                new NewReadResDTO(docsRequestId, DocsRequestType.CREATED, DocsCategory.CAFE.getCategory(), "자고 싶어..", new DocsLocation(23.4, 532.4))
        );

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/requests/new/{docs_request_id}", docsRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("새 문서 생성 요청 승락")
    @WithMockUser
    public void approveCreateRequest_test() throws Exception {
        // given
        Long docsRequestId = 1L;

        // stub
        Mockito.when(adminWriteService.approveNewDocs(docsRequestId)).thenReturn(
                NewApproveResDTO.builder()
                        .id(docsRequestId)
                        .docsName("자고싶다니까?")
                        .docsCategory(DocsCategory.CAFE.getCategory())
                        .docsLocation(DocsLocation.builder()
                                .lng(342.4)
                                .lat(32.4).build())
                        .docsCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                        .build());

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/approve/new/{docs_request_id}", docsRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("문서 수정 요청 승락")
    @WithMockUser
    public void approveModifiedRequest_test() throws Exception {
        // given
        Long docsRequestId = 1L;

        // stub
        Mockito.when(adminWriteService.updateDocsFromRequest(docsRequestId)).thenReturn(
                InfoEditResDTO.builder()
                        .docsId(docsRequestId)
                        .docsName("자고싶다니까?")
                        .docsCategory(DocsCategory.CAFE.getCategory())
                        .docsLocation(DocsLocation.builder()
                                .lng(342.4)
                                .lat(32.4).build())
                        .docsContent("fsf")
                        .docsModifiedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .build());

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/approve/update/{docs_request_id}", docsRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("문서 요청 반려")
    @WithMockUser
    public void rejectRequest_test() throws Exception {
        // given
        Long docsRequestId = 1L;

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/reject/{docs_request_id}", docsRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").value("요청이 반려되었습니다."));
    }
}
