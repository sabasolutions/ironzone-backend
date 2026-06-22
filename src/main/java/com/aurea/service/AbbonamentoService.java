package com.aurea.service;

import com.aurea.dto.AbbonamentoRequest;
import com.aurea.dto.AbbonamentoResponse;
import com.aurea.entity.Abbonamento;
import com.aurea.entity.Iscritto;
import com.aurea.repository.AbbonamentoRepository;
import com.aurea.repository.IscrittoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbbonamentoService {

    private final AbbonamentoRepository abbonamentoRepo;
    private final IscrittoRepository iscrittoRepo;

    public List<AbbonamentoResponse> getTutti() {
        return abbonamentoRepo.findAll().stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    public List<AbbonamentoResponse> getPerIscritto(Long iscrittoId) {
        return abbonamentoRepo.findByIscrittoId(iscrittoId).stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    public AbbonamentoResponse crea(AbbonamentoRequest richiesta) {
        Iscritto iscritto = iscrittoRepo.findById(richiesta.getIscrittoId())
                .orElseThrow(() -> new RuntimeException("Iscritto non trovato"));

        Abbonamento abbonamento = new Abbonamento();
        abbonamento.setTipo(richiesta.getTipo());
        abbonamento.setDataInizio(richiesta.getDataInizio());
        abbonamento.setDataFine(richiesta.getDataFine());
        abbonamento.setPrezzo(richiesta.getPrezzo());
        abbonamento.setStato("ATTIVO");
        abbonamento.setIscritto(iscritto);

        return convertiInResponse(abbonamentoRepo.save(abbonamento));
    }

    public AbbonamentoResponse modifica(Long id, AbbonamentoRequest richiesta) {
        Abbonamento abbonamento = abbonamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Abbonamento non trovato"));

        abbonamento.setTipo(richiesta.getTipo());
        abbonamento.setDataInizio(richiesta.getDataInizio());
        abbonamento.setDataFine(richiesta.getDataFine());
        abbonamento.setPrezzo(richiesta.getPrezzo());

        return convertiInResponse(abbonamentoRepo.save(abbonamento));
    }

    public void elimina(Long id) {
        abbonamentoRepo.deleteById(id);
    }

    public List<AbbonamentoResponse> getInScadenza() {
        LocalDate oggi = LocalDate.now();
        LocalDate traSettegiorni = oggi.plusDays(7);
        return abbonamentoRepo.findByDataFineBetweenAndStato(oggi, traSettegiorni, "ATTIVO")
                .stream().map(this::convertiInResponse).collect(Collectors.toList());
    }

    public AbbonamentoResponse convertiInResponse(Abbonamento a) {
        return new AbbonamentoResponse(
                a.getId(), a.getTipo(), a.getDataInizio(), a.getDataFine(),
                a.getPrezzo(), a.getStato(),
                a.getIscritto().getId(),
                a.getIscritto().getNome(),
                a.getIscritto().getCognome()
        );
    }
}

