package com.aurea.service;

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

    public List<CorsoResponse> getAll() {
        return corsoRepo.findAll().stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    public CorsoResponse findById(Long id) {
        Corso corso = corsoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        return convertiInResponse(corso);
    }

    public CorsoResponse create(CorsoRequest request) {
        Corso corso = new Corso();
        corso.setNome(request.getNome());
        corso.setDescrizione(request.getDescrizione());
        corso.setTrainer(request.getTrainer());
        corso.setGiorno(request.getGiorno());
        corso.setOrario(request.getOrario());
        corso.setDurata(request.getDurata());
        corso.setPostiDisponibili(request.getPostiDisponibili());
        corso.setStato(request.getStato() != null ? request.getStato() : "ATTIVO");
        return convertiInResponse(corsoRepo.save(corso));
    }

    public CorsoResponse update(Long id, CorsoRequest request) {
        Corso corso = corsoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        corso.setNome(request.getNome());
        corso.setDescrizione(request.getDescrizione());
        corso.setTrainer(request.getTrainer());
        corso.setGiorno(request.getGiorno());
        corso.setOrario(request.getOrario());
        corso.setDurata(request.getDurata());
        corso.setPostiDisponibili(request.getPostiDisponibili());
        if (request.getStato() != null) corso.setStato(request.getStato());
        return convertiInResponse(corsoRepo.save(corso));
    }

    public void delete(Long id) {
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

