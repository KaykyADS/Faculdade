package com.trabalho.Faculdade.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Area {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="area_id")
	private Long id;
	private String descricao;
	private String nome; // Add this field to resolve the issue
}
