package com.aurea.service;

import com.ironzone.dto.DashboardResponse;
import com.ironzone.repository.IscrittoRepository;
import com.ironzone.repository.AbbonamentoRepository;
import com.ironzone.repository.CorsoRepository;
import com.ironzone.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IscrittoRepository iscrittoRepo;
    private final AbbonamentoRepository abbonamentoRepo;
    private final CorsoRepository corsoRepo;
    private final PrenotazioneRepository prenotazioneRepo;

    public DashboardResponse getDashboard() {
        LocalDate oggi = LocalDate.now();
        LocalDate traSettegiorni = oggi.plusDays(7);

        String giornoOggi = oggi.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
                .toUpperCase()
                .replace("À", "A")
                .replace("Ì", "I")
                .replace("È", "E");

        return new DashboardResponse(
                iscrittoRepo.count(),
                iscrittoRepo.countByStato("ATTIVO"),
                abbonamentoRepo.countByDataFineBetweenAndStato(oggi, traSettegiorni, "ATTIVO"),
                corsoRepo.countByGiornoAndStato(giornoOggi, "ATTIVO"),
                prenotazioneRepo.countByDataLezione(oggi)
        );
    }
}

