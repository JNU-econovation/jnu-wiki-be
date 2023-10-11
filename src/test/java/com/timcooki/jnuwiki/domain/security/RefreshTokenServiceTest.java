//package com.timcooki.jnuwiki.domain.security;
//
//import com.timcooki.jnuwiki.domain.member.entity.Member;
//import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
//import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
//import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
//import com.timcooki.jnuwiki.domain.security.repository.RefreshTokenRepository;
//import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//
//@ExtendWith(MockitoExtension.class)
//public class RefreshTokenServiceTest {
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private RefreshTokenService refreshTokenService;
//
//    @BeforeEach
//    public void setUp() {
//        // Mock 객체를 초기화합니다.
//        MockitoAnnotations.initMocks(this);
//    }
//
//    // TODO: 테스트 stub이 실행될 때의 now와 서비스단 메서드가 실행될 때의 now가 달라서 에러 발생
//    @Test
//    @DisplayName("리프레시 토큰이 만료되지 않았을 경우 기존의 토큰이 리턴되는가?")
//    public void create_Refresh_Token_exist_test () {
//        // given
//        Member member = Member.builder()
//                .email("minl741@naver.com")
//                .nickName("momo")
//                .password("asbc1234!")
//                .role(MemberRole.USER)
//                .build();
//
//        RefreshToken expectedToken = RefreshToken.builder()
//                .token("member 1의 만료되지 않은 토큰")
//                .expiredDate(Instant.ofEpochSecond(2_000_000_000))
//                .member(member)
//                .build();
//
//        // stub
//        Mockito.when(refreshTokenRepository.findByMemberAndExpiredDateIsAfter(member, Instant.now())).thenReturn(Optional.of(expectedToken));
//        Mockito.when(refreshTokenRepository.save(expectedToken)).thenReturn(expectedToken);
//
//        // when
//        RefreshToken actualToken = refreshTokenService.createRefreshToken(member);
//
//        // then
//        assertEquals(expectedToken, actualToken);
//    }
//}