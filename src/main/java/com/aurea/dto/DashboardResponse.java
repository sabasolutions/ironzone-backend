package com.aurea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totaleIscritti;
    private Long iscrittiAttivi;
    private Long abbonамentiInScadenza;
    private Long corsiOggi;
    private Long prenotazioniOggi;
}

