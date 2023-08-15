package com.timcooki.jnuwiki.domain.docs.DTO.response;

import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

public record SearchReadResDTO(
        Long docsId,
        String docsName,
        DocsLocation docsLocation,
        String docsContent,
        String createdBy,
        String createdAt,
        String docsCategory
) {
    @Builder
    public SearchReadResDTO{

    }
}
