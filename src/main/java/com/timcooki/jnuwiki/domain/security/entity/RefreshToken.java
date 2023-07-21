package com.timcooki.jnuwiki.domain.security.entity;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "Refresh_Token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    private Instant expiredDate;

    @OneToOne
    @JoinColumn(name = "memberId", referencedColumnName = "member_id")
    private Member member;

    @Builder
    public RefreshToken(String token, Instant expiredDate, Member member){
        this.token = token;
        this.expiredDate = expiredDate;
        this.member = member;
    }
}
