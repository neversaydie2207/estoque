package com.fullstack.sic.model.enums;

public enum OrgaoEmissorIdentidade
{
	SSP("Secretaria de Segurança Pública", "SSP"),
	SSPDS("Secretaria de Segurança Pública e Defesa Social", "SSPDS"),
	PM("Polícia Militar", "PM"),
	PC("Policia Civil", "PC"),
	CNT("Carteira Nacional de Habilitação", "CNT"),
	DIC("Diretoria de Identificação Civil", "DIC"),
	CTPS("Carteira de Trabaho e Previdência Social", "CTPS"),
	FGTS("Fundo de Garantia do Tempo de Serviço", "FGTS"),
	IFP("Instituto Félix Pacheco", "IFP"),
	IPF("Instituto Pereira Faustino", "IPF"),
	IML("Instituto Médico-Legal", "IML"),
	MTE("Ministério do Trabalho e Emprego", "MTE"),
	MMA("Ministério da Marinha", "MMA"),
	MAE("Ministério da Aeronáutica", "MAE"),
	MEX("Ministério do Exército", "MEX"),
	POF("Polícia Federal", "POF"),
	POM("Polícia Militar", "POM"),
	SES("Carteira de Estrangeiro", "SES"),
	SJS("Secretaria da Justiça e Segurança", "SJS"),
	SJTS("Secretaria da Justiça do Trabalho e Segurança", "SJTS"),
	ZZZ("Outros (inclusive exterior)", "ZZZ");
	
	private String descricao;
	private String nome;
	
	OrgaoEmissorIdentidade(String descricao, String nome)
	{
		this.descricao = descricao;
		this.nome = nome;
	}
	
	public String getDescricao()
	{
		return descricao;
	}
	
	public String getNome()
	{
		return nome;
	}

}
