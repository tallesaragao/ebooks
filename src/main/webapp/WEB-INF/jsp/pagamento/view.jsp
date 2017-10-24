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
	<div class="container">
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
	</div>
	
	<form action="#" method="post">
		<div class="container">
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Detalhes do pedido
				</legend>
				<div class="row">
					<div class="col-xs-12">
						<dl>
							<dt>PRODUTOS</dt>
							<fmt:setLocale value="pt-BR"/>
							<c:forEach items="${pedido.itensPedido}" var="item">
								<dd>
									${item.livro.titulo} (${item.quantidade} un) - 
									<fmt:formatNumber value="${item.subtotal}" type="currency"/>
								</dd>
							</c:forEach>
							<dt>FRETE</dt>
							<dd>
								<fmt:setLocale value="pt-BR"/>
								<fmt:formatNumber value="${pedido.frete.valor}" type="currency"/>
								- Até ${pedido.frete.diasEntrega} dias úteis para (${pedido.enderecoEntrega.identificacao})						
							</dd>
							<dt>VALOR TOTAL</dt>
							<dd>
								<fmt:formatNumber value="${pedido.valorTotal}" type="currency"/>
							</dd>
						</dl>
					</div>
				</div>
			</fieldset>
			
			<fieldset>
			
			</fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-tags"></span> Adicionar cupom promocional
				</legend>
				<div class="row">
					<div class="col-xs-12 col-sm-4">
						<div class="form-group">
							<label for="codigoPromocional" class="control-label">Código</label>
							<input type="text" name="codigoPromocional" placeholder="Digite o código" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-3">
						<button type="submit" formaction="pedidoAdicionarCupom" name="operacao"
						value="SALVAR" id="btnAdicionarCupom" class="btn btn-primary btn-select">
							Adicionar
						</button>
					</div>
				</div>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-usd"></span> Forma de pagamento
				</legend>
				<div class="row">
					<div class="col-xs-12 col-sm-4">
						<div class="form-group">
							<label for="valeCompras" class="control-label">Vale-compras</label>
							<input type="text" name="codigoValeCompras" placeholder="Digite o código" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-3">
						<button type="submit" formaction="pedidoAdicionarValeCompras" name="operacao"
						value="SALVAR" id="btnAdicioarValeCompras" class="btn btn-primary btn-select">
							Validar
						</button>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-4">
						<div class="form-group">
							<label for="endereco" class="control-label">Cartão de crédito</label>
							<select multiple name="cartoesCredito" class="form-control">
								<option disabled value="">Escolha um ou mais cartões</option>
								<c:forEach items="${pedido.cliente.cartoesCredito}" var="cartao">
									<option value="${cartao.id}">
										${cartao.bandeira.nome} (${cartao.numero})
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-3">
						<div class="row">
							<button type="submit" formaction="pagamentoSelecionarCartoes" name="operacao"
							value="SALVAR" id="btnSelecionarCartoes" class="btn btn-primary btn-select btn-select-multiple">
								Selecionar
							</button>
						</div>
						<div class="row">
							<button type="submit" class="btn btn-primary btn-select-multiple"
							method="get" formaction="cartaoCreditoForm?idCliente=${pedido.cliente.id}">
								<span class="glyphicon glyphicon-plus"></span> Novo cartão
							</button>
						</div>
					</div>
				</div>
				<c:if test="${pedido.formaPagamento != null}">
					<div class="row">
						<c:forEach items="${pedido.formaPagamento.pagamentos}" var="pagamento">
							<c:if test="${pagamento.getClass().getSimpleName() eq 'PagamentoCartao'}">
								<div class="col-xs-12 col-sm-4">
									<div class="form-group">
										<label for="valorCartao${pagamento.cartaoCredito.id}" class="control-label">
											${pagamento.cartaoCredito.bandeira.nome} (${pagamento.cartaoCredito.numero})
										</label>
										<input type="text" name="valorCartao${pagamento.cartaoCredito.id}"
										placeholder="Digite o valor a ser pago nesse cartão" class="form-control"/>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
			</fieldset>
			<div class="row">
				<div class="col-xs-12">
					<c:if test="${not empty pedido.itensPedido}">	
						<button type="submit" formaction="carrinhoPagamento"
						id="btnCarrinhoPagamento" class="btn btn-primary">
							Ir para pagamento
						</button>
					</c:if>
					<button type="submit" id="btnContinuarComprando" 
					formaction="livroList" class="btn btn-default">
						Continuar comprando
					</button>
				</div>
			</div>
		</div>
	</form>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>