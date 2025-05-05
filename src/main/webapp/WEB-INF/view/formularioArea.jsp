<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<!DOCTYPE html>
		<html lang="pt-BR">

		<head>
			<meta charset="UTF-8" />
			<title>Cadastro de Áreas</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
			<link href="resources/css/style.css" type="text/css" rel="stylesheet" />
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
			<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		</head>

		<body class="bg-dark">
			<div class="container">
				<div class="d-flex justify-content-start m-3">
					<a href="/Faculdade/" class="text-decoration-none text-light fw-bold">
						<img style="max-width: 50px"
							src="${pageContext.request.contextPath}/resources/assets/setinha.png" alt="Voltar" />
					</a>
				</div>

				<div class="d-flex flex-column align-items-center justify-content-center">
					<div class="card p-3">
						<form action="formularioArea" method="post" class="m-0 p-3">
							<fieldset>
								<div>
									<h1 class="text-center text-black fw-bold">Cadastro de Área</h1>
								</div>
								<div class="mb-3">
									<input type="hidden" name="id" />
									<label for="nome" class="form-label text-black d-block text-start ps-0">Nome da Área</label>
									<input id="input-nome" type="text" name="nome" class="form-control"
										placeholder="Digite o Nome da Área" value="<c:out value='${area.nome}'/>"
										onchange="checarInput(event)" onkeyup="checarInput(event)" />
								</div>

								<div class="mb-3">
									<label for="descricao" class="form-label text-black d-block text-start ps-0">Descrição</label>
									<textarea id="input-descricao" name="descricao" class="form-control"
										placeholder="Digite a Descrição da Área" rows="4" onchange="checarInput(event)"
										onkeyup="checarInput(event)"><c:out value='${area.descricao}'/></textarea>
								</div>

								<div class="d-flex justify-content-center gap-2">
									<button id="botao-inserir" type="submit" name="botao" value="Inserir"
										class="btn btn-success">
										Inserir
									</button>
									<button type="submit" name="botao" value="Atualizar"
										class="text-white btn btn-warning">
										Atualizar
									</button>
									<button type="submit" name="botao" value="Listar" class="btn btn-primary">
										Listar
									</button>
								</div>
							</fieldset>
						</form>
					</div>
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

					<c:if test="${not empty areas}">
						<table class="table table-dark table-striped mt-4">
							<thead>
								<tr>
									<th>Nome</th>
									<th>Descrição</th>
									<th>Excluir</th>
									<th>Editar</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="a" items="${areas}">
									<tr>
										<td>${a.nome}</td>
										<td>${a.descricao}</td>
										<td>
											<a href="formularioArea?acao=excluir&id=${a.id}"
												class="btn btn-danger btn-sm">Excluir</a>
										</td>
										<td>
											<a href="formularioArea?acao=atualizar&id=${a.id}"
												class="btn btn-warning btn-sm">Editar</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>

			<script>
				function checarInput(event) {
					if (
						document.getElementById("input-nome").value.trim() == "" ||
						document.getElementById("input-descricao").value.trim() == ""
					) {
						document.getElementById("botao-inserir").disabled = true;
					} else {
						document.getElementById("botao-inserir").disabled = false;
					}
				}
				checarInput({});
			</script>
		</body>

		</html>