package com.fullstack.sic.model.enums;

public enum Turno
{

	M("Manhã"), T("Tarde"), MT("Manhã/Tarde"), MNT("Manhã/Tarde/Noite"), MN("Manhã/Noite"), TN("Tarde/Noite"), N("Noite");

	private String descricao;

	Turno(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}

}
