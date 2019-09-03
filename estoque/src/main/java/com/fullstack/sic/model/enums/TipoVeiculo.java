package com.fullstack.sic.model.enums;

public enum TipoVeiculo
{
	BICILETA("Bicicleta"),
	MICRO_ONIBUS("Micro-Ônibus"),
	ONIBUS("Ônibus"),
	TRACAO_ANIMAL("Tração Animal"),
	VANS_KOMBI("Vans/VW Kombi"),
	OUTROS_RODOVIARIO("Outro tipo de veículo rodoviário"),
	AQUAVIARIO_I("Aquaviário - Capacidade até 5 alunos"),
	AQUAVIARIO_II("Aquaviário - Capacidade de 5 a 15 alunos"),
	AQUAVIARIO_III("Aquaviário - Capacidade de 15 a 35 alunos"),
	AQUAVIARIO_IV("Aquaviário - Capacidade acima de 35 alunos"),
	FERROVIARIO("Trem/Metrô");
	
	private String descricao;
	
	TipoVeiculo(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

}
