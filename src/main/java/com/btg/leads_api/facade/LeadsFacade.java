package com.btg.leads_api.facade;

import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import com.btg.leads_api.service.LeadsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
        leadsService.validarNome(dto.nome());
        leadsService.validarEmail(dto.email());
        leadsService.validarTelefone(dto.telefone());
        leadsService.validarCpf(dto.cpf());
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
