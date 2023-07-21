package com.timcooki.jnuwiki.domain.docsRequest.mapper;

import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.ModifiedFindAllReqDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.ModifiedFindByIdReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocsRequestMapper {
    DocsRequest toEntity(CreatedRequestWriteDTO createdRequestWriteDTO);

    DocsRequest modifiedDTOToEntity(ModifiedRequestWriteDTO modifiedRequestWriteDto);

    ModifiedFindByIdReqDTO toDTO(DocsRequest docsRequest);
}