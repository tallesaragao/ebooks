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
				</div>
			</fieldset>
			
			</fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-tags"></span> Informa��es do pagamento
				</legend>
				<div class="row">
					<div class="col-xs-12 col-sm-4">
						<div class="form-group">
							<label for="codigoPromocional" class="control-label">C�digo</label>
							<input type="text" name="codigoPromocional" placeholder="Digite o c�digo"
							value="${pedido.cupomPromocional.codigo}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-3">
						<button type="submit" formaction="pagamentoAdicionarCupom" name="operacao"
						value="SALVAR" id="btnAdicionarCupom" class="btn btn-primary btn-select">
							Aplicar
						</button>
						
						<c:if test="${pedido.cupomPromocional != null }">
							<button type="submit" formaction="pagamentoRemoverCupom" name="operacao"
							value="EXCLUIR" id="btnRemoverCupom" class="btn btn-warning btn-select">
								Remover
							</button>
						</c:if>
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
							<input type="text" name="codigoValeCompras" placeholder="Digite o c�digo"
							value="${codigoValeCompras}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-3">
						<button type="submit" formaction="pagamentoAdicionarValeCompras" name="operacao"
						value="SALVAR" id="btnAdicionarValeCompras" class="btn btn-primary btn-select">
							Aplicar
						</button>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-4">
						<div class="form-group">
							<label for="endereco" class="control-label">Cart�o de cr�dito</label>
							<select multiple name="cartoesCredito" class="form-control">
								<option disabled value="">Escolha um ou mais cart�es</option>
								<c:forEach items="${pedido.cliente.cartoesCredito}" var="cartao">
									<option value="${cartao.id}">
										${cartao.bandeira.nome} - 
										${fn:substring(cartao.numero, 0, 4)}
										<c:set var="asteriscos" value=""/>
										<c:forEach begin="1" end="${fn:length(cartao.numero) - 8}">
											<c:out value="*"/>
										</c:forEach>
										${fn:substring(cartao.numero, fn:length(cartao.numero) - 4, fn:length(cartao.numero))}
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
								<span class="glyphicon glyphicon-plus"></span> Novo cart�o
							</button>
						</div>
					</div>
				</div>
			</fieldset>
			<c:if test="${pedido.formaPagamento != null && !pedido.formaPagamento.pagamentos.isEmpty()}">
				<fieldset>
					<legend>
						<span class="legend-logo glyphicon glyphicon-usd"></span> Divis�o do pagamento
					</legend>
					<c:forEach items="${pedido.formaPagamento.pagamentos}" var="pagamento">
						<c:if test="${pagamento.getClass().getSimpleName() eq 'PagamentoValeCompras'}">
							<div class="row">
								<div class="col-xs-12 col-sm-4">
									<div class="form-group">
										<label for="valorValeCompras${pagamento.valeCompras.id}" class="control-label">
											Vale-compras(Valor dispon�vel: ${pagamento.valeCompras.valor}) 
										</label>
										<input type="text" name="valorValeCompras${pagamento.valeCompras.id}"
										placeholder="Digite o valor a ser pago nesse vale-compras" class="form-control"/>
									</div>
								</div>								
								<div class="col-xs-2">
									<button type="submit" name="operacao" method="get" data-toggle="tooltip"
									title="Remover" value="EXCLUIR"	onclick="return excluir()" id="btnValeComprasRemover"
									class="btn btn-sm btn-danger botao-excluir btn-icone btn-select"
									 formaction="pagamentoRemoverValeCompras?id=${pagamento.valeCompras.id}">
										<span class="glyphicon glyphicon-trash"></span>
									</button>
								</div>
							</div>
						</c:if>
						<c:if test="${pagamento.getClass().getSimpleName() eq 'PagamentoCartao'}">
							<div class="row">
								<div class="col-xs-10 col-sm-4">
									<div class="form-group">
										<label for="valorCartao${pagamento.cartaoCredito.id}" class="control-label">
											${pagamento.cartaoCredito.bandeira.nome} - 
											${fn:substring(pagamento.cartaoCredito.numero, 0, 4)}
											<c:set var="asteriscos" value=""/>
											<c:forEach begin="1" end="${fn:length(pagamento.cartaoCredito.numero) - 8}">
												<c:out value="*"/>
											</c:forEach>
											${fn:substring(pagamento.cartaoCredito.numero,
											fn:length(pagamento.cartaoCredito.numero) - 4,
											fn:length(pagamento.cartaoCredito.numero))}
										</label>
										<input type="text" name="valorCartao${pagamento.cartaoCredito.id}"
										placeholder="Digite o valor a ser pago nesse cart�o" class="form-control"/>
									</div>
								</div>
								<div class="col-xs-2">
									<button type="submit" name="operacao" method="get" data-toggle="tooltip"
									title="Remover" value="EXCLUIR"	onclick="return excluir()" id="btnCartaoCreditoRemover"
									class="btn btn-sm btn-danger botao-excluir btn-icone btn-select"
									 formaction="pagamentoRemoverCartao?id=${pagamento.cartaoCredito.id}">
										<span class="glyphicon glyphicon-trash"></span>
									</button>
								</div>
							</div>
						</c:if>
					</c:forEach>
					<div class="row">
						<div class="col-xs-12">
							<c:if test="${not empty pedido.itensPedido}">	
								<button type="submit" formaction="pedidoDetalhes"
								id="btnPedidoDetalhes" class="btn btn-primary">
									Finalizar pedido
								</button>
							</c:if>
							<button type="submit" id="btnVoltarCarrinho" 
							formaction="carrinhoCliente" class="btn btn-default">
								Voltar ao carrinho
							</button>
						</div>
					</div>
				</fieldset>
			</c:if>
			
		</div>
	</form>
	</br>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>