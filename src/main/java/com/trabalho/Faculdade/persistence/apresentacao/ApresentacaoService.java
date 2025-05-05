package com.trabalho.Faculdade.persistence.apresentacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Apresentacao;
import com.trabalho.Faculdade.repository.ApresentacaoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApresentacaoService {
    @Autowired
    private ApresentacaoRepository repository;

    public List<Apresentacao> listar() {
        return repository.findAll();
    }
    
    public Apresentacao getApresentacaoById(Long id) {
    	return repository.getReferenceById(id);
    }
    // Métodos para cadastro, atualização e deleção
    // SRP (SOLID): Serviço apenas para lógica de apresentação
    
    public void saveApresentacao(Apresentacao apresentacao) {
    	repository.save(apresentacao);
    }
    
    public void deleteApresentacaoById(Long id) {
    	repository.deleteById(id);
    }

    public List<Apresentacao> findApresentacoesDia(LocalDate data) {
        return repository.findApresentacoesHoje(data);
    }
}
