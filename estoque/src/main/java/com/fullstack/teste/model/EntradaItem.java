package com.fullstack.teste.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fullstack.sic.model.Produto;

public class EntradaItem {

	private Long id;

	private Produto produto;

	private Unidade unidade;

	private Integer quantidade;

	private Double valorUnitario;

	private Double valorTotal;

	private Boolean perecivel;

	private String numeroLote;

	@Temporal(TemporalType.DATE)
	private Date validadeLote;

}