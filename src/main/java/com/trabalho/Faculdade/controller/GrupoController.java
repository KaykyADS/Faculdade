package com.trabalho.Faculdade.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.trabalho.Faculdade.model.Aluno;
import com.trabalho.Faculdade.model.Grupo;
import com.trabalho.Faculdade.model.Professor;
import com.trabalho.Faculdade.persistence.grupo.GrupoService;
import com.trabalho.Faculdade.persistence.professor.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GrupoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private ProfessorService professorService;

	@RequestMapping(name = "formularioGrupo", value = "/formularioGrupo", method = RequestMethod.GET)
	public ModelAndView grupoGet(@RequestParam Map<String, String> params, ModelMap model) {
		String acao = params.get("acao");
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
			id = Long.parseLong(params.get("id"));
		}

		Grupo grupo = new Grupo();
		List<Grupo> grupos = new ArrayList<>();
		List<Aluno> alunos = null;
		List<Professor> professores = professorService.listar();

		String erro = "";
		String saida = "";

		try {
			if (id != null) {
				if ("excluir".equalsIgnoreCase(acao)) {
					grupoService.deleGrupoById(id);
					saida = "Grupo deletado com sucesso.";
					grupos = grupoService.listar();
					grupo = null;
				} else if ("atualizar".equalsIgnoreCase(acao)) {
					grupo = grupoService.getGrupoById(id);
					grupos = null;
				}
			}
		} catch (Exception e) {
			erro = "Não se pode excluir um grupo com alunos associados";
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("grupo", grupo);
			model.addAttribute("grupos", grupos);
			model.addAttribute("alunos", alunos);
			model.addAttribute("professores", professores);
		}
		return new ModelAndView("formularioGrupo");
	}

	@RequestMapping(name = "formularioGrupo", value = "/formularioGrupo", method = RequestMethod.POST)
	public ModelAndView grupoPost(@RequestParam Map<String, String> params, ModelMap model) {
		Long id = null;
		if (params.get("id") != null && !params.get("id").isEmpty()) {
			id = Long.parseLong(params.get("id"));
		}

		String nome = params.get("nome");
		String orientadorIdStr = params.get("orientadorId");
		Long orientadorId = orientadorIdStr != null && !orientadorIdStr.isEmpty() ? Long.parseLong(orientadorIdStr)
				: null;
		String cmd = params.get("botao");

		Grupo grupo = new Grupo();
		if (!"Listar".equalsIgnoreCase(cmd)) {
			grupo.setId(id);
		}
		if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
			grupo.setNome(nome);
		}

		String saida = "";
		String erro = "";
		List<Grupo> grupos = new ArrayList<>();
		List<Professor> professores = professorService.listar();

		try {
			if ("Inserir".equalsIgnoreCase(cmd)) {
				if (orientadorId != null) {
					Professor orientador = professorService.getProfessorById(orientadorId);
					grupo.setOrientador(orientador);
				}
				grupoService.saveGrupo(grupo);
				saida = "Grupo cadastrado com sucesso.";
				grupo = null;
			}
			if ("Atualizar".equalsIgnoreCase(cmd)) {
				if (id == null) {
					erro = "Grupo não encontrado para atualizar.";
				} else {
					grupo = grupoService.getGrupoById(id);
					grupo.setNome(nome);
					if (orientadorId != null) {
						Professor orientador = professorService.getProfessorById(orientadorId);
						grupo.setOrientador(orientador);
					}
					grupoService.saveGrupo(grupo);
					saida = "Grupo atualizado com sucesso.";
					grupo = null;
				}
			}
			if ("Buscar".equalsIgnoreCase(cmd)) {
				String nomeAluno = params.get("aluno-nome");
				String nomeProfessor = params.get("professor-nome");
				String data = params.get("data");

				if ((nomeAluno == null || nomeAluno.isBlank()) && (nomeProfessor != null && !nomeProfessor.isBlank())
						&& (data == null || data.isBlank())) {
					grupos = grupoService.findGrupoByProfessor(nomeProfessor);
				} else if ((nomeProfessor == null || nomeProfessor.isBlank())
						&& (nomeAluno != null && !nomeAluno.isBlank()) && (data == null || data.isBlank())) {
					Grupo g = grupoService.findByNomeAluno(nomeAluno);
					if (g == null) {
						erro = "grupo não encontrado";
					} else {
						grupos.add(g);
					}
				} else if ((nomeProfessor == null || nomeProfessor.isBlank())
						&& (nomeAluno == null || nomeAluno.isBlank()) && (data != null && !data.isBlank())) {
					LocalDate dataAgendamento = LocalDate.parse(data);
					grupos = grupoService.findGrupoByData(dataAgendamento);
				} else {
					erro = "Insira nome de professor ou de aluno ou uma data para buscar os grupos";
				}
			}
			if ("Listar".equalsIgnoreCase(cmd)) {
				grupos = grupoService.listar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			erro = "Erro ao realizar operação.";
		}

		model.addAttribute("erro", erro);
		model.addAttribute("saida", saida);
		model.addAttribute("grupo", grupo);
		model.addAttribute("grupos", grupos);
		model.addAttribute("professores", professores);

		return new ModelAndView("formularioGrupo");
	}
}
