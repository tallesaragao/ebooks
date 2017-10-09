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
		<h1 class="page-header titulo">Informações do cliente</h1>
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
					<span class="legend-logo glyphicon glyphicon-user"></span> Dados pessoais
				</legend>
				<dl class="dl-horizontal">
					<dt>NOME</dt>
					<dd>${cliente.nome}</dd>
					<dt>CPF</dt>
					<dd>${cliente.cpf}</dd>
					<dt>GÊNERO</dt>
					<dd>${cliente.genero}</dd>
					<dt>DATA NASCIMENTO</dt>
					<dd><fmt:formatDate value="${cliente.dataNascimento}" pattern="dd/MM/yyyy"/></dd>
					<dt>E-MAIL</dt>
					<dd>${cliente.email}</dd>
					<dt>SITUAÇÃO</dt>
					<dd>
						<c:set var="ativo" value="Inativo"/>
						<c:if test="${cliente.ativo}">
							<c:set var="ativo" value="Ativo"/>
						</c:if>
						${ativo}
					</dd>
				</dl>
				<div class="row">
					<div class="col-xs-12">
						<button class="botao-voltar btn-icone btn btn-sm btn-default" type="button"
						data-toggle="tooltip" title="Voltar" id="btnVoltar">
							<span class="glyphicon glyphicon-arrow-left"></span>
						</button>
								
						<button type="submit" data-toggle="tooltip" title="Editar"
						class="btn btn-sm btn-default btn-icone" method="get" id="btnClienteEdit"
						formaction="clienteEdit?operacao=CONSULTAR&id=${cliente.id}">
							<span class="glyphicon glyphicon-pencil"></span>
						</button>
						
						<c:choose>
							<c:when test="${cliente.ativo}">
								<button type="submit" data-toggle="tooltip" title="Inativar"
								class="btn btn-sm btn-warning btn-icone" method="get" id="btnClienteInativar"
								formaction="clienteInativar?operacao=CONSULTAR&id=${cliente.id}">
									<span class="glyphicon glyphicon-ban-circle"></span>
								</button>
							</c:when>
							<c:otherwise>
								<button type="submit" data-toggle="tooltip" title="Ativar"
								class="btn btn-sm btn-success btn-icone" method="get" id="btnClienteAtivar"
								formaction="clienteAtivar?operacao=CONSULTAR&id=${cliente.id}">
									<span class="glyphicon glyphicon-ok-sign"></span>
								</button>
							</c:otherwise>
						</c:choose>
							
												
						<button type="submit" name="operacao" method="get" data-toggle="tooltip"
						title="Excluir" value="EXCLUIR"	onclick="return excluir()" id="btnClienteExcluir"
						class="btn btn-sm btn-danger botao-excluir btn-icone"
						 formaction="clienteExcluir?id=${cliente.id}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
					</div>
				</div>
				</br>
			</fieldset>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-map-marker"></span> Endereços
				</legend>
				<div class="row">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-primary "
						method="get" formaction="enderecoForm?idCliente=${cliente.id}">
							<span class="glyphicon glyphicon-plus"></span> Novo endereço
						</button>
					</div>
				</div>
				<div class="row">
					<div class="table-responsive">
						<div class="col-sm-11 col-sm-12 col-md-12">
							<table class="table table-striped table-condensed">
								<thead>
									<tr>
										<th>NOME ID</th>
										<th>LOGRADOURO</th>
										<th>Nº</th>
										<th>COMPLEMENTO</th>
										<th>BAIRRO</th>
										<th>CEP</th>
										<th>CIDADE</th>
										<th>ESTADO</th>
										<th>PAÍS</th>
										<th>
											<span class="glyphicon glyphicon-cog icone-engrenagem"></span> AÇÕES
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cliente.enderecos}" var="endereco">
										<tr>
											<td>${endereco.identificacao}</td>
											<td>${endereco.logradouro}</td>
											<td>${endereco.numero}</td>
											<td>${endereco.complemento}</td>
											<td>${endereco.bairro}</td>							
											<td>${endereco.cep}</td>							
											<td>${endereco.cidade}</td>							
											<td>${endereco.estado}</td>					
											<td>${endereco.pais}</td>					
											<td>
												<button type="submit" data-toggle="tooltip" title="Editar"
												class="btn btn-sm btn-default btn-icone" method="get" id="btnEnderecoEdit${endereco.id}"
												formaction="enderecoEdit?operacao=CONSULTAR&id=${endereco.id}&idCliente=${cliente.id}">
													<span class="glyphicon glyphicon-pencil"></span>
												</button>
												<button type="submit" name="operacao" method="get" data-toggle="tooltip"
												title="Excluir" value="EXCLUIR"	onclick="return excluir()" id="btnEnderecoExcluir${endereco.id}"
												class="btn btn-sm btn-danger botao-excluir btn-icone"
												 formaction="enderecoExcluir?id=${endereco.id}&idCliente=${cliente.id}">
													<span class="glyphicon glyphicon-trash"></span>
												</button>
											</td>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>	
			</fieldset>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-credit-card"></span> Cartões de crédito
				</legend>
				<div class="row">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-primary "
						method="get" formaction="cartaoCreditoForm?idCliente=${cliente.id}">
							<span class="glyphicon glyphicon-plus"></span> Novo cartão
						</button>
					</div>
				</div>
				<div class="row">
					<div class="table-responsive">
						<div class="col-sm-11 col-sm-12 col-md-12">
							<table class="table table-striped table-condensed">
								<thead>
									<tr>
										<th>NÚMERO</th>
										<th>NOME TITULAR</th>
										<th>DATA VENCIMENTO</th>
										<th>CÓDIGO</th>
										<th>BANDEIRA</th>
										<th>
											<span class="glyphicon glyphicon-cog icone-engrenagem"></span> AÇÕES
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cliente.cartoesCredito}" var="cartaoCredito">
										<tr>
											<td>${cartaoCredito.numero}</td>
											<td>${cartaoCredito.nomeTitular}</td>
											<td><fmt:formatDate value="${cartaoCredito.dataVencimento}" pattern="dd/MM/yyyy"/></td>
											<td>${cartaoCredito.codigoSeguranca}</td>
											<td>${cartaoCredito.bandeira.nome}</td>						
											<td>
												<button type="submit" data-toggle="tooltip" title="Editar"
												class="btn btn-sm btn-default btn-icone" method="get" id="btnCarCredEdit${cartaoCredito.id}"
												formaction="cartaoCreditoEdit?operacao=CONSULTAR&id=${cartaoCredito.id}&idCliente=${cliente.id}">
													<span class="glyphicon glyphicon-pencil"></span>
												</button>
												<button type="submit" name="operacao" method="get" data-toggle="tooltip"
												title="Excluir" value="EXCLUIR"	onclick="return excluir()" id="btnCarCredExcluir${cartaoCredito.id}"
												class="btn btn-sm btn-danger botao-excluir btn-icone"
												formaction="cartaoCreditoExcluir?id=${cartaoCredito.id}&idCliente=${cliente.id}">
													<span class="glyphicon glyphicon-trash"></span>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>	
			</fieldset>
		</div>
	</form>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>