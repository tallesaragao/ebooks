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
		<h1 class="page-header titulo">Lista de pedidos</h1>
		<c:if test="${sucesso != null}">
			<div class="row">
				<div class="col-md-5">
					<div class="alert alert-success alert-dismissible">
						<span class="glyphicon glyphicon-ok"></span>
						<strong>${sucesso}.</strong>				
						<button type="button" id="btnFecharMsgSucesso" class="close" data-dismiss="alert" aria-label="Close">
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
						<button type="button" id="btnFecharMsgErro" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</div>
			</div>
		</c:if>
		<form action="#" method="post">
			<fieldset>
				<legend>Fa�a sua pesquisa</legend>
				<div class="row">
					<div class="col-xs-12 col-md-6">
						<div class="form-group">
							<label for="nome" class="control-label">N� Pedido</label>
							<input type="text" name="nome" placeholder="Nome" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-6">
						<div class="form-group">
							<label for="email" class="control-label">Cliente</label>
							<input type="email" name="email" placeholder="E-mail" class="form-control"/>
						</div>				
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-2">							
							<button type="submit" class="btn btn-md btn-primary" id="btnPesquisar"
							name="operacao" value="CONSULTAR" formaction="pedidoConsultar">
								<span class="glyphicon glyphicon-search"></span> Pesquisar
							</button>
						</div>
					</div>
				</div>
				</div>
			</fieldset>
			<br>
			<c:if test="${not empty consulta}">
				<div class="row">
					<div class="table-responsive">
						<div class="col-sm-11 col-sm-12 col-md-12">
							<table class="table table-striped table-condensed">
								<thead>
									<tr>
										<th>N�MERO</th>
										<th>ITENS</th>
										<th>VALOR TOTAL</th>
										<th>DATA DA COMPRA</th>
										<th>STATUS</th>
										<th>
											<span class="glyphicon glyphicon-cog icone-engrenagem"></span> A��ES
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${consulta}" var="pedido">
										<tr>
											<td>${pedido.numero}</td>
											<td>
												<c:forEach items="${pedido.itensPedido}" var="item" varStatus="status">
													<span>
														${item.livro.titulo} (${item.quantidade} un)<c:if test="${!status.last}">, </c:if>
													</span>
												</c:forEach>
											</td>											
											<td>
												<fmt:setLocale value="pt-BR"/>
												<fmt:formatNumber value="${pedido.valorTotal}" type="currency"/>
											</td>
											<td><fmt:formatDate value="${pedido.dataCadastro}" pattern="dd/MM/yyyy"/></td>
											<td>
												<c:forEach items="${pedido.statusesPedido}" var="statusPedido">
													<c:if test="${statusPedido.atual}">
														${statusPedido.status.nome}
													</c:if>
												</c:forEach>
											</td>					
											<td>
												<button type="submit" data-toggle="tooltip" title="Detalhes" name="detalhes"
												id="btnDetalhesPedido${pedido.id}"class="btn btn-sm btn-default btn-icone" method="get" 
												formaction="pedidoView?operacao=CONSULTAR&idPedido=${pedido.id}&idCliente=${pedido.cliente.id}">
													<span class="glyphicon glyphicon-eye-open"></span>
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
	<script src="resources/js/clienteForm.js"></script>
</body>
</html>