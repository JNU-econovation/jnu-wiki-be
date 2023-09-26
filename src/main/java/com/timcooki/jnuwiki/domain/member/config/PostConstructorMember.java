package com.timcooki.jnuwiki.domain.member.config;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import com.timcooki.jnuwiki.domain.docs.repository.DocsRepository;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsCategory;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import com.timcooki.jnuwiki.domain.docsRequest.repository.DocsRequestRepository;
import com.timcooki.jnuwiki.domain.member.entity.Member;
import com.timcooki.jnuwiki.domain.member.entity.MemberRole;
import com.timcooki.jnuwiki.domain.member.repository.MemberRepository;
import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostConstructorMember {

    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;
    private final PasswordEncoder passwordEncoder;
    private final DocsRequestRepository docsRequestRepository;
    private final ScrapRepository scrapRepository;
    @PostConstruct
    public void init(){

        Member admin = Member.builder()
                .email("admin@naver.com")
                .password(passwordEncoder.encode("adminA1234!@"))
                .role(MemberRole.ADMIN)
                .nickName("admin")
                .build();
        memberRepository.save(admin);
        Member user = Member.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("testT1234!@"))
                .role(MemberRole.USER)
                .nickName("test")
                .build();
        memberRepository.save(user);

        Docs docs1 = Docs.builder()
                .docsName("업데이트1")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs1.updateContent("문서 컨텐츠");
        docsRepository.save(docs1);

        Docs docs2 = Docs.builder()
                .docsName("업데이트2")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs2.updateContent("문서 컨텐츠22");
        docsRepository.save(docs2);

        Docs docs3 = Docs.builder()
                .docsName("업데이트3")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs3.updateContent("문서 컨텐츠33");
        docsRepository.save(docs3);

        Docs docs4 = Docs.builder()
                .docsName("업데이트4")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs4.updateContent("4444문서 컨텐츠");
        docsRepository.save(docs4);

        Docs docs5 = Docs.builder()
                .docsName("업데이트5")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs5.updateContent("문서 컨텐츠22");
        docsRepository.save(docs5);

        Docs docs6 = Docs.builder()
                .docsName("업데이트X 6")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
//        docs6.updateContent("문서 컨텐츠33");
        docsRepository.save(docs6);

        Docs docs7 = Docs.builder()
                .docsName("업데이트X 7")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
//        docs7.updateContent("문서 컨텐츠");
        docsRepository.save(docs7);

        Docs docs8 = Docs.builder()
                .docsName("업데이트X 8")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
//        docs8.updateContent("문서 컨텐츠22");
        docsRepository.save(docs8);

        Docs docs9 = Docs.builder()
                .docsName("업데이트X 9")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
//        docs9.updateContent("문서 컨텐츠33");
        docsRepository.save(docs9);

        Docs docs10 = Docs.builder()
                .docsName("업데이트 10")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs10.updateContent("문32434서 컨텐츠");
        docsRepository.save(docs10);

        Docs docs11 = Docs.builder()
                .docsName("업데이트 11")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs11.updateContent("문서 컨텐츠22");
        docsRepository.save(docs11);

        Docs docs12 = Docs.builder()
                .docsName("업데이트 12")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs12.updateContent("문서 컨텐츠33");
        docsRepository.save(docs12);

        Docs docs13 = Docs.builder()
                .docsName("업데이트 13")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs13.updateContent("문서 컨텐츠");
        docsRepository.save(docs13);

        Docs docs14 = Docs.builder()
                .docsName("업데이트X 14")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
//        docs14.updateContent("문서 컨텐츠22");
        docsRepository.save(docs14);

        Docs docs15 = Docs.builder()
                .docsName("업데이트 15")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .createdBy(user)
                .build();
        docs15.updateContent("문서 컨텐츠33");
        docsRepository.save(docs15);

        // 이제 문서
        DocsRequest docsRequest1 = DocsRequest.builder()
                .docsRequestName("요청문서11")
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .build();
        docsRequestRepository.save(docsRequest1);

        DocsRequest docsRequest2 = DocsRequest.builder()
                .docsRequestName("요청문서22")
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .build();
        docsRequestRepository.save(docsRequest2);

        DocsRequest docsRequest3 = DocsRequest.builder()
                .docsRequestName("요청문서33")
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .build();
        docsRequestRepository.save(docsRequest3);

        DocsRequest docsRequest4 = DocsRequest.builder()
                .docsRequestName("요청문서44444")
                .docsRequestType(DocsRequestType.MODIFIED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .docs(docs3)
                .build();
        docsRequestRepository.save(docsRequest4);

        DocsRequest docsRequest5 = DocsRequest.builder()
                .docsRequestName("요청문서5555")
                .docsRequestType(DocsRequestType.MODIFIED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .docs(docs3)
                .build();
        docsRequestRepository.save(docsRequest5);

        DocsRequest docsRequest6 = DocsRequest.builder()
                .docsRequestName("요청문666")
                .docsRequestType(DocsRequestType.MODIFIED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(35.17641341218037)
                        .lng(126.91349388159176)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .docs(docs1)
                .build();
        docsRequestRepository.save(docsRequest6);

        Scrap scrap1 = Scrap.builder()
                .memberId(0L)
                .docsId(0L)
                .createdAt(LocalDateTime.now()).build();
        scrapRepository.save(scrap1);

        Scrap scrap2 = Scrap.builder()
                .memberId(2L)
                .docsId(1L)
                .createdAt(LocalDateTime.now()).build();
        scrapRepository.save(scrap2);

        Scrap scrap3 = Scrap.builder()
                .memberId(2L)
                .docsId(11L)
                .createdAt(LocalDateTime.now()).build();
        scrapRepository.save(scrap3);

        Scrap scrap4 = Scrap.builder()
                .memberId(1L)
                .docsId(2L)
                .createdAt(LocalDateTime.now()).build();
        scrapRepository.save(scrap4);

        Scrap scrap5 = Scrap.builder()
                .memberId(1L)
                .docsId(6L)
                .createdAt(LocalDateTime.now()).build();
        scrapRepository.save(scrap5);

        //UPDATE MEMBER SET CREATED_AT = '2023-06-27 06:23:13.271379' WHERE MEMBER_ID=2
    }

}
