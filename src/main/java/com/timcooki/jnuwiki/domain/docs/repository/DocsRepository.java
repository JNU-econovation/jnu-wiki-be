package com.timcooki.jnuwiki.domain.docs.repository;

import com.timcooki.jnuwiki.domain.docs.entity.Docs;
import com.timcooki.jnuwiki.domain.docs.entity.DocsLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocsRepository extends JpaRepository<Docs, Long> {
    Page<Docs> findAll(Pageable pageable);
    // 마이페이지 - 최근 스크랩한 게시글 조회
    @Query(value = "select d from Docs d join fetch Scrap s on d.docsId = s.docsId where s.memberId = :memberId order by s.createdAt desc")
    Page<Docs> mFindScrappedDocsByMemberId(Long memberId, Pageable pageable);

    // 검색
    @Query(value = "select d from Docs d where d.docsName like %:search% or d.docsContent like %:search%")
    List<Docs> searchLike(@Param("search")String search);

//    @Query(value = "SELECT d from Docs d where d.docsLocation.lat " +
//            "between :leftLat and :rightLat and " +
//            "d.docsLocation.lat between :leftLng and :rightLng")
//    Page<Docs> mfindAll(Double rightLat, Double rightLng, Double leftLat, Double leftLng, Pageable pageable);

    @Query(value = "SELECT d from Docs d where d.docsLocation.lat " +
            "between :#{#leftDown.lat} and :#{#rightUp.lat} and " +
            "d.docsLocation.lng between :#{#leftDown.lng} and :#{#rightUp.lng}")
    Page<Docs> mfindAll(DocsLocation rightUp, DocsLocation leftDown, Pageable pageable);
}
