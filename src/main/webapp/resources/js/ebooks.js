$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();
	selecionarItemTroca();
	selecionarCompraToda();
});

$('.botao-voltar').click(function() {
	history.go(-1);
});


function excluir() {
	if (confirm("Deseja realmente excluir?") == false) {
		return false;
	}
}
