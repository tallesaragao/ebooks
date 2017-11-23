<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<h2 class="page-header titulo">Aprovação de troca</h2>
		<c:if test="${mensagens != null}">
			<div class="row">
				<div class="col-sm-8 col-md-5">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-alert"></span><strong> Problema(s) na inserção:</strong>
						</br>
						<c:forEach var="mensagem" items="${mensagens}">
							${mensagem}.
							</br>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
		<form class="form" action="#" method="post">
			<input type="hidden" name="idTroca" value="${troca.id}"/>
			<input type="hidden" name="idCliente" value="${troca.cliente.id}"/>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Informações do pedido
				</legend>
				<input type="hidden" name="id" value="${troca.id}"/>
				<div class="row">
					<div class="col-xs-12">
						<dl>
							<dt>Número do pedido</dt>
							<dd>${troca.pedido.numero}</dd>
						</dl>
					</div>
				</div>
			</fieldset>
			<fieldset id="fsItens">
				<legend>
					<span class="legend-logo glyphicon glyphicon-book"></span> Informações dos itens
				</legend>
				<p>Informe as quantidades dos itens que devem retornar ao estoque:</p>
				<c:forEach items="${troca.itensTroca}" var="item">
					<input type="hidden" name="idItem" value="${item.id}"/>
					<div class="row">
						<div class="col-xs-6 col-md-4">
							<label for="selecionarItem${item.id}" class="control-label">
								Item de troca
							</label>
							<p name="selecionarItem${item.id}">
								${item.itemPedido.livro.titulo} - (${item.quantidadeTrocada} un)
							</p>
						</div>
						<div class="col-xs-2">
							<div class="form-group">
								<label for="quantidade${item.id}" class="control-label">Quantidade</label>
								<input type="number" min="0" max="${item.quantidadeTrocada}" class="form-control selectItem"
								id="quantidade${item.id}" name="quantidade${item.id}" 
								value="${item.quantidadeTrocada}">
							</div>
						</div>
					</div>
				</c:forEach>
			</fieldset>
			<input type="hidden" name="status" value="Trocado"/>	
			<div class="row">
				<div class="col-xs-12">				
					<button type="submit" name="operacao" value="SALVAR" formaction="statusTrocaSalvar" class="btn btn-success">
						Aprovar troca
					</button>
					<button class="botao-voltar btn btn-default" type="button">Cancelar</button>
				</div>
			</div>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>