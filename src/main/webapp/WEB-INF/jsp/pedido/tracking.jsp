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
		<h1 class="page-header titulo">Acompanhamento do pedido</h1>
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
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Informações da compra
				</legend>
				<div class="row">
					<fmt:setLocale value="pt-BR"/>
					<div class="col-xs-12 col-sm-6">
						<dl class="dl-horizontal">
							<dt>Nº DO PEDIDO</dt>
							<dd>${pedido.numero}</dd>
							<dt>VALOR TOTAL</dt>
							<dd><fmt:formatNumber value="${pedido.valorTotal}" type="currency"/></dd>
							<c:if test="${login.perfilAcesso.nome eq 'Administrador' }">
								<dt>CLIENTE</dt>
								<dd>${pedido.cliente.nome}</dd>
							</c:if>
							<dt>DATA DA COMPRA</dt>
							<dd><fmt:formatDate value="${pedido.dataCadastro}" pattern="dd/MM/yyyy"/></dd>
							<dt>ENTREGA (PREVISÃO)</dt>
							<dd><fmt:formatDate value="${pedido.frete.prazoEstimado}" pattern="dd/MM/yyyy"/></dd>
							<dt>STATUS</dt>
							<c:forEach items="${pedido.statusesPedido}" var="statusPedido">
								<c:if test="${not statusPedido.atual}">
									<dd>
										${statusPedido.status.nome} - 
										<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
									</dd>
								</c:if>
								<c:if test="${statusPedido.atual}">
									<c:if test="${statusPedido.status.nome eq 'Em processamento'}">
										<dd>
											${statusPedido.status.nome} -
											<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
										<c:if test="${login.perfilAcesso.nome eq 'Administrador'}">
											<dd>
												<a href="statusSalvar?operacao=SALVAR&status=Aprovada&idPedido=${pedido.id}">Aprovar pedido</a>
											</dd>
										</c:if>
									</c:if>
									<c:if test="${statusPedido.status.nome eq 'Aprovada'}">
										<dd>
											${statusPedido.status.nome} - 
											<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
										<c:if test="${login.perfilAcesso.nome eq 'Administrador'}">
											<dd>
												<a href="statusSalvar?operacao=SALVAR&status=Em transporte&idPedido=${pedido.id}">
													Liberar para entrega
												</a>
											</dd>
										</c:if>
									</c:if>
									<c:if test="${statusPedido.status.nome eq 'Reprovada'}">
										<dd>
											${statusPedido.status.nome} - 
											<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
									</c:if>
									<c:if test="${statusPedido.status.nome eq 'Em transporte'}">
										<dd>
											${statusPedido.status.nome} - 
											<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
										<c:if test="${login.perfilAcesso.nome eq 'Administrador'}">
											<dd>
												<a href="statusSalvar?operacao=SALVAR&status=Entregue&idPedido=${pedido.id}">
													Confirmar entrega
												</a>
											</dd>
										</c:if>
									</c:if>
									<c:if test="${statusPedido.status.nome eq 'Entregue'}">
										<dd>
											${statusPedido.status.nome} - 
											<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
										</dd>
										<dd>
											<a href="pedidoTroca?operacao=CONSULTAR&idPedido=${pedido.id}&idCliente=${pedido.cliente.id}">
												Solicitar troca
											</a>
										</dd>
									</c:if>
									<c:if test="${statusPedido.status.nome eq 'Em troca'}">
										<dd>
											${statusPedido.status.nome} - 
											<fmt:formatDate value="${statusPedido.dataCadastro}" pattern="dd/MM/yyyy"/>
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
					<span class="legend-logo glyphicon glyphicon-book"></span> Informações dos produtos
				</legend>
				<div class="row">
					<fmt:setLocale value="pt-BR"/>
					<c:forEach items="${pedido.itensPedido}" var="item">
						<div class="col-xs-12 col-sm-6">
							<dl class="dl-horizontal">
								<dt>TÍTULO</dt>
								<dd>
									${item.livro.titulo}
								</dd>
								<dt>AUTOR(ES)</dt>
								<c:forEach items="${item.livro.autores}" var="autor">
									<dd>${autor.nome}</dd>
								</c:forEach>
								<dt>EDIÇÃO</dt>
								<dd>${item.livro.edicao}</dd>
								<dt>QUANTIDADE</dt>
								<dd>${item.quantidade}</dd>
							</dl>
						</div>
					</c:forEach>
				</div>
			</fieldset>
			
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-send"></span> Informações da entrega
				</legend>
				<div class="row">	
					<div class="col-xs-12 col-sm-6">
						<dl class="dl-horizontal">
							<dt>ENTREGA</dt>
							<dd>${pedido.enderecoEntrega.identificacao}</dd>
							<dt>ENDEREÇO</dt>
							<dd>
								${pedido.enderecoEntrega.logradouro} nº ${pedido.enderecoEntrega.numero} ${pedido.enderecoEntrega.complemento},
								${pedido.enderecoEntrega.cidade}, ${pedido.enderecoEntrega.estado}, ${pedido.enderecoEntrega.pais} - 
								${pedido.enderecoEntrega.cep}
							</dd>
						</dl>
					</div>
					<div class="col-xs-12 col-sm-6">
						<dl class="dl-horizontal">
							<dt>COBRANÇA</dt>
							<dd>${pedido.enderecoEntrega.identificacao}</dd>
							<dt>ENDEREÇO</dt>
							<dd>
								${pedido.enderecoEntrega.logradouro} nº ${pedido.enderecoEntrega.numero} ${pedido.enderecoEntrega.complemento},
								${pedido.enderecoEntrega.cidade}, ${pedido.enderecoEntrega.estado}, ${pedido.enderecoEntrega.pais} - 
								${pedido.enderecoEntrega.cep}
							</dd>
						</dl>
					</div>
				</div>
			</fieldset>
			
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-usd"></span> Informações do pagamento
				</legend>
				<div class="row">
					<c:forEach items="${pedido.formaPagamento.pagamentos}" var="pagamento">
						<c:if test="${pagamento.getClass().getSimpleName() eq 'PagamentoValeCompras'}">
							<div class="col-xs-4">
								<dl>					
									<dt>VALE-COMPRAS</dt>
									<dd>${pagamento.valeCompras.codigo}</dd>
									<dt>SALDO</dt>
									<dd>
										<fmt:setLocale value="pt-BR"/>
										<fmt:formatNumber value="${pagamento.valeCompras.valor}" type="currency"/>
									</dd>
									<dt>PAGAMENTO (R$)</dt>
									<dd>
										<fmt:setLocale value="pt-BR"/>
										<fmt:formatNumber value="${pagamento.valorPago}" type="currency"/>
									</dd>
								</dl>
							</div>
						</c:if>
						<c:if test="${pagamento.getClass().getSimpleName() eq 'PagamentoCartao'}">
							<div class="col-xs-4">
								<dl>					
									<dt>CARTÃO DE CRÉDITO</dt>
									<dd>
										${pagamento.cartaoCredito.bandeira.nome} - 
										${fn:substring(pagamento.cartaoCredito.numero, 0, 4)}
										<c:set var="asteriscos" value=""/>
										<c:forEach begin="1" end="${fn:length(pagamento.cartaoCredito.numero) - 8}">
											<c:out value="*"/>
										</c:forEach>
										${fn:substring(pagamento.cartaoCredito.numero,
										fn:length(pagamento.cartaoCredito.numero) - 4,
										fn:length(pagamento.cartaoCredito.numero))}
									</dd>
									<dt>PAGAMENTO (R$)</dt>
									<dd>
										<fmt:setLocale value="pt-BR"/>
										<fmt:formatNumber value="${pagamento.valorPago}" type="currency"/>
									</dd>
								</dl>
							</div>
						</c:if>
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