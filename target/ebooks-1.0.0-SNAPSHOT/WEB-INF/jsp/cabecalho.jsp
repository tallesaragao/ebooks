<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<nav id="navbar" role="navigation" class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header navbar-cabecalho">
				<a href="#" class="navbar-brand navbar-logo">
					Livraria Online
				</a>
				<p class="navbar-text">Olá, <c:out value="${login.usuario}" default="visitante"></c:out></p>
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
					<li><a href="loginCliente">Login</a></li>
					<li><a href="categoriaList">Categoria</a></li>
					<li><a href="livroList">Livro</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>