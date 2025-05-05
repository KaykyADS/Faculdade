package com.trabalho.Faculdade.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trabalho.Faculdade.model.Apresentacao;

public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {
    @Query("SELECT a FROM Apresentacao a WHERE a.data = :dataAgendamento")
    List<Apresentacao> findApresentacoesHoje(LocalDate dataAgendamento);
}
