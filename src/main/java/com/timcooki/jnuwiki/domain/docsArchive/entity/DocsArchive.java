package com.timcooki.jnuwiki.domain.docsArchive.entity;


import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "DOCS_ARCHIVE")
public class DocsArchive {

    @Id
    @GeneratedValue
    @Column(name = "docs_archive_id")
    private Long docsArchiveId;

    @Column(name = "docs_archive_name", nullable = false)
    private String docsArchiveName;

    @Column(name = "docs_archive_location", nullable = false)
    private String docsArchiveLocation;

    @Column(name = "docs_archive_content")
    private String docsArchiveContent;

    @Column(name = "docs_archive_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsCategory docsArchiveCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member createdBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;


    @Builder
    public DocsArchive(String docsArchiveName, String docsArchiveLocation, String docsArchiveContent, Member createdBy, DocsCategory docsArchiveCategory) {
        this.docsArchiveName = docsArchiveName;
        this.docsArchiveLocation = docsArchiveLocation;
        this.docsArchiveContent = docsArchiveContent;
        this.createdBy = createdBy;
        this.docsArchiveCategory = docsArchiveCategory;
    }
}
