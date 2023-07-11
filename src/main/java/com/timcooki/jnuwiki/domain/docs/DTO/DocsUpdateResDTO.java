package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DocsUpdateResDTO {
    private Long docsId;
    private String docsName;
    // TODO - ENUM으로 변경
    private String docsCategory;
    private List<Integer> docsLocation;
    private String docsContent;
    private String docsModifiedBy;
    private LocalDateTime docsModifiedAt;
}
