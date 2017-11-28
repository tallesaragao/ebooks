<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<nav id="navbar" role="navigation" class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header navbar-cabecalho">
				<a href="livroList" class="navbar-brand navbar-logo">
					Livraria Online
				</a>
				<button type="button" class="navbar-left navbar-toggle collapsed"
				data-toggle="collapse" data-target="#menu-collapse"
				aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
			</div>
			<div id="menu-collapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test="${login.usuario != null}">
							<c:if test="${login.perfilAcesso.nome eq 'Cliente' || login.perfilAcesso.nome eq 'Administrador'}">
								<li id="idLoginDrop" class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								  		Olá, ${login.usuario} <span class="caret"></span></a>				
									<ul class="dropdown-menu" role="menu">
										<c:if test="${login.perfilAcesso.nome eq 'Cliente' }">
											<li>
							                	<a id="detalhesCliente" href="clienteView?operacao=CONSULTAR&id=${login.cliente.id}">
													Dados da conta
												</a>
											</li>
						                	<li class="divider"></li>
					                	</c:if>
					                	<li><a id="logoutSite" href="logoutSite">Logout</a></li>
					             	</ul>                
					            </li>
					            <c:if test="${login.perfilAcesso.nome eq 'Cliente' }">
									<li><a id="carrinhoCliente" href="carrinhoCliente">Carrinho</a></li>
								</c:if>
							</c:if>
							<c:if test="${login.perfilAcesso.nome eq 'Administrador'}">
								<li><a id="clienteList" href="clienteList">Clientes</a></li>
								<li><a id="categoriaList" href="categoriaList">Categorias</a></li>
								<li><a id="pedidoList" href="pedidoList">Pedidos</a></li>
								<li><a id="trocaList" href="trocaList">Trocas</a></li>
							</c:if>
						</c:when>
						<c:otherwise>
							<li><a id="detalhesCliente" href="loginSite">Login</a></li>
						</c:otherwise>
					</c:choose>
					<li><a id="livroList" href="livroList">Livros</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>