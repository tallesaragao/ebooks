$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();
});

$('.botao-voltar').click(function() {
	alert("TESTANDO");
	history.go(-1);
});

function excluir() {
	if (confirm("Deseja realmente excluir?") == false) {
		return false;
	}
}
