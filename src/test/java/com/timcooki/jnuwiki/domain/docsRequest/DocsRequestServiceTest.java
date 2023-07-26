package com.timcooki.jnuwiki.domain.docsRequest;

import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class DocsRequestServiceTest {

    @Autowired
    private DocsRequestService docsRequestService;

    @Test
    public void 유저_15일_체크(){
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 6, 1, 1, 1);

        // when
        boolean result = ChronoUnit.DAYS.between(localDateTime, LocalDateTime.now())>15;

        // then
        Assertions.assertThat(result).isEqualTo(true);
        System.out.println(result);
    }

    @Test
    public void 유저_15일_체크_예외(){
        // given
        LocalDateTime localDateTime = LocalDateTime.now();

        // when
        boolean result = ChronoUnit.DAYS.between(localDateTime, LocalDateTime.now())>15;

        // then
        Assertions.assertThat(result).isNotEqualTo(true);
        System.out.println(result);
    }
}
