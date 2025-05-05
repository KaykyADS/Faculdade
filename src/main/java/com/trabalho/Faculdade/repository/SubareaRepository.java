package com.trabalho.Faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trabalho.Faculdade.model.Subarea;

import java.util.List;

public interface SubareaRepository extends JpaRepository<Subarea, Long> {
    List<Subarea> findByAreaId(Long areaId);
}
