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
        return ResponseEntity.ok(corsoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorsoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(corsoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CorsoResponse> create(@RequestBody CorsoRequest richiesta) {
        return ResponseEntity.ok(corsoService.create(richiesta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsoResponse> update(
            @PathVariable Long id, @RequestBody CorsoRequest richiesta) {
        return ResponseEntity.ok(corsoService.update(id, richiesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corsoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
