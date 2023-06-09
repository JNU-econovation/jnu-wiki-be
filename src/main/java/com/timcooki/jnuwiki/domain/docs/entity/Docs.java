package com.timcooki.jnuwiki.domain.docs.entity;


import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "docs_location", nullable = false)
    private String docsLocation;

    @Column(name = "docs_content")
    private String docsContent;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    @Column(name = "docs_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsCategory docsCategory;

    @Builder
    public Docs(String docsName, String docsLocation, Member createdBy, DocsCategory docsCategory) {
        this.docsName = docsName;
        this.docsLocation = docsLocation;
        this.createdBy = createdBy;
        this.docsCategory = docsCategory;
    }

    public void updateBasicInfo(String docsName, String docsLocation, DocsCategory docsCategory) {
        this.docsName = docsName;
        this.docsLocation = docsLocation;
        this.docsCategory = docsCategory;
    }
}
