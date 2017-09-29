<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<h2 class="page-header titulo">Novo login de cliente</h2>
		</br>
		<c:if test="${mensagens != null}">
			<div class="row">
				<div class="col-sm-8 col-md-5">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-alert"></span><strong> Problema(s) na inserção:</strong>
						</br>
						<c:forEach var="mensagem" items="${mensagens}">
							${mensagem}.
							</br>
						</c:forEach>
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
					<div class="col-sm-6 col-md-4">
						<div class="form-group">
							<label for="senhaConfirmacao" class="control-label">Confirme a senha</label>
							<input type="password" name="senhaConfirmacao" placeholder="Confirmação" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="row">						
							<div class="form-group">
								<div class="col-xs-6 col-sm-6 col-md-6">
				                	<button type="submit" formaction="loginSalvar" method="post" name="operacao"
				                	value="SALVAR" class="btn btn-md btn-primary btn-block">Salvar</button>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<a href="loginCliente" class="btn btn-md btn-default btn-block">Cancelar</a>
								</div>
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