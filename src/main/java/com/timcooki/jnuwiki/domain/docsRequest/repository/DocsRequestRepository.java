package com.timcooki.jnuwiki.domain.docsRequest.repository;

import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequestType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DocsRequestRepository extends JpaRepository<DocsRequest, Long> {
    List<DocsRequest> findAllByDocsRequestType(DocsRequestType docsRequestType, Pageable pageable);
}
