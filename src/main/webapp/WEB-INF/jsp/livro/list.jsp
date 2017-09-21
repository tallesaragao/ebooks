<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>WeBooze</title>
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="resources/css/hmweb.css">
</head>
<body>
	<c:import url="../cabecalho.jsp"/>
	<div class="container">
		<h1 class="page-header titulo">Lista de bebidas</h1>
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
			<div class="row pesquisa">
				<div class="col-xs-12">
					<div class="row">
						<div class="col-xs-10 col-sm-6 col-md-4">
							<div class="input-group input-group-md">
								<input type="text" name="busca" placeholder="Faça sua busca" class="form-control"/>
								<div class="input-group-btn">
									<button type="submit" class="btn btn-md btn-primary btn-icone"
									name="operacao" value="CONSULTAR" formaction="bebidaConsultar">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</div>
							</div>
						</div>
						<div class="col-xs-2 col-md-3 btn-adicionar">			  	
							<button class="btn btn-primary btn-icone" type="submit" formaction="bebidaForm"
							data-toggle="tooltip" title="Adicionar bebida">
								<span class="glyphicon glyphicon-plus"></span>
							</button>
						</div>
					</div>
				</div>
			</div>	
			
			<c:if test="${not empty consulta}">
				<div class="row">
					<div class="table-responsive tabela-clientes">
						<div class="col-sm-11 col-sm-7 col-md-8">
							<table class="table table-striped table-condensed">
								<thead>
									<tr>
										<th>NOME</th>
										<th>CATEGORIA</th>
										<th>FORNECEDOR</th>
										<th>FABRICAÇÃO</th>
										<th>VALIDADE</th>
										<th>QUANTIDADE</th>
										<th>PREÇO (R$)</th>
										<th>
											<span class="glyphicon glyphicon-cog icone-engrenagem"></span> AÇÕES
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${consulta}" var="bebida">
										<tr>
											<td>${bebida.nome}</td>
											<td>${bebida.categoria.nome}</td>
											<td>${bebida.fornecedor}</td>
											<td>${bebida.dataFabricacao}</td>
											<td>${bebida.dataValidade}</td>
											<td>${bebida.estoque.quantidadeAtual}</td>
											<td>${bebida.preco}</td>											
											<td>
												<button type="submit" data-toggle="tooltip" title="Editar"
												class="btn btn-sm btn-default btn-icone" method="get"
												formaction="bebidaEdit?operacao=CONSULTAR&id=${bebida.id}">
													<span class="glyphicon glyphicon-pencil"></span>
												</button>
												<button type="submit" name="operacao" method="get" data-toggle="tooltip"
												title="Excluir" value="EXCLUIR"	onclick="return excluir()"
												class="btn btn-sm btn-danger botao-excluir btn-icone" formaction="bebidaExcluir?id=${bebida.id}">
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
			</c:if>		
		</form>
		
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/webooze.js"></script>
</body>
</html>