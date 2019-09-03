
var Amges = Amges || {};

Amges.MaskMoney = (function () {
	
	function MaskMoney()
	{
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function ()
	{
		//Numeros decimais
		this.decimal.maskMoney( {decimal: ",", thousands: ".", allowZero: "true", showSymbol: "true", symbol: "R$" } );
		
		//Precisão 0 para numeros inteiros
		this.plain.maskMoney( { precision : 0, thousands: "." } );
		
	}
	
	return MaskMoney;
	
}());

Amges.Mask = (function () {
	
	function Mask()
	{
		this.data = $('.js-mask-data');
		this.hora = $('.js-mask-hora');
		this.cpf = $('.js-mask-cpf');
		this.cnpj = $('.js-mask-cnpj');
		this.celular = $('.js-mask-celular');
		this.telefone = $('.js-mask-telefone');
		this.inep = $('.js-mask-inep');
		this.cnes = $('.js-mask-cnes');
		this.cep = $('.js-mask-cep');
		this.cef = $('.js-mask-cef');
		this.matricula = $('.js-mask-matricula');
		this.ch = $('.js-mask-ch');
		this.codigo4 = $('.js-mask-codigo-4d');
	}
	
	Mask.prototype.enable = function ()
	{
		/*
		var NonoDigitoMask = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 0 0000-0000' : '(00) 0000-00009';
			},
			ndOptions = {
			  onKeyPress: function(val, e, field, options) {
			      field.mask(NonoDigitoMask.apply({}, arguments), options);
			    }
			};
		*/
		
		//data
		this.data.mask( "00/00/0000", {clearIfNotMatch: true});

		//hora
		this.hora.mask( "00:00", {clearIfNotMatch: true});
		
		//cpf
		this.cpf.mask( '000.000.000-00', {clearIfNotMatch: true} );
		
		this.cnpj.mask( '00.000.000/0000-00', {clearIfNotMatch: true} );
		
		this.celular.mask('(00) 0 0000-0000', {clearIfNotMatch: true});
		
		this.telefone.mask('(00) 0000-0000', {clearIfNotMatch: true});
		
		this.inep.mask( "00000000", {clearIfNotMatch: true});
		this.cnes.mask( "0000000", {clearIfNotMatch: true});
		
		this.cep.mask( "00000-000", {clearIfNotMatch: true});
		
		this.cef.mask( "SSS-0000", {clearIfNotMatch: true});
		
		this.matricula.mask( "000000-A", {clearIfNotMatch: true});
		
		this.ch.mask( "009", {clearIfNotMatch: true});
		this.codigo4.mask( "0000", {clearIfNotMatch: true});
		
	}
	
	return Mask;
	
}());

$(function() {
	
	var maskMoney = new Amges.MaskMoney();
	maskMoney.enable();
	
	var mask = new Amges.Mask();
	mask.enable();
	
	/*Time do Carousel
	$('.carousel').carousel({
		interval: 5000
	});
	*/
	
	//Chama funcao do arquivo maximus-dialog para configuar datetimepicker
	//amges.initFormExtendedDatetimepickers();
	
	//Configura data atual para input-text
	data = new Date();
	if($('.js-data-atual').val() === '')
	{
		$('.js-data-atual').val(moment().format("L"));
	}


});

/* DESABILITA DIV */
$(document).ready(function() {
    $('.readonly').find('input, textarea, select').attr('disabled', 'disabled');
});