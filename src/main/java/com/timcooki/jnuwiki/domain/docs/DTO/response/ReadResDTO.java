package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record ReadResDTO(
        Long id,
        String docsName,
        String docsCategory,
        DocsLocation docsLocation,
        String docsContent,
        String docsCreatedBy,
        String docsCreatedAt,
        String docsModifiedAt,
        boolean scrap
) {
    public static ReadResDTO of(final Docs docs, final boolean scrap) {
        return new ReadResDTO(
                docs.getDocsId(),
                docs.getDocsName(),
                docs.getDocsCategory().getCategory(),
                docs.getDocsLocation(),
                docs.getDocsContent(),
                docs.getCreatedBy().getNickName(),
                docs.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                docs.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                scrap);

    }
}




