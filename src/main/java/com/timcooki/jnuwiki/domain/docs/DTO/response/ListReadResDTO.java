package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDate;

public record ListReadResDTO(
        Long id,
        String docsName,
        // TODO Type 변경 - ENUM
        DocsCategory docsCategory,
         DocsLocation docsLocation,
         String docsContent,
         Member docsCreatedBy,
         LocalDate docsCreatedAt
) {
    @Builder
    public ListReadResDTO {
    }
}
