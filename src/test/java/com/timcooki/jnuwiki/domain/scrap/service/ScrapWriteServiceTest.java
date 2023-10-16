package com.timcooki.jnuwiki.domain.scrap.service;

import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.DeleteScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.DTO.request.NewScrapReqDTO;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScrapWriteServiceTest {
    @InjectMocks private ScrapWriteService scrapWriteService;
    @Mock private MemberRepository memberRepository;
    @Mock private ScrapRepository scrapRepository;

    @Test
    @DisplayName("스크랩 생성")
     void delete_scrap_test() {
        // given
        NewScrapReqDTO newScrapReqDTO = NewScrapReqDTO.builder()
                .memberId(1L)
                .docsId(1L)
                .build();

        // when
        scrapWriteService.create(newScrapReqDTO);

        // then
        verify(scrapRepository, times(1)).save(any(Scrap.class));
    }

    @Test
    @DisplayName("스크랩 삭제")
     void create_scrap_test() {
        // given
        DeleteScrapReqDTO deleteScrapReqDTO = DeleteScrapReqDTO.builder()
                .memberId(1L)
                .docsId(1L)
                .build();

        // when
        scrapWriteService.delete(deleteScrapReqDTO);

        // then
        verify(scrapRepository, times(1)).delete(any(Scrap.class));
    }
}
