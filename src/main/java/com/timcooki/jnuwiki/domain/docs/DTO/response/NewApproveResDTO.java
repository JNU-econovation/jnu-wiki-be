package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;


@Builder
public record NewApproveResDTO(
    Long id,
    String docsName,
    String docsCategory,
    DocsLocation docsLocation,
    String docsCreatedAt
){
    public static NewApproveResDTO of(Docs docs){
        return NewApproveResDTO.builder()
                .id(docs.getDocsId())
                .docsCategory(docs.getDocsCategory().getCategory())
                .docsName(docs.getDocsName())
                .docsLocation(docs.getDocsLocation())
                .docsCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .build();
    }
}
