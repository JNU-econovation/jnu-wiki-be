package com.timcooki.jnuwiki.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class DocsRepositoryTest {

    @Autowired
    private DocsRepository docsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        //em.createNativeQuery("ALTER TABLE DOCS ALTER COLUMN docs_id RESTART WITH 1").executeUpdate();
        Double a = 1.0;
        Double b = 2.0;
        Docs docs = docsRepository.save(Docs.builder()
                        .docsName("테스트문서")
                        .docsCategory(DocsCategory.CAFE)
                        .docsLocation(DocsLocation.builder()
                                .lat(a)
                                .lng(b)
                                .build())
                        .createdBy(null)
                .build());
        docsRepository.findAll().forEach(System.out::println);
        docs.updateContent("테스트내용");
        em.flush();
        em.clear();
    }

    @Test
    public void search_like_test()throws JsonProcessingException {
        //given
        String search = "테스트";

        // when
        List<Docs> docsList = docsRepository.searchLike(search);
        System.out.println("으아아아아ㅏ아아");
        docsList.forEach(d -> System.out.println(d.getDocsContent()));

        // then
        Assertions.assertThat(docsList.get(0).getDocsId()).isEqualTo(1);
        Assertions.assertThat(docsList.get(0).getDocsContent()).isEqualTo("테스트내용");

    }
}
