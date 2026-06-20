package com.aurea.repository;

import com.ironzone.entity.Abbonamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbbonamentoRepository extends JpaRepository<Abbonamento, Long> {
    List<Abbonamento> findByIscrittoId(Long iscrittoId);

    List<Abbonamento> findByDataFineBetweenAndStato(LocalDate da, LocalDate a, String stato);

    Long countByDataFineBetweenAndStato(LocalDate da, LocalDate a, String stato);
}

