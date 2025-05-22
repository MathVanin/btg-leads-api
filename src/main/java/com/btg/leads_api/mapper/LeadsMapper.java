package com.btg.leads_api.mapper;

import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LeadsMapper {
    public abstract Leads requestDtoParaEntidade(
            LeadsRequestDto dto);

    public abstract LeadsResponseDto entidadeParaResponseDto(
            Leads leads);
}
