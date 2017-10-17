<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<h2 class="page-header titulo">Login de cliente</h2>
		</br>
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
		<form class="form" action="#" method="post">
			<fieldset>
				<legend>Informe seus dados</legend>
				<div class="row">
					<div class="col-sm-6 col-md-4">
						<div class="form-group">
							<label for="usuario" class="control-label">Usuário</label>
							<input type="text" name="usuario" placeholder="Usuário" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-md-4">
						<div class="form-group">
							<label for="senha" class="control-label">Senha</label>
							<input type="password" name="senha" placeholder="Senha" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
			                	<button type="submit" formaction="loginConsultar" method="post" name="operacao"
			                	value="CONSULTAR" id="btnLogar" class="btn btn-md btn-success btn-block">Entrar</button>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6">
								<a id="btnRegistrar" href="clienteForm" class="btn btn-md btn-primary btn-block">Registre-se</a>
							</div>
						</div>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/webooze.js"></script>
</body>
</html>