package com.ironzone.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

// =============================================
// ISCRITTO - i clienti della palestra
// =============================================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "iscritto")
public class Iscritto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;

    private LocalDate dataNascita;

    private LocalDate dataIscrizione;

    // "ATTIVO", "SOSPESO", "SCADUTO"
    private String stato;

    // Un iscritto può avere più abbonamenti nel tempo
    // mappedBy = "iscritto" dice a JPA che la relazione è gestita dal campo "iscritto" in Abbonamento
    @OneToMany(mappedBy = "iscritto", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamenti;

    // Un iscritto può prenotare più corsi
    @OneToMany(mappedBy = "iscritto", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;
}
