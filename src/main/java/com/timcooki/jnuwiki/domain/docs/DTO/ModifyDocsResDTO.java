package com.timcooki.jnuwiki.domain.docs.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ModifyDocsResDTO {

    private Long id;
    private String docsContent;
    private String docsModifiedBy;
    private LocalDateTime docsModifiedAt;
}
