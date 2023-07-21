package com.timcooki.jnuwiki.domain.docsArchive.repository;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docsArchive.entity.DocsArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocsArchiveRepository extends JpaRepository<DocsArchive, Long> {
    void save(Docs docs);
}
