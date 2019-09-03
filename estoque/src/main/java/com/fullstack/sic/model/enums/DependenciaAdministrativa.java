package com.fullstack.sic.model.enums;

public enum DependenciaAdministrativa
{
	FEDERAL("Federal"), ESTADUAL("Estadual"), MUNICIPAL("Municipal"), PRIVADA("Privada"), 
	COMUNITARIA("Comunitária"), MUNICIPAL_CONVENIADA("Municipal Conveniada"), PARTICULAR_CONVENIADA("Particular Conveniada");
	
	private String descricao;
	
	DependenciaAdministrativa(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
}
