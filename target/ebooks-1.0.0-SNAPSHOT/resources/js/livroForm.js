$(document).ready(function() {
	$('.cpf').mask('000.000.000-00', {reverse:true});
	$('.cnpj').mask('00.000.000/0000-00', {reverse:true});
	$('.ano').mask('0000');
	$('isbn').mask('0000000000000');
});
	