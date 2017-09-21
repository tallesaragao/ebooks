<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<c:choose>
			<c:when test="${operacao eq 'ALTERAR'}">
				<h1 class="page-header titulo">Alteração de bebida</h1>
			</c:when>
			<c:otherwise>
				<h1 class="page-header titulo">Cadastro de bebida</h1>				
			</c:otherwise>
		</c:choose>
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
			<div class="row">
				<div class="col-xs-12 col-md-4">
					<div class="form-group">
						<label for="nome" class="control-label">Nome</label>
						<input type="text" name="nome" placeholder="Nome"
						value="${bebida.nome}" class="form-control"/>
					</div>
				</div>
				<div class="col-xs-12 col-md-4">
					<div class="form-group">
						<label for="dataFabricacao" class="control-label">Data de Fabricação</label>
						<input type="date" name="dataFabricacao" class="form-control"
						value="<fmt:formatDate value="${bebida.dataFabricacao}" pattern="yyyy-MM-dd"/>"/>
					</div>
				</div>
				<div class="col-xs-12 col-md-4">
					<div class="form-group">
						<label for="categoria" class="control-label">Categoria</label>
						<select name="categoria" class="form-control">
							<c:forEach items="${categorias}" var="cat">
								<c:choose>
									<c:when test="${bebida.categoria.id eq cat.id}">
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
				<div class="col-xs-12 col-md-4">					
					<div class="form-group">
						<label for="fornecedor" class="control-label">Fornecedor</label>						
						<input type="text" name="fornecedor" placeholder="Fornecedor"
						value="${bebida.fornecedor}" class="form-control"/>
					</div>
				</div>
				<div class="col-xs-12 col-md-4">
					<div class="form-group">
						<label for="fabricante" class="control-label">Fabricante</label>						
						<input type="text" name="fabricante" placeholder="Fabricante"
						value="${bebida.fabricante}" class="form-control"/>
					</div>
				</div>
				<div class="col-xs-12 col-md-4">
					<div class="form-group">
						<label for="preco" class="control-label">Preço (R$)</label>						
						<input type="number" step="0.01" name="preco" value="${bebida.preco}" class="form-control"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-2">
					<label for="radios" class="control-label">Alcoólica?</label>
					<c:choose>
						<c:when test="${bebida.alcoolica eq true}">
							<div name="radios" class="form-group">
								<label class="radio-inline">
									<input type="radio" name="alcoolica" value="1" checked/>Sim
								</label>
								<label class="radio-inline">
									<input type="radio" name="alcoolica" value="0"/>Não
								</label>
							</div>
						</c:when>
						<c:otherwise>
							<div name="radios" class="form-group">
								<label class="radio-inline">
									<input type="radio" name="alcoolica" value="1"/>Sim
								</label>
								<label class="radio-inline">
									<input type="radio" name="alcoolica" value="0" checked/>Não
								</label>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-xs-12 col-md-4">
					<div class="form-group">
						<label for="teorAlcool" class="control-label">Teor Alcoólico (%)</label>
						<c:choose>
							<c:when test="${bebida.alcoolica == true}">
								<input type="number" step="any" name="teorAlcool" id="teorAlcool"
								value="${bebida.teorAlcool}" class="form-control"/>
							</c:when>
							<c:otherwise>
								<input type="number" step="any" name="teorAlcool" id="teorAlcool"
								class="form-control" disabled/>
							</c:otherwise>
						</c:choose>						
					</div>
				</div>
				<div class="col-xs-12 col-md-2">
					<div class="form-group">
						<label for="quantidadeAtual" class="control-label">Quantidade</label>						
						<input type="number" name="quantidadeAtual" value="${bebida.estoque.quantidadeAtual}" class="form-control"/>
					</div>
				</div>
				<div class="col-xs-12 col-md-2">
					<div class="form-group">
						<label for="quantidadeMinima" class="control-label">Quantidade Minima</label>						
						<input type="number" name="quantidadeMinima" value="${bebida.estoque.quantidadeMinima}" class="form-control"/>
					</div>
				</div>
				<div class="col-xs-12 col-md-2">
					<div class="form-group">
						<label for="quantidadeMaxima" class="control-label">Quantidade Máxima</label>						
						<input type="number" name="quantidadeMaxima" value="${bebida.estoque.quantidadeMaxima}" class="form-control"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-6">
					<div class="form-group">
						<label for="ingredientes" class="control-label">Ingredientes</label>
						<textarea name="ingredientes" rows="4" class="form-control">${bebida.ingredientes}</textarea>
					</div>
				</div>
			</div>		
			<div class="row">
				<c:choose>
					<c:when test="${operacao eq 'ALTERAR'}">
						<input type="hidden" name="id" value="${bebida.id}"/>
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" value="ALTERAR"
							formaction="bebidaAlterar" class="btn btn-primary">
								Alterar
							</button>
						</div>					
					</c:when>
					<c:otherwise>
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" value="SALVAR"
							formaction="bebidaSalvar" class="btn btn-primary">
								Salvar
							</button>
						</div>					
					</c:otherwise>
				</c:choose>
				<div class="form-group col-xs-1 col-xs-offset-2 col-sm-offset-1 col-md-offset-0">
					<a href="bebidaList" class="btn btn-default">Cancelar</a>
				</div>
			</div>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/bebidaForm.js"></script>
</body>
</html>