package com.ironzone.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

// =============================================
// PRENOTAZIONE - quale iscritto è iscritto a quale corso
// =============================================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prenotazione")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Data specifica della lezione prenotata
    @Column(nullable = false)
    private LocalDate dataLezione;

    // Quando è stata fatta la prenotazione
    private LocalDateTime dataPrenotazione;

    // "CONFERMATA", "CANCELLATA", "PRESENTE", "ASSENTE"
    private String stato;

    // Relazione con Iscritto
    @ManyToOne
    @JoinColumn(name = "iscritto_id", nullable = false)
    private Iscritto iscritto;

    // Relazione con Corso
    @ManyToOne
    @JoinColumn(name = "corso_id", nullable = false)
    private Corso corso;
}
