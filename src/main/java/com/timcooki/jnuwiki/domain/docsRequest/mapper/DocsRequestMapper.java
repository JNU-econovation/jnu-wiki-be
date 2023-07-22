package com.timcooki.jnuwiki.domain.docsRequest.mapper;

import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DocsRequestMapper {
    DocsRequest toEntity(NewWriteReqDTO newWriteReqDTO);

    DocsRequest modifiedDTOToEntity(EditWriteReqDTO editWriteReqDto);

    EditReadResDTO toDTO(DocsRequest docsRequest);
}