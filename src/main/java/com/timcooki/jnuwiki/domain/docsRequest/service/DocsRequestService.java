package com.timcooki.jnuwiki.domain.docsRequest.service;

import com.timcooki.jnuwiki.domain.docs.dto.DocsCreateDto;
import com.timcooki.jnuwiki.domain.docs.dto.DocsUpdateInfoDto;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.CreatedRequestWriteDto;
import com.timcooki.jnuwiki.domain.docsRequest.dto.request.ModifiedRequestWriteDto;
import com.timcooki.jnuwiki.domain.docsRequest.entity.DocsRequest;
import com.timcooki.jnuwiki.domain.member.dto.response.admin.CreatedRequestFindAllDto;
import com.timcooki.jnuwiki.domain.member.dto.response.admin.CreatedRequestFindByIdDto;
import com.timcooki.jnuwiki.domain.member.dto.response.admin.ModifiedRequestFindAllDto;
import com.timcooki.jnuwiki.domain.member.dto.response.admin.ModifiedRequestFindByIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DocsRequestService {

    public DocsRequest createModifiedRequest(ModifiedRequestWriteDto modifiedRequestWriteDto) {
    }

    public DocsRequest createCreatedRequest(CreatedRequestWriteDto createdRequestDto) {
    }
    
    public Page<ModifiedRequestFindAllDto> getModifiedRequestList(Pageable pageable) {
    }

    public Page<CreatedRequestFindAllDto> getCreatedRequestList(Pageable pageable) {
    }

    public ModifiedRequestFindByIdDto getOneModifiedRequest(String docsRequestId) {
    }

    public CreatedRequestFindByIdDto getOneCreatedRequest(String docsRequestId) {
    }

    public DocsCreateDto createDocsFromRequest(String docsRequestId) {
    }

    public DocsUpdateInfoDto updateDocsFromRequest(String docsRequestId) {
    }

    public void rejectRequest(String docsRequestId) {
    }

    public boolean hasRequest(String docsRequestId) {
    }
}
