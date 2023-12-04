package com.timcooki.jnuwiki.domain.member.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
@Builder
public record ScrapResDTO(
        Long docsId,
        String docsName,
        String docsCategory,
        DocsLocation docsRequestLocation,
        String member
) {
    public static ScrapResDTO of(Docs docs, Member member) {
        return ScrapResDTO.builder()
                .docsId(docs.getDocsId())
                .docsName(docs.getDocsName())
                .docsCategory(docs.getDocsCategory().getCategory())
                .docsRequestLocation(docs.getDocsLocation())
                .member(member.getNickName())
                .build();
    }
}
