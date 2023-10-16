//package com.timcooki.jnuwiki.docs;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.timcooki.jnuwiki.domain.docs.DTO.request.FindAllReqDTO;
//import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
//import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
//import com.timcooki.jnuwiki.domain.docs.controller.DocsController;
//import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
//import com.timcooki.jnuwiki.domain.docs.service.DocsService;
//import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
//import com.timcooki.jnuwiki.domain.member.entity.Member;
//import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
//import com.timcooki.jnuwiki.domain.security.config.AuthenticationConfig;
//import com.timcooki.jnuwiki.domain.security.config.JwtFilter;
//import com.timcooki.jnuwiki.domain.security.service.MemberSecurityService;
//import com.timcooki.jnuwiki.util.errors.GlobalExceptionHandler;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@Import({
//        AuthenticationConfig.class,
//        JwtFilter.class,
//        GlobalExceptionHandler.class
//})
//@WebMvcTest(controllers = DocsController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class DocsControllerTest {
//
//    @MockBean
//    private DocsService docsService;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper om;
//    @MockBean
//    private MemberSecurityService memberSecurityService;
//
//    @Test
//    @DisplayName("문서 조회 권한 테스트")
//    public void findAll_security_filter_test() throws Exception {
//        // given
//        List<ListReadResDTO> docsList = new ArrayList<>();
//        docsList.add(ListReadResDTO.builder()
//                .docsId(1L)
//                .docsCategory("cafe")
//                .build());
//        FindAllReqDTO dto = FindAllReqDTO.builder()
//                .rightUp(DocsLocation.builder()
//                        .lat(40.0)
//                        .lng(40.0)
//                        .build())
//                .leftDown(DocsLocation.builder()
//                        .lat(30.0)
//                        .lng(30.0)
//                        .build())
//                .build();
//
//        Mockito.when(docsService.getDocsList(any(), any())).thenReturn(docsList);
//        String requestBody = om.writeValueAsString(dto);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .get("/docs")
//                        .content(requestBody)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        String res = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 결과 : " + res);
//
//        // then
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
//
//    }
//
////    @Test
////    @DisplayName("문서 목록 조회")
////    @WithMockUser(username = "mminl@naver.cm", roles = "USER")
////    public void findAll_test() throws Exception {
////        // given
////        Member member = Member.builder()
////                .email("mminl@naver.cm")
////                .nickName("mmmm")
////                .password("1235!dasd")
////                .role(MemberRole.USER)
////                .build();
////
////        List<ListReadResDTO> list = new ArrayList<>();
////        list.add(new ListReadResDTO(1L, "내용1", DocsCategory.CONV.getCategory(), true));
////        list.add(new ListReadResDTO(2L, "내용2", DocsCategory.CAFE.getCategory(), false));
////        list.add(new ListReadResDTO(3L, "내용3", DocsCategory.SCHOOL.getCategory(), false));
////
////        list.forEach(System.out::println);
////
////        // stub
////        Mockito.when(docsReadService.getOneDocs(eq("mminl@naver.cm"), eq(docsId))).thenReturn(
////                new ReadResDTO(1L, "내용1", DocsCategory.CONV.getCategory(), new DocsLocation(13.1, 34.3), "1nuu", member.getNickName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),true)
////        );
////
////        // when
////        ResultActions result = mockMvc.perform(
////                MockMvcRequestBuilders
////                        .get("/docs/1", docsId)
////                        .contentType(MediaType.APPLICATION_JSON));
////
////        String responseBody = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
////        System.out.println("테스트 : " + responseBody);
////
////        // then
////        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
////    }
////
////}