package com.timcooki.jnuwiki.domain.docsRequest.repository;

import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocsRequestRepository extends JpaRepository<DocsRequest, Long> {
}
