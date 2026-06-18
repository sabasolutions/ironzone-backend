package com.ironzone.controller;

import com.ironzone.dto.LoginRequest;
import com.ironzone.dto.LoginResponse;
import com.ironzone.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
