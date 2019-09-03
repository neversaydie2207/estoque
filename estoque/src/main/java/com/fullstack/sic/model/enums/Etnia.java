package com.fullstack.sic.model.enums;

public enum Etnia
{
	
	BRANCA("Branca"), PRETA("Preta"), PARDA("Parda"), AMARELA("Amarela"), INDIGENA("Indígena"), 
	NAO_DECLARADA("Não Declarada"); 
	
	private String descricao;
	
	Etnia(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

}
