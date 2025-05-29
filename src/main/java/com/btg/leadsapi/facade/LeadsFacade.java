package com.btg.leadsapi.facade;

import com.btg.leadsapi.domain.Leads;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import com.btg.leadsapi.service.LeadsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LeadsFacade {
    private final LeadsService leadsService;

    public LeadsResponseDto criarLead(LeadsRequestDto dto) {
        leadsService.validarDados(dto);
        Leads lead = leadsService.mapearParaEntidade(dto);
        lead = leadsService.salvarLead(lead);
        return leadsService.mapearParaResponse(lead);
    }

    public Page<LeadsResponseDto> listarLeads(Pageable pageable,
                                              UUID uuid,
                                              String nome,
                                              String email,
                                              String telefone,
                                              String cpf,
                                              LocalDate dataCadastro) {
        Page<Leads> leads = leadsService.listarLeads(pageable, uuid, nome, email, telefone, cpf, dataCadastro);
        List<LeadsResponseDto> leadsResponse = leadsService.mapearParaResponse(leads.getContent());
        return new PageImpl<>(leadsResponse, pageable, leads.getTotalElements());
    }
}
