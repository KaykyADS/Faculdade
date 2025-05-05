<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Menu Principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/style.css" type="text/css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body class="bg-dark text-light">

    <div class="container py-5">
        <h1 class="text-center mb-5 display-5 fw-bold">Menu Principal</h1>
        <div class="d-flex flex-column gap-3 align-items-center">
            <a href="formularioArea" class="btn btn-outline-light btn-lg w-50">Cadastrar Área</a>
            <a href="formularioSubarea" class="btn btn-outline-light btn-lg w-50">Cadastrar Subárea</a>
            <a href="formularioProfessor" class="btn btn-outline-light btn-lg w-50">Cadastrar Professor</a>
            <a href="formularioGrupo" class="btn btn-outline-light btn-lg w-50">Cadastrar Grupo</a>
            <a href="formularioTrabalho" class="btn btn-outline-light btn-lg w-50">Cadastrar Trabalho</a>
            <a href="formularioAluno" class="btn btn-outline-light btn-lg w-50">Cadastrar Aluno</a>
            <a href="formularioApresentacao" class="btn btn-outline-light btn-lg w-50">Cadastrar Apresentação</a>
        </div>
    </div>
</body>
</html>
