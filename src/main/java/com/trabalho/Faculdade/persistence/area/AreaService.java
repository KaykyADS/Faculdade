package com.trabalho.Faculdade.persistence.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trabalho.Faculdade.model.Area;
import com.trabalho.Faculdade.repository.AreaRepository;

@Service
public class AreaService {
	@Autowired
	private AreaRepository repository;
	
	public List<Area> getAllArea() {
		return repository.findAll(Sort.by("nome").ascending());
	}
	public Area getAreaById(Long id) {
		return repository.getReferenceById(id);
	}
	
	public void deleteAreaById(Long id) {
		repository.deleteById(id);
	}
	
	public void saveArea(Area area) {
		repository.save(area);
	}
	
	public List<Area> getAreasDeInteresse(List<Long> ids) {
	    return repository.findAllById(ids);
	}
}
