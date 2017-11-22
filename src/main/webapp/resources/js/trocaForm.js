function selecionarItemTroca() {
	$('.selectItem').on('click', function() {
		var nomeId = $(this).attr('id');
		var id = nomeId.replace('selecionarItem', '');
		var nomeElemento = '#quantidade' + id;
		if($(this).is(':checked')) {
			$(nomeElemento).attr('disabled', false);
		}
		else {
			$(nomeElemento).attr('disabled', true);
		}
	});
}

function selecionarCompraToda() {
	$('.select-compra').on('click', function() {
		if($(this).is(':checked')) {
			$('#fsItens').attr('disabled', true);
		}
		else {
			$('#fsItens').attr('disabled', false);
		}
	});
}