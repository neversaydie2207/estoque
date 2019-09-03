package com.fullstack.sic.model.nativequery;

import java.io.Serializable;

public class TotalProdutoEstoque implements Serializable {
	private static final long serialVersionUID = 1L;

	private String Produto;
	private Integer total;

	public String getProduto() {
		return Produto;
	}

	public void setProduto(String produto) {
		Produto = produto;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
