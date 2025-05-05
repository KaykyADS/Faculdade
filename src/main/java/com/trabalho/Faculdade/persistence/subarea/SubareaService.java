package com.trabalho.Faculdade.persistence.subarea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Subarea;
import com.trabalho.Faculdade.repository.SubareaRepository;

import java.util.List;

@Service
public class SubareaService {
    @Autowired
    private SubareaRepository repository;

    public List<Subarea> listar() {
    	return repository.findAll(Sort.by("nome").ascending());
    }
    
    public Subarea getSubareaById(Long id) {
    	return repository.getReferenceById(id);
    }
    // Métodos para cadastro, atualização e deleção
    // SRP (SOLID): Serviço apenas para lógica de subárea
    public void deleSubareaById(Long id) {
    	repository.deleteById(id);
    }
    
    public void saveSubarea(Subarea sub) {
    	repository.save(sub);
    }
}
