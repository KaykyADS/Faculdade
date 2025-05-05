package com.trabalho.Faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trabalho.Faculdade.model.Aluno;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, String> {
	List<Aluno> findByPercentualConclusao(Double percentualConclusao);
    // Consulta para buscar alunos por percentual de conclusão
}
