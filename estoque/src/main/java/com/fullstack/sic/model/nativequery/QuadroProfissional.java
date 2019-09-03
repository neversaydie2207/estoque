package com.fullstack.sic.model.nativequery;

import java.io.Serializable;

public class QuadroProfissional implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idFuncao;

	private String funcao;
	private int quantidadeServidorLotado;
	private int quantidadeServidorNecessario;
	private int cargaHorariaLotada;
	private int cargaHorariaNecessaria;
	private Double proventos;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getIdFuncao()
	{
		return idFuncao;
	}

	public void setIdFuncao(Long idFuncao)
	{
		this.idFuncao = idFuncao;
	}

	public String getFuncao()
	{
		return funcao;
	}

	public void setFuncao(String funcao)
	{
		this.funcao = funcao;
	}

	public int getQuantidadeServidorLotado()
	{
		return quantidadeServidorLotado;
	}

	public void setQuantidadeServidorLotado(int quantidadeServidorLotado)
	{
		this.quantidadeServidorLotado = quantidadeServidorLotado;
	}

	public int getQuantidadeServidorNecessario()
	{
		return quantidadeServidorNecessario;
	}

	public void setQuantidadeServidorNecessario(int quantidadeServidorNecessario)
	{
		this.quantidadeServidorNecessario = quantidadeServidorNecessario;
	}

	public int getCargaHorariaLotada()
	{
		return cargaHorariaLotada;
	}

	public void setCargaHorariaLotada(int cargaHorariaLotada)
	{
		this.cargaHorariaLotada = cargaHorariaLotada;
	}

	public int getCargaHorariaNecessaria()
	{
		return cargaHorariaNecessaria;
	}

	public void setCargaHorariaNecessaria(int cargaHorariaNecessaria)
	{
		this.cargaHorariaNecessaria = cargaHorariaNecessaria;
	}

	public Double getProventos()
	{
		return proventos;
	}

	public void setProventos(Double proventos)
	{
		this.proventos = proventos;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuadroProfissional other = (QuadroProfissional) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
