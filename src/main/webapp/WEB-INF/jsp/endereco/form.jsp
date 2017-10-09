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
				<h2 class="page-header">Alteração de endereço</h2>					
			</c:when>
			<c:otherwise>
				<h2 class="page-header">Cadastro de endereço</h2>				
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
		<form class="form" role="form" action="#" method="post">
			<input type="hidden" name="idCliente" value="${idCliente}"/>
			<input type="hidden" name="id" value="${endereco.id}"/>
				<fieldset>
					<legend>
						<span class="legend-logo glyphicon glyphicon-map-marker"></span> Dados do endereço
					</legend>
					
					<div class="row">
						
						<div class="form-group col-xs-12 col-sm-2">
							<label for="cep" class="control-label">CEP</label>
							<input type="text" id="cep" name="cep" value="${endereco.cep}"
							placeholder="CEP" class="form-control cep"/>
						</div>
						
						<div class="form-group col-xs-9 col-sm-4 col-md-3 col-lg-3">
							<label for="cidade" class="control-label">Cidade</label>
							<input type="text" id="cidade" name="cidade" value="${endereco.cidade}"
							placeholder="Cidade" class="form-control"/>
						</div>
						
						<div class="form-group col-xs-3 col-sm-2">
							<label for="estado" class="control-label">Estado</label>
							<input type="text" id="estado" name="estado" value="${endereco.estado}"
							placeholder="Estado" class="form-control estado"/>
						</div>
											
						<div class="form-group col-xs-3 col-sm-2">
							<label for="pais" class="control-label">País</label>
							<input type="text" id="pais" name="pais" value="${endereco.pais}"
							placeholder="País" class="form-control pais"/>
						</div>					
											
						<div class="form-group col-xs-12 col-sm-3">
							<label for="bairro" class="control-label">Bairro</label>
							<input type="text" id="bairro" name="bairro" value="${endereco.bairro}"
							placeholder="Bairro" class="form-control"/>
						</div>
						
					</div>
					<div class="row">
						<div class="form-group col-xs-12 col-sm-6">
							<label for="logradouro" class="control-label">Logradouro</label>
							<input type="text" id="logradouro" name="logradouro" value="${endereco.logradouro}"
							placeholder="Logradouro" class="form-control"/>
						</div>
						
						<div class="form-group col-xs-3 col-sm-2 col-lg-1">
							<label for="numeroEnd" class="control-label">Número</label>
							<input type="text" id="numeroEnd" name="numeroEnd" value="${endereco.numero}"
							placeholder="Número" class="form-control"/>
						</div>
						
						<div class="form-group col-xs-9 col-sm-4 col-md-3 col-lg-5">
							<label for="complemento" class="control-label">Complemento</label>
							<input type="text" name="complemento" value="${endereco.complemento}"
							placeholder="Complemento" class="form-control"/>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-xs-12 col-sm-4 col-md-2">
							<label for="tipoEndereco" class="control-label">Tipo de endereço</label>
							<select name="tipoEndereco" class="form-control" required>
								<option value="" disabled selected>Escolha um tipo</option>
								<option value="1">Residencial</option>
								<option value="2">Comercial</option>
							</select>
						</div>
						
						<div class="form-group col-xs-9 col-sm-4 col-md-3 col-lg-4">
							<label for="identificacao" class="control-label">Identificação</label>
							<input type="text" name="identificacao" value="${endereco.identificacao}" 
							placeholder="Uma frase curta para identificar o endereço" class="form-control"/>
						</div>
					</div>
				</fieldset>
				</br>
			<fieldset>
			<div class="row">
				<c:choose>
					<c:when test="${operacao eq 'ALTERAR'}">
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" value="ALTERAR" id="btnAlterar" formaction="enderecoAlterar" class="btn btn-primary">
								Alterar
							</button>
						</div>					
					</c:when>
					<c:otherwise>
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" value="SALVAR" id="btnSalvar" formaction="enderecoSalvar" class="btn btn-primary">
								Salvar
							</button>
						</div>					
					</c:otherwise>
				</c:choose>
				<div class="form-group col-xs-1 col-xs-offset-2 col-sm-offset-1 col-md-offset-0">
					<a href="clienteList" class="btn btn-default">Cancelar</a>
				</div>
			</div>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/clienteForm.js"></script>
</body>
</html>