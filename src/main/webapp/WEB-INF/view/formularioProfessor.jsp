<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Cadastro de Professor</title>
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
		<form action="formularioProfessor" method="post"
			class="bg-dark d-flex flex-column align-items-center justify-content-center">
			<fieldset class="p-3 border">
				<legend class="text-light text-center display-6 fw-bold">Cadastro
					de Professor</legend>

				<div class="mb-3">
					<input type="hidden" name="id" value="${professor.id}" /> <label
						for="nome" class="form-label text-light">Nome do Professor</label>
					<input id="input-nome" type="text" name="nome"
						class="form-control w-100"
						placeholder="Digite o Nome do Professor"
						value="<c:out value='${professor.nome}'/>"
						onchange="checarInput(event)" onkeyup="checarInput(event)">
				</div>

				<div class="mb-3">
					<label for="titulacao" class="form-label text-light">Titulação</label>
					<input id="input-titulacao" type="text" name="titulacao"
						class="form-control w-100"
						placeholder="Digite a Titulação do Professor"
						value="<c:out value='${professor.titulacao}'/>"
						onchange="checarInput(event)" onkeyup="checarInput(event)">
				</div>
				<div class="border rounded bg-dark p-2 mb-3"
					style="max-height: 200px; overflow-y: auto;" id="banca-container">
					<c:forEach var="a" items="${areas}">
						<div class="form-check m-0 d-flex align-items-center">
							<input class="form-check-input me-2" type="checkbox"
								name="areasIds" value="${a.id}" id="area-${a.id}"
								style="width: 18px; height: 18px;" onchange="checarInput()">
							<label class="form-check-label text-light small"
								for="area-${a.id}">${a.nome}</label>
						</div>
					</c:forEach>
				</div>
				<div class="d-flex gap-2">
					<button id="botao-inserir" type="submit" name="botao"
						value="Inserir" class="btn btn-success w-50">Inserir</button>
					<button type="submit" name="botao" value="Atualizar"
						class="btn btn-warning w-50">Atualizar</button>
					<button type="submit" name="botao" value="Listar"
						class="btn btn-secondary w-50">Listar</button>
					<button type="submit" name="botao" value="Buscar"
						class="btn btn-info w-50">Buscar Área</button>
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

		<c:if test="${not empty quantidade}">
			<div
				class="mt-3 w-auto mx-auto d-flex align-items-center justify-content-center"
				style="height: 100px;">
				<p class="text-light display-6 fw-bold mb-0">Quantidade de
					grupos: ${quantidade}</p>
			</div>
			<table class="table table-dark table-striped mt-4">
				<thead>
					<tr>
						<th>Nome do Grupo</th>
						<th>Orientador</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="g" items="${grupos}">
						<tr>
							<td>${g.nome}</td>
							<td>${g.orientador.nome}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

		<c:if test="${not empty professores}">
			<table class="table table-dark table-striped mt-4">
				<thead>
					<tr>
						<th>Nome</th>
						<th>Titulação</th>
						<th>Excluir</th>
						<th>Editar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${professores}">
						<tr>
							<td>${p.nome}</td>
							<td>${p.titulacao}</td>
							<td><a href="formularioProfessor?acao=excluir&id=${p.id}"
								class="btn btn-danger btn-sm">Excluir</a></td>
							<td><a href="formularioProfessor?acao=atualizar&id=${p.id}"
								class="btn btn-warning btn-sm">Editar</a></td>
							<td><a href="formularioProfessor?acao=quantidade&id=${p.id}"
								class="btn btn-warning btn-sm">Grupos</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>

	<script>
		function checarInput(event) {
			if ((document.getElementById('input-nome').value).trim() == ''
					|| (document.getElementById('input-titulacao').value)
							.trim() == '') {
				document.getElementById("botao-inserir").disabled = true;
			} else {
				document.getElementById("botao-inserir").disabled = false;
			}
		}
		checarInput({});
	</script>
</body>
</html>
