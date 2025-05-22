package com.btg.leads_api.service;

import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface LeadsService {
    void validarNome(String nome);

    void validarEmail(String email);

    void validarTelefone(String telefone);

    void validarCpf(String cpf);

    Leads mapearParaEntidade(LeadsRequestDto dto);

    Leads salvarLead(Leads lead);

    LeadsResponseDto mapearParaResponse(Leads lead);
}
