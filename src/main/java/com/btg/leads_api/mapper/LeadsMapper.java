package com.btg.leads_api.mapper;

import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeadsMapper {

    Leads requestDtoParaEntidade(LeadsRequestDto dto);

    LeadsResponseDto entidadeParaResponseDto(Leads leads);
}
