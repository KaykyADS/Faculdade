<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Cadastro de Alunos</title>
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
		<form action="formularioAluno" method="post"
			class="bg-dark d-flex flex-column align-items-center justify-content-center">
			<fieldset class="p-3 border">
				<legend class="text-light text-center display-6 fw-bold">Cadastro
					de Aluno</legend>

				<div class="mb-3">
					<label for="ra" class="form-label text-light">RA</label> <input
						id="input-ra" type="text" name="ra" class="form-control w-100"
						placeholder="Digite o RA" value="<c:out value='${aluno.ra}'/>"
						onchange="checarInput(event)" onkeyup="checarInput(event)" />
				</div>

				<div class="mb-3">
					<label for="nome" class="form-label text-light">Nome do
						Aluno</label> <input id="input-nome" type="text" name="nome"
						class="form-control w-100" placeholder="Digite o Nome"
						value="<c:out value='${aluno.nome}'/>"
						onchange="checarInput(event)" onkeyup="checarInput(event)" />
				</div>

				<div class="mb-3">
					<label for="percentualConclusao" class="form-label text-light">%
						Conclusão</label> <input id="input-percentual" type="number"
						name="percentualConclusao" step="0.01" class="form-control w-100"
						placeholder="Percentual de conclusão"
						value="<c:out value='${aluno.percentualConclusao}'/>"
						onchange="checarInput(event)" onkeyup="checarInput(event)" />
				</div>

				<div class="mb-3">
					<label id="select-grupo" for="grupoId"
						class="form-label text-light">Grupo</label> <select
						id="grupo-input" name="grupoId" class="form-select"
						onkeyup="checarInput()" onchange="checarInput()">
						<option value="">Nenhum</option>
						<c:forEach var="g" items="${grupos}">
							<option value="${g.id}"
								<c:if test="${aluno.grupo != null && aluno.grupo.id == g.id}">selected</c:if>>${g.nome}</option>
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
					<button type="submit" name="botao" value="Buscar"
						class="btn btn-info w-50">Buscar</button>
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

		<c:if test="${not empty alunos}">
			<table class="table table-dark table-striped mt-4">
				<thead>
					<tr>
						<th>RA</th>
						<th>Nome</th>
						<th>% Conclusão</th>
						<th>Grupo</th>
						<th>Excluir</th>
						<th>Editar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="a" items="${alunos}">
						<tr>
							<td>${a.ra}</td>
							<td>${a.nome}</td>
							<td>${a.percentualConclusao}</td>
							<td>${a.grupo.nome}</td>
							<td><a href="formularioAluno?acao=excluir&ra=${a.ra}"
								class="btn btn-danger btn-sm">Excluir</a></td>
							<td><a href="formularioAluno?acao=atualizar&ra=${a.ra}"
								class="btn btn-warning btn-sm">Editar</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	<script>
		function checarInput(event) {
			const nome = document.getElementById('input-nome').value.trim();
			const ra = document.getElementById('input-ra').value.trim();
			const percentual = document.getElementById('input-percentual').value.trim();
			const grupo = document.getElementById('grupo-input').value.trim();

			if (nome === '' || ra === '' || percentual === '' || grupo === '') {
				document.getElementById("botao-inserir").disabled = true;
			} else {
				document.getElementById("botao-inserir").disabled = false;
			}
		}
		checarInput({});
	</script>
</body>
</html>
