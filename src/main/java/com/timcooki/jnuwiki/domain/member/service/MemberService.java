package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.DTO.request.*;
import com.timcooki.jnuwiki.domain.member.DTO.response.LoginResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapListResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.security.entity.RefreshToken;
import com.timcooki.jnuwiki.domain.security.service.RefreshTokenService;
import com.timcooki.jnuwiki.util.ApiUtils;
import com.timcooki.jnuwiki.util.JwtUtil.JwtUtil;
import com.timcooki.jnuwiki.util.errors.exception.Exception400;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final Validator validator;
    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;



    public ResponseEntity<?> login(LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.email();
        String password = loginReqDTO.password();
        validEmail(loginReqDTO.email());
        validPassword(loginReqDTO.password());


        // AuthenticationManger에게 인증 진행 위임
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));


        // 인증되었다면,
        if (authentication.isAuthenticated()) {
            // 리프레시 토큰 발급
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(email, memberRepository.findByEmail(email));
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new Exception404("존재하지 않는 회원입니다.")
            );
            Long memberId = member.getMemberId();
            String memberRole = member.getRole().toString();

            String token = JwtUtil.createJwt(email, memberRole, secretKey);

            // header 생성
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
            httpHeaders.set(HttpHeaders.SET_COOKIE, refreshToken.getToken());

            // DTO 생성
            LoginResDTO loginResDTO = LoginResDTO.builder()
                    .id(memberId)
                    .role(memberRole)
                    .build();

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(ApiUtils.success(loginResDTO));

        } else {// 인증 오류시
            // TODO - 401변경 양식맞추기
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일과 비밀번호를 확인해주세요", HttpStatus.UNAUTHORIZED));
        }

    }


    public ResponseEntity<?> join(JoinReqDTO joinReqDTO) {

        validEmail(joinReqDTO.email());

        duplicateCheckEmail(joinReqDTO.email());

        validPassword(joinReqDTO.password());
        // TODO - MapStruct Test 필요
        /*
        MemberMapper mapper = Mappers.getMapper(MemberMapper.class);

        Member member = mapper.toEntity(joinReqDTO, MemberRole.USER);

         */

        Member member = Member.builder()
                .email(joinReqDTO.email())
                .nickName(joinReqDTO.nickName())
                .role(MemberRole.USER)
                .password(passwordEncoder.encode(joinReqDTO.password()))
                .build();

        memberRepository.save(member);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    private void validPassword(String password) {
        // 비밀번호 형식 확인
        if (!validator.isValidPassword(password)) {
            throw new Exception400("비밀번호는 8~16자여야 하고 영문, 숫자, 특수문자가 포함되어야합니다.:" + password);
        }
    }

    private void duplicateCheckEmail(String email) {
        // email 중복 확인
        if (existEmail(email)) {
            throw new Exception400("존재하는 이메일 입니다.");
        }
    }

    private void validEmail(String email) {
        // 이메일 형식 검증
        if (!validator.isValidEmail(email)) {
            throw new Exception400("이메일 형식으로 작성해주세요.:" + email);
        }
    }

    private boolean existEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    private boolean validationMember(String email, String password) {
        // id가 있는지 확인
        if (memberRepository.findByEmail(email).isEmpty()) {
            return false;
        }
        System.out.println("id 있음");

        Member loginMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );
        System.out.println("이메일 얻어옴");

        // 아이디에 대응되는 비밀번호가 맞는지 확인
        return loginMember.getPassword().equals(passwordEncoder.encode(password));
    }

    public boolean isPresentNickName(CheckNicknameReqDTO checkNicknameReqDTO) {

        return memberRepository.findByNickName(checkNicknameReqDTO.nickname()).isPresent();
    }

    public boolean isPresentEmail(CheckEmailReqDTO checkEmailReqDTO) {

        validEmail(checkEmailReqDTO.email());
        return memberRepository.findByEmail(checkEmailReqDTO.email()).isPresent();
    }

    public ReadResDTO getInfo(UserDetails userDetails) {
        Member memberOptional = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );
        // TODO - mapStruct 사용
        ReadResDTO resDTO = ReadResDTO.builder()
                .id(memberOptional.getMemberId())
                .nickName(memberOptional.getNickName())
                .password(memberOptional.getPassword())
                .build();
        return resDTO;
    }

    @Transactional
    public void editInfo(UserDetails userDetails, EditReqDTO editReqDTO) {

        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        if (memberRepository.findByNickName(editReqDTO.nickname()).isPresent()) {
            throw new Exception400("중복된 닉네임 입니다.:nickname");
        }
        validPassword(editReqDTO.password());


        member.update(editReqDTO.nickname(), passwordEncoder.encode(editReqDTO.password()));
    }

    public ScrapListResDTO getScrappedDocs(UserDetails userDetails, Pageable pageable) {
        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        Page<Docs> docsList = docsRepository.mFindScrappedDocsByMemberId(member.getMemberId(), pageable);

        ScrapListResDTO list = ScrapListResDTO.builder()
                .scrapList(docsList.stream()
                        .map(d -> new ScrapResDTO(d.getDocsId(), d.getDocsName(), d.getDocsName()))
                        .toList())
                .totalPages(docsList.getTotalPages())
                .build();
        return list;
    }
}
