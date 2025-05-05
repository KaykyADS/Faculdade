package com.trabalho.Faculdade.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Apresentacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apresentacao {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
    private LocalDate data;
    private String tipoTcc; // TCC1 ou TCC2
    @ManyToMany
    @JoinTable(
        name = "apresentacao_banca",
        joinColumns = @JoinColumn(name = "apresentacao_id"),
        inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private List<Professor> banca;
    private Double nota;
}