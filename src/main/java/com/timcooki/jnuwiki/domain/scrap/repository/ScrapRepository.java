package com.timcooki.jnuwiki.domain.scrap.repository;

import com.timcooki.jnuwiki.domain.scrap.entity.Scrap;
import com.timcooki.jnuwiki.domain.scrap.entity.ScrapId;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    List<Scrap> findAllByMemberId(Long memberId);
}
