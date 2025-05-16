package com.trabalho.Faculdade.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trabalho.Faculdade.model.Apresentacao;
import com.trabalho.Faculdade.model.Grupo;
import com.trabalho.Faculdade.model.Professor;
import com.trabalho.Faculdade.persistence.ApresentacaoService;
import com.trabalho.Faculdade.persistence.GrupoService;
import com.trabalho.Faculdade.persistence.ProfessorService;

@Controller
public class ApresentacaoController {

    @Autowired
    private ApresentacaoService service;

    @Autowired
    private GrupoService gService;

    @Autowired
    private ProfessorService pService;

    // Carrega a página de apresentação (GET)
    @RequestMapping(name = "formularioApresentacao", value = "/formularioApresentacao", method = RequestMethod.GET)
    public ModelAndView apresentacaoGet(@RequestParam Map<String, String> params, ModelMap model) {
        String acao = params.get("acao");
        Long id = null;
        if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }

        Apresentacao apresentacao = new Apresentacao();
        List<Apresentacao> apresentacoes = new ArrayList<>();
        List<Professor> professoresBanca = new ArrayList<>();
        String erro = "";
        String saida = "";

        try {
            if (id != null) {
                apresentacao = service.getApresentacaoById(id);
                if ("excluir".equalsIgnoreCase(acao)) {
                    service.deleteApresentacaoById(id);
                    saida = "Apresentação excluída com sucesso";
                    apresentacoes = service.listar();
                    apresentacao = null;
                } else if ("atualizar".equalsIgnoreCase(acao)) {
                    apresentacao = service.getApresentacaoById(id);
                    apresentacoes = null; // limpa a lista na tela
                } else if ("banca".equalsIgnoreCase(acao)) {
                    apresentacoes = null;
                    professoresBanca = pService.findBancaProfessores(id);
                }
            }
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("apresentacao", apresentacao);
            model.addAttribute("apresentacoes", apresentacoes);
            model.addAttribute("grupos", gService.listar());
            model.addAttribute("professores", pService.listar());
            model.addAttribute("professoresBanca", professoresBanca);
        }

        return new ModelAndView("formularioApresentacao");
    }

    // Lida com os formulários de apresentação (POST)
    @RequestMapping(name = "formularioApresentacao", value = "/formularioApresentacao", method = RequestMethod.POST)
    public ModelAndView apresentacaoPost(@RequestParam Map<String, String> params, ModelMap model,
            @RequestParam(name = "bancaIds", required = false) List<Long> banca) {
        Long id = null;
        if (params.get("id") != null && !params.get("id").isEmpty()) {
            id = Long.parseLong(params.get("id"));
        }
        Long idGrupo = null;
        if (params.get("grupoId") != null && !params.get("grupoId").isEmpty()) {
            idGrupo = Long.parseLong(params.get("grupoId"));
        }
        LocalDate data = null;
        if (params.get("data") != null && !params.get("data").isEmpty()) {
            data = LocalDate.parse(params.get("data"));
        }
        String tipo = params.get("tipoTcc");
        Double nota = null;
        if (params.get("nota") != null && !params.get("nota").isEmpty()) {
            nota = Double.parseDouble(params.get("nota"));
        }
        String cmd = params.get("botao");
        List<Grupo> grupos = gService.listar();

        Apresentacao apresentacao = new Apresentacao();
        if ("Inserir".equalsIgnoreCase(cmd) || "Atualizar".equalsIgnoreCase(cmd)) {
            apresentacao.setTipoTcc(tipo);
            apresentacao.setNota(nota);
            apresentacao.setData(data);
        }

        String saida = "";
        String erro = "";
        List<Apresentacao> apresentacoes = new ArrayList<>();

        try {
            if ("Inserir".equalsIgnoreCase(cmd)) {
                if (banca == null) {
                    banca = new ArrayList<>();
                }
                if (gService.verificaSeTemAlunos(idGrupo)) {
                    Grupo g = gService.getGrupoById(idGrupo);
                    System.out.println("*********************************Tamanho da banca: **********************************" + banca.size());
                    List<Professor> professores = pService.listarPorId(banca);
                    apresentacao.setGrupo(g);
                    apresentacao.setTipoTcc(tipo);
                    apresentacao.setNota(nota);
                    apresentacao.setData(data);
                    apresentacao.setBanca(professores);

                    service.saveApresentacao(apresentacao);
                    saida = "Apresentação cadastrada com sucesso.";
                    apresentacao = null;
                }
            }
            if ("Atualizar".equalsIgnoreCase(cmd)) {
                if (banca == null) {
                    banca = new ArrayList<>();
                }
                if (id == null) {
                    erro = "Apresentação não encontrada para atualizar";
                } else {
                    Apresentacao apresentacaoExistente = service.getApresentacaoById(id);

                    apresentacaoExistente.setTipoTcc(tipo);
                    apresentacaoExistente.setNota(nota);
                    apresentacaoExistente.setData(data);

                    Grupo g = gService.getGrupoById(idGrupo);
                    apresentacaoExistente.setGrupo(g);

                    List<Professor> professores = pService.listarPorId(banca);
                    apresentacaoExistente.setBanca(professores);

                    service.saveApresentacao(apresentacaoExistente);
                    saida = "Apresentação atualizada com sucesso.";
                    apresentacao = null;
                }
            }
            if ("Listar".equalsIgnoreCase(cmd)) {
                apresentacoes = service.listar();
                apresentacao = null;
            }
            if ("Buscar".equalsIgnoreCase(cmd)) {
                apresentacoes = service.findApresentacoesDia(LocalDate.now());
            }
        } catch (Exception e) {
            erro = e.getMessage();
            Pattern pattern = Pattern.compile("Erro[^!]*!");
            Matcher matcher = pattern.matcher(erro);
            if (matcher.find()) {
                erro = matcher.group();
            }
        } finally {
            if (!"Listar".equalsIgnoreCase(cmd) && !"Buscar".equalsIgnoreCase(cmd)) {
                apresentacoes = null;
            }
        }

        model.addAttribute("erro", erro);
        model.addAttribute("saida", saida);
        model.addAttribute("apresentacao", apresentacao);
        model.addAttribute("apresentacoes", apresentacoes);
        model.addAttribute("grupos", grupos);
        model.addAttribute("professores", pService.listar());

        return new ModelAndView("formularioApresentacao");
    }
}