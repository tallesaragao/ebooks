<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		<h1 class="page-header titulo">Acompanhamento da troca</h1>
	</div>
	<div class="container">
		<c:if test="${mensagens != null}">
			<div class="row">
				<div class="col-sm-8 col-md-5">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-alert"></span><strong> Problema(s) detectados:</strong>
						</br>
						<c:forEach var="mensagem" items="${mensagens}">
							${mensagem}.
							</br>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
	<form action="#" method="post">
		<div class="container">
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-transfer"></span> Informações da troca
				</legend>
				<div class="row">
					<fmt:setLocale value="pt-BR"/>
					<div class="col-xs-12 col-sm-6">
						<dl class="dl-horizontal">
							<dt>Nº DO PEDIDO</dt>
							<dd>${troca.pedido.numero}</dd>
							<c:if test="${login.perfilAcesso.nome eq 'Administrador' }">
								<dt>CLIENTE</dt>
								<dd>${troca.pedido.cliente.nome}</dd>
							</c:if>
							<dt>DATA SOLICITAÇÃO</dt>
							<dd><fmt:formatDate value="${troca.dataCadastro}" pattern="dd/MM/yyyy"/></dd>
							<dt>STATUS</dt>
							<c:forEach items="${troca.statusesTroca}" var="statusTroca">
								<c:if test="${not statusTroca.atual}">
									<dd>
										${statusTroca.status.nome} - 
										<fmt:formatDate value="${statusTroca.dataCadastro}" pattern="dd/MM/yyyy"/>
									</dd>
								</c:if>
								<c:if test="${statusTroca.atual}">
									<c:if test="${statusTroca.status.nome eq 'Em troca'}">
										<dd>
											${statusTroca.status.nome} - 
											<fmt:formatDate value="${statusTroca.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
										<c:if test="${login.perfilAcesso.nome eq 'Administrador'}">
											<dd>
												<a href="trocaAprovar?operacao=CONSULTAR&idTroca=
												${troca.id}&idCliente=${troca.cliente.id}">
													Aprovar troca
												</a>
											</dd>
										</c:if>
									</c:if>
									<c:if test="${statusTroca.status.nome eq 'Trocado'}">
										<dd>
											${statusTroca.status.nome} - 
											<fmt:formatDate value="${statusTroca.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
										<fmt:setLocale value="pt-BR"/>
										<dd>
											OBS: Foi gerado um cupom de troca, com código ${troca.cupomTroca.codigo}
											e valor de <fmt:formatNumber value="${troca.cupomTroca.valor}" type="currency"/>,
											podendo ser utilizado no pagamento de compras futuras.
										</dd>
									</c:if>
								</c:if>
							</c:forEach>
						</dl>
					</div>
				</div>
			</fieldset>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-book"></span> Informações dos itens a trocar
				</legend>
				<div class="row">
					<fmt:setLocale value="pt-BR"/>
					<c:forEach items="${troca.itensTroca}" var="item">
						<div class="col-xs-12 col-sm-6">
							<dl class="dl-horizontal">
								<dt>TÍTULO</dt>
								<dd>
									${item.itemPedido.livro.titulo}
								</dd>
								<dt>AUTOR(ES)</dt>
								<c:forEach items="${item.itemPedido.livro.autores}" var="autor">
									<dd>${autor.nome}</dd>
								</c:forEach>
								<dt>EDIÇÃO</dt>
								<dd>${item.itemPedido.livro.edicao}</dd>
								<dt>QTDE. A TROCAR</dt>
								<dd>${item.quantidadeTrocada}</dd>
							</dl>
						</div>
					</c:forEach>
				</div>
			</fieldset>
		</div>
	</form>
	</br>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>