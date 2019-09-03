package com.fullstack.sic.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.fullstack.sic.model.Produto;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Produtos;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;

	private Produto produto;
	private Filtro filtro = new Filtro();
	private LazyDataModel<Produto> model;

	public void init() {
		produto = new Produto();
		initLazyDataModel();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<Produto>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Produto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(produtos.quantidadeFiltrados(filtro));

				return produtos.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (produto != null) {
			Produto ProdutoCadastrado = produtos.pesquisarPorCodigo(produto.getCodigo());

			if (ProdutoCadastrado != null) {
				produto = ProdutoCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Produto já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getProduto() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setProduto(new Produto());
	}

	public void salvar() {
		if (getProduto() != null) {
			setProduto(produtos.salvar(getProduto()));

			if (getProduto().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Produto cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar o Produto. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Produto> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Produto> model) {
		this.model = model;
	}

}