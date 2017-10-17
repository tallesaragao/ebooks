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
		<form class="form" role="form" action="#" method="post">
			<c:choose>
				<c:when test="${operacao eq 'ALTERAR'}">
					<h1 class="page-header titulo">Alteração de cliente</h1>	
				</c:when>
				<c:otherwise>
					<h1 class="page-header titulo">Cadastro de cliente</h1>				
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
			
			<input type="hidden" name="id" value="${cliente.id}"/>
			<input type="hidden" name="ativo" value="${cliente.ativo}"
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-user"></span> Dados pessoais
				</legend>
				
				
				<div class="row">
					<div class="form-group col-sm-6 col-md-5">
						<label for="nome" class="control-label">Nome</label>
						<input type="text" name="nome" value="${cliente.nome}"
						placeholder="Nome" class="form-control"/>
					</div>
					
					<div class="form-group col-sm-3 col-md-2">
						<label for="dataNascimento" class="control-label">Data Nascimento</label>
						<input type="date" name="dataNascimento" class="form-control data"
						value="<fmt:formatDate value="${cliente.dataNascimento}" pattern="yyyy-MM-dd"/>"/>
					</div>
					
					<div class="form-group col-sm-3 col-md-2">
						<label for="cpf" class="control-label">CPF</label>
						<input type="text" name="cpf" value="${cliente.cpf}"
						placeholder="CPF" class="form-control cpf"/>
					</div>
					
					<div class="col-xs-12 col-md-2">
						<label for="radios" class="control-label">Gênero</label>
						<div name="radios" class="form-group">
							<label class="radio-inline">
								<input type="radio" name="genero" value="M" checked />M
							</label>
							<label class="radio-inline">
								<input type="radio" name="genero" value="F" <c:if test="${cliente.genero eq 'F'.charAt(0)}">checked</c:if>/>F
							</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-md-5">
						<div class="form-group">
							<label for="email" class="control-label">E-mail</label>
							<input type="email" name="email" class="form-control" value="${cliente.email}" placeholder="mail@mail.com" />
						</div>
					</div>
				</div>
			</fieldset>
			</br>
			<c:if test="${operacao != 'ALTERAR' }">
				<fieldset>
					<legend>
						<span class="legend-logo glyphicon glyphicon-map-marker"></span> Endereço
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
							<select name="tipoEndereco" class="form-control">
								<option value="" disabled selected>Escolha um tipo</option>
								<c:forEach items="${tiposEndereco}" var="tipoEnd">
									<option <c:if test="${endereco.tipoEndereco.id eq tipoEnd.id}">selected</c:if> value="${tipoEnd.id}">
										${tipoEnd.nome}
									</option>
								</c:forEach>
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
			</c:if>
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-earphone"></span> Telefone
				</legend>
				<input type="hidden" name="idTelefone" value="${cliente.telefone.id}"/>
				<div class="row">
					<div class="form-group col-xs-3 col-sm-2 col-md-1">
						<label for="ddd" class="control-label">DDD</label>
						<input type="text" name="ddd" value="${cliente.telefone.ddd}"
						placeholder="(XX)" class="form-control ddd"/>
					</div>
					
					<div class="form-group col-xs-9 col-sm-4 col-md-3">
						<label for="numeroTel" class="control-label">Número</label>
						<input type="text" name="numeroTel" value="${cliente.telefone.numero}"
						placeholder="XXXXX-XXXX" class="form-control numeroTel"/>
					</div>
					
					<div class="form-group col-xs-12 col-sm-4 col-md-2">
						<label for="tipoTelefone" class="control-label">Tipo de telefone</label>
						<select name="tipoTelefone" class="form-control">
							<option value="" disabled selected>Escolha um tipo</option>
							<c:forEach items="${tiposTelefone}" var="tipoTel">
								<option <c:if test="${cliente.telefone.tipoTelefone.id eq tipoTel.id}">selected</c:if> value="${tipoTel.id}">
									${tipoTel.nome}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</fieldset>
			</br>
			<c:if test="${operacao != 'ALTERAR' }">
				<fieldset>
						<legend>
							<span class="legend-logo glyphicon glyphicon-log-in"></span> Login
						</legend>
					<div class="row">
						<div class="col-sm-6 col-md-4">
							<div class="form-group">
								<label for="usuario" class="control-label">Usuário</label>
								<input type="text" name="usuario" value="${cliente.login.usuario}"
								placeholder="Usuário" class="form-control"/>
							</div>
						</div>
						
						<div class="col-sm-6 col-md-4">
							<div class="form-group">
								<label for="senha" class="control-label">Senha</label>
								<input type="password" name="senha" placeholder="Senha" class="form-control"/>
							</div>
						</div>
						
						<div class="col-sm-6 col-md-4">
							<div class="form-group">
								<label for="senhaConfirmacao" class="control-label">Confirme a senha</label>
								<input type="password" name="senhaConfirmacao" placeholder="Confirmação" class="form-control"/>
							</div>
						</div>
					</div>
				</fieldset>
				</br>
			</c:if>
			<div class="row">
				<c:choose>
					<c:when test="${operacao eq 'ALTERAR'}">
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" formaction="clienteAlterar"
							id="btnAlterar" class="btn btn-primary" value="ALTERAR">
								Alterar
							</button>
						</div>					
					</c:when>
					<c:otherwise>
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" formaction="clienteSalvar"
							id="btnSalvar" class="btn btn-primary" value="SALVAR">
								Salvar
							</button>
						</div>					
					</c:otherwise>
				</c:choose>
				<div class="form-group col-xs-1 col-xs-offset-2 col-sm-offset-1 col-md-offset-0">
					<a href="clienteList" id="btnCancelar" class="btn btn-default">Cancelar</a>
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