package com.timcooki.jnuwiki.domain.docs.entity;


import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "DOCS")
public class Docs {

    @Id
    @GeneratedValue
    @Column(name = "docs_id")
    private Long docsId;

    @Column(name = "docs_name", nullable = false)
    private String docsName;

    @Embedded
    private DocsLocation docsLocation;

    @Column(name = "docs_content")
    private String docsContent;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "docs_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsCategory docsCategory;

    @Builder
    public Docs(String docsName, DocsLocation docsLocation, Member createdBy, DocsCategory docsCategory) {
        this.docsName = docsName;
        this.docsLocation = docsLocation;
        this.createdBy = createdBy;
        this.docsCategory = docsCategory;
    }

    public void updateBasicInfo(String docsName, DocsLocation docsLocation, DocsCategory docsCategory) {
        this.docsName = docsName;
        this.docsLocation = docsLocation;
        this.docsCategory = docsCategory;
    }

    public void updateContent(String docsContent) {
        this.docsContent = docsContent;
        this.modifiedAt = LocalDateTime.now();
    }
}
