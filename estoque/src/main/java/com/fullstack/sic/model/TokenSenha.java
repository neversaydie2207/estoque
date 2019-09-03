package com.fullstack.sic.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "tb_tokensenhas")
public class TokenSenha implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	private String email;
	//private String cpf;

	@Column(name = "data_solicitacao")
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacao;

	@Column(name = "habilitado")
	private boolean isHabilitado;

	@Column(name = "validado")
	private boolean isValidado;

	@Column(name = "data_validacao")
	@Temporal(TemporalType.DATE)
	private Date dataValidacao;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	/*
	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}
	*/
	
	public Date getDataSolicitacao()
	{
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao)
	{
		this.dataSolicitacao = dataSolicitacao;
	}

	public boolean isHabilitado()
	{
		return isHabilitado;
	}

	public void setHabilitado(boolean isHabilitado)
	{
		this.isHabilitado = isHabilitado;
	}

	public boolean isValidado()
	{
		return isValidado;
	}

	public void setValidado(boolean isValidado)
	{
		this.isValidado = isValidado;
	}

	public Date getDataValidacao()
	{
		return dataValidacao;
	}

	public void setDataValidacao(Date dataValidacao)
	{
		this.dataValidacao = dataValidacao;
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
		TokenSenha other = (TokenSenha) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
