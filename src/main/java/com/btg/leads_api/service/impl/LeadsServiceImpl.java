package com.btg.leads_api.service.impl;

import br.com.caelum.stella.validation.CPFValidator;
import com.btg.leads_api.domain.Leads;
import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import com.btg.leads_api.mapper.LeadsMapper;
import com.btg.leads_api.repository.LeadsRepository;
import com.btg.leads_api.service.LeadsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.btg.leads_api.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class LeadsServiceImpl implements LeadsService {

    private final LeadsRepository leadsRepository;
    private final LeadsMapper leadsMapper;

    @Override
    public void validarNome(String nome) {
        if (nome.length() < 3)
            throw new IllegalArgumentException(NOME_INVALIDO);
    }

    @Override
    public void validarEmail(String email) {
        if (email.length() < 5)
            throw new IllegalArgumentException(EMAIL_INVALIDO);
        leadsRepository.findByEmail(email)
                .ifPresent(lead -> {
                    throw new IllegalArgumentException(EMAIL_JA_CADASTRADO);
                });
    }

    @Override
    public void validarTelefone(String telefone) {
        if (telefone.length() < 10)
            throw new IllegalArgumentException(TELEFONE_INVALIDO);
        leadsRepository.findByTelefone(telefone)
                .ifPresent(lead -> {
                    throw new IllegalArgumentException(TELEFONE_JA_CADASTRADO);
                });
    }

    @Override
    public void validarCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(CPF_INVALIDO);
        }
        leadsRepository.findByCpf(cpf)
                .ifPresent(lead -> {
                    throw new IllegalArgumentException(CPF_JA_CADASTRADO);
                });
    }

    @Override
    public Leads mapearParaEntidade(LeadsRequestDto dto) {
        return leadsMapper.requestDtoParaEntidade(dto);
    }

    @Override
    public Leads salvarLead(Leads lead) {
        return leadsRepository.save(lead);
    }

    @Override
    public LeadsResponseDto mapearParaResponse(Leads lead) {
        return leadsMapper.entidadeParaResponseDto(lead);
    }
}
