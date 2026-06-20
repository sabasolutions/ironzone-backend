package com.aurea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneResponse {
    private Long id;
    private LocalDate dataLezione;
    private LocalDateTime dataPrenotazione;
    private String stato;
    private Long iscrittoId;
    private String iscrittoNome;
    private String iscrittoCognome;
    private Long corsoId;
    private String corsoNome;
}

