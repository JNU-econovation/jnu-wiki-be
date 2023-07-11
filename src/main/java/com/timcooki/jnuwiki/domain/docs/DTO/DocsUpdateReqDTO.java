package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocsUpdateReqDTO {

    private String docsName;
    // TODO - ENUM으로 변경할 것
    private String docsCategory;
    private List<Integer> docsLocation;
    private String docsContent;
    private String docsModifiedBy;
}
