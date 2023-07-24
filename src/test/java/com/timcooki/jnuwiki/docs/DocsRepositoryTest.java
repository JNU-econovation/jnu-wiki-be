package com.timcooki.jnuwiki.docs;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
public class DocsRepositoryTest {

    @Autowired
    private DocsRepository docsRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE DOCS ALTER COLUMN docs_id RESTART WITH 1").executeUpdate();
        Docs docs = docsRepository.save(Docs.builder()
                        .docsName("테스트문서")
                        .docsCategory(null)
                        .docsLocation(null)
                        .createdBy(null)
                .build());
        docs.updateContent("테스트내용");
        em.clear();
    }

    @Test
    public void search_like_test(){
        //given
        String search = "테스트";

        // when
        List<Docs> docsList = docsRepository.searchLike(search);
        docsList.forEach(System.out::println);

        // then
        Assertions.assertThat(docsList.get(0).getDocsId()).isEqualTo(1);
        Assertions.assertThat(docsList.get(0).getDocsContent()).isEqualTo("테스트내용");

    }
}
