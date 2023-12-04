package com.timcooki.jnuwiki.domain.member.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;

import java.util.List;
import org.springframework.data.domain.Page;

@Builder
public record ScrapListResDTO(
        List<ScrapResDTO> scrapList,
        int totalPages
) {
    public static ScrapListResDTO of(Page<Docs> docs, Member member) {
        return ScrapListResDTO.builder()
                .scrapList(docs.stream()
                        .map(d -> ScrapResDTO.of(d, member))
                        .toList())
                .totalPages(docs.getTotalPages())
                .build();
    }
}

