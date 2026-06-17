package com.ironzone.service;

import com.ironzone.dto.*;
import com.ironzone.entity.*;
import com.ironzone.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// =============================================
// ISCRITTO SERVICE
// Contiene tutta la logica di business per gli iscritti
// Il Controller chiama il Service, il Service chiama il Repository
// =============================================
@Service
@RequiredArgsConstructor
public class IscrittoService {

    private final IscrittoRepository iscrittoRepo;

    // Restituisce tutti gli iscritti
    public List<IscrittoResponse> getTutti() {
        return iscrittoRepo.findAll().stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    // Cerca iscritto per ID
    public IscrittoResponse getPerId(Long id) {
        Iscritto iscritto = iscrittoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Iscritto non trovato con id: " + id));
        return convertiInResponse(iscritto);
    }

    // Crea nuovo iscritto
    public IscrittoResponse crea(IscrittoRequest richiesta) {
        if (iscrittoRepo.findByEmail(richiesta.getEmail()).isPresent()) {
            throw new RuntimeException("Email già registrata: " + richiesta.getEmail());
        }

        Iscritto iscritto = new Iscritto();
        iscritto.setNome(richiesta.getNome());
        iscritto.setCognome(richiesta.getCognome());
        iscritto.setEmail(richiesta.getEmail());
        iscritto.setTelefono(richiesta.getTelefono());
        iscritto.setDataNascita(richiesta.getDataNascita());
        iscritto.setDataIscrizione(LocalDate.now());
        iscritto.setStato(richiesta.getStato() != null ? richiesta.getStato() : "ATTIVO");

        return convertiInResponse(iscrittoRepo.save(iscritto));
    }

    // Modifica iscritto esistente
    public IscrittoResponse modifica(Long id, IscrittoRequest richiesta) {
        Iscritto iscritto = iscrittoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Iscritto non trovato con id: " + id));

        iscritto.setNome(richiesta.getNome());
        iscritto.setCognome(richiesta.getCognome());
        iscritto.setEmail(richiesta.getEmail());
        iscritto.setTelefono(richiesta.getTelefono());
        iscritto.setDataNascita(richiesta.getDataNascita());
        if (richiesta.getStato() != null) iscritto.setStato(richiesta.getStato());

        return convertiInResponse(iscrittoRepo.save(iscritto));
    }

    // Elimina iscritto
    public void elimina(Long id) {
        if (!iscrittoRepo.existsById(id)) {
            throw new RuntimeException("Iscritto non trovato con id: " + id);
        }
        iscrittoRepo.deleteById(id);
    }

    // Cerca iscritti per nome o cognome
    public List<IscrittoResponse> cerca(String testo) {
        return iscrittoRepo
                .findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(testo, testo)
                .stream()
                .map(this::convertiInResponse)
                .collect(Collectors.toList());
    }

    // Converte Entity → DTO Response
    public IscrittoResponse convertiInResponse(Iscritto iscritto) {
        return new IscrittoResponse(
                iscritto.getId(),
                iscritto.getNome(),
                iscritto.getCognome(),
                iscritto.getEmail(),
                iscritto.getTelefono(),
                iscritto.getDataNascita(),
                iscritto.getDataIscrizione(),
                iscritto.getStato()
        );
    }
}
