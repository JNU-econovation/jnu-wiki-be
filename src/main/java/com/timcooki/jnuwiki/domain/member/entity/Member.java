package com.timcooki.jnuwiki.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_nickname", nullable = false, unique = true)
    private String nickName;

    @CreatedDate
    @Column(name = "member_join_date")
    private LocalDate createdAt;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;


    public boolean canEdit() {
        // 수정 가능한지 - 현재가 15일 이후면 가
        if (LocalDate.now().isAfter(createdAt.plusDays(15))) {
            return true;
        }

        else return false;
    }

    @Builder
    public Member(String email, String password, String nickName, MemberRole role) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }
}
