package com.fullstack.teste.model;

import javax.persistence.Embedded;

import com.fullstack.sic.model.embeddable.Contato;
import com.fullstack.sic.model.embeddable.Endereco;

public class Local {

	private Long id;
	private String nome;
	private String sigla;
	private Boolean ativo;

	@Embedded
	private Endereco endereco;

	@Embedded
	private Contato contato;

	//private UnidadeOrcamentaria unidadeOrcamentaria;

}
