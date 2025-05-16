package com.trabalho.Faculdade.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Trabalho;
import com.trabalho.Faculdade.repository.TrabalhoRepository;

import java.util.List;

@Service
public class TrabalhoService {
    @Autowired
    private TrabalhoRepository repository;

    public List<Trabalho> listar() {
        return repository.findAll();
    }
    
    public Trabalho getTrabalhoById(Long id) {
    	return repository.getReferenceById(id);
    }
    
    public void deleteTrabalhoById(Long id) {
    	repository.deleteById(id);
    }
    
    public void saveTrabalho(Trabalho trabalho) {
    	repository.save(trabalho);
    }
    // Métodos para cadastro, atualização e deleção
    // SRP (SOLID): Serviço apenas para lógica de trabalho
}
