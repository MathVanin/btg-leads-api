package com.btg.leadsapi.mapper;

import com.btg.leadsapi.domain.Leads;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeadsMapper {

    Leads requestDtoParaEntidade(LeadsRequestDto dto);

    LeadsResponseDto entidadeParaResponseDto(Leads leads);
}
