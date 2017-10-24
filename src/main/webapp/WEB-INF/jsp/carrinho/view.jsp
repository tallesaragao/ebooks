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
					<span class="legend-logo glyphicon glyphicon-shopping-cart"></span> Itens adicionados
				</legend>
				<c:if test="${empty pedido.itensPedido }">
					<h4>O carrinho está vazio</h4>
				</c:if>
				<c:if test="${not empty pedido.itensPedido}">
					<div class="row">
						<div class="table-responsive">
							<div class="col-sm-11 col-sm-12 col-md-12">
								<table class="table table-striped table-condensed">
									<thead>
										<tr>
											<th>TÍTULO</th>
											<th>AUTORES</th>
											<th>CATEGORIAS</th>
											<th>EDITORA</th>
											<th>PREÇO</th>
											<th>SUBTOTAL</th>
											<th>QUANTIDADE</th>
											<th>
												<span class="glyphicon glyphicon-cog icone-engrenagem"></span> AÇÕES
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pedido.itensPedido}" var="itemPedido">
											<tr>
												<fmt:setLocale value="pt-BR" />
												<td>${itemPedido.livro.titulo}</td>
												<td>
													<c:forEach items="${itemPedido.livro.autores}" var="autor" varStatus="status">
														<span>${autor.nome}<c:if test="${!status.last}">, </c:if></span>
													</c:forEach>
												</td>
												<td>
													<c:forEach items="${itemPedido.livro.categorias}" var="categoria" varStatus="status">
														<span>${categoria.nome}<c:if test="${!status.last}">, </c:if></span>
													</c:forEach>
												</td>
												<td>${itemPedido.livro.editora.nome}</td>
												<td>												
													<fmt:formatNumber value="${itemPedido.livro.precificacao.precoVenda}" type="currency"/>
												</td>
												<td>
													<fmt:formatNumber value="${itemPedido.subtotal}" type="currency"/>
												</td>
												<td>
													<div class="input-group">
														<input type="number" min="1" name="quantidade${itemPedido.livro.id}"
														value="${itemPedido.quantidade}" class="form-control"/>
														<span class="input-group-btn">
															<button type="submit" data-toggle="tooltip" title="Alterar"
															class="btn btn-default btn-icone" method="get" id="btnCarrinhoAlterar"
															formaction="carrinhoAlterar?operacao=ALTERAR&id=${itemPedido.livro.id}">
																<span class="glyphicon glyphicon-pencil"></span>
															</button>
														</span>	
													</div>
												</td>
												<td>
													<button type="submit" name="operacao" method="get" data-toggle="tooltip"
													title="Excluir" onclick="return excluir()" id="btnRemoverCarrinho"
													class="btn btn-sm btn-danger botao-excluir btn-icone"
													formaction="carrinhoRemover?operacao=EXCLUIR&id=${itemPedido.livro.id}">
														<span class="glyphicon glyphicon-trash"></span>
													</button>
												</td>							
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</c:if>
			</fieldset>
			<c:if test="${not empty pedido.itensPedido}">
				<fieldset>
					<legend>
						<span class="legend-logo glyphicon glyphicon-send"></span> Frete
					</legend>
					<div class="row">
						<div class="form-group col-xs-6 col-sm-5 col-md-4">
							<label for="endereco" class="control-label">Endereço de entrega</label>
							<select name="endereco" class="form-control">
								<option value="" disabled>Escolha um endereço para prosseguir</option>
								<c:forEach items="${pedido.cliente.enderecos}" var="end">
									<option <c:if test="${pedido.enderecoEntrega.id eq end.id}">selected</c:if> value="${end.id}">
										${end.identificacao}
									</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-6">
							<button type="submit" formaction="freteCalcular" name="operacao"
							value="CONSULTAR" id="btnCalcularFrete" class="btn btn-primary btn-select">
								Calcular
							</button>
							
							<button type="submit" class="btn btn-primary btn-select"
							method="get" formaction="enderecoForm?idCliente=${pedido.cliente.id}">
								<span class="glyphicon glyphicon-plus"></span> Novo endereço
							</button>
						</div>
					</div>
					<c:if test="${pedido.frete != null}">
						<div class="row">
							<div class="col-xs-12">
								<dl>
									<dt>ENDEREÇO (${pedido.enderecoEntrega.identificacao})</dt>
									<dd>
										${pedido.enderecoEntrega.logradouro} nº ${pedido.enderecoEntrega.numero} ${pedido.enderecoEntrega.complemento},
										${pedido.enderecoEntrega.cidade}, ${pedido.enderecoEntrega.estado}, ${pedido.enderecoEntrega.pais} - 
										${pedido.enderecoEntrega.cep}
									</dd>
									<dt>VALOR DO FRETE</dt>
									<dd>
										<fmt:setLocale value="pt-BR"/>
										<fmt:formatNumber value="${pedido.frete.valor}" type="currency"/>									
									</dd>
									<dt>ENTREGA</dt>
									<dd>Em até ${pedido.frete.diasEntrega} dias úteis</dd>
								</dl>
							</div>
						</div>
					</c:if>
				</fieldset>
			</c:if>
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