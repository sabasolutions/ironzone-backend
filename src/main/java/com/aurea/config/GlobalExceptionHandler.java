package com.aurea.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

// =============================================
// GLOBAL EXCEPTION HANDLER
// Intercetta le eccezioni e restituisce risposte JSON leggibili
// Invece di stack trace HTML, il frontend riceve { "errore": "messaggio" }
// =============================================
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Credenziali errate al login
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("errore", "Username o password non corretti"));
    }

    // Errori di business (es. email già esistente, posto non disponibile)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errore", e.getMessage()));
    }

    // Qualsiasi altro errore imprevisto
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("errore", "Errore interno del server"));
    }
}
