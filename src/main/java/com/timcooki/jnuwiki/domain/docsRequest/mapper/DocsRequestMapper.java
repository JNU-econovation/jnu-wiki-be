package com.timcooki.jnuwiki.domain.docsRequest.mapper;

import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import org.mapstruct.Mapper;

@Mapper
public interface DocsRequestMapper {
    DocsRequest toEntity(CreatedRequestWriteDTO createdRequestWriteDTO);

    DocsRequest modifiedDTOToEntity(ModifiedRequestWriteDTO modifiedRequestWriteDto);
}