<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<dt>Gênero</dt>
					<dd>${cliente.genero}</dd>
					<dt>DATA NASCIMENTO</dt>
					<dd>${cliente.dataNascimento}</dd>
					<dt>E-MAIL</dt>
					<dd>${cliente.email}</dd>
				</dl>
				<div class="row">
					<div class="col-xs-12">
						<button class="botao-voltar btn-icone btn btn-sm btn-default" formaction="clienteList"
						data-toggle="tooltip" title="Voltar">
							<span class="glyphicon glyphicon-arrow-left"></span>
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
												class="btn btn-sm btn-default btn-icone" method="get"
												formaction="enderecoEdit?operacao=CONSULTAR&id=${endereco.id}&idCliente=${cliente.id}">
													<span class="glyphicon glyphicon-pencil"></span>
												</button>
												<button type="submit" name="operacao" method="get" data-toggle="tooltip"
												title="Excluir" value="EXCLUIR"	onclick="return excluir()"
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
												<td>${cartaoCredito.dataVencimento}</td>
												<td>${cartaoCredito.codigoSeguranca}</td>
												<td>${cartaoCredito.bandeira.nome}</td>						
												<td>
													<button type="submit" data-toggle="tooltip" title="Editar"
													class="btn btn-sm btn-default btn-icone" method="get"
													formaction="cartaoCreditoEdit?operacao=CONSULTAR&id=${cartaoCredito.id}&idCliente=${cliente.id}">
														<span class="glyphicon glyphicon-pencil"></span>
													</button>
													<button type="submit" name="operacao" method="get" data-toggle="tooltip"
													title="Excluir" value="EXCLUIR"	onclick="return excluir()"
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