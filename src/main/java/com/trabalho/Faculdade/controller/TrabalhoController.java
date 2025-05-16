package com.trabalho.Faculdade.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trabalho.Faculdade.model.Area;
import com.trabalho.Faculdade.model.Grupo;
import com.trabalho.Faculdade.model.Subarea;
import com.trabalho.Faculdade.model.Trabalho;
import com.trabalho.Faculdade.persistence.AreaService;
import com.trabalho.Faculdade.persistence.GrupoService;
import com.trabalho.Faculdade.persistence.SubareaService;
import com.trabalho.Faculdade.persistence.TrabalhoService;


@Controller
public class TrabalhoController {

	@Autowired
	private AreaService aService;

	@Autowired
	private GrupoService gService;

	@Autowired
	private SubareaService sService;

	@Autowired
	private TrabalhoService service;

	// Carrega a página de área (GET)
	@RequestMapping(name = "formularioTrabalho", value = "/formularioTrabalho", method = RequestMethod.GET)
	public ModelAndView trabalhoGet(@RequestParam Map<String, String> params, ModelMap model) {
		String acao = params.get("acao");
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
			id = Long.parseLong(params.get("id"));
		}

		Trabalho trabalho = new Trabalho();
		List<Trabalho> trabalhos = new ArrayList<>();
		String erro = "";
		String saida = "";

		try {
			if (id != null) {
				if ("excluir".equalsIgnoreCase(acao)) {
					Trabalho t = service.getTrabalhoById(id);
					Grupo g = gService.getGrupoById(t.getGrupo().getId());
					g.setTrabalho(null);
					service.deleteTrabalhoById(id);
					saida = "Trabalho excluído com sucesso.";
					trabalhos = service.listar();
				} else if ("atualizar".equalsIgnoreCase(acao)) {
					trabalho = service.getTrabalhoById(id);
					trabalhos = null; // limpa a lista na tela
				}
			}
		} catch (Exception e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("trabalho", trabalho);
			model.addAttribute("trabalhos", trabalhos);
			model.addAttribute("subareas", sService.listar());
			model.addAttribute("grupos", gService.listar());
			model.addAttribute("areas", aService.getAllArea());
		}

		return new ModelAndView("formularioTrabalho");
	}

	// Lida com os formulários de área (POST)
	@RequestMapping(name = "formularioTrabalho", value = "/formularioTrabalho", method = RequestMethod.POST)
	public ModelAndView trabalhoPost(@RequestParam Map<String, String> params, ModelMap model) {
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
			id = Long.parseLong(params.get("id"));
		}
		Long idArea = null;
		if (params.get("areaId") != null && !params.get("areaId").isEmpty()) {
			idArea = Long.parseLong(params.get("areaId"));
		}
		Long idSubarea = null;
		if (params.get("subareaId") != null && !params.get("subareaId").isEmpty()) {
			idSubarea = Long.parseLong(params.get("subareaId"));
		}
		Long idGrupo = null;
		if (params.get("grupoId") != null && !params.get("grupoId").isEmpty()) {
			idGrupo = Long.parseLong(params.get("grupoId"));
		}

		String titulo = params.get("titulo");
		String cmd = params.get("botao");
		String saida = "";
		String erro = "";
		List<Trabalho> trabalhos = new ArrayList<>();

		Trabalho trabalho = new Trabalho();
		if (!"Listar".equalsIgnoreCase(cmd)) {
			trabalho.setId(id);
		}
		if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
			trabalho.setTitulo(titulo);
		}
		try {
			if ("Inserir".equalsIgnoreCase(cmd)) {
				Area area = aService.getAreaById(idArea);
				Subarea subarea = sService.getSubareaById(idSubarea);
				Grupo grupo = gService.getGrupoById(idGrupo);
				trabalho.setArea(area);
				trabalho.setSubarea(subarea);
				trabalho.setGrupo(grupo);
				service.saveTrabalho(trabalho);
				saida = "Trabalho cadastrado com sucesso.";
				trabalho = null;
			}
			if ("Atualizar".equalsIgnoreCase(cmd)) {
				if (id == null) {
					erro = "Trabalho não encontrado para atualizar";
				} else if (idArea == null || idSubarea == null || idGrupo == null) {
					erro = "Selecione todos os campos para atualizar trabalho";
				}
				else {
					trabalho = service.getTrabalhoById(id);
					trabalho.setTitulo(titulo);
					Area area = aService.getAreaById(idArea);
					Subarea subarea = sService.getSubareaById(idSubarea);
					Grupo grupo = gService.getGrupoById(idGrupo);
					trabalho.setArea(area);
					trabalho.setSubarea(subarea);
					trabalho.setGrupo(grupo);
					service.saveTrabalho(trabalho);
					saida = "Trabalho atualizado com sucesso.";
					trabalho = null;
				}
			}
			if ("Listar".equalsIgnoreCase(cmd)) {
				trabalhos = service.listar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			erro = "Ocorreu um erro inesperado: " + e.getMessage();
		} finally {
			if (!"Listar".equalsIgnoreCase(cmd)) {
				trabalhos = null;
			}
		}
		
		model.addAttribute("erro", erro);
		model.addAttribute("saida", saida);
		model.addAttribute("trabalho", trabalho);
		model.addAttribute("trabalhos", trabalhos);
		model.addAttribute("subareas", sService.listar());
		model.addAttribute("grupos", gService.listar());
		model.addAttribute("areas", aService.getAllArea());

		return new ModelAndView("formularioTrabalho");
	}
}
