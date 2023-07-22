package com.timcooki.jnuwiki.domain.docsRequest.mapper;


import com.timcooki.jnuwiki.domain.docsRequest.dto.request.NewWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.EditWriteReqDTO;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.EditReadResDTO;
import com.timcooki.jnuwiki.domain.member.DTO.response.admin.NewReadResDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DocsRequestMapper {
    DocsRequest newDTOToEntity(NewWriteReqDTO newWriteReqDTO);
    DocsRequest editDTOToEntity(EditWriteReqDTO editWriteReqDto);
    EditReadResDTO editEntityToDTO(DocsRequest docsRequest);
    NewReadResDTO newEntityToDTO(DocsRequest docsRequest);
}