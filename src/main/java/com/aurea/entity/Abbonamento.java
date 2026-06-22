package com.aurea.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

// =============================================
// ABBONAMENTO - piano di ogni iscritto
// =============================================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "abbonamento")
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo piano: "BASE", "PRO", "ELITE"
    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDate dataInizio;

    @Column(nullable = false)
    private LocalDate dataFine;

    // Prezzo pagato per questo abbonamento
    private BigDecimal prezzo;

    // "ATTIVO", "SCADUTO", "SOSPESO"
    private String stato;

    // Relazione con Iscritto - molti abbonamenti appartengono a un iscritto
    // @ManyToOne dice che molti abbonamenti → un iscritto
    // @JoinColumn specifica il nome della colonna chiave esterna nel DB
    @ManyToOne
    @JoinColumn(name = "iscritto_id", nullable = false)
    private Iscritto iscritto;
}
