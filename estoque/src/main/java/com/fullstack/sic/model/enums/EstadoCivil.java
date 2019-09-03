package com.fullstack.sic.model.enums;

public enum EstadoCivil
{
	SOLTEIRO("Solteiro"), CASADO("Casado"), VIUVO("Viúvo"), 
	SEPARADO_JUDICIALMENTE("Separado Judicialmente"), DIVORCIADO("Divorciado"),
	NAO_INFORMADO("Não Informado");
	
	private String descricao;
	
	EstadoCivil(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

}
