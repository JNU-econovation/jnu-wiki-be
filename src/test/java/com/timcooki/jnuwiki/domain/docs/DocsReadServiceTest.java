package com.timcooki.jnuwiki.domain.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.DTO.request.FindAllReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docs.service.DocsReadService;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithAnonymousUser;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@WithAnonymousUser
public class DocsReadServiceTest {

    @Autowired
    private DocsReadService docsReadService;
    @Autowired
    private DocsRepository docsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private ObjectMapper om;

    // TODO - Repository Mock으로 대체 후 테스트 코드 작성
    @DisplayName("문서 조회 테스트")
    @Test
    public void findAll_test() throws Exception{
        // given
        Pageable pageable = PageRequest.of(0, 10);
        FindAllReqDTO dto = FindAllReqDTO.builder()
                .rightUp(DocsLocation.builder()
                        .lat(40.0)
                        .lng(40.0)
                        .build())
                .leftDown(DocsLocation.builder()
                        .lat(30.0)
                        .lng(30.0)
                        .build())
                .build();


        // when
        ListReadResDTO response = docsReadService.getDocsList(pageable, dto);

        // then
        String result1 = om.writeValueAsString(response);
        System.out.println("테스트 결과 :" + result1);
        Assertions.assertEquals(1L, response.docsList().get(0).docsId());
    }


}
