package com.btg.leads_api.facade;

import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import com.btg.leads_api.service.LeadsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeadsFacade {
    private final LeadsService leadsService;

    public LeadsResponseDto criarLead(LeadsRequestDto dto) {
        leadsService.validarNome(dto.getNome());
        leadsService.validarEmail(dto.getEmail());
        leadsService.validarTelefone(dto.getTelefone());
        leadsService.validarCpf(dto.getCpf());
        Leads lead = leadsService.mapearParaEntidade(dto);
        lead = leadsService.salvarLead(lead);
        return leadsService.mapearParaResponse(lead);
    }
}
