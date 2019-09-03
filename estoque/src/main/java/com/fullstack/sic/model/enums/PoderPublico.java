package com.fullstack.sic.model.enums;

public enum PoderPublico
{
	MUNICIPAL("Municipal"), ESTADUAL("Estadual");
	
	private String descricao;
	
	PoderPublico(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
}
