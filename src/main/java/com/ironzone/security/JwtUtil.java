package com.ironzone.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

// =============================================
// JWT UTILITY
// Classe che gestisce la creazione e validazione dei token JWT
// Un token JWT è una stringa firmata che contiene info sull'utente
// Es: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9.xyz"
// =============================================
@Component
public class JwtUtil {

    // Legge i valori da application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Crea la chiave di firma dal secret
    private Key getChiave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Genera un nuovo token per un utente
    public String generaToken(String username) {
        return Jwts.builder()
                .setSubject(username)                          // chi è l'utente
                .setIssuedAt(new Date())                       // quando è stato creato
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // quando scade
                .signWith(getChiave())                         // firma con la chiave segreta
                .compact();
    }

    // Estrae lo username dal token
    public String estraiUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChiave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica se il token è valido e non scaduto
    public boolean isTokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getChiave())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token non valido, scaduto o malformato
            return false;
        }
    }
}
