package com.aurea.repository;

import com.aurea.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Long> {
    List<Corso> findByGiorno(String giorno);

    List<Corso> findByStato(String stato);

    Long countByGiornoAndStato(String giorno, String stato);
}

