package com.timcooki.jnuwiki.domain.docsRequest.entity;


import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.util.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "DOCS_REQUEST")
public class DocsRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docs_request_id")
    private Long docsRequestId;

    @Column(name = "docs_request_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsRequestType docsRequestType;

    @Column(name = "docs_request_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocsCategory docsRequestCategory;

    @Column(name = "docs_request_name", nullable = false)
    private String docsRequestName;

    @Embedded
    private DocsLocation docsRequestLocation;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member docsRequestedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docs_id")
    private Docs docs;

    @Builder
    public DocsRequest(DocsRequestType docsRequestType, DocsCategory docsRequestCategory, String docsRequestName, DocsLocation docsRequestLocation, Member docsRequestedBy, Docs docs) {
        this.docsRequestType = docsRequestType;
        this.docsRequestCategory = docsRequestCategory;
        this.docsRequestName = docsRequestName;
        this.docsRequestLocation = docsRequestLocation;
        this.docsRequestedBy = docsRequestedBy;
        this.docs = docs;
    }
}
