package com.aurea.controller;

import com.aurea.dto.IscrittoRequest;
import com.aurea.dto.IscrittoResponse;
import com.aurea.service.IscrittoService;
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
    public ResponseEntity<List<IscrittoResponse>> getAll(
            @RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(iscrittoService.find(search));
        }
        return ResponseEntity.ok(iscrittoService.getAll());
    }

    // GET /api/iscritti/1 → iscritto con id 1
    @GetMapping("/{id}")
    public ResponseEntity<IscrittoResponse> getPerId(@PathVariable Long id) {
        return ResponseEntity.ok(iscrittoService.getById(id));
    }

    // POST /api/iscritti → crea nuovo iscritto
    @PostMapping
    public ResponseEntity<IscrittoResponse> create(@RequestBody IscrittoRequest richiesta) {
        return ResponseEntity.ok(iscrittoService.create(richiesta));
    }

    // PUT /api/iscritti/1 → modifica iscritto con id 1
    @PutMapping("/{id}")
    public ResponseEntity<IscrittoResponse> update(
            @PathVariable Long id, @RequestBody IscrittoRequest richiesta) {
        return ResponseEntity.ok(iscrittoService.update(id, richiesta));
    }

    // DELETE /api/iscritti/1 → elimina iscritto con id 1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iscrittoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
