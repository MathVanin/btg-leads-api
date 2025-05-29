package com.btg.leadsapi.service;

import com.btg.leadsapi.domain.Leads;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface LeadsService {
    void validarDados(LeadsRequestDto leadsRequestDto);

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
