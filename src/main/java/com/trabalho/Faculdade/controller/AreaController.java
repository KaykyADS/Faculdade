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
import com.trabalho.Faculdade.persistence.AreaService;


@Controller
public class AreaController {

    @Autowired
    private AreaService service;

    // Carrega a página de área (GET)
    @RequestMapping(name = "formularioArea", value = "/formularioArea", method = RequestMethod.GET)
    public ModelAndView areaGet(@RequestParam Map<String, String> params, ModelMap model) {
        String acao = params.get("acao");
        Long id = null;
        if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }

        Area area = new Area();
        List<Area> areas = new ArrayList<>();
        String erro = "";
        String saida = "";

        try {
            if (id != null) {
                area = service.getAreaById(id);
                if ("excluir".equalsIgnoreCase(acao)) {
                    service.deleteAreaById(id);
                    saida = "Área excluída com sucesso.";
                    areas = service.getAllArea();
                    area = null;
                } else if ("atualizar".equalsIgnoreCase(acao)) {
                    area = service.getAreaById(id);
                    areas = null; // limpa a lista na tela
                }
            }
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("area", area);
            model.addAttribute("areas", areas);
        }

        return new ModelAndView("formularioArea");
    }

    // Lida com os formulários de área (POST)
    @RequestMapping(name = "formularioArea", value = "/formularioArea", method = RequestMethod.POST)
    public ModelAndView areaPost(@RequestParam Map<String, String> params, ModelMap model) {
        Long id = null;
        if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }
        
        String nome = params.get("nome");
        String descricao = params.get("descricao");
        String cmd = params.get("botao");

        Area area = new Area();
        if (!"Listar".equalsIgnoreCase(cmd)) {
            area.setId(id);
        }
        if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
        	area.setNome(nome);
            area.setDescricao(descricao);
        }

        String saida = "";
        String erro = "";
        List<Area> areas = new ArrayList<>();

        try {
            if ("Inserir".equalsIgnoreCase(cmd)) {
                service.saveArea(area);
                saida = "Área cadastrada com sucesso.";
                area = null;
            }
            if ("Atualizar".equalsIgnoreCase(cmd)) {
            	if (id == null) {
            		erro = "Área não encontrada para atualizar";
            	} else {
	            	area = service.getAreaById(id);
	            	area.setNome(nome);
	            	area.setDescricao(descricao);
	            	service.saveArea(area);
	                saida = "Área atualizada com sucesso.";
	                area = null;
            	}
            }
            if ("Listar".equalsIgnoreCase(cmd)) {
                areas = service.getAllArea();
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            if (!"Listar".equalsIgnoreCase(cmd)) {
                areas = null;
            }
        }

        model.addAttribute("erro", erro);
        model.addAttribute("saida", saida);
        model.addAttribute("area", area);
        model.addAttribute("areas", areas);

        return new ModelAndView("formularioArea");
    }
}
