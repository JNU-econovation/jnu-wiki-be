package com.timcooki.jnuwiki.domain.security;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
import com.timcooki.jnuwiki.testutil.DataJpaTestUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Instant;

class RefreshTokenRepositoryTest extends DataJpaTestUtil {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        Member member1 = Member.builder()
                .email("minl741@naver.com")
                .nickName("momo")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();

        Member member2 = Member.builder()
                .email("213d@mmm.com")
                .nickName("uu")
                .password("asbc1234!")
                .role(MemberRole.USER)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        RefreshToken refreshToken1 = refreshTokenRepository.save(
                RefreshToken.builder()
                        .token("member 1의 만료된 토큰")
                        .expiredDate(Instant.EPOCH)
                        .member(member1)
                        .build()
        );

        RefreshToken refreshToken2 = refreshTokenRepository.save(
                RefreshToken.builder()
                        .token("member 2의 만료된 토큰")
                        .expiredDate(Instant.EPOCH)
                        .member(member2)
                        .build()
        );

        RefreshToken refreshToken3 = refreshTokenRepository.save(
                RefreshToken.builder()
                        .token("member 1의 만료되지 않은 토큰")
                        .expiredDate(Instant.ofEpochSecond(2_000_000_000))
                        .member(member1)
                        .build()
        );

        RefreshToken refreshToken32 = refreshTokenRepository.save(
                RefreshToken.builder()
                        .token("member 1의 만료되지 않은 토큰2")
                        .expiredDate(Instant.ofEpochSecond(2_000_000_000))
                        .member(member1)
                        .build()
        );

        RefreshToken refreshToken5 = refreshTokenRepository.save(
                RefreshToken.builder()
                        .token("member 2의 만료되지 않은 토큰")
                        .expiredDate(Instant.ofEpochSecond(2_000_000_000))
                        .member(member2)
                        .build()
        );

        refreshTokenRepository.save(refreshToken1);
        refreshTokenRepository.save(refreshToken2);
        refreshTokenRepository.save(refreshToken3);
        refreshTokenRepository.save(refreshToken32);
        refreshTokenRepository.save(refreshToken5);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("특정 유저의 만료 시간 전의 리프레시 토큰만 조회되는가")
    public void findBy_Member_And_ExpiredDate_IsAfter_test() {
        // given
        Member member = memberRepository.findByEmail("minl741@naver.com").get();
        // when
        List<RefreshToken> tokens = refreshTokenRepository.findByMemberAndExpiredDateIsAfter(member, Instant.now());
        tokens.forEach(token -> System.out.println(token.getExpiredDate() + " 시간이랑 멤버 " + token.getToken()));

        // then
    }
}
