<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Livraria Online</title>
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="resources/css/ebooks.css">
</head>
<body>
	<c:import url="../cabecalho.jsp"/>
	<div class="container">
		<h1 class="page-header titulo">Lista de livros</h1>
		<c:if test="${sucesso != null}">
			<div class="row">
				<div class="col-md-5">
					<div class="alert alert-success alert-dismissible">
						<span class="glyphicon glyphicon-ok"></span>
						<strong>${sucesso}.</strong>				
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</div>
			</div>
		</c:if>
			
		<c:if test="${erro != null}">
			<div class="row">
				<div class="col-md-5">
					<div class="alert alert-danger alert-dismissible">
						<span class="glyphicon glyphicon-alert"></span>
						<strong>${erro}.</strong>
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</div>
			</div>
		</c:if>
		<form action="#" method="post">
			<fieldset>
				<legend>Faça sua pesquisa</legend>
				<div class="row">
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="titulo" class="control-label">Título</label>
							<input type="text" name="titulo" placeholder="Título" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-4">						
						<div class="form-group">
							<label for="codigo" class="control-label">Código</label>
							<input type="text" name="codigo" placeholder="Código" class="form-control"/>	
						</div>			
					</div>
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="isbn" class="control-label">ISBN</label>
							<input type="text" name="isbn" placeholder="ISBN" class="form-control"/>
						</div>				
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="categoria" class="control-label">Categoria</label>
							<input type="text" name="categoria" placeholder="Categoria" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="autor" class="control-label">Autor</label>
							<input type="text" name="autor" placeholder="Autor" class="form-control"/>	
						</div>			
					</div>
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="editora" class="control-label">Editora</label>
							<input type="text" name="editora" placeholder="Editora" class="form-control"/>		
						</div>		
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-2">							
							<button type="submit" class="btn btn-md btn-primary" id="btnPesquisar"
							name="operacao" value="CONSULTAR" formaction="livroConsultar">
								<span class="glyphicon glyphicon-search"></span> Pesquisar
							</button>
						</div>
						<c:if test="${login.perfilAcesso.nome eq 'Administrador'}">
							<div class="col-xs-6 col-md-2 btn-adicionar">			  	
								<button class="btn btn-primary" type="submit" id="btnNovoLivro" formaction="livroForm">
									<span class="glyphicon glyphicon-plus"></span> Novo Livro
								</button>
							</div>
						</c:if>
					</div>
				</div>
				</div>
			</fieldset>
			<c:if test="${not empty consulta}">
				<div class="row">
					<div class="table-responsive tabela-clientes">
						<div class="col-sm-11 col-sm-12 col-md-12">
							<table class="table table-striped table-condensed">
								<thead>
									<tr>
										<th>ISBN</th>
										<th>TÍTULO</th>
										<th>AUTOR</th>
										<th>CATEGORIA</th>
										<th>EDITORA</th>
										<th>PREÇO (R$)</th>
										<th>ESTOQUE</th>
										<th>
											<span class="glyphicon glyphicon-cog icone-engrenagem"></span> AÇÕES
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${consulta}" var="livro">
										<tr>
											<td>${livro.isbn}</td>
											<td>${livro.titulo}</td>
											<td>
												<c:forEach items="${livro.autores}" var="autor" varStatus="status">
													<span>${autor.nome}<c:if test="${!status.last}">, </c:if></span>
												</c:forEach>
											</td>
											<td>
												<c:forEach items="${livro.categorias}" var="categoria" varStatus="status">
													<span>${categoria.nome}<c:if test="${!status.last}">, </c:if></span>
												</c:forEach>
											</td>
											<td>${livro.editora.nome}</td>
											<td>
												<fmt:setLocale value="pt-BR"/>
												<fmt:formatNumber value="${livro.precificacao.precoVenda}" type="currency"/>
											</td>
											<td>${livro.estoque.quantidadeAtual - livro.estoque.quantidadeReservada}</td>										
											<td>
												<button type="submit" data-toggle="tooltip" title="Editar" id="btnEdit${livro.id}"
												class="btn btn-sm btn-default btn-icone" method="get"
												formaction="livroEdit?operacao=CONSULTAR&id=${livro.id}">
													<span class="glyphicon glyphicon-pencil"></span>
												</button>
												<button type="submit" data-toggle="tooltip" title="Adicionar ao carrinho"
												id="btnCarrinho${livro.id}" class="btn btn-sm btn-primary btn-icone" method="get"
												formaction="carrinhoAdicionar?operacao=SALVAR&id=${livro.id}">
													<span class="glyphicon glyphicon-shopping-cart"></span>
												</button>
												<button type="submit" name="operacao" method="get" data-toggle="tooltip" id="btnExcluir${livro.id}"
												title="Excluir" value="EXCLUIR"	onclick="return excluir()"
												class="btn btn-sm btn-danger botao-excluir btn-icone" formaction="livroExcluir?id=${livro.id}">
													<span class="glyphicon glyphicon-trash"></span>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>		
		</form>
		
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>