package com.timcooki.jnuwiki.domain.docsRequest.repository;

import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocsRequestRepository extends JpaRepository<DocsRequest, Long> {
    Page<DocsRequest> findAllByDocsRequestType(DocsRequestType docsRequestType, Pageable pageable);
}
