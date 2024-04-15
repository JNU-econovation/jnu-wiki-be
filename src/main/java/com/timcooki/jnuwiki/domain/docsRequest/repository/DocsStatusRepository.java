package com.timcooki.jnuwiki.domain.docsRequest.repository;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocsStatusRepository extends JpaRepository<DocsStatus, Long> {

    @Modifying
    @Query("delete from DocsStatus ds WHERE ds.member.email = :email")
    void deleteByEmail(@Param("email") String email);

    boolean existsByDocs(Docs docs);
    boolean existsByDocs_DocsId(Long docsId);

}
