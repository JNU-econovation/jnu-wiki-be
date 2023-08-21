package com.timcooki.jnuwiki.domain.scrap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapId implements Serializable {
    private Long memberId;
    private Long docsId;

    // 식별자 클래스 정의 시에는 equals, hashCode 메서드 재정의 필수 (복합키 비교)
    @Override
    public int hashCode() {
        return Objects.hash(getDocsId(), getMemberId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(getDocsId(), ((ScrapId) obj).getDocsId())
                && Objects.equals(getMemberId(), ((ScrapId) obj).getMemberId());
    }
}
