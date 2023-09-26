package com.timcooki.jnuwiki.domain.member.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ObjectMapper.class)
@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        Member member1 = Member.builder()
                .email("aa@naver.com")
                .nickName("aa")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .email("bb@naver.com")
                .nickName("bb")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member2);

        em.flush();
        em.clear();
    }

    @Test
    public void findAll_test() {
        // given

        // when
        List<Member> memberList = memberRepository.findAll();

        // then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    public void findByEmail_test() {
        // given
        String email = "aa@naver.com";

        // when
        Member member = memberRepository.findByEmail(email).get();

        // then
        assertThat(email).isEqualTo(member.getEmail());
    }

    @Test
    public void findByNickName_test() {
        // given
        String nickname = "bb";

        // when
        Member member = memberRepository.findByNickName(nickname).get();

        // then
        assertThat(nickname).isEqualTo(member.getNickName());
    }
}