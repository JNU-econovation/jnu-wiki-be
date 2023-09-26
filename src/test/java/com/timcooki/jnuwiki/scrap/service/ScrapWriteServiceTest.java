package com.timcooki.jnuwiki.scrap.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import com.timcooki.jnuwiki.domain.scrap.service.ScrapWriteService;
import com.timcooki.jnuwiki.util.ApiUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ScrapWriteServiceTest {

    @Mock
    private ScrapRepository scrapRepository;
    @InjectMocks
    private ScrapWriteService scrapWriteService;
    private final ObjectMapper om = new ObjectMapper();

    @DisplayName("스크랩 생성 메서드 단위 테스트")
    @Test
    public void create_test() throws Exception{
        // given
        Scrap scrap = Scrap.builder().docsId(1L).memberId(1L).build();
        NewScrapReqDTO dto = NewScrapReqDTO.builder()
                .docsId(1L)
                .memberId(1L)
                .build();
        given(scrapRepository.save(any())).willReturn(scrap);

        // when
        ResponseEntity<?> response = scrapWriteService.create(dto);

        // then
        String expected = om.writeValueAsString(ResponseEntity.ok().body(ApiUtils.success(null)));
        String result = om.writeValueAsString(response);
        System.out.println(expected);
        System.out.println(result);
        Assertions.assertEquals(expected, result);
    }

    @DisplayName("스크랩 삭제 메서드 단위 테스트")
    @Test
    public void delete_test() throws Exception{
        // given
        Scrap scrap = Scrap.builder().docsId(1L).memberId(1L).build();
        DeleteScrapReqDTO dto = DeleteScrapReqDTO.builder()
                .docsId(1L)
                .memberId(1L)
                .build();
        //given(scrapRepository.delete(any())).;

        // when
        ResponseEntity<?> response = scrapWriteService.delete(dto);

        // then
        String expected = om.writeValueAsString(ResponseEntity.ok().body(ApiUtils.success(null)));
        String result = om.writeValueAsString(response);
        System.out.println(expected);
        System.out.println(result);
        Assertions.assertEquals(expected, result);
    }
}
