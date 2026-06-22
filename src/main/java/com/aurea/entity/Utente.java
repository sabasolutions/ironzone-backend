package com.aurea.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// =============================================
// UTENTE - chi accede al gestionale
// =============================================
// Questa classe mappa la tabella "utente" nel database
// @Data di Lombok genera automaticamente getter, setter, equals, hashCode, toString
// @Entity dice a JPA che questa classe è una tabella del DB
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // salvata criptata con BCrypt

    @Column(nullable = false)
    private String ruolo; // "ADMIN" o "RECEPTIONIST"

    private String nome;
    private String cognome;
}
