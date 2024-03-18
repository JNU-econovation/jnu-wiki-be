package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import java.util.List;
import java.util.Objects;
import lombok.Builder;

@Builder
public record OneOfListReadResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        DocsLocation docsLocation,
        boolean scrap

) {
    public static OneOfListReadResDTO of(final Docs docs, final List<Scrap> scrapList) {
        return OneOfListReadResDTO.builder()
                .docsId(docs.getDocsId())
                .docsName(docs.getDocsName())
                .docsCategory(docs.getDocsCategory().getCategory())
                .docsLocation(docs.getDocsLocation())
                .scrap(!scrapList.isEmpty() && scrapList.stream()
                        .anyMatch(s -> Objects.equals(s.getDocsId(), docs.getDocsId())))
                .build();
    }
}

