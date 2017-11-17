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
		<h2 class="page-header titulo">Solicita��o de troca</h2>
		<c:if test="${mensagens != null}">
			<div class="row">
				<div class="col-sm-8 col-md-5">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-alert"></span><strong> Problema(s) na inser��o:</strong>
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
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Informa��es do pedido
				</legend>
				<input type="hidden" name="id" value="${pedido.id}"/>
				<div class="row">
					<div class="col-xs-12">
						<dl>
							<dt>N�mero do pedido</dt>
							<dd>${pedido.numero}</dd>
							<dd>
								<div class="checkbox">
									<label for="selecionarCompra" class="control-label">
										<input type="checkbox" id="selecionarCompra" name="compraToda" value="true"/>
										Trocar toda a compra						
									</label>
								</div>
							</dd>
						</dl>
					</div>
				</div>
			</fieldset>
			<fieldset id="fsItens">
				<legend>
					<span class="legend-logo glyphicon glyphicon-book"></span> Informa��es dos itens
				</legend>
				<p>Selecione os itens a serem trocados e informe as quantidades que deseja trocar</p>
				<c:forEach items="${pedido.itensPedido}" var="item">
					<input type="hidden" name="idItem" value="${item.id}"/>
					<div class="row">
						<div class="col-xs-6 col-md-4">
							<div class="checkbox">
								<label for="selecionarItem${item.id}" class="control-label">
									<input type="checkbox" id="selecionarItem${item.id}"
									name="item${item.id}" value="true"/> ${item.livro.titulo} 
									- (${item.quantidade} un) - <fmt:formatNumber value="${item.subtotal}" type="currency"/>					
								</label>
							</div>
						</div>
						<div class="col-xs-2">
							<div class="form-group">
								<label for="quantidade${item.id}" class="control-label">Quantidade</label>
								<input type="number" name="quantidade${item.id}" class="form-control" min="1"
								max=${item.quantidade } value="${item.quantidade}">
							</div>
						</div>
					</div>
				</c:forEach>				
			<div class="row">
				<div class="col-xs-12">				
					<button type="submit" name="operacao" value="SALVAR" formaction="trocaSalvar" class="btn btn-success">
						Confirmar troca
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