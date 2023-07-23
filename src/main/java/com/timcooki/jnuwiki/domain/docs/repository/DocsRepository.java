package com.timcooki.jnuwiki.domain.docs.repository;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocsRepository extends JpaRepository<Docs, Long> {
    Page<Docs> findAll(Pageable pageable);
}
