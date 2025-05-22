package com.btg.leads_api.service;

import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface LeadsService {
    void validarNome(String nome);

    void validarEmail(String email);

    void validarTelefone(String telefone);

    void validarCpf(String cpf);

    Leads mapearParaEntidade(LeadsRequestDto dto);

    Leads salvarLead(Leads lead);

    LeadsResponseDto mapearParaResponse(Leads lead);

    List<LeadsResponseDto> mapearParaResponse(List<Leads> leads);

    Page<Leads> listarLeads(Pageable pageable,
                            UUID uuid,
                            String nome,
                            String email,
                            String telefone,
                            String cpf,
                            LocalDate dataCadastro);
}
