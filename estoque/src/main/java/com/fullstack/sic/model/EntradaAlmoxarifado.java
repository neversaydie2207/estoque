package com.fullstack.sic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_entrada_almoxarifado")
public class EntradaAlmoxarifado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	private String nome;

	@Temporal(TemporalType.DATE)
	private Date data;

	@ManyToOne
	private Pessoa responsavel;

	@ManyToOne
	private Almoxarifado almoxarifado;

	@ManyToOne
	private Movimentacao movimentacaoEntrada;

	private String observacao;

	private Boolean armazenado;

	@OneToMany(mappedBy = "entrada")
	private List<EntradaProduto> entradaProduto;

	public Long getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}

	public Almoxarifado getAlmoxarifado() {
		return almoxarifado;
	}

	public void setAlmoxarifado(Almoxarifado almoxarifado) {
		this.almoxarifado = almoxarifado;
	}

	public Movimentacao getMovimentacaoEntrada() {
		return movimentacaoEntrada;
	}

	public void setMovimentacaoEntrada(Movimentacao movimentacaoEntrada) {
		this.movimentacaoEntrada = movimentacaoEntrada;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getArmazenado() {
		return armazenado;
	}

	public void setArmazenado(Boolean armazenado) {
		this.armazenado = armazenado;
	}

	public List<EntradaProduto> getEntradaProduto() {
		return entradaProduto;
	}

	public void setEntradaProduto(List<EntradaProduto> entradaProduto) {
		this.entradaProduto = entradaProduto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntradaAlmoxarifado other = (EntradaAlmoxarifado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}