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
		<h1 class="page-header titulo">Análise de vendas</h1>
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
				<legend>Escolha os filtros para gerar a análise</legend>
				<div class="row">
					<div class="col-xs-12 col-md-4">
						<div class="form-group">
							<label for="categorias" class="control-label">Categorias</label>
							<select multiple required name="categorias" class="form-control">
								<c:forEach items="${categorias}" var="cat">
									<c:choose>
										<c:when test="${categoria.id eq cat.id}">
											<option value="${cat.id}" selected>${cat.nome}</option>
										</c:when>
										<c:otherwise>
											<option value="${cat.id}">${cat.nome}</option>
										</c:otherwise>									
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-2">							
							<button type="submit" class="btn btn-md btn-primary" id="btnAnalise"
							name="operacao" value="CONSULTAR" formaction="vendasAnalise">
								Gerar gráfico
							</button>
						</div>
					</div>
				</div>
				</div>
			</fieldset>
			<div class="row">
				<div class="col-xs-10">
					<img src="graficoImagem" class="img-responsive" alt="Gráfico do volume de vendas"/>
				</div>
			</div>
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
												class="btn btn-sm btn-default btn-icone" method="get" name="detalhes"
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
</body>
</html>