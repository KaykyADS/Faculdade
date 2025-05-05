package com.trabalho.Faculdade.controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trabalho.Faculdade.model.Area;
import com.trabalho.Faculdade.model.Subarea;
import com.trabalho.Faculdade.persistence.area.AreaService;
import com.trabalho.Faculdade.persistence.subarea.SubareaService;


@Controller
public class SubareaController {
	@Autowired
	private SubareaService service;
	@Autowired AreaService aService;
	
	@RequestMapping(name = "formularioSubarea", value = "/formularioSubarea", method = RequestMethod.GET)
	public ModelAndView subareaGet(@RequestParam Map<String, String> params, ModelMap model) {
		String acao = params.get("acao");
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }
		
		Subarea sub = new Subarea();
		List<Subarea> subs = new ArrayList<>();
		List<Area> areas = new ArrayList<>();
		areas = aService.getAllArea();
		String erro = "";
		String saida = "";
		
		try {
			if (id != null) {
				if ("excluir".equalsIgnoreCase(acao)) {
					service.deleSubareaById(id);
					saida = "Subárea deletada com sucesso";
					subs = service.listar();
					sub = null;
				} else if ("atualizar".equalsIgnoreCase(acao)) {
					sub = service.getSubareaById(id);
					subs = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("subarea", sub);
			model.addAttribute("subAreas", subs);
			model.addAttribute("areas", areas);
		}
		
		return new ModelAndView("formularioSubarea");
	}
	
	@RequestMapping(name = "formularioSubarea", value = "/formularioSubarea", method = RequestMethod.POST)
    public ModelAndView subareaPost(@RequestParam Map<String, String> params, ModelMap model) {
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }
		
		String nome = params.get("nome");
		Long idArea = null;
		if (params.get("area") != null && !params.get("area").isEmpty()) {
			idArea = Long.parseLong(params.get("area"));
        }
		String cmd = params.get("botao");
		String saida = "";
		String erro = "";
		List<Subarea> subs = new ArrayList<>();
		List<Area> areas = new ArrayList<>();
		areas = aService.getAllArea();
		
		Subarea sub = new Subarea();
		Area area = new Area();
		
		if (!"Listar".equalsIgnoreCase(cmd)) {
            sub.setId(id);
        }
        if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
        	sub.setNome(nome);
        }
        
        try {
        	if ("Inserir".equalsIgnoreCase(cmd)) {
        		area = aService.getAreaById(idArea);
                sub.setArea(area);
        		service.saveSubarea(sub);
        		saida = "Subárea cadastrada com sucesso";
				sub = null;
        	} 
        	if ("Atualizar".equalsIgnoreCase(cmd)) {
        		if (id == null || idArea == null) erro = "Subárea não encontrada para atualização";
        		else {
        			sub = service.getSubareaById(id);
        			sub.setNome(nome);
        			area = aService.getAreaById(idArea);
        			sub.setArea(area);
        			saida = "Subárea atualizada com sucesso";
        			sub = null;
        		}
        	}
        	if ("Listar".equalsIgnoreCase(cmd)) {
        		subs = service.listar();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (!"Listar".equalsIgnoreCase(cmd)) {
                subs = null;
            }
        }
        model.addAttribute("saida", saida);
    	model.addAttribute("erro", erro);
    	model.addAttribute("subarea", sub);
    	model.addAttribute("subAreas", subs);
    	model.addAttribute("areas", areas);
        return new ModelAndView("formularioSubarea");
	}
}
