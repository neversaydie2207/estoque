package com.fullstack.teste.model;

public class TotalProdutosAlmoxarifado {

	public TotalProdutosAlmoxarifado(String nome, String produto, Integer total) {
		this.nome = nome;
		this.produto = produto;
		this.total = total;
	}

	private String nome;

	private String produto;

	private Integer total;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
