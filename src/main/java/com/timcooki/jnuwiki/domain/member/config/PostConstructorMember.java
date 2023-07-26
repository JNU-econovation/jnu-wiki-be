package com.timcooki.jnuwiki.domain.member.config;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class PostConstructorMember {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){

        Member admin = Member.builder()
                .email("admin@naver.com")
                .password(passwordEncoder.encode("adminA1234!@"))
                .role(MemberRole.ADMIN)
                .nickName("admin")
                .build();
        memberRepository.save(admin);

        //UPDATE MEMBER SET CREATED_AT = '2023-06-27 06:23:13.271379' WHERE MEMBER_ID=2
    }

}
