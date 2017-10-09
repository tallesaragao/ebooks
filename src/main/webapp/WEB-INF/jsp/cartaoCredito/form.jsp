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
		<c:choose>
			<c:when test="${operacao eq 'ALTERAR'}">
				<h2 class="page-header">Alteração de cartão de crédito</h2>					
			</c:when>
			<c:otherwise>
				<h2 class="page-header">Cadastro de cartão de crédito</h2>				
			</c:otherwise>
		</c:choose>
		<c:if test="${mensagens != null}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-alert"></span><strong> Problema(s) na inserção:</strong>
					</br>
					<c:forEach var="mensagem" items="${mensagens}">
						${mensagem}.
						</br>
					</c:forEach>
				</div>
			</c:if>
			</br>
		<form class="form" action="#" method="post">
			<input type="hidden" name="idCliente" value="${idCliente}"/>
			<input type="hidden" name="id" value="${cartaoCredito.id}"/>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-credit-card"></span> Dados do cartão
				</legend>
				
				<div class="row">
					
					<div class="form-group col-xs-12 col-sm-3">
						<label for="numero" class="control-label">Número do cartão</label>
						<input type="text" name="numero" value="${cartaoCredito.numero}"
						placeholder="Número do cartão" class="form-control"/>
					</div>
					
					<div class="form-group col-xs-9 col-sm-4 col-md-3 col-lg-3">
						<label for="nomeTitular" class="control-label">Nome do titular</label>
						<input type="text" name="nomeTitular" value="${cartaoCredito.nomeTitular}"
						placeholder="Nome do titular" class="form-control"/>
					</div>
					
					<div class="form-group col-xs-3 col-sm-2">
						<label for="dataVencimento" class="control-label">Data Vencimento</label>
						<input type="date" name="dataVencimento" class="form-control"
						value="<fmt:formatDate value="${cartaoCredito.dataVencimento}" pattern="yyyy-MM-dd"/>"/>
					</div>
										
					<div class="form-group col-xs-3 col-sm-2">
						<label for="codigoSeguranca" class="control-label">Código Segurança</label>
						<input type="text" name="codigoSeguranca" value="${cartaoCredito.codigoSeguranca}"
						placeholder="Código" class="form-control pais"/>
					</div>					
										
					
					<div class="form-group col-xs-12 col-sm-4 col-md-2">
						<label for="bandeira" class="control-label">Bandeira</label>
						<select name="bandeira" class="form-control" required>
							<option value="" disabled selected>Escolha a bandeira</option>
							<c:forEach items="${bandeiras}" var="band">
								<option <c:if test="${cartaoCredito.bandeira.id eq band.id}">selected</c:if> value="${band.id}">${band.nome}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</fieldset>
			</br>
			<div class="row">
				<div class="col-xs-12">
					<c:choose>
						<c:when test="${operacao eq 'ALTERAR'}">
							<button type="submit" name="operacao" formaction="cartaoCreditoAlterar"
							id="btnAlterar" class="btn btn-primary" value="ALTERAR">
								Alterar
							</button>			
						</c:when>
						<c:otherwise>
							<button type="submit" name="operacao" formaction="cartaoCreditoSalvar"
							id="btnSalvar" class="btn btn-primary" value="SALVAR">
								Salvar
							</button>				
						</c:otherwise>
					</c:choose>
					<button class="botao-voltar btn btn-default" id="btnCancelar" type="button">Cancelar</button>
				</div>
			</div>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/ebooks.js"></script>
	<script src="resources/js/clienteForm.js"></script>
</body>
</html>