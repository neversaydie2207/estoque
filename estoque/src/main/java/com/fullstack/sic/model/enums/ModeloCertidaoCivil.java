package com.fullstack.sic.model.enums;

public enum ModeloCertidaoCivil
{
	MODELO_ANTIGO("Modelo Antigo"), MODELO_NOVO("Modelo Novo");
	
	private String descricao;
	
	ModeloCertidaoCivil(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
}
