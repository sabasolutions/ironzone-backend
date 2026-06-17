package com.ironzone.service;

import com.ironzone.dto.CorsoRequest;
import com.ironzone.dto.CorsoResponse;
import com.ironzone.entity.Corso;
import com.ironzone.repository.CorsoRepository;
import com.ironzone.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CorsoService {

    private final CorsoRepository corsoRepo;
    private final PrenotazioneRepository prenotazioneRepo;

    public List<CorsoResponse> getTutti() {
        return corsoRepo.findAll().stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    public CorsoResponse getPerId(Long id) {
        Corso corso = corsoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        return convertiInResponse(corso);
    }

    public CorsoResponse crea(CorsoRequest richiesta) {
        Corso corso = new Corso();
        corso.setNome(richiesta.getNome());
        corso.setDescrizione(richiesta.getDescrizione());
        corso.setTrainer(richiesta.getTrainer());
        corso.setGiorno(richiesta.getGiorno());
        corso.setOrario(richiesta.getOrario());
        corso.setDurata(richiesta.getDurata());
        corso.setPostiDisponibili(richiesta.getPostiDisponibili());
        corso.setStato(richiesta.getStato() != null ? richiesta.getStato() : "ATTIVO");
        return convertiInResponse(corsoRepo.save(corso));
    }

    public CorsoResponse modifica(Long id, CorsoRequest richiesta) {
        Corso corso = corsoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        corso.setNome(richiesta.getNome());
        corso.setDescrizione(richiesta.getDescrizione());
        corso.setTrainer(richiesta.getTrainer());
        corso.setGiorno(richiesta.getGiorno());
        corso.setOrario(richiesta.getOrario());
        corso.setDurata(richiesta.getDurata());
        corso.setPostiDisponibili(richiesta.getPostiDisponibili());
        if (richiesta.getStato() != null) corso.setStato(richiesta.getStato());
        return convertiInResponse(corsoRepo.save(corso));
    }

    public void elimina(Long id) {
        corsoRepo.deleteById(id);
    }

    public CorsoResponse convertiInResponse(Corso c) {
        long postiOccupati = prenotazioneRepo
                .countByCorsoIdAndDataLezioneAndStato(c.getId(), LocalDate.now(), "CONFERMATA");

        return new CorsoResponse(
                c.getId(), c.getNome(), c.getDescrizione(), c.getTrainer(),
                c.getGiorno(), c.getOrario(), c.getDurata(),
                c.getPostiDisponibili(), (int) postiOccupati, c.getStato()
        );
    }
}

