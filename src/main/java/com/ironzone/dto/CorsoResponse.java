package com.ironzone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorsoResponse {
    private Long id;
    private String nome;
    private String descrizione;
    private String trainer;
    private String giorno;
    private LocalTime orario;
    private Integer durata;
    private Integer postiDisponibili;
    private Integer postiOccupati;
    private String stato;
}

