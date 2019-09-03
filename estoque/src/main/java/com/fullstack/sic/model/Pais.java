package com.fullstack.sic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_pais")
public class Pais implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(name = "nome_pt")
	private String nomePortugues;

	private String iso2;
	private String iso3;

	private Integer bacen;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getNomePortugues()
	{
		return nomePortugues;
	}

	public void setNomePortugues(String nomePortugues)
	{
		this.nomePortugues = nomePortugues;
	}

	public String getIso2()
	{
		return iso2;
	}

	public void setIso2(String iso2)
	{
		this.iso2 = iso2;
	}

	public String getIso3()
	{
		return iso3;
	}

	public void setIso3(String iso3)
	{
		this.iso3 = iso3;
	}

	public Integer getBacen()
	{
		return bacen;
	}

	public void setBacen(Integer bacen)
	{
		this.bacen = bacen;
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
		Pais other = (Pais) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
