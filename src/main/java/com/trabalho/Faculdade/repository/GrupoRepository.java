package com.trabalho.Faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trabalho.Faculdade.model.Grupo;

import java.time.LocalDate;
import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Query("SELECT g FROM Grupo g WHERE g.orientador.nome LIKE %:nomeProfessor%")
    List<Grupo> findByNomeProfessor(String nomeProfessor);
    
    @Query("SELECT MIN(a.percentualConclusao) FROM Aluno a WHERE a.grupo.id = :idGrupo")
    Double findMinPercentualByGrupo(Long idGrupo);
    
    @Query("SELECT g FROM Grupo g JOIN Aluno a ON g.id = a.grupo.id WHERE a.nome LIKE %:nomeAluno%")
    Grupo findByNomeAluno(String nomeAluno);
    
    @Query("SELECT g FROM Grupo g JOIN Apresentacao a ON g.id = a.grupo.id WHERE a.data = :dataAgendamento")
    List<Grupo> findByData(LocalDate dataAgendamento);

    @Query("SELECT g FROM Grupo g JOIN Aluno a ON a.grupo.id = g.id WHERE g.id = :id")
    Grupo verificaSeTemAulunos(Long id);
}
