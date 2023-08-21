package com.timcooki.jnuwiki.domain.scrap.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@IdClass(ScrapId.class)
public class Scrap implements Serializable {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Id
    @Column(name = "docs_id")
    private Long docsId;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Scrap(Long memberId, Long docsId, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.docsId = docsId;
        this.createdAt = createdAt;
    }
}
