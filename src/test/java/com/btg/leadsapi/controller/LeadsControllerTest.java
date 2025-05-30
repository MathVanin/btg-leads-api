package com.btg.leadsapi.controller;

import com.btg.leadsapi.LeadsController;
import com.btg.leadsapi.config.RateLimiter;
import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import com.btg.leadsapi.facade.LeadsFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static com.btg.leadsapi.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(LeadsController.class)
class LeadsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LeadsController leadsController;
    @MockBean
    private LeadsFacade leadsFacade;
    @MockBean
    private RateLimiter rateLimiter;
    private RateLimiter.RequestCounter requestCounter;

    @BeforeEach
    void setUp() {
        requestCounter = mock(RateLimiter.RequestCounter.class);
        // Garante que qualquer chamada, com qualquer parâmetro, retorna sempre o mesmo mock
        doReturn(requestCounter).when(rateLimiter).getRequestCounter(any());
        doReturn(requestCounter).when(rateLimiter).getRequestCounter(anyString());
        doReturn(requestCounter).when(rateLimiter).getRequestCounter(isNull());
        when(requestCounter.getCount()).thenReturn(0);
    }

    @Test
    void criarLeads_DeveRetornarLeadCriado() {
        LeadsRequestDto requestDto =
                mock(LeadsRequestDto.class);
        LeadsResponseDto responseDto =
                new LeadsResponseDto(UUID.randomUUID().toString(),
                        "Teste", "teste@teste.com",
                        "11234567894",
                        LocalDate.now().toString());
        when(leadsFacade.criarLead(requestDto)).thenReturn(responseDto);

        ResponseEntity<LeadsResponseDto> response =
                leadsController.criarLeads(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(leadsFacade, times(1)).criarLead(requestDto);
    }

    @Test
    void listarLeads_DeveRetornarPaginaDeLeads() {
        Pageable pageable = PageRequest.of(0, 10);
        LeadsResponseDto responseDto =
                new LeadsResponseDto(UUID.randomUUID().toString(),
                        "Teste", "teste@teste.com",
                        "11234567894",
                        LocalDate.now().toString());
        Page<LeadsResponseDto> page =
                new PageImpl<>(Collections.singletonList(responseDto));
        when(leadsFacade.listarLeads(pageable, null, null
                , null, null, null, null)).thenReturn(page);

        Page<LeadsResponseDto> result =
                leadsController.listarLeads(pageable,
                        null, null, null, null, null, null);

        assertEquals(1, result.getTotalElements());
        verify(leadsFacade, times(1)).listarLeads(pageable, null, null, null, null, null, null);
    }

    @Test
    void listarLeads_ComFiltros_DeveChamarFacadeComParametros() {
        Pageable pageable = PageRequest.of(0, 5);
        LeadsResponseDto leadsResponseDto =
                new LeadsResponseDto(UUID_VALIDO.toString(),
                        NOME_VALIDO, EMAIL_VALIDO,
                        TELEFONE_VALIDO,
                        DATA_CADASTRO.toString());
        Page<LeadsResponseDto> page =
                new PageImpl<>(Collections.singletonList(leadsResponseDto));
        when(leadsFacade.listarLeads(pageable,
                UUID_VALIDO, NOME_VALIDO
                , EMAIL_VALIDO, TELEFONE_VALIDO,
                CPF_VALIDO, DATA_CADASTRO)).thenReturn(page);

        Page<LeadsResponseDto> result =
                leadsController.listarLeads(pageable,
                        UUID_VALIDO, NOME_VALIDO,
                        EMAIL_VALIDO, TELEFONE_VALIDO,
                        CPF_VALIDO,
                        DATA_CADASTRO);

        assertNotNull(result);
        verify(leadsFacade, times(1))
                .listarLeads(pageable, UUID_VALIDO,
                        NOME_VALIDO, EMAIL_VALIDO,
                        TELEFONE_VALIDO, CPF_VALIDO,
                        DATA_CADASTRO);
    }

    @Test
    void endpoints_DeveExigirAutenticacao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "/leads"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void rateLimiting_DeveBloquearAposExcesso() throws Exception {
        when(leadsFacade.listarLeads(any(), any(), any(),
                any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        when(requestCounter.getCount()).thenReturn(10);
        // Simula limite atingido
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get(
                            "/leads").with(csrf()))
                    .andExpect(MockMvcResultMatchers.status().isTooManyRequests());
        }
    }


}
