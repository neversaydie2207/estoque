package com.fullstack.teste.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class RetiradaItem {

	private Long id;

	private Item item;
	
	private Unidade unidade;
	
	private Integer quantidade;
	
	private Double valorUnitario;
	
	private Double valorTotal;
	
	private Boolean perecivel;
	
	private String numeroLote;
	
	@Temporal(TemporalType.DATE)
	private Date validadeLote;

}