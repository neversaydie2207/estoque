package com.fullstack.teste.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class NotaFiscal {

	private Long id;
	
	private String numero;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private Double valor;
}
