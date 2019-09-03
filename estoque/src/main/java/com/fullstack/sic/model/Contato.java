package com.fullstack.sic.model;

import javax.persistence.Embeddable;

@Embeddable
public class Contato {
	
	private String numero_telefone;

	public String getNumero_telefone() {
		return numero_telefone;
	}

	public void setNumero_telefone(String numero_telefone) {
		this.numero_telefone = numero_telefone;
	}
	
	

}
