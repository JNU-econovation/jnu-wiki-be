package com.timcooki.jnuwiki.domain.docsRequest.entity;


import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "DOCS_REQUEST")
public class DocsRequest {

    @Id
    @GeneratedValue
    @Column(name = "docs_request_id")
    private Long docsRequestId;

    @Column(name = "docs_request_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsRequestType docsRequestType;

    @Column(name = "docs_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsCategory docsCategory;

    @Column(name = "docs_name", nullable = false)
    private String docsName;

    @Column(name = "docs_location", nullable = false)
    private DocsLocation docsLocation;

    @ManyToOne
    // TODO : join column 에 createdBy 적용 가능 확인
    @JoinColumn(name = "member_id", nullable = false)
    private Member docsRequestedBy;

    @CreatedDate
    @Column(name = "requested_at")
    private LocalDateTime docsRequestAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_id")
    private Docs docs;

    @Builder
    public DocsRequest(Long requestId, DocsRequestType docsRequestType, DocsCategory docsCategory, String docsName, DocsLocation docsLocation, Member docsRequestedBy, Docs docs) {
        this.docsRequestType = docsRequestType;
        this.docsCategory = docsCategory;
        this.docsName = docsName;
        this.docsLocation = docsLocation;
        this.docsRequestedBy = docsRequestedBy;
        this.docs = docs;
    }
}
