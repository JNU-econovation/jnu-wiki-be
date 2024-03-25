package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapListResDTO;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;

    public ReadResDTO getInfo() {
        Member member = getMember();
        return ReadResDTO.of(member);
    }

    public ScrapListResDTO getScrappedDocs(Pageable pageable) {
        Member member = getMember();
        Page<Docs> docsList = docsRepository.mFindScrappedDocsByMemberId(member.getMemberId(), pageable);
        return ScrapListResDTO.of(docsList, member);
    }

    public Member getMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        return memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );
    }
}
