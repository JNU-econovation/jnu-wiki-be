package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record InfoEditResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        DocsLocation docsLocation,
        String docsContent,
        String docsModifiedAt
) {
    public static InfoEditResDTO of(Docs docs) {
        return InfoEditResDTO.builder()
                .docsId(docs.getDocsId())
                .docsName(docs.getDocsName())
                .docsLocation(docs.getDocsLocation())
                .docsContent(docs.getDocsContent())
                .docsCategory(docs.getDocsCategory().getCategory())
                .docsModifiedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .build();
    }
}


