package com.btg.leadsapi;

import com.btg.leadsapi.dto.LeadsRequestDto;
import com.btg.leadsapi.dto.LeadsResponseDto;
import com.btg.leadsapi.exception.ErrorResponseDto;
import com.btg.leadsapi.facade.LeadsFacade;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/leads")
@RequiredArgsConstructor
@Tag(name = "Leads", description = "Gerenciamento de Leads")
public class LeadsController {

    private final LeadsFacade leadsFacade;

    @Operation(summary = "Cria um novo lead", description = "Cria um novo lead com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lead criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeadsResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflito de dados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<LeadsResponseDto> criarLeads(
            @Valid @RequestBody LeadsRequestDto dto) {
        return ResponseEntity.ok(leadsFacade.criarLead(dto));
    }

    @Operation(summary = "Lista leads com filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de leads retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeadsResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum lead encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public Page<LeadsResponseDto> listarLeads(@PageableDefault Pageable pageable,
                                              @RequestParam(required = false) UUID uuid,
                                              @RequestParam(required = false) String nome,
                                              @RequestParam(required = false) String email,
                                              @RequestParam(required = false) String telefone,
                                              @RequestParam(required = false) String cpf,
                                              @RequestParam(required = false) LocalDate dataCadastro) {
        return leadsFacade.listarLeads(pageable, uuid,
                nome, email, telefone, cpf, dataCadastro);
    }
}
