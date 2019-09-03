package com.fullstack.sic.model.enums;

public enum TipoPesquisa
{
	POR_CPF("POR CPF"), POR_NOME("POR NOME"), ATUALIZADOS("ATUALIZADOS");

	private String descricao;

	TipoPesquisa(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}
}
