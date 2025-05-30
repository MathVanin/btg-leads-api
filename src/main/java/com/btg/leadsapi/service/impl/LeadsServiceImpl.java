package com.btg.leadsapi.service.impl;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.btg.leadsapi.domain.Leads;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import com.btg.leadsapi.exception.DadoCadastradoEx;
import com.btg.leadsapi.exception.DadoInvalidoEx;
import com.btg.leadsapi.exception.NotFoundEx;
import com.btg.leadsapi.mapper.LeadsMapper;
import com.btg.leadsapi.repository.LeadsRepository;
import com.btg.leadsapi.service.LeadsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.btg.leadsapi.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class LeadsServiceImpl implements LeadsService {

    private final LeadsRepository leadsRepository;
    private final LeadsMapper leadsMapper;

    @Override
    public void validarDados(LeadsRequestDto leadsRequestDto) {
        validarNome(leadsRequestDto.nome());
        validarEmail(leadsRequestDto.email());
        validarTelefone(leadsRequestDto.telefone());
        validarCpf(leadsRequestDto.cpf());
    }

    private void validarNome(String nome) {
        if (nome.length() < 3)
            throw new DadoInvalidoEx(NOME);
    }

    private void validarEmail(String email) {
        if (email.length() < 5)
            throw new DadoInvalidoEx(EMAIL);
        leadsRepository.findByEmail(email)
                .ifPresent(lead -> {
                    throw new DadoCadastradoEx(EMAIL);
                });
    }

    private void validarTelefone(String telefone) {
        if (telefone.length() < 10)
            throw new DadoInvalidoEx(TELEFONE);
        leadsRepository.findByTelefone(telefone)
                .ifPresent(lead -> {
                    throw new DadoCadastradoEx(TELEFONE);
                });
    }

    private void validarCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            throw new DadoInvalidoEx(CPF);
        }
        leadsRepository.findByCpf(cpf)
                .ifPresent(lead -> {
                    throw new DadoCadastradoEx(CPF);
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

    @Override
    public List<LeadsResponseDto> mapearParaResponse(List<Leads> lead) {
        return lead.stream().map(this::mapearParaResponse).toList();
    }

    @Override
    public Page<Leads> listarLeads(Pageable pageable,
                                   UUID uuid, String nome
            , String email, String telefone,
                                   String cpf,
                                   LocalDate dataCadastro) {
        Page<Leads> leadsPage =
                leadsRepository.findWithFilters(pageable,
                        uuid, nome, email, telefone, cpf,
                        dataCadastro);
        if (leadsPage.isEmpty())
            throw new NotFoundEx("Nenhum lead encontrado " +
                    "com os filtros.");
        return leadsPage;
    }
}
