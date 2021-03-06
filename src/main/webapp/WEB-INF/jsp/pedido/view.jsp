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
		<h1 class="page-header titulo">Detalhes do pedido</h1>
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
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Informa��es dos produtos
				</legend>
				<div class="row">
					<fmt:setLocale value="pt-BR"/>
					<c:forEach items="${pedido.itensPedido}" var="item">
						<div class="col-xs-12 col-sm-6">
							<dl class="dl-horizontal">
								<dt>T�TULO</dt>
								<dd>
									${item.livro.titulo}
								</dd>
								<dt>AUTOR(ES)</dt>
								<c:forEach items="${item.livro.autores}" var="autor">
									<dd>${autor.nome}</dd>
								</c:forEach>
								<dt>EDI��O</dt>
								<dd>${item.livro.edicao}</dd>
								<dt>PRE�O UNIT�RIO</dt>
								<dd><fmt:formatNumber value="${item.livro.precificacao.precoVenda}" type="currency"/></dd>
								<dt>QUANTIDADE</dt>
								<dd>${item.quantidade}</dd>
								<dt>SUBTOTAL</dt>
								<dd>
									<fmt:formatNumber value="${item.subtotal}" type="currency"/>
								</dd>
							</dl>
						</div>
					</c:forEach>
				</div>
			</fieldset>
			
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-send"></span> Informa��es da entrega
				</legend>
				<div class="row">	
					<div class="col-xs-12 col-sm-6">
						<dl class="dl-horizontal">
							<dt>ENTREGA</dt>
							<dd>${pedido.enderecoEntrega.identificacao}</dd>
							<dt>ENDERE�O</dt>
							<dd>
								${pedido.enderecoEntrega.logradouro} n� ${pedido.enderecoEntrega.numero} ${pedido.enderecoEntrega.complemento},
								${pedido.enderecoEntrega.cidade}, ${pedido.enderecoEntrega.estado}, ${pedido.enderecoEntrega.pais} - 
								${pedido.enderecoEntrega.cep}
							</dd>
							<dt>VALOR DO FRETE</dt>
							<dd>
								<fmt:setLocale value="pt-BR"/>
								<fmt:formatNumber value="${pedido.frete.valor}" type="currency"/>
							</dd>
							<dt>PRAZO DE ENTREGA</dt>
							<dd>At� ${pedido.frete.diasEntrega} dias �teis (a partir da aprova��o do pagamento)</dd>
						</dl>
					</div>
					<div class="col-xs-12 col-sm-6">
						<dl class="dl-horizontal">
							<dt>COBRAN�A</dt>
							<dd>${pedido.enderecoEntrega.identificacao}</dd>
							<dt>ENDERE�O</dt>
							<dd>
								${pedido.enderecoEntrega.logradouro} n� ${pedido.enderecoEntrega.numero} ${pedido.enderecoEntrega.complemento},
								${pedido.enderecoEntrega.cidade}, ${pedido.enderecoEntrega.estado}, ${pedido.enderecoEntrega.pais} - 
								${pedido.enderecoEntrega.cep}
							</dd>
						</dl>
					</div>
				</div>
			</fieldset>
			
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-tags"></span> Informa��es do pagamento
				</legend>
				<p>
					OBS: Caso o valor dos cupons de troca ultrapasse o valor da compra,
					ser� gerado e vinculado � sua conta um novo cupom de troca com a diferen�a.
				</p>
				<div class="row">
					<c:forEach items="${pedido.formaPagamento.pagamentos}" var="pagamento">
						<c:if test="${pagamento.getClass().getSimpleName() eq 'PagamentoValeCompras'}">
							<div class="col-xs-4">
								<dl>					
									<dt>CUPOM DE TROCA</dt>
									<dd>${pagamento.cupomTroca.codigo}</dd>
									<dt>SALDO</dt>
									<dd>
										<fmt:setLocale value="pt-BR"/>
										<fmt:formatNumber value="${pagamento.cupomTroca.valor}" type="currency"/>
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
									<dt>CART�O DE CR�DITO</dt>
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
				<div class="row">
					<div class="col-xs-12">
						<dl>						
							<c:if test="${pedido.cupomPromocional != null}">
								<dt>CUPOM</dt>
								<dd>${pedido.cupomPromocional.codigo} - ${pedido.cupomPromocional.porcentagemDesconto}% de desconto</dd>
							</c:if>
							<dt>VALOR TOTAL</dt>
							<dd>
								<fmt:setLocale value="pt-BR"/>
								<fmt:formatNumber value="${pedido.valorTotal}" type="currency"/>							
							</dd>
						</dl>
					</div>
				</div>
			</fieldset>
			
			<div class="row">
				<div class="col-xs-12">
					<c:if test="${not empty pedido.itensPedido}">	
						<button type="submit" formaction="pedidoConfirmarCompra" name="operacao"
						value="SALVAR" id="btnPedidoConfirmarCompra" class="btn btn-primary">
							Confirmar compra
						</button>
					</c:if>
					<button type="submit" id="btnVoltarCarrinho" name="operacao"
					value="SALVAR" formaction="carrinhoPagamento" class="btn btn-default">
						Voltar ao pagamento
					</button>
				</div>
			</div>
		</div>
	</form>
	</br>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>