package com.trabalho.Faculdade.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trabalho.Faculdade.model.Area;
import com.trabalho.Faculdade.model.Grupo;
import com.trabalho.Faculdade.model.Professor;
import com.trabalho.Faculdade.persistence.AreaService;
import com.trabalho.Faculdade.persistence.ProfessorService;


@Controller
public class ProfessorController {
	@Autowired
	private ProfessorService service;
	@Autowired
	private AreaService aService;
	
	@RequestMapping(name = "formularioProfessor", value = "/formularioProfessor", method = RequestMethod.GET)
    public ModelAndView professorGet(@RequestParam Map<String, String> params, ModelMap model) {
		String acao = params.get("acao");
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }
		
		Professor prof = new Professor();
		List<Professor> profs = new ArrayList<>();
		List<Area> areas = new ArrayList<>();
		List<Grupo> grupos = new ArrayList<>();
		areas = aService.getAllArea();
		String erro = "";
		String saida = "";
		String quantidade = "";
		
		try {
			if (id != null) {
				if ("excluir".equalsIgnoreCase(acao)) {
					service.deleteProfessorById(id);
					saida = "Professor deletado com sucesso";
					profs = service.listar();
					prof = null;
				} else if ("atualizar".equalsIgnoreCase(acao)) {
					prof = service.getProfessorById(id);
					profs = null;
				} else if ("quantidade".equalsIgnoreCase(acao)) {
					quantidade = String.valueOf(service.qtdGruposProfessor(id));
					grupos = service.gruposDoProfessor(id);
					profs = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("professor", prof);
			model.addAttribute("professores", profs);
			model.addAttribute("areas", areas);
			model.addAttribute("quantidade", quantidade);
			model.addAttribute("grupos", grupos);
		}
		return new ModelAndView("formularioProfessor");
	}
	
	@RequestMapping(name = "formularioProfessor", value = "/formularioProfessor", method = RequestMethod.POST)
    public ModelAndView professorPost(@RequestParam Map<String, String> params, ModelMap model, @RequestParam(name = "areasIds", required = false) List<Long> areasIds) {
		Long id = null;
        if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }
        
        String nome = params.get("nome");
        String titulacao = params.get("titulacao");
        String cmd = params.get("botao");

        Professor prof = new Professor();
        if (!"Listar".equalsIgnoreCase(cmd)) {
            prof.setId(id);
        }
        if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
        	prof.setNome(nome);
            prof.setTitulacao(titulacao);
        }

        String saida = "";
        String erro = "";
        List<Area> areas = new ArrayList<>();
        List<Professor> profs = new ArrayList<>();

        try {
            if ("Inserir".equalsIgnoreCase(cmd)) {
                areas = aService.getAreasDeInteresse(areasIds);
                prof.setAreasInteresse(areas);
                service.saveProfessor(prof);
                saida = "Professor cadastrado com sucesso.";
				prof = null;
            }
            if ("Atualizar".equalsIgnoreCase(cmd)) {
            	if (id == null) {
            		erro = "Professor não encontrado para atualizar";
            	} else {
	            	prof = service.getProfessorById(id);
	            	prof.setNome(nome);
	            	prof.setTitulacao(titulacao);
	            	areas = aService.getAreasDeInteresse(areasIds);
	                prof.setAreasInteresse(areas);
	            	service.saveProfessor(prof);
	                saida = "Professor atualizado com sucesso.";
	                prof = null;
            	}
            }
            if ("Listar".equalsIgnoreCase(cmd)) {
                profs = service.listar();
            }
            if ("Buscar".equalsIgnoreCase(cmd)) {
                String areaIdStr = params.get("areasIds");
                if (areaIdStr != null && !areaIdStr.isEmpty()) {
                    Long areaId = Long.parseLong(areaIdStr);
                    Area area = aService.getAreaById(areaId);
                    profs = service.listarPorArea(area.getNome());
                }
            }
        } catch (Exception e) {
            erro = e.getMessage();
            Pattern pattern = Pattern.compile("Erro[^!]*!");
            Matcher matcher = pattern.matcher(erro);
            if (matcher.find()) {
                erro = matcher.group();
            }
        }
        areas = aService.getAllArea(); 
        model.addAttribute("erro", erro);
        model.addAttribute("saida", saida);
        model.addAttribute("professor", prof);
        model.addAttribute("professores", profs);
        model.addAttribute("areas", areas);

		return new ModelAndView("formularioProfessor");
	}
}
