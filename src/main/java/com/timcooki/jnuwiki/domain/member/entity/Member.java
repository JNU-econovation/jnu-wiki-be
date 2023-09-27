package com.timcooki.jnuwiki.domain.member.entity;

import com.timcooki.jnuwiki.util.auditing.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "MEMBER")
public class Member extends BaseTimeEntity {

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

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public void update(String nickname, String password) {
        this.nickName = nickname;
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickName = nickname;
    }
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    @Builder
    public Member(String email, String password, String nickName, MemberRole role) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }
}
