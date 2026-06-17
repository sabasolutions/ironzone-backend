package com.ironzone.controller;

import com.ironzone.dto.*;
import com.ironzone.security.JwtUtil;
import com.ironzone.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// =============================================
// AUTH CONTROLLER
// Gestisce login e generazione token JWT
// =============================================
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final com.ironzone.repository.UtenteRepository utenteRepo;

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest richiesta) {
        // Verifica username e password
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(richiesta.getUsername(), richiesta.getPassword())
        );

        // Genera il token JWT
        UserDetails utente = userDetailsService.loadUserByUsername(richiesta.getUsername());
        String token = jwtUtil.generaToken(utente.getUsername());

        // Prende i dati extra dell'utente dal DB
        var utenteDb = utenteRepo.findByUsername(richiesta.getUsername()).orElseThrow();

        return ResponseEntity.ok(new LoginResponse(
                token,
                utenteDb.getUsername(),
                utenteDb.getRuolo(),
                utenteDb.getNome(),
                utenteDb.getCognome()
        ));
    }
}

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

// =============================================
// DASHBOARD CONTROLLER
// =============================================
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
class DashboardController {

    private final DashboardService dashboardService;

    // GET /api/dashboard → statistiche generali
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}
