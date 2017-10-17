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
	<c:import url="../cabecalho.jsp" />
	<div class="container">
		<h1 class="page-header titulo">Carrinho de compras</h1>
	</div>
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
		<div class="container">
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Itens adicionados
				</legend>
				<c:if test="${pedido.itensPedido eq null}">
					<h4>O carrinho est� vazio</h4>
				</c:if>
				<c:forEach items="${pedido.itensPedido}" var="itemPedido">
					<dl class="dl-horizontal">
						<dt>T�TULO</dt>
						<dd>${itemPedido.livro.titulo}</dd>
						<dt>QUANTIDADE</dt>
						<dd>${itemPedido.quantidade}</dd>
						<dt>Autor(es)</dt>
						<dd>
							<c:forEach items="${itemPedido.livro.autores}" var="autor" varStatus="status">
								<span>${autor.nome}<c:if test="${!status.last}">, </c:if></span>
							</c:forEach>
						</dd>
						<dt>Categoria(s)</dt>
						<dd>
							<c:forEach items="${itemPedido.livro.categorias}" var="categoria" varStatus="status">
								<span>${categoria.nome}<c:if test="${!status.last}">, </c:if></span>
							</c:forEach>
						</dd>
						<dt>Editora</dt>
						<dd>${itemPedido.livro.editora.nome}</dd>
						<dt>Pre�o unit�rio</dt>
						<dd>${itemPedido.livro.precificacao.precoVenda}</dd>	
						<dt>Pre�o do item</dt>
						<dd>${itemPedido.livro.precificacao.precoVenda * itemPedido.quantidade}</dd>
					</dl>
					<div class="row">
						<div class="col-xs-12">
							<button type="submit" data-toggle="tooltip" title="Alterar quantidade"
							class="btn btn-sm btn-default btn-icone" method="get" id="btnAlterarCarrinho"
							formaction="alterarCarrinho?operacao=CONSULTAR&id=${itemPedido.livro.id}">
								<span class="glyphicon glyphicon-pencil"></span>
							</button>		
													
							<button type="submit" name="operacao" method="get" data-toggle="tooltip"
							title="Remover do carrinho" onclick="return excluir()" id="btnRemoverCarrinho"
							class="btn btn-sm btn-danger botao-excluir btn-icone"
							formaction="carrinhoRemover?id=${itemPedido.livro.id}">
								<span class="glyphicon glyphicon-trash"></span>
							</button>
						</div>
					</div>
					</br>
				</c:forEach>
			</fieldset>
		</div>
	</form>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>