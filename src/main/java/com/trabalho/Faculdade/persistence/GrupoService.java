package com.trabalho.Faculdade.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Grupo;
import com.trabalho.Faculdade.repository.GrupoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository repository;

    public List<Grupo> listar() {
        return repository.findAll(Sort.by("nome").ascending());
    }
    
    public Grupo getGrupoById(Long id) {
    	return repository.getReferenceById(id);
    }
    // Métodos para cadastro, atualização e deleção
    // SRP (SOLID): Serviço apenas para lógica de grupo
    public void saveGrupo(Grupo grupo) {
    	repository.save(grupo);
    }
    
    public void deleGrupoById(Long id) {
    	repository.deleteById(id);
    }
    
    public Double findPercentualMinGrupo(Long id) {
    	return repository.findMinPercentualByGrupo(id);
    }
    
    public List<Grupo> findGrupoByProfessor(String nome) {
    	return repository.findByNomeProfessor(nome);
    }
    
    public List<Grupo> findGrupoByData(LocalDate dataAgendamento) {
    	return repository.findByData(dataAgendamento);
    }
    
    public Grupo findByNomeAluno(String nome) {
    	return repository.findByNomeAluno(nome);
    }

    public Boolean verificaSeTemAlunos(Long id) {
        Grupo g = repository.verificaSeTemAulunos(id);
        if (g != null) return true;
        else return false;
    }
}
