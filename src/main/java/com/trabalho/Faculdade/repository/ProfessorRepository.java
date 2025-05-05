package com.trabalho.Faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trabalho.Faculdade.model.Professor;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("SELECT p FROM Professor p JOIN p.areasInteresse a WHERE a.nome LIKE %:areaNome%")
    List<Professor> findByAreaNome(String areaNome);

    @Query("SELECT p FROM Apresentacao a JOIN a.banca p WHERE a.id = :id")
    List<Professor> findBancaApresentacao(Long id);
}
