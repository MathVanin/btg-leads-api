package com.btg.leads_api.controller;

import com.btg.leads_api.dto.LeadsRequestDto;
import com.btg.leads_api.dto.LeadsResponseDto;
import com.btg.leads_api.facade.LeadsFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leads")
@RequiredArgsConstructor
public class LeadsController {
    private final LeadsFacade leadsFacade;

    //TODO Testar o @Valid se está funcionando OK
    @PostMapping
    public ResponseEntity<LeadsResponseDto> criarLeads(
            @Valid @RequestBody LeadsRequestDto dto) {
        return ResponseEntity.ok(leadsFacade.criarLead(dto));
    }
}
