package com.timcooki.jnuwiki.domain.member.repository;

import com.timcooki.jnuwiki.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAll();

    Optional<Member> findByMemberId(Long id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickName);
    boolean existsByNickName(String nickname);
    boolean existsByEmail(String email);
}
