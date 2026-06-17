package com.ironzone.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;
import java.util.List;

// =============================================
// CORSO - i corsi disponibili in palestra
// =============================================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corso")
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // es. "CrossFit", "Yoga", "Spinning"

    private String descrizione;

    // Trainer che tiene il corso
    private String trainer;

    // Giorno della settimana: "LUNEDI", "MARTEDI", ecc.
    private String giorno;

    private LocalTime orario;

    // Durata in minuti
    private Integer durata;

    // Quante persone possono prenotarsi
    private Integer postiDisponibili;

    // "ATTIVO", "SOSPESO"
    private String stato;

    // Lista di prenotazioni per questo corso
    @OneToMany(mappedBy = "corso", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;
}
