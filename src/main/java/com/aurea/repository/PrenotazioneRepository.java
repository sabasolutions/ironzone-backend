package com.aurea.repository;

import com.aurea.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByIscrittoId(Long iscrittoId);

    List<Prenotazione> findByCorsoIdAndDataLezione(Long corsoId, LocalDate dataLezione);

    Long countByCorsoIdAndDataLezioneAndStato(Long corsoId, LocalDate dataLezione, String stato);

    List<Prenotazione> findByDataLezione(LocalDate data);

    Long countByDataLezione(LocalDate data);

    boolean existsByIscrittoIdAndCorsoIdAndDataLezione(Long iscrittoId, Long corsoId, LocalDate dataLezione);
}

