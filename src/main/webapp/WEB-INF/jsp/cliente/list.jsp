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
		<h1 class="page-header titulo">Lista de clientes</h1>
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
				<legend>Faça sua pesquisa</legend>
				<div class="row">
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="nome" class="control-label">Nome</label>
							<input type="text" name="nome" placeholder="Nome" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="email" class="control-label">E-mail</label>
							<input type="email" name="email" placeholder="E-mail" class="form-control"/>
						</div>				
					</div>
					<div class="col-xs-12 col-md-3">						
						<div class="form-group">
							<label for="cpf" class="control-label">CPF</label>
							<input type="text" name="cpf" placeholder="CPF" class="form-control cpf"/>	
						</div>			
					</div>
					<div class="col-xs-12 col-md-1">						
						<div class="form-group">
							<label for="genero" class="control-label">Gênero</label>
							<input type="text" name="genero" placeholder="M/F" class="form-control"/>	
						</div>			
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-2">							
							<button type="submit" class="btn btn-md btn-primary" id="btnPesquisar"
							name="operacao" value="CONSULTAR" formaction="clienteConsultar">
								<span class="glyphicon glyphicon-search"></span> Pesquisar
							</button>
						</div>
						<div class="col-xs-6 col-md-2 btn-adicionar">			  	
							<button class="btn btn-primary" type="submit" formaction="clienteForm" id="btnNovoCliente">
								<span class="glyphicon glyphicon-plus"></span> Novo cliente
							</button>
						</div>
					</div>
				</div>
				</div>
			</fieldset>
			<c:if test="${not empty consulta}">
				<div class="row">
					<div class="table-responsive tabela-clientes">
						<div class="col-sm-11 col-sm-12 col-md-12">
							<table class="table table-striped table-condensed">
								<thead>
									<tr>
										<th>NOME</th>
										<th>CPF</th>
										<th>DATA NASCIMENTO</th>
										<th>TELEFONE</th>
										<th>EMAIL</th>
										<th>
											<span class="glyphicon glyphicon-cog icone-engrenagem text-center"></span>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${consulta}" var="cliente">
										<tr>
											<td>${cliente.nome}</td>
											<td>${cliente.cpf}</td>
											<td><fmt:formatDate value="${cliente.dataNascimento}" pattern="dd/MM/yyyy"/></td>
											<td><span>${cliente.telefone.ddd} ${cliente.telefone.numero}</span></td>
											<td>${cliente.email}</td>										
											<td>
												<button type="submit" data-toggle="tooltip" title="Detalhes" id="btnDetalhes${cliente.id}"
												class="btn btn-sm btn-default btn-icone" method="get"
												formaction="clienteView?operacao=CONSULTAR&id=${cliente.id}">
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