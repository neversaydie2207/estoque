package com.fullstack.sic.model.embeddable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fullstack.sic.model.Cidade;
import com.fullstack.sic.model.Distrito;
import com.fullstack.sic.model.Estado;

@Embeddable
public class Endereco
{
	private String cep;
	
	@ManyToOne
	private Estado estado;
	
	@ManyToOne
	private Cidade cidade;
	
	private String bairro;
	
	private String logradouro;
	
	private String numero;
	
	private String referencia;
	private String complemento;
	
	@ManyToOne
	private Distrito distrito;
	
	@Transient
	private String enderecoCompleto;
	
	public String getCep()
	{
		return cep;
	}

	public void setCep(String cep)
	{
		this.cep = cep;
	}

	public Estado getEstado()
	{
		return estado;
	}

	public void setEstado(Estado estado)
	{
		this.estado = estado;
	}

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}
	
	public String getBairro()
	{
		return bairro;
	}

	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}

	public String getLogradouro()
	{
		return logradouro;
	}

	public void setLogradouro(String logradouro)
	{
		this.logradouro = logradouro;
	}

	public String getNumero()
	{
		return numero;
	}

	public void setNumero(String numero)
	{
		this.numero = numero;
	}

	public String getReferencia()
	{
		return referencia;
	}

	public void setReferencia(String referencia)
	{
		this.referencia = referencia;
	}

	public String getComplemento()
	{
		return complemento;
	}

	public void setComplemento(String complemento)
	{
		this.complemento = complemento;
	}

	public Distrito getDistrito()
	{
		return distrito;
	}

	public void setDistrito(Distrito distrito)
	{
		this.distrito = distrito;
	}

	public String getEnderecoCompleto()
	{
		if(cidade != null)
		{
			if(distrito != null)
			{
				enderecoCompleto = logradouro + ", " + numero  + ", Distrito " + distrito.getNome() + 
						" / " + cidade.getNome() + "-" + estado.getNome();
			}
			else
			{
				enderecoCompleto = logradouro + ", " + numero  + 
						" / " + cidade.getNome() + "-" + estado.getNome();

			}
		}
		
		return enderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto)
	{
		this.enderecoCompleto = enderecoCompleto;
	}

}
