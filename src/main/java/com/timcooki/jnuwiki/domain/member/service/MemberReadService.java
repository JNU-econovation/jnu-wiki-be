package com.timcooki.jnuwiki.domain.member.service;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapListResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.ScrapResDTO;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        Member memberOptional = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        return ReadResDTO.builder()
                .id(memberOptional.getMemberId())
                .nickName(memberOptional.getNickName())
                .password(memberOptional.getPassword())
                .build();
    }

    public ScrapListResDTO getScrappedDocs(Pageable pageable) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails memberDetails = (UserDetails) principal;

        Member member = memberRepository.findByEmail(memberDetails.getUsername()).orElseThrow(
                () -> new Exception404("존재하지 않는 회원입니다.")
        );

        Page<Docs> docsList = docsRepository.mFindScrappedDocsByMemberId(member.getMemberId(), pageable);

        ScrapListResDTO list = ScrapListResDTO.builder()
                .scrapList(docsList.stream()
                        .map(d -> ScrapResDTO.builder()
                                .docsId(d.getDocsId())
                                .docsName(d.getDocsName())
                                .docsCategory(d.getDocsCategory().getCategory())
                                .docsRequestLocation(d.getDocsLocation())
                                .member(member.getNickName())
                                .build())
                        .toList())
                .totalPages(docsList.getTotalPages())
                .build();
        return list;
    }

}
