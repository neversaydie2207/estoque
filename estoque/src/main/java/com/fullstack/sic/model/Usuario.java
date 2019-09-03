package com.fullstack.sic.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_usuarios")
public class Usuario implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@ManyToOne
	private Sexo sexo;

	private String apelido;
	private String telefone;
	private String telefoneSecundario;
	private String cpf;
	private String rg;

	@Column(name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	private String email;
	private String senha;

	private Boolean ativo;

	private String cc;

	@ManyToOne
	private Grupo grupo;
	
	@OneToOne
	private Cidadao servidor;
	
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
		this.nome = nome.toUpperCase();
	}

	public Sexo getSexo()
	{
		return sexo;
	}

	public void setSexo(Sexo sexo)
	{
		this.sexo = sexo;
	}

	public String getApelido()
	{
		return apelido;
	}

	public void setApelido(String apelido)
	{
		this.apelido = apelido.toUpperCase();
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getTelefone()
	{
		return telefone;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
	}

	public String getTelefoneSecundario()
	{
		return telefoneSecundario;
	}

	public void setTelefoneSecundario(String telefoneSecundario)
	{
		this.telefoneSecundario = telefoneSecundario;
	}

	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public String getRg()
	{
		return rg;
	}

	public void setRg(String rg)
	{
		this.rg = rg;
	}

	public Date getDataNascimento()
	{
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public Grupo getGrupo()
	{
		return grupo;
	}

	public void setGrupo(Grupo grupo)
	{
		this.grupo = grupo;
	}

	public Boolean getAtivo()
	{
		return ativo;
	}

	public void setAtivo(Boolean ativo)
	{
		this.ativo = ativo;
	}

	public String getCc()
	{
		return cc;
	}

	public void setCc(String cc)
	{
		this.cc = cc;
	}

	public Cidadao getServidor()
	{
		return servidor;
	}

	public void setServidor(Cidadao servidor)
	{
		this.servidor = servidor;
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
		Usuario other = (Usuario) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
