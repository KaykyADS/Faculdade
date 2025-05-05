package com.trabalho.Faculdade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    @Id
    private String ra;
    private String nome;
    private double percentualConclusao;
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    // SRP (SOLID): Esta classe representa apenas o aluno
    // Regra de negócio: alunos com menos de 75% não podem defender TCC1, menos de 90% não podem defender TCC2
    // Esta regra será implementada via trigger no banco de dados, conforme solicitado no enunciado.
    // Consulta customizada para buscar alunos por percentual de conclusão
    // Exemplo de uso no repository: findByPercentualConclusaoGreaterThanEqual

    // Métodos utilitários para facilitar consultas e regras de negócio
    public boolean podeDefenderTCC1() {
        return this.percentualConclusao >= 75.0;
    }
    public boolean podeDefenderTCC2() {
        return this.percentualConclusao >= 90.0;
    }
}