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
				<h1 class="page-header titulo">Alteração de livro</h1>
			</c:when>
			<c:otherwise>
				<h1 class="page-header titulo">Cadastro de livro</h1>				
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
			<fieldset>
				<legend>
					<span class="legend-logo glyphicon glyphicon-book"></span> Dados do livro					
				</legend>
				<div class="row">
					<div class="col-xs-12 col-md-4">					
						<div class="form-group">
							<label for="titulo" class="control-label">Título</label>						
							<input type="text" name="titulo" placeholder="Título do livro"
							value="${livro.titulo}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">
						<div class="form-group">
							<label for="ano" class="control-label">Ano</label>						
							<input type="number" name="ano" placeholder="Ano"
							value="${livro.ano}" class="form-control ano"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-1">
						<div class="form-group">
							<label for="edicao" class="control-label">Edição</label>						
							<input type="text" name="edicao" placeholder="Edição"
							value="${livro.edicao}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">					
						<div class="form-group">
							<label for="numeroPaginas" class="control-label">Nº Páginas</label>						
							<input type="number" name="numeroPaginas" placeholder="Nº Páginas"
							value="${livro.numeroPaginas}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">					
						<div class="form-group">
							<label for="isbn" class="control-label">ISBN</label>						
							<input type="text" name="isbn" placeholder="ISBN"
							value="${livro.isbn}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">
						<div class="form-group">
							<label for="quantMin" class="control-label">Qtde. Mínima</label>						
							<input type="number" name="quantMin" placeholder="0"
							value="${livro.estoque.quantidadeMinima}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">
						<div class="form-group">
							<label for="quantMax" class="control-label">Qtde. Máxima</label>						
							<input type="number" name="quantMax" placeholder="0"
							value="${livro.estoque.quantidadeMaxima}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">
						<div class="form-group">
							<label for="quantAtual" class="control-label">Qtde. em Estoque</label>						
							<input type="number" name="quantAtual" placeholder="0"
							value="${livro.estoque.quantidadeAtual}" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-md-2">
						<div class="form-group">
							<label for="grupoPrecificacao" class="control-label">Grupo</label>
							<select required name="grupoPrecificacao" class="form-control">
								<option selected disabled value="">Escolha um grupo</option>
								<c:forEach items="${gruposPrecificacao}" var="gp">
									<c:choose>
										<c:when test="${livro.grupoPrecificacao.id eq gp.id}">
											<option value="${gp.id}" selected>${gp.nome}</option>
										</c:when>
										<c:otherwise>
											<option value="${gp.id}">${gp.nome}</option>
										</c:otherwise>									
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">
						<div class="form-group">
							<label for="precoCusto" class="control-label">Preço de custo</label>						
							<input type="number" step="any" name="precoCusto" placeholder="Preço de custo"
							value="${livro.precificacao.precoCusto}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">					
						<div class="form-group">
							<label for="altura" class="control-label">Altura (cm)</label>						
							<input type="number" step="any" name="altura" placeholder="Altura"
							value="${livro.dimensoes.altura}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">					
						<div class="form-group">
							<label for="largura" class="control-label">Largura (cm)</label>						
							<input type="number" step="any" name="largura" placeholder="Largura"
							value="${livro.dimensoes.largura}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">					
						<div class="form-group">
							<label for="profundidade" class="control-label">Profundidade (cm)</label>						
							<input type="number" step="any" name="profundidade" placeholder="Profundidade"
							value="${livro.dimensoes.profundidade}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-2">					
						<div class="form-group">
							<label for="peso" class="control-label">Peso (g)</label>						
							<input type="number" step="any" name="peso" placeholder="Peso"
							value="${livro.dimensoes.peso}" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-md-3">
						<div class="form-group">
							<label for="categorias" class="control-label">Categorias</label>
							<select multiple required name="categorias" class="form-control">
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
					<div class="col-xs-12 col-md-9">
						<div class="form-group">
							<label for="sinopse" class="control-label">Sinopse</label>
							<textarea name="sinopse" rows="4" class="form-control">${livro.sinopse}</textarea>
						</div>
					</div>
				</div>	
			</fieldset>
			<fieldset>
				<legend>					
					<span class="legend-logo glyphicon glyphicon-user"></span> Dados do autor
				</legend>
				<div class="row">
					<div class="col-xs-12 col-md-6">					
						<div class="form-group">
							<label for="nomeAutor" class="control-label">Nome</label>						
							<input type="text" name="nomeAutor" placeholder="Nome do autor"
							value="${autor.nome}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-3">
						<div class="form-group">
							<label for="dataNascimento" class="control-label">Data de Nascimento</label>
							<input type="date" name="dataNascimento" class="form-control"
							value="<fmt:formatDate value="${autor.dataNascimento}" pattern="yyyy-MM-dd"/>"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-3">					
						<div class="form-group">
							<label for="cpf" class="control-label">CPF</label>						
							<input type="text" name="cpf" placeholder="CPF"
							value="${autor.cpf}" class="form-control cpf"/>
						</div>
					</div>
				</div>
			</fieldset>
			<fieldset>
				<legend>					
					<span class="legend-logo glyphicon glyphicon-education"></span> Dados da editora
				</legend>
				<div class="row">
					<div class="col-xs-12 col-md-4">					
						<div class="form-group">
							<label for="nomeEditora" class="control-label">Nome</label>						
							<input type="text" name="nomeEditora" placeholder="Nome da editora"
							value="${livro.editora.nome}" class="form-control"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-3">
						<div class="form-group">
							<label for="cnpj" class="control-label">CNPJ</label>
							<input type="text" name="cnpj" placeholder="CNPJ" class="form-control cnpj"
							value="${livro.editora.cnpj}"/>
						</div>
					</div>
					<div class="col-xs-12 col-md-5">					
						<div class="form-group">
							<label for="razaoSocial" class="control-label">Razão Social</label>						
							<input type="text" name="razaoSocial" placeholder="Razão Social"
							value="${livro.editora.razaoSocial}" class="form-control"/>
						</div>
					</div>
				</div>
			</fieldset>
			<div class="row">
				<c:choose>
					<c:when test="${operacao eq 'ALTERAR'}">
						<input type="hidden" name="id" value="${livro.id}"/>
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" value="ALTERAR" id="btnAlterar"
							formaction="livroAlterar" class="btn btn-primary">
								Alterar
							</button>
						</div>					
					</c:when>
					<c:otherwise>
						<div class="form-group col-xs-1">
							<button type="submit" name="operacao" value="SALVAR" id="btnSalvar"
							formaction="livroSalvar" class="btn btn-primary">
								Salvar
							</button>
						</div>					
					</c:otherwise>
				</c:choose>
				<div class="form-group col-xs-1 col-xs-offset-2 col-sm-offset-1 col-md-offset-0">
					<a href="livroList" class="btn btn-default">Cancelar</a>
				</div>
			</div>
		</form>
	</div>
	<script src="resources/js/jquery-3.1.1.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/jquery.mask.js"></script>
	<script src="resources/js/livroForm.js"></script>
</body>
</html>