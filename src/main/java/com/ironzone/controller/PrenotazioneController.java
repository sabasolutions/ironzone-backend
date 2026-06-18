package com.ironzone.controller;

import com.ironzone.dto.*;
import com.ironzone.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// =============================================
// PRENOTAZIONE CONTROLLER
// =============================================
@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getTutte() {
        return ResponseEntity.ok(prenotazioneService.getTutte());
    }

    @GetMapping("/oggi")
    public ResponseEntity<List<PrenotazioneResponse>> getOggi() {
        return ResponseEntity.ok(prenotazioneService.getOggi());
    }

    @GetMapping("/iscritto/{iscrittoId}")
    public ResponseEntity<List<PrenotazioneResponse>> getPerIscritto(@PathVariable Long iscrittoId) {
        return ResponseEntity.ok(prenotazioneService.getPerIscritto(iscrittoId));
    }

    @PostMapping
    public ResponseEntity<PrenotazioneResponse> crea(@RequestBody PrenotazioneRequest richiesta) {
        return ResponseEntity.ok(prenotazioneService.crea(richiesta));
    }

    // PATCH invece di DELETE perché non eliminiamo, cambiamo stato a "CANCELLATA"
    @PatchMapping("/{id}/cancella")
    public ResponseEntity<Void> cancella(@PathVariable Long id) {
        prenotazioneService.cancella(id);
        return ResponseEntity.noContent().build();
    }
}

