package com.fullstack.sic.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_entrada_produto")
public class EntradaProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	@ManyToOne
	private EntradaAlmoxarifado entrada;//campo para teste, tentar resolver sem esse campo - mapeamento e join

	@ManyToOne
	private Produto produto;

	private Integer quantidade;

	private Double valorUnitario;

	private Double valorTotal;

	private Boolean perecivel;

	private String numeroLote;

	private Boolean armazenado;

	@Temporal(TemporalType.DATE)
	private Date validadeLote;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EntradaAlmoxarifado getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradaAlmoxarifado entrada) {
		this.entrada = entrada;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Boolean getPerecivel() {
		return perecivel;
	}

	public void setPerecivel(Boolean perecivel) {
		this.perecivel = perecivel;
	}

	public String getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(String numeroLote) {
		this.numeroLote = numeroLote;
	}

	public Date getValidadeLote() {
		return validadeLote;
	}

	public void setValidadeLote(Date validadeLote) {
		this.validadeLote = validadeLote;
	}

	public Boolean getArmazenado() {
		return armazenado;
	}

	public void setArmazenado(Boolean armazenado) {
		this.armazenado = armazenado;
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
		EntradaProduto other = (EntradaProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}