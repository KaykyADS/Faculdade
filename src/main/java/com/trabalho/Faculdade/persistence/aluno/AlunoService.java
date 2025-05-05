package com.trabalho.Faculdade.persistence.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Aluno;
import com.trabalho.Faculdade.repository.AlunoRepository;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repository;

    public List<Aluno> listar() {
        return repository.findAll();
    }
    
    public Aluno getAlunoById(String ra) {
    	return repository.getReferenceById(ra);
    }

    public List<Aluno> listarPorPercentual(Double percentual) {
        return repository.findByPercentualConclusao(percentual);
    }

    public void saveAluno(Aluno aluno) {
        repository.save(aluno);
    }
    public void deleAlunoById(String ra) {
    	repository.deleteById(ra);
    }
    // Métodos para cadastro, atualização e deleção
    // SRP (SOLID): Serviço apenas para lógica de aluno
}
