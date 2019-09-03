package com.fullstack.sic.model.enums;

public enum Semestre
{
	SEMESTRE_1("1º Semestre"), SEMESTRE_2("2º Semestre"), SEMESTRE_3("3º Semestre"),
	SEMESTRE_4("4º Semestre"), SEMESTRE_5("5º Semestre"), SEMESTRE_6("6º Semestre"),
	SEMESTRE_7("7º Semestre"), SEMESTRE_8("8º Semestre"), SEMESTRE_9("9º Semestre"),
	SEMESTRE_10("10º Semestre"), SEMESTRE_11("11º Semestre"), SEMESTRE_12("12º Semestre"),
	CURSO_CONCLUIDO("Curso Concluído"), CURSO_NAO_CONCLUIDO("Curso Não Concluído");
	
	private String descricao;
	
	Semestre(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
}
