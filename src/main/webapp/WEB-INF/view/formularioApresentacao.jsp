<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<!DOCTYPE html>
		<html lang="pt-BR">

		<head>
			<meta charset="UTF-8">
			<title>Cadastro de Apresentações</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
			<link href="resources/css/style.css" type="text/css" rel="stylesheet">
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
			<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		</head>

		<body class="bg-dark">
			<div class="d-flex justify-content-start m-3">
				<a href="/Faculdade/" class="text-decoration-none text-light fw-bold">
					<img style="max-width: 50px;" src="${pageContext.request.contextPath}/resources/assets/setinha.png"
						alt="Voltar">
				</a>
			</div>

			<div class="container m-auto">
				<form action="formularioApresentacao" method="post"
					class="bg-dark d-flex flex-column align-items-center justify-content-center">
					<fieldset class="p-3 border">
						<legend class="text-light text-center display-6 fw-bold">Cadastro de Apresentação</legend>

						<input type="hidden" name="id" value="${apresentacao.id}" />

						<div class="mb-3">
							<label class="form-label text-light">Grupo</label>
							<select name="grupoId" class="form-select w-100" id="grupo-input" onchange="checarInput()">
								<option value="">Selecione um grupo</option>
								<c:forEach var="g" items="${grupos}">
									<option value="${g.id}" <c:if test="${g.id == apresentacao.grupo.id}">selected
										</c:if>>
										${g.nome}
									</option>
								</c:forEach>
							</select>
						</div>

						<div class="mb-3">
							<label class="form-label text-light">Data da Apresentação</label>
							<input type="date" name="data" class="form-control w-100" id="data-input"
								value="${apresentacao.data}" onchange="checarInput()" />
						</div>

						<div class="mb-3">
							<label class="form-label text-light">Tipo de TCC</label>
							<select name="tipoTcc" class="form-select w-100" id="tipo-input" onchange="checarInput()">
								<option value="">Selecione</option>
								<option value="TCC1" <c:if test="${apresentacao.tipoTcc == 'TCC1'}">selected</c:if>>TCC1
								</option>
								<option value="TCC2" <c:if test="${apresentacao.tipoTcc == 'TCC2'}">selected</c:if>>TCC2
								</option>
							</select>
						</div>

						<div class="mb-3">
							<label class="form-label text-light">Nota</label>
							<input type="number" step="0.01" name="nota" class="form-control w-100" id="nota-input"
								value="${apresentacao.nota}" onchange="checarInput()" />
						</div>

						<label class="form-label text-light small mb-2">Banca (Professores)</label>
						<div class="border rounded bg-dark p-2 mb-3" style="max-height: 200px; overflow-y: auto;"
							id="banca-container">
							<c:forEach var="p" items="${professores}">
								<div class="form-check m-0 d-flex align-items-center">
									<input class="form-check-input me-2" type="checkbox" name="bancaIds" value="${p.id}"
										id="prof-${p.id}" style="width: 18px; height: 18px;" onchange="checarInput()">
									<label class="form-check-label text-light small"
										for="prof-${p.id}">${p.nome}</label>
								</div>
							</c:forEach>
						</div>
						<div class="d-flex gap-2">
							<button id="botao-inserir" type="submit" name="botao" value="Inserir"
								class="btn btn-success w-50">Inserir</button>
							<button type="submit" name="botao" value="Atualizar"
								class="btn btn-warning w-50">Atualizar</button>
							<button type="submit" name="botao" value="Listar"
								class="btn btn-secondary w-50">Listar</button>
							<button type="submit" name="botao" value="Buscar"
								class="btn btn-info w-50">Hoje</button>
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

				<c:if test="${not empty apresentacoes}">
					<table class="table table-dark table-striped mt-4">
						<thead>
							<tr>
								<th>Grupo</th>
								<th>Data</th>
								<th>Tipo</th>
								<th>Nota</th>
								<th>Excluir</th>
								<th>Editar</th>
								<th>Banca</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="a" items="${apresentacoes}">
								<tr>
									<td>${a.grupo.nome}</td>
									<td>${a.data}</td>
									<td>${a.tipoTcc}</td>
									<td>${a.nota}</td>
									<td><a href="formularioApresentacao?acao=excluir&id=${a.id}"
											class="btn btn-danger btn-sm">Excluir</a></td>
									<td><a href="formularioApresentacao?acao=atualizar&id=${a.id}"
											class="btn btn-warning btn-sm">Editar</a></td>
									<td><a href="formularioApresentacao?acao=banca&id=${a.id}"
											class="btn btn-info btn-sm">Banca</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${not empty professoresBanca}">
					<legend class="text-light text-center display-6 fw-bold">Banca da Apresentação</legend>
					<table class="table table-secondary table-striped">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Titulação</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pb" items="${professoresBanca}">
								<tr>
									<td>${pb.nome}</td>
									<td>${pb.titulacao}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>

			<script>
				function checarInput() {
					const grupo = document.getElementById('grupo-input').value.trim();
					const tipo = document.getElementById('tipo-input').value.trim();
					const data = document.getElementById('data-input').value.trim();

					const checkboxes = document.querySelectorAll('#banca-container input[type="checkbox"]');
					const algumSelecionado = Array.from(checkboxes).some(cb => cb.checked);

					document.getElementById("botao-inserir").disabled = !(grupo && tipo && data && algumSelecionado);
				}

				// Executar ao carregar
				checarInput();
			</script>
		</body>

		</html>