package com.fullstack.sic.model.enums;

public enum TipoCertidaoCivil
{
	NASCIMENTO("Certidão de Nascimento"), CASAMENTO("Certidão de Casamento");
	
	private String descricao;
	
	TipoCertidaoCivil(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
}
