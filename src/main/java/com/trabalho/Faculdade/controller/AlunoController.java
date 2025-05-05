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

import com.trabalho.Faculdade.model.Aluno;
import com.trabalho.Faculdade.model.Grupo;
import com.trabalho.Faculdade.persistence.aluno.AlunoService;
import com.trabalho.Faculdade.persistence.grupo.GrupoService;


@Controller
public class AlunoController {

    @Autowired
    private AlunoService service;
    
    @Autowired
    private GrupoService gService;

    // Carrega a página de Aluno (GET)
    @RequestMapping(name = "formularioAluno", value = "/formularioAluno", method = RequestMethod.GET)
    public ModelAndView alunoGet(@RequestParam Map<String, String> params, ModelMap model) {
        String acao = params.get("acao");
        String ra = params.get("ra");

        Aluno aluno = new Aluno();
        List<Aluno> alunos = new ArrayList<>();
        List<Grupo> grupos = new ArrayList<>();
        grupos = gService.listar();
        String erro = "";
        String saida = "";

        try {
            if (ra != null && !ra.isBlank()) {
                aluno = service.getAlunoById(ra);
                if ("excluir".equalsIgnoreCase(acao)) {
                    service.deleAlunoById(ra);
                    saida = "Aluno excluído com sucesso.";
                    alunos = service.listar();
                    aluno = null;
                } else if ("atualizar".equalsIgnoreCase(acao)) {
                    aluno = service.getAlunoById(ra);
                    alunos = null; // limpa a lista na tela
                }
            }
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("aluno", aluno);
            model.addAttribute("alunos", alunos);
            model.addAttribute("grupos", grupos);
        }

        return new ModelAndView("formularioAluno");
    }

    // Lida com os formulários de Aluno (POST)
    @RequestMapping(name = "formularioAluno", value = "/formularioAluno", method = RequestMethod.POST)
    public ModelAndView alunoPost(@RequestParam Map<String, String> params, ModelMap model) {
    	String ra = params.get("ra");
        String nome = params.get("nome");
        Double percentual = null;
        if (params.get("percentualConclusao") != null && !params.get("percentualConclusao").isBlank()) {
            percentual = Double.parseDouble(params.get("percentualConclusao"));
        }
        String cmd = params.get("botao");
        Long idGrupo = null;
        if (params.get("grupoId") != null && !params.get("grupoId").isBlank()) idGrupo = Long.parseLong(params.get("grupoId"));

        Aluno aluno = new Aluno();
        if (!"Listar".equalsIgnoreCase(cmd)) {
            aluno.setRa(ra);
        }
        if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
        	aluno.setNome(nome);
        	aluno.setPercentualConclusao(percentual);
        }

        String saida = "";
        String erro = "";
        List<Aluno> alunos = new ArrayList<>();
        List<Grupo> grupos = gService.listar();

        try {
            if ("Inserir".equalsIgnoreCase(cmd)) {
            	Grupo grupo = gService.getGrupoById(idGrupo);
            	aluno.setGrupo(grupo);
                service.saveAluno(aluno);
                saida = "Aluno cadastrado com sucesso.";
                aluno = null;
            }
            if ("Atualizar".equalsIgnoreCase(cmd)) {
            	if (ra.isBlank()) {
            		erro = "Aluno não encontrado para atualizar";
            	} else if (idGrupo == null) erro = "O aluno deve estar em um grupo";
            	else {
	            	aluno = service.getAlunoById(ra);
	            	aluno.setNome(nome);
	            	aluno.setPercentualConclusao(percentual);
	            	Grupo grupo = gService.getGrupoById(idGrupo);
	            	aluno.setGrupo(grupo);
	            	aluno.setGrupo(grupo);
	                saida = "Aluno atualizado com sucesso.";
	                aluno = null;
            	}
            }
            if ("Listar".equalsIgnoreCase(cmd)) {
                alunos = service.listar();
            }
            if ("Buscar".equalsIgnoreCase(cmd)) {
                try {
                    if (ra != null && !ra.isBlank()) {
                        aluno = service.getAlunoById(ra);
                    } else if (percentual != null) {
                        alunos = service.listarPorPercentual(percentual);
                    } else {
                        erro = "Preencha RA ou Percentual para buscar.";
                    }
                } catch (Exception e) {
                    erro = "Erro ao buscar aluno: " + e.getMessage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            if (!"Listar".equalsIgnoreCase(cmd) && !"Buscar".equalsIgnoreCase(cmd)) {
                alunos = null;
            }
        }

        model.addAttribute("erro", erro);
        model.addAttribute("saida", saida);
        model.addAttribute("aluno", aluno);
        model.addAttribute("alunos", alunos);
        model.addAttribute("grupos", grupos);

        return new ModelAndView("formularioAluno");
    }
}
