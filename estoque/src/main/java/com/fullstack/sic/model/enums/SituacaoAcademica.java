package com.fullstack.sic.model.enums;

public enum SituacaoAcademica
{
	COMPLETO("Completo"), INCOMPLETO("Incompleto"), CURSANDO("Cursando");

	private String descricao;

	SituacaoAcademica(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}

}
