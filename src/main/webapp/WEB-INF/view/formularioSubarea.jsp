<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Cadastro de Subáreas</title>
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
		<form action="formularioSubarea" method="post"
			class="bg-dark d-flex flex-column align-items-center justify-content-center">
			<fieldset class="p-3 border">
				<legend class="text-light text-center display-6 fw-bold">Cadastro
					de Subárea</legend>

				<div class="mb-3">
					<input type="hidden" name="id" value="${subarea.id}" /> <label
						for="nome" class="form-label text-light">Nome da Subárea</label> <input
						id="input-nome" type="text" name="nome"
						class="form-control w-100 mb-2"
						placeholder="Digite o Nome da Subárea"
						value="<c:out value='${subarea.nome}'/>" onkeyup="checarInput()"
						onchange="checarInput()" /> <label for="area"
						class="form-label text-light">Área</label> <select
						id="select-area" name="area" class="form-select w-100"
						onchange="checarInput()">
						<option value="">Selecione uma Área</option>
						<c:forEach var="a" items="${areas}">
							<option value="${a.id}"
								<c:if test="${subarea.area != null && subarea.area.id == a.id}">selected</c:if>>
								${a.nome}</option>
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

		<c:if test="${not empty subAreas}">
			<table class="table table-dark table-striped mt-4">
				<thead>
					<tr>
						<th>Nome</th>
						<th>Area</th>
						<th>Excluir</th>
						<th>Editar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="s" items="${subAreas}">
						<tr>
							<td>${s.nome}</td>
							<td>${s.area.nome}</td>
							<td><a href="formularioSubarea?acao=excluir&id=${s.id}"
								class="btn btn-danger btn-sm">Excluir</a></td>
							<td><a href="formularioSubarea?acao=atualizar&id=${s.id}"
								class="btn btn-warning btn-sm">Editar</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>

	<script>
		function preencherNome(select) {
			document.getElementById('input-nome').value = select.value;
			checarInput({});
		}

		function preencherDescricao(select) {
			document.getElementById('select-area').value = select.value;
			checarInput({});
		}

		function checarInput(event) {
			if ((document.getElementById('input-nome').value).trim() == ''
					|| (document.getElementById('select-area').value).trim() == '') {
				document.getElementById("botao-inserir").disabled = true;
			} else {
				document.getElementById("botao-inserir").disabled = false;
			}
		}
		checarInput({});
	</script>

</body>
</html>
