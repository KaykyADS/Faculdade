package com.trabalho.Faculdade.persistence.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Professor;
import com.trabalho.Faculdade.repository.ProfessorRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
	
@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Professor> listar() {
        return repository.findAll(Sort.by("nome").ascending());
    }

    public List<Professor> listarPorArea(String areaNome) {
        return repository.findByAreaNome(areaNome);
    }
    
    public List<Professor> listarPorId(List<Long> id) {
    	return repository.findAllById(id);
    }

    // Substitui uso de método deprecated por varargs
    public int qtdGruposProfessor(Long professorId) {
        return jdbcTemplate.queryForObject("SELECT qtd_grupos_professor(?)", Integer.class, professorId);
    }
    // ... Métodos de cadastro, atualização e deleção ...
    // SOLID: SRP - Serviço apenas para lógica de professor
    
    public void saveProfessor(Professor professor) {
    	repository.save(professor);
    }
    
    public void deleteProfessorById(Long id) {
    	repository.deleteById(id);
    }
    
    public Professor getProfessorById(Long id) {
    	return repository.getReferenceById(id);
    }

    public List<Professor> findBancaProfessores(Long id) {
        return repository.findBancaApresentacao(id);
    }
}
