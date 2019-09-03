package com.fullstack.sic.model.nativequery;

import java.io.Serializable;

public class TotalMatriculaSeriacao implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String totalMatricula;
	private String seriacao;

	public String getTotalMatricula()
	{
		return totalMatricula;
	}

	public void setTotalMatricula(String totalMatricula)
	{
		this.totalMatricula = totalMatricula;
	}

	public String getSeriacao()
	{
		return seriacao;
	}

	public void setSeriacao(String seriacao)
	{
		this.seriacao = seriacao;
	}

}
