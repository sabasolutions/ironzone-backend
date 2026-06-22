package com.aurea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IscrittoResponse {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate dataNascita;
    private LocalDate dataIscrizione;
    private String stato;
}

