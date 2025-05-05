<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Cadastro de Grupo</title>
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
		<form action="formularioGrupo" method="post"
			class="bg-dark d-flex flex-column align-items-center justify-content-center">
			<fieldset class="p-3 border">
				<div class="mb-3">
					<label for="codigo" class="form-label text-light">Buscar
						pelo Nome de aluno</label>
					<div class="d-flex gap-2">
						<input type="text" id="nome-aluno" name="aluno-nome"
							class="form-control" placeholder="Nome do Aluno">
						<button type="submit" name="botao" value="Buscar"
							class="btn btn-secondary w-25">Buscar</button>
					</div>
				</div>
				<div class="mb-3">
					<label for="codigo" class="form-label text-light">Buscar
						pelo Nome do Professor</label>
					<div class="d-flex gap-2">
						<input type="text" id="nome-professor" name="professor-nome"
							class="form-control" placeholder="Nome do Professor">
						<button type="submit" name="botao" value="Buscar"
							class="btn btn-secondary w-25">Buscar</button>
					</div>
				</div>
				<div class="mb-3">
					<label class="form-label text-light">Buscar por Data</label>
					<div class="d-flex gap-2">
						<input type="date" name="data" class="form-control w-100"
							id="data-input" />
						<button type="submit" name="botao" value="Buscar"
							class="btn btn-secondary w-25">Buscar</button>
					</div>
				</div>
				<legend class="text-light text-center display-6 fw-bold">Cadastro
					de Grupo</legend>

				<div class="mb-3">
					<input type="hidden" name="id" value="${grupo.id}" /> <label
						for="nome" class="form-label text-light">Nome do Grupo</label> <input
						id="input-nome" type="text" name="nome" class="form-control w-100"
						placeholder="Digite o Nome do Grupo"
						value="<c:out value='${grupo.nome}'/>"
						onchange="checarInput(event)" onkeyup="checarInput(event)">
				</div>
				<div class="mb-3">
					<label for="orientador" class="form-label text-light">Orientador</label>
					<select id="orientador" name="orientadorId"
						class="form-select w-100">
						<option value="">Selecione o Orientador</option>
						<c:forEach var="prof" items="${professores}">
							<option value="${prof.id}">${prof.nome}</option>
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

		<c:if test="${not empty grupos}">
			<table class="table table-dark table-striped mt-4">
				<thead>
					<tr>
						<th>Nome do Grupo</th>
						<th>Orientador</th>
						<th>Excluir</th>
						<th>Editar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="g" items="${grupos}">
						<tr>
							<td>${g.nome}</td>
							<td>${g.orientador.nome}</td>
							<td><a href="formularioGrupo?acao=excluir&id=${g.id}"
								class="btn btn-danger btn-sm">Excluir</a></td>
							<td><a href="formularioGrupo?acao=atualizar&id=${g.id}"
								class="btn btn-warning btn-sm">Editar</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>

	<script>
		function checarInput(event) {
			if ((document.getElementById('input-nome').value).trim() == '') {
				document.getElementById("botao-inserir").disabled = true;
			} else {
				document.getElementById("botao-inserir").disabled = false;
			}
		}
		checarInput({});
	</script>

</body>
</html>
