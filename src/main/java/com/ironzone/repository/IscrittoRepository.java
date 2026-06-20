package com.aurea.repository;

import com.ironzone.entity.Iscritto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IscrittoRepository extends JpaRepository<Iscritto, Long> {
    Optional<Iscritto> findByEmail(String email);

    List<Iscritto> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);

    Long countByStato(String stato);
}

