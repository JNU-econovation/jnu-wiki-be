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
import com.timcooki.jnuwiki.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class PostConstructorMember {

    private final MemberRepository memberRepository;
    private final DocsRepository docsRepository;
    private final PasswordEncoder passwordEncoder;
    private final DocsRequestRepository docsRequestRepository;

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
                .docsName("문서테스트")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lng(1.0)
                        .lat(1.2)
                        .build())
                .createdBy(user)
                .build();
        docs1.updateContent("문서 컨텐츠");
        docsRepository.save(docs1);

        Docs docs2 = Docs.builder()
                .docsName("문서테스트22")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lng(1.0)
                        .lat(1.2)
                        .build())
                .createdBy(user)
                .build();
        docs2.updateContent("문서 컨텐츠22");
        docsRepository.save(docs2);

        Docs docs3 = Docs.builder()
                .docsName("문서테스트33")
                .docsCategory(DocsCategory.CAFE)
                .docsLocation(DocsLocation.builder()
                        .lng(1.0)
                        .lat(1.2)
                        .build())
                .createdBy(user)
                .build();
        docs3.updateContent("문서 컨텐츠33");
        docsRepository.save(docs3);

        DocsRequest docsRequest1 = DocsRequest.builder()
                .docsRequestName("요청문서11")
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(1.4)
                        .lng(1.2)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .build();
        docsRequestRepository.save(docsRequest1);

        DocsRequest docsRequest2 = DocsRequest.builder()
                .docsRequestName("요청문서22")
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(1.4)
                        .lng(1.2)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .build();
        docsRequestRepository.save(docsRequest2);

        DocsRequest docsRequest3 = DocsRequest.builder()
                .docsRequestName("요청문서33")
                .docsRequestType(DocsRequestType.CREATED)
                .docsRequestLocation(DocsLocation.builder()
                        .lat(1.4)
                        .lng(1.2)
                        .build())
                .docsRequestedBy(user)
                .docsRequestCategory(DocsCategory.CAFE)
                .build();
        docsRequestRepository.save(docsRequest3);


        //UPDATE MEMBER SET CREATED_AT = '2023-06-27 06:23:13.271379' WHERE MEMBER_ID=2
    }

}
