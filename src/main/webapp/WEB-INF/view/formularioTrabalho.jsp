<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Cadastro de Trabalhos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="resources/css/style.css" type="text/css" rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body class="bg-dark">
	<div class="d-flex justify-content-start m-3">
		<a href="/Faculdade/" class="text-decoration-none text-light fw-bold">
			<img style="max-width: 50px;"
			src="${pageContext.request.contextPath}/resources/assets/setinha.png"
			alt="Voltar">
		</a>
	</div>

	<div class="container m-auto">
		<form action="formularioTrabalho" method="post"
			class="bg-dark d-flex flex-column align-items-center justify-content-center">
			<fieldset class="p-3 border">
				<legend class="text-light text-center display-6 fw-bold">Cadastro
					de Trabalho</legend>

				<input type="hidden" name="id" value="${trabalho.id}" />

				<div class="mb-3">
					<label class="form-label text-light" for="titulo">Título</label> <input
						type="text" class="form-control w-100" id="titulo-input"
						name="titulo" placeholder="Digite o título"
						value="<c:out value='${trabalho.titulo}'/>" />
				</div>

				<div class="mb-3">
					<label class="form-label text-light" for="area">Área</label> <select
						class="form-select" name="areaId" id="area-input">
						<option value="">Selecione</option>
						<c:forEach var="a" items="${areas}">
							<option value="${a.id}"
								<c:if test="${trabalho.area.id == a.id}">selected</c:if>>${a.nome}</option>
						</c:forEach>
					</select>
				</div>

				<div class="mb-3">
					<label class="form-label text-light" for="subarea">Subárea</label>
					<select class="form-select" name="subareaId" id="subarea-input">
						<option value="">Selecione</option>
						<c:forEach var="s" items="${subareas}">
							<option value="${s.id}"
								<c:if test="${trabalho.subarea.id == s.id}">selected</c:if>>${s.nome}</option>
						</c:forEach>
					</select>
				</div>

				<div class="mb-3">
					<label class="form-label text-light" for="grupo">Grupo</label> <select
						class="form-select" name="grupoId" id="grupo-input">
						<option value="">Selecione</option>
						<c:forEach var="g" items="${grupos}">
							<option value="${g.id}"
								<c:if test="${trabalho.grupo.id == g.id}">selected</c:if>>${g.nome}</option>
						</c:forEach>
					</select>
				</div>

				<div class="d-flex gap-2">
					<button id="botao-inserir" type="submit" name="botao"
						value="Inserir" class="btn btn-success w-50">Inserir</button>
					<button type="submit" name="botao" value="Atualizar"
						class="btn btn-warning w-50">Atualizar</button>
					<button type="submit" name="botao" value="Listar"
						class="btn btn-secondary w-50">Listar</button>
				</div>
			</fieldset>
		</form>

		<c:if test="${not empty saida}">
			<div class="mt-3 alert alert-primary w-100 mx-auto">
				<c:out value="${saida}" />
			</div>
		</c:if>

		<c:if test="${not empty erro}">
			<div class="mt-3 alert alert-danger w-auto mx-auto">
				<c:out value="${erro}" />
			</div>
		</c:if>

		<c:if test="${not empty trabalhos}">
			<table class="table table-dark table-striped mt-4">
				<thead>
					<tr>
						<th>Título</th>
						<th>Área</th>
						<th>Subárea</th>
						<th>Grupo</th>
						<th>Excluir</th>
						<th>Editar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="t" items="${trabalhos}">
						<tr>
							<td>${t.titulo}</td>
							<td>${t.area.nome}</td>
							<td>${t.subarea.nome}</td>
							<td>${t.grupo.nome}</td>
							<td><a href="formularioTrabalho?acao=excluir&id=${t.id}"
								class="btn btn-danger btn-sm">Excluir</a></td>
							<td><a href="formularioTrabalho?acao=atualizar&id=${t.id}"
								class="btn btn-warning btn-sm">Editar</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	<script>
		function checarInput(event) {
			if ((document.getElementById('titulo-input').value).trim() == ''
					|| (document.getElementById('area-input').value).trim() == ''
					|| (document.getElementById('subarea-input').value).trim() == ''
					|| (document.getElementById('grupo-input').value).trim() == '') {
				document.getElementById("botao-inserir").disabled = true;
			} else {
				document.getElementById("botao-inserir").disabled = false;
			}
		}

		document.getElementById('titulo-input').addEventListener('input',
				checarInput);
		document.getElementById('area-input').addEventListener('change',
				checarInput);
		document.getElementById('subarea-input').addEventListener('change',
				checarInput);
		document.getElementById('grupo-input').addEventListener('change',
				checarInput);

		checarInput();
	</script>
</body>
</html>
