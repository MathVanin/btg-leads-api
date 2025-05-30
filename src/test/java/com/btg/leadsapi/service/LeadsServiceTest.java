package com.btg.leadsapi.service;

import com.btg.leadsapi.domain.Leads;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import com.btg.leadsapi.exception.DadoCadastradoEx;
import com.btg.leadsapi.exception.DadoInvalidoEx;
import com.btg.leadsapi.exception.NotFoundEx;
import com.btg.leadsapi.mapper.LeadsMapper;
import com.btg.leadsapi.repository.LeadsRepository;
import com.btg.leadsapi.service.impl.LeadsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LeadsServiceTest {
    @Mock
    private LeadsRepository leadsRepository;
    @Mock
    private LeadsMapper leadsMapper;
    @InjectMocks
    private LeadsServiceImpl leadsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validarDados_DeveLancarExcecaoParaNomeInvalido() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Jo");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("12345678909");
        assertThrows(DadoInvalidoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DeveLancarExcecaoParaEmailCadastrado() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("12345678909");
        when(leadsRepository.findByEmail(anyString())).thenReturn(Optional.of(new Leads()));
        assertThrows(DadoCadastradoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DeveLancarExcecaoParaTelefoneCadastrado() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("12345678909");
        when(leadsRepository.findByTelefone(anyString())).thenReturn(Optional.of(new Leads()));
        assertThrows(DadoCadastradoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DeveLancarExcecaoParaCpfCadastrado() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("12345678909");
        when(leadsRepository.findByCpf(anyString())).thenReturn(Optional.of(new Leads()));
        assertThrows(DadoCadastradoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DeveLancarExcecaoParaTelefoneInvalido() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("123");
        when(dto.cpf()).thenReturn("12345678909");
        assertThrows(DadoInvalidoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DeveLancarExcecaoParaEmailInvalido() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("a@b");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("12345678909");
        assertThrows(DadoInvalidoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DeveLancarExcecaoParaCpfInvalido() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("cpf_invalido");
        assertThrows(DadoInvalidoEx.class,
                () -> leadsService.validarDados(dto));
    }

    @Test
    void validarDados_DevePassarParaDadosValidos() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        when(dto.nome()).thenReturn("Joao");
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.telefone()).thenReturn("11999999999");
        when(dto.cpf()).thenReturn("12345678909");
        when(leadsRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(leadsRepository.findByTelefone(anyString())).thenReturn(Optional.empty());
        when(leadsRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> leadsService.validarDados(dto));
    }

    @Test
    void mapearParaEntidade_DeveChamarMapper() {
        LeadsRequestDto dto = mock(LeadsRequestDto.class);
        Leads lead = new Leads();
        when(leadsMapper.requestDtoParaEntidade(dto)).thenReturn(lead);
        Leads result = leadsService.mapearParaEntidade(dto);
        assertEquals(lead, result);
    }

    @Test
    void salvarLead_DeveChamarRepository() {
        Leads lead = new Leads();
        when(leadsRepository.save(lead)).thenReturn(lead);
        Leads result = leadsService.salvarLead(lead);
        assertEquals(lead, result);
    }

    @Test
    void mapearParaResponse_DeveChamarMapper() {
        Leads lead = new Leads();
        LeadsResponseDto dto = mock(LeadsResponseDto.class);
        when(leadsMapper.entidadeParaResponseDto(lead)).thenReturn(dto);
        LeadsResponseDto result =
                leadsService.mapearParaResponse(lead);
        assertEquals(dto, result);
    }

    @Test
    void mapearParaResponseList_DeveChamarMapperParaCadaLead() {
        UUID uuid = UUID.randomUUID();
        Leads lead1 = new Leads(1L, uuid, "Lead 1",
                "lead1@lead.com", "11999999999",
                "12345678901",
                LocalDate.now().atStartOfDay());
        Leads lead2 = new Leads(2L, UUID.randomUUID(),
                "Lead 2", "lead2@lead.com", "11999999998"
                , "12345678902",
                LocalDate.now().atStartOfDay());
        LeadsResponseDto dto1 =
                mock(LeadsResponseDto.class);
        LeadsResponseDto dto2 =
                mock(LeadsResponseDto.class);
        when(leadsMapper.entidadeParaResponseDto(lead1)).thenReturn(dto1);
        when(leadsMapper.entidadeParaResponseDto(lead2)).thenReturn(dto2);
        List<LeadsResponseDto> result =
                leadsService.mapearParaResponse(List.of(lead1, lead2));
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    void listarLeads_DeveRetornarPagina() {
        Pageable pageable = mock(Pageable.class);
        Page<Leads> page =
                new PageImpl<>(List.of(new Leads()));
        when(leadsRepository.findWithFilters(any(), any()
                , any(), any(), any(), any(), any())).thenReturn(page);
        Page<Leads> result =
                leadsService.listarLeads(pageable,
                        UUID.randomUUID(), "nome", "email"
                        , "telefone", "cpf",
                        LocalDate.now());
        assertEquals(page, result);
    }

    @Test
    void listarLeads_DeveLancarNotFoundExQuandoVazio() {
        Pageable pageable = mock(Pageable.class);
        Page<Leads> page = new PageImpl<>(List.of());
        when(leadsRepository.findWithFilters(any(), any()
                , any(), any(), any(), any(), any())).thenReturn(page);
        assertThrows(NotFoundEx.class,
                () -> leadsService.listarLeads(pageable,
                        UUID.randomUUID(), "nome", "email"
                        , "telefone", "cpf",
                        LocalDate.now()));
    }
}
