package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.FindAllReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.*;
import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.mapper.DocsMapper;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.entity.ScrapId;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import com.timcooki.jnuwiki.util.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocsReadService {
    private final DocsRepository docsRepository;
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;

    // SecurityContextHolder로 로그인한 유저의 정보를 가져온다.
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    private String getEmail(){
        Authentication loggedInUser = getAuthentication();
        return loggedInUser.getName();
    }

    private boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public ListReadResDTO getDocsList(Pageable pageable, FindAllReqDTO findAllReqDTO) {
        DocsLocation rightUp = DocsLocation.builder()
                .lat(findAllReqDTO.rightLat())
                .lng(findAllReqDTO.rightLng())
                .build();
        DocsLocation leftDown = DocsLocation.builder()
                .lat(findAllReqDTO.leftLat())
                .lng(findAllReqDTO.leftLng())
                .build();
        log.info("테스트7: {}", findAllReqDTO.rightLat());
        log.info("테스트8: {}", findAllReqDTO.leftLat());

        Page<Docs> docsList = docsRepository.mfindAll(rightUp,leftDown, pageable);
        Optional<Member> member = memberRepository.findByEmail(getEmail());
        List<Scrap> scrapList = member.isPresent() ? scrapRepository.findAllByMemberId(member.get().getMemberId()) : new ArrayList<>();

        int totalPages = docsList.getTotalPages();


        List<OneOfListReadResDTO> result = docsList.getContent().stream()
                .map(d -> OneOfListReadResDTO.builder()
                        .docsId(d.getDocsId())
                                .docsName(d.getDocsName())
                                .docsCategory(d.getDocsCategory().getCategory())
                                .docsLocation(d.getDocsLocation())
                                .scrap(!scrapList.isEmpty() && scrapList.stream()
                                        .anyMatch(s -> Objects.equals(s.getDocsId(), d.getDocsId()))
                                )
                                .build()
                        )
                .collect(Collectors.toList());



        return ListReadResDTO.builder()
                .docsList(result)
                .totalPages(totalPages)
                .build();
    }



    public ReadResDTO getOneDocs(Long docsId) {
        boolean scrap = false;
        Docs docs = docsRepository.findById(docsId).orElseThrow(
                () -> new Exception404("존재하지 않는 문서입니다.")
        );

        if (isAuthenticated()) {
            Member member = memberRepository.findByEmail(getEmail()).orElseThrow(
                    () -> new Exception404("존재하지 않는 회원입니다."));
            if (scrapRepository.findById(new ScrapId(member.getMemberId(), docs.getDocsId())).isPresent()) scrap = true;
        }

        return new ReadResDTO(
                docs.getDocsId(),
                docs.getDocsName(),
                docs.getDocsCategory().getCategory(),
                docs.getDocsLocation(),
                docs.getDocsContent(),
                docs.getCreatedBy().getNickName(),
                docs.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                scrap
        );
    }

    public List<SearchReadResDTO> searchLike(String search) {
        List<Docs> docsList = docsRepository.searchLike(search);
        if (docsList != null && !docsList.isEmpty()) {
            DocsMapper mapper = Mappers.getMapper(DocsMapper.class);

            return docsList.stream()
                    .map((docs -> mapper.entityToDTO(docs, docs.getCreatedBy().getNickName(), docs.getDocsCategory().getCategory())))
                    .toList();
        } else {
            throw new Exception404("요청결과가 존재하지 않습니다.");
        }

    }
}
