package com.fullstack.sic.model.enums;

public enum TipoFormacao
{
	ENSINO_FUNDAMENTAL("Ensino Fundamental"), ENSINO_MEDIO("Ensino Médio"), 
	NIVEL_PEDAGOGICO_III("3º Nivel Pedagógico"), NIVEL_PEDAGOGICO_IV("4º Nivel Pedagógico"), 
	GRADUACAO("Graduação"), POS_GRADUACAO("Pós Graduação"), MESTRADO("Mestrado"), DOUTORADO("Doutorado"), 
	POS_DOUTORADO("Pós Doutorado");

	private String descricao;

	TipoFormacao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}
}
