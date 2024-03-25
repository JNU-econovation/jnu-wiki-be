package com.timcooki.jnuwiki.domain.docs.service;

import com.timcooki.jnuwiki.domain.docs.DTO.request.FindAllReqDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.OneOfListReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.ReadResDTO;
import com.timcooki.jnuwiki.domain.docs.DTO.response.SearchReadResDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private String getEmail() {
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
        DocsLocation rightUp = new DocsLocation(findAllReqDTO.rightLat(), findAllReqDTO.rightLng());
        DocsLocation leftDown = new DocsLocation(findAllReqDTO.leftLat(), findAllReqDTO.leftLng());

        Page<Docs> docsList = docsRepository.mfindAll(rightUp, leftDown, pageable);
        Optional<Member> member = memberRepository.findByEmail(getEmail());
        List<Scrap> scrapList =
                member.isPresent() ? scrapRepository.findAllByMemberId(member.get().getMemberId()) : new ArrayList<>();

        List<OneOfListReadResDTO> result = docsList.getContent().stream()
                .map(docs -> OneOfListReadResDTO.of(docs, scrapList))
                .toList();

        return ListReadResDTO.builder()
                .docsList(result)
                .totalPages(docsList.getTotalPages())
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
            if (scrapRepository.findById(new ScrapId(member.getMemberId(), docs.getDocsId())).isPresent()) {
                scrap = true;
            }
        }

        return ReadResDTO.of(docs, scrap);
    }

    public List<SearchReadResDTO> searchLike(String search) {
        List<Docs> docsList = docsRepository.searchLike(search);
        if (docsList != null && !docsList.isEmpty()) {
            DocsMapper mapper = Mappers.getMapper(DocsMapper.class);

            return docsList.stream()
                    .map((docs -> mapper.entityToDTO(docs, docs.getCreatedBy().getNickName(),
                            docs.getDocsCategory().getCategory())))
                    .toList();
        } else {
            throw new Exception404("요청결과가 존재하지 않습니다.");
        }

    }
}
