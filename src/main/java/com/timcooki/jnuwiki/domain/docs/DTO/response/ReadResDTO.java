package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;

public record ReadResDTO(
        Long id,
        String docsName,
        String docsCategory,
        DocsLocation docsLocation,
        String docsContent,
        String docsCreatedBy,
        String docsCreatedAt,
        boolean scrap
) {
    @Builder
    public ReadResDTO {

    }
}




