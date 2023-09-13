package com.timcooki.jnuwiki.domain.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Import(ObjectMapper.class)
@DataJpaTest
public class DocsRepositoryTest {
    @Autowired
    private DocsRepository docsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        Docs docs = Docs.builder()
                .docsCategory(DocsCategory.CAFE)
                .docsName("테스트1")
                .docsLocation(DocsLocation.builder()
                        .lng(35.0)
                        .lat(35.0)
                        .build())
                .build();
        docsRepository.save(docs);
        em.flush();
        em.clear();
    }

    @Test
    public void mfindAll_test(){
        // given
        int page = 0;
        int size = 4;
        DocsLocation rightUp = DocsLocation.builder()
                .lat(40.0)
                .lng(40.0)
                .build();
        DocsLocation leftDown = DocsLocation.builder()
                .lat(20.0)
                .lng(20.0)
                .build();



        // when
        Pageable pageable = PageRequest.of(page, size);
        //Page<Docs> docs = docsRepository.mfindAll(rightUp.getLat(), rightUp.getLng(), leftDown.getLat(), leftDown.getLng(), pageable);
        Page<Docs> docs = docsRepository.mfindAll(rightUp, leftDown, pageable);
        System.out.println("테스트 시작");
        docs.forEach(d -> System.out.println("테스트 결과 " + d.getDocsName()));

        // then
    }
}
