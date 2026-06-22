package com.aurea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbbonamentoRequest {
    private String tipo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private BigDecimal prezzo;
    private Long iscrittoId;
}

