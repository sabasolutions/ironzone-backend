package com.ironzone.controller;

import com.ironzone.dto.AbbonamentoRequest;
import com.ironzone.dto.AbbonamentoResponse;
import com.ironzone.service.AbbonamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// =============================================
// ABBONAMENTO CONTROLLER
// =============================================
@RestController
@RequestMapping("/api/abbonamenti")
@RequiredArgsConstructor
class AbbonamentoController {

    private final AbbonamentoService abbonamentoService;

    @GetMapping
    public ResponseEntity<List<AbbonamentoResponse>> getTutti() {
        return ResponseEntity.ok(abbonamentoService.getTutti());
    }

    @GetMapping("/iscritto/{iscrittoId}")
    public ResponseEntity<List<AbbonamentoResponse>> getPerIscritto(@PathVariable Long iscrittoId) {
        return ResponseEntity.ok(abbonamentoService.getPerIscritto(iscrittoId));
    }

    @GetMapping("/in-scadenza")
    public ResponseEntity<List<AbbonamentoResponse>> getInScadenza() {
        return ResponseEntity.ok(abbonamentoService.getInScadenza());
    }

    @PostMapping
    public ResponseEntity<AbbonamentoResponse> crea(@RequestBody AbbonamentoRequest richiesta) {
        return ResponseEntity.ok(abbonamentoService.crea(richiesta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbbonamentoResponse> modifica(
            @PathVariable Long id, @RequestBody AbbonamentoRequest richiesta) {
        return ResponseEntity.ok(abbonamentoService.modifica(id, richiesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        abbonamentoService.elimina(id);
        return ResponseEntity.noContent().build();
    }
}
