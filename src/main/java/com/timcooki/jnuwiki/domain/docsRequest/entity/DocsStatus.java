package com.timcooki.jnuwiki.domain.docsRequest.entity;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "DOCS_STATUS")
@EntityListeners(AuditingEntityListener.class)
public class DocsStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docs_status_id")
    private Long docsStatusId;

    @JoinColumn(name = "docs_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Docs docs;

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public DocsStatus(Docs docs, Member member){
        this.docs = docs;
        this.member = member;
    }

}
