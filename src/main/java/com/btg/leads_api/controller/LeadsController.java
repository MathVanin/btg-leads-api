package com.btg.leads_api.controller;

import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import com.btg.leads_api.facade.LeadsFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/leads")
@RequiredArgsConstructor
public class LeadsController {
    private final LeadsFacade leadsFacade;

    @PostMapping
    public ResponseEntity<LeadsResponseDto> criarLeads(
            @Valid @RequestBody LeadsRequestDto dto) {
        return ResponseEntity.ok(leadsFacade.criarLead(dto));
    }

    @GetMapping
    public Page<LeadsResponseDto> listarLeads(@PageableDefault Pageable pageable,
                                              @RequestParam(required = false) UUID uuid,
                                              @RequestParam(required = false) String nome,
                                              @RequestParam(required = false) String email,
                                              @RequestParam(required = false) String telefone,
                                              @RequestParam(required = false) String cpf,
                                              @RequestParam(required = false) LocalDate dataCadastro) {
        return leadsFacade.listarLeads(pageable, uuid, nome, email, telefone, cpf, dataCadastro);
    }
}
