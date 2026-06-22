package com.aurea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneRequest {
    private LocalDate dataLezione;
    private Long iscrittoId;
    private Long corsoId;
}

