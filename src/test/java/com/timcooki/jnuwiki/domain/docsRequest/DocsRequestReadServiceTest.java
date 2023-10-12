//TODO - 테스트 리팩토링 할 것
//package com.timcooki.jnuwiki.domain.docsRequest;
//
//import com.timcooki.jnuwiki.domain.docsRequest.service.DocsRequestReadService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//
//@SpringBootTest
//public class DocsRequestReadServiceTest {
//
//    @Autowired
//    private DocsRequestReadService docsRequestReadService;
//
//    @Test
//    public void fifteen_check(){
//        // given
//        LocalDateTime localDateTime = LocalDateTime.of(2023, 6, 1, 1, 1);
//
//        // when
//        boolean result = ChronoUnit.DAYS.between(localDateTime, LocalDateTime.now())>15;
//
//        // then
//        Assertions.assertThat(result).isEqualTo(true);
//        System.out.println(result);
//    }
//
//    @Test
//    public void fifteen_check_fail(){
//        // given
//        LocalDateTime localDateTime = LocalDateTime.now();
//
//        // when
//        boolean result = ChronoUnit.DAYS.between(localDateTime, LocalDateTime.now())>15;
//
//        // then
//        Assertions.assertThat(result).isNotEqualTo(true);
//        System.out.println(result);
//    }
//}
