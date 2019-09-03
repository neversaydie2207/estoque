package com.fullstack.teste.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fullstack.sic.model.UnidadeOrcamentaria;

public class Kit {

	private Long id;

	private UnidadeOrcamentaria destino;

	private UnidadeOrcamentaria origem;

	@Temporal(TemporalType.DATE)
	private Date dataEnvio;

	@Temporal(TemporalType.DATE)
	private Date dataRecebimento;

}