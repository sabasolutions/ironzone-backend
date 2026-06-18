package com.ironzone.controller;

import com.ironzone.dto.IscrittoRequest;
import com.ironzone.dto.IscrittoResponse;
import com.ironzone.service.IscrittoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// =============================================
// ISCRITTO CONTROLLER
// =============================================
@RestController
@RequestMapping("/api/iscritti")
@RequiredArgsConstructor
class IscrittoController {

    private final IscrittoService iscrittoService;

    // GET /api/iscritti → tutti gli iscritti
    // GET /api/iscritti?cerca=mario → cerca per nome
    @GetMapping
    public ResponseEntity<List<IscrittoResponse>> getTutti(
            @RequestParam(required = false) String cerca) {
        if (cerca != null && !cerca.isBlank()) {
            return ResponseEntity.ok(iscrittoService.cerca(cerca));
        }
        return ResponseEntity.ok(iscrittoService.getTutti());
    }

    // GET /api/iscritti/1 → iscritto con id 1
    @GetMapping("/{id}")
    public ResponseEntity<IscrittoResponse> getPerId(@PathVariable Long id) {
        return ResponseEntity.ok(iscrittoService.getPerId(id));
    }

    // POST /api/iscritti → crea nuovo iscritto
    @PostMapping
    public ResponseEntity<IscrittoResponse> crea(@RequestBody IscrittoRequest richiesta) {
        return ResponseEntity.ok(iscrittoService.crea(richiesta));
    }

    // PUT /api/iscritti/1 → modifica iscritto con id 1
    @PutMapping("/{id}")
    public ResponseEntity<IscrittoResponse> modifica(
            @PathVariable Long id, @RequestBody IscrittoRequest richiesta) {
        return ResponseEntity.ok(iscrittoService.modifica(id, richiesta));
    }

    // DELETE /api/iscritti/1 → elimina iscritto con id 1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        iscrittoService.elimina(id);
        return ResponseEntity.noContent().build();
    }
}
