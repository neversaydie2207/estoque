package com.fullstack.teste.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fullstack.sic.model.Almoxarifado;
import com.fullstack.sic.model.Movimentacao;
import com.fullstack.sic.model.Pessoa;

public class Entrada {

	private Long id;

	@Temporal(TemporalType.DATE)
	private Date data;

	private Pessoa responsavel;

	private Almoxarifado almoxarifado;

	private Movimentacao movimentacaoEntrada;
	
	private String observacao;

}