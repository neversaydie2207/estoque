package com.fullstack.sic.model.enums;

public enum Nacionalidade
{
	
	BRASILEIRA("Brasileira"), BRASILEIRA_NATURALIZADA("Brasileira - Nascido no exterior ou naturalizado"), 
	EXTRANGEIRA("Estrangeira"); 
	
	private String descricao;
	
	Nacionalidade(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

}
