package com.timcooki.jnuwiki.domain.member.DTO.response;

import lombok.Builder;

// 멤버가 스크랩한 게시글 응답
public record ScrapResDTO (
        Long docsId,
        String docsName,
        String docsCategory
){

    @Builder
    public ScrapResDTO {
    }
}