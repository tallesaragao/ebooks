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
		<h2 class="page-header titulo">Ativação de ${nomeEntidade}</h2>
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
			<input type="hidden" name="id" value="${entidade.id}"/>
			<input type="hidden" name="nomeEntidade" value="${nomeEntidade}"/>
			<div class="row">
				<div class="col-xs-12">
					<dl>
						<dt>${nomeEntidade}</dt>
						<dd>${identificacao}</dd>
					</dl>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="justificativa" class="control-label">Justificativa</label>
						<textarea rows="6" name="justificativa" class="form-control"></textarea>
					</div>
				</div>
			</div>						
			<div class="row">
				<div class="col-xs-12">				
					<button type="submit" name="operacao" value="SALVAR" formaction="ativacaoSalvar" class="btn btn-success">
						Ativar
					</button>
					<button class="botao-voltar btn btn-default" type="button">Cancelar</button>
				</div>
			</div>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
</body>
</html>