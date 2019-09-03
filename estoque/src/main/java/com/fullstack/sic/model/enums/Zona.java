package com.fullstack.sic.model.enums;

public enum Zona
{
	URBANA("Urbana"), RURAL("Rural");
	
	private String descricao;
	
	Zona(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

}
