package com.timcooki.jnuwiki.domain.scrap.repository;

import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.entity.ScrapId;
import com.timcooki.jnuwiki.testutil.DataJpaTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

class ScrapRepositoryTest extends DataJpaTestUtil {

    @Autowired
    private ScrapRepository scrapRepository;

    @BeforeEach
    public void setUp(){
        Scrap scrap = Scrap.builder()
                .docsId(2L)
                .memberId(2L)
                .createdAt(LocalDateTime.now())
                .build();
        scrapRepository.save(scrap);
        em.flush();
        em.clear();
    }

    @Test
    public void save_test(){
        // given
        Scrap scrap = Scrap.builder()
                .docsId(1L)
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        Scrap result = scrapRepository.save(scrap);

        // then
        Assertions.assertThat(result.getMemberId()).isEqualTo(scrap.getMemberId());
        Assertions.assertThat(result.getDocsId()).isEqualTo(scrap.getDocsId());
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(scrap.getCreatedAt());

    }

    @Test
    public void delete_test(){
        // given
        Long memberId = 2L;
        Long docsId = 2L;
        ScrapId scrapId = new ScrapId(memberId, docsId);

        // when
        scrapRepository.deleteById(scrapId);
        Optional<Scrap> result = scrapRepository.findById(scrapId);

        // then
        Assertions.assertThat(result.isPresent()).isFalse();

    }
}
