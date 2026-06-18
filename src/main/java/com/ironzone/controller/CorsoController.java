package com.ironzone.controller;

import com.ironzone.dto.CorsoRequest;
import com.ironzone.dto.CorsoResponse;
import com.ironzone.service.CorsoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// =============================================
// CORSO CONTROLLER
// =============================================
@RestController
@RequestMapping("/api/corsi")
@RequiredArgsConstructor
class CorsoController {

    private final CorsoService corsoService;

    @GetMapping
    public ResponseEntity<List<CorsoResponse>> getTutti() {
        return ResponseEntity.ok(corsoService.getTutti());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsoResponse> getPerId(@PathVariable Long id) {
        return ResponseEntity.ok(corsoService.getPerId(id));
    }

    @PostMapping
    public ResponseEntity<CorsoResponse> crea(@RequestBody CorsoRequest richiesta) {
        return ResponseEntity.ok(corsoService.crea(richiesta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsoResponse> modifica(
            @PathVariable Long id, @RequestBody CorsoRequest richiesta) {
        return ResponseEntity.ok(corsoService.modifica(id, richiesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        corsoService.elimina(id);
        return ResponseEntity.noContent().build();
    }
}
