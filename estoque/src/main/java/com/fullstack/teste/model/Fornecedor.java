package com.fullstack.teste.model;

import javax.persistence.Embedded;

import com.fullstack.sic.model.embeddable.Contato;
import com.fullstack.sic.model.embeddable.Endereco;

public class Fornecedor {
	
	private Long id;
	
	private String cnpjCpf;
	
	private String nome;
	
	private String nomeFantasia;
	
	private String ie;
	
	@Embedded
	private Endereco endereco;
	
	@Embedded
	private Contato contato;

	

}
