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
		<c:if test="${mensagens != null}">
			<div class="row">
				<div class="col-sm-8 col-md-5">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-alert"></span><strong> Problema(s) detectados:</strong>
						</br>
						<c:forEach var="mensagem" items="${mensagens}">
							${mensagem}.
							</br>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
		<form action="#" method="post">
			<fieldset>
				<legend>Escolha os filtros para gerar a análise</legend>
				<div class="row">
					<div class="col-xs-12">
						<p>OBS: Selecione as categorias e um intervalo de tempo de 3 a 12 meses.</p>
					</div>
				</div>
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
					<div class="form-group col-xs-6 col-md-2">
						<label for="mesInicial" class="control-label">Mês inicial</label>
						<select required name="mesInicial" class="form-control">
							<option disabled selected>Escolha o mês</option>
							<option value="01">Janeiro</option>
							<option value="02">Fevereiro</option>
							<option value="03">Março</option>
							<option value="04">Abril</option>
							<option value="05">Maio</option>
							<option value="06">Junho</option>
							<option value="07">Julho</option>
							<option value="08">Agosto</option>
							<option value="09">Setembro</option>
							<option value="10">Outubro</option>
							<option value="11">Novembro</option>
							<option value="12">Dezembro</option>
						</select>
					</div>
					<div class="form-group col-xs-6 col-md-2">
						<label for="anoInicial" class="control-label">Ano inicial</label>
						<input type="number" name="anoInicial" class="form-control" placeholder="Digite o ano"/>
					</div>
					<div class="form-group col-xs-6 col-md-2">
						<label for="mesFinal" class="control-label">Mês final</label>
						<select required name="mesFinal" class="form-control">
							<option disabled selected>Escolha o mês</option>
							<option value="01">Janeiro</option>
							<option value="02">Fevereiro</option>
							<option value="03">Março</option>
							<option value="04">Abril</option>
							<option value="05">Maio</option>
							<option value="06">Junho</option>
							<option value="07">Julho</option>
							<option value="08">Agosto</option>
							<option value="09">Setembro</option>
							<option value="10">Outubro</option>
							<option value="11">Novembro</option>
							<option value="12">Dezembro</option>
						</select>
					</div>
					<div class="form-group col-xs-6 col-md-2">
						<label for="anoFinal" class="control-label">Ano final</label>
						<input type="number" name="anoFinal" class="form-control" placeholder="Digite o ano"/>
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
				<div class="col-xs-12">
					<img src="graficoImagem" class="img-responsive"/>
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