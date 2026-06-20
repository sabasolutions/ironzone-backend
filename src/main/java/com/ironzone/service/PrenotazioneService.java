package com.aurea.service;

import com.ironzone.dto.PrenotazioneRequest;
import com.ironzone.dto.PrenotazioneResponse;
import com.ironzone.entity.Iscritto;
import com.ironzone.entity.Corso;
import com.ironzone.entity.Prenotazione;
import com.ironzone.repository.IscrittoRepository;
import com.ironzone.repository.CorsoRepository;
import com.ironzone.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepo;
    private final IscrittoRepository iscrittoRepo;
    private final CorsoRepository corsoRepo;

    public List<PrenotazioneResponse> getTutte() {
        return prenotazioneRepo.findAll().stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    public List<PrenotazioneResponse> getPerIscritto(Long iscrittoId) {
        return prenotazioneRepo.findByIscrittoId(iscrittoId).stream()
                .map(this::convertiInResponse).collect(Collectors.toList());
    }

    public List<PrenotazioneResponse> getOggi() {
        return prenotazioneRepo.findByDataLezione(LocalDate.now()).stream()
                .map(this::convertiInResponse).collect(Collectors.toList());
    }

    public PrenotazioneResponse crea(PrenotazioneRequest richiesta) {
        if (prenotazioneRepo.existsByIscrittoIdAndCorsoIdAndDataLezione(
                richiesta.getIscrittoId(), richiesta.getCorsoId(), richiesta.getDataLezione())) {
            throw new RuntimeException("Iscritto già prenotato per questo corso in questa data");
        }

        Iscritto iscritto = iscrittoRepo.findById(richiesta.getIscrittoId())
                .orElseThrow(() -> new RuntimeException("Iscritto non trovato"));
        Corso corso = corsoRepo.findById(richiesta.getCorsoId())
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));

        long postiOccupati = prenotazioneRepo.countByCorsoIdAndDataLezioneAndStato(
                corso.getId(), richiesta.getDataLezione(), "CONFERMATA");
        if (postiOccupati >= corso.getPostiDisponibili()) {
            throw new RuntimeException("Nessun posto disponibile per questo corso");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataLezione(richiesta.getDataLezione());
        prenotazione.setDataPrenotazione(LocalDateTime.now());
        prenotazione.setStato("CONFERMATA");
        prenotazione.setIscritto(iscritto);
        prenotazione.setCorso(corso);

        return convertiInResponse(prenotazioneRepo.save(prenotazione));
    }

    public void cancella(Long id) {
        Prenotazione p = prenotazioneRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
        p.setStato("CANCELLATA");
        prenotazioneRepo.save(p);
    }

    public PrenotazioneResponse convertiInResponse(Prenotazione p) {
        return new PrenotazioneResponse(
                p.getId(), p.getDataLezione(), p.getDataPrenotazione(), p.getStato(),
                p.getIscritto().getId(), p.getIscritto().getNome(), p.getIscritto().getCognome(),
                p.getCorso().getId(), p.getCorso().getNome()
        );
    }
}

