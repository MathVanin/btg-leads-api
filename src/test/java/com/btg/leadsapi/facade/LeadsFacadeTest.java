package com.btg.leadsapi.facade;

import com.btg.leadsapi.domain.Leads;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import com.btg.leadsapi.exception.DadoInvalidoEx;
import com.btg.leadsapi.service.LeadsService;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LeadsFacadeTest {
    @Mock
    private LeadsService leadsService;
    @InjectMocks
    private LeadsFacade leadsFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarLead_DeveRetornarResponseDto() {
        LeadsRequestDto requestDto =
                mock(LeadsRequestDto.class);
        Leads lead = new Leads();
        LeadsResponseDto responseDto =
                mock(LeadsResponseDto.class);
        when(leadsService.mapearParaEntidade(requestDto)).thenReturn(lead);
        when(leadsService.salvarLead(lead)).thenReturn(lead);
        when(leadsService.mapearParaResponse(lead)).thenReturn(responseDto);

        LeadsResponseDto result =
                leadsFacade.criarLead(requestDto);
        assertEquals(responseDto, result);
        verify(leadsService).validarDados(requestDto);
        verify(leadsService).mapearParaEntidade(requestDto);
        verify(leadsService).salvarLead(lead);
        verify(leadsService).mapearParaResponse(lead);
    }

    @Test
    void criarLead_DevePropagarExcecaoDoService() {
        LeadsRequestDto requestDto =
                mock(LeadsRequestDto.class);
        doThrow(new DadoInvalidoEx("erro")).when(leadsService).validarDados(requestDto);
        assertThrows(DadoInvalidoEx.class,
                () -> leadsFacade.criarLead(requestDto));
        verify(leadsService).validarDados(requestDto);
    }

    @Test
    void listarLeads_DeveRetornarPaginaDeResponseDto() {
        Pageable pageable = mock(Pageable.class);
        UUID uuid = UUID.randomUUID();
        String nome = "nome";
        String email = "email";
        String telefone = "telefone";
        String cpf = "cpf";
        LocalDate dataCadastro = LocalDate.now();
        Leads lead = new Leads();
        LeadsResponseDto responseDto =
                mock(LeadsResponseDto.class);
        Page<Leads> leadsPage =
                new PageImpl<>(List.of(lead), pageable, 1);
        when(leadsService.listarLeads(pageable, uuid,
                nome, email, telefone, cpf, dataCadastro)).thenReturn(leadsPage);
        when(leadsService.mapearParaResponse(leadsPage.getContent())).thenReturn(List.of(responseDto));

        Page<LeadsResponseDto> result =
                leadsFacade.listarLeads(pageable, uuid,
                        nome, email, telefone, cpf,
                        dataCadastro);
        assertEquals(1, result.getTotalElements());
        assertEquals(responseDto,
                result.getContent().get(0));
        verify(leadsService).listarLeads(pageable, uuid,
                nome, email, telefone, cpf, dataCadastro);
        verify(leadsService).mapearParaResponse(leadsPage.getContent());
    }
}
