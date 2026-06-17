package com.ironzone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// =============================================
// JWT FILTER
// Intercetta ogni richiesta HTTP e controlla se c'è un token valido
// Se il token è valido, imposta l'utente nel contesto di sicurezza
// =============================================
@Component
@Lazy
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest richiesta,
            HttpServletResponse risposta,
            FilterChain catena
    ) throws ServletException, IOException {

        // Prende l'header "Authorization" dalla richiesta
        // Es: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        String headerAutorizzazione = richiesta.getHeader("Authorization");

        String token = null;
        String username = null;

        // Controlla che l'header esista e inizi con "Bearer "
        if (headerAutorizzazione != null && headerAutorizzazione.startsWith("Bearer ")) {
            // Estrae solo il token (senza "Bearer ")
            token = headerAutorizzazione.substring(7);
            try {
                username = jwtUtil.estraiUsername(token);
            } catch (Exception e) {
                // Token malformato, ignoriamo
            }
        }

        // Se abbiamo uno username e non c'è già un utente autenticato
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails utente = userDetailsService.loadUserByUsername(username);

            // Se il token è valido, autentica l'utente
            if (jwtUtil.isTokenValido(token)) {
                UsernamePasswordAuthenticationToken autenticazione =
                        new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());
                autenticazione.setDetails(new WebAuthenticationDetailsSource().buildDetails(richiesta));
                SecurityContextHolder.getContext().setAuthentication(autenticazione);
            }
        }

        // Passa la richiesta al prossimo filtro
        catena.doFilter(richiesta, risposta);
    }
}
