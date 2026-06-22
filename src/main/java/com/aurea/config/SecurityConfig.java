package com.aurea.config;

import com.aurea.repository.UtenteRepository;
import com.aurea.security.JwtFilter;
import com.aurea.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

// =============================================
// SECURITY CONFIG
// Configura Spring Security: chi può accedere a cosa
// =============================================
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UtenteRepository utenteRepository;
    private final JwtUtil jwtUtil;

    @Value("${cors.allowed-origins}")
    private String allowedOriginsRaw;


    // Carica l'utente dal DB dato lo username
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> utenteRepository.findByUsername(username)
                .map(utente -> org.springframework.security.core.userdetails.User.builder()
                        .username(utente.getUsername())
                        .password(utente.getPassword())
                        .roles(utente.getRuolo())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }

    // Crea il JWT Filter bean per evitare dipendenze circolari
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil, userDetailsService());
    }

    // Encoder per le password (BCrypt è lo standard sicuro)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Provider che usa il nostro UserDetailsService e il nostro PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AuthenticationManager: gestisce il processo di autenticazione
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configurazione principale della sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
            // Disabilita CSRF (non serve con JWT)
            .csrf(csrf -> csrf.disable())

            // Configura CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Definisce chi può accedere a cosa
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/corsi").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contatti").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
            // Usa sessioni stateless (nessuna sessione server-side, solo JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Permette iframe per la console H2
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            .authenticationProvider(authenticationProvider())

            // Aggiunge il nostro JWT filter prima del filtro standard
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Splitta la stringa in lista
        List<String> origins = List.of(allowedOriginsRaw.split(","));
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
