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

import com.fullstack.sic.model.EntradaAlmoxarifado;
import com.fullstack.sic.model.EntradaProduto;
import com.fullstack.sic.model.Produto;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.EntradasProdutos;
import com.fullstack.sic.repository.Produtos;

@Named
@ViewScoped
public class CadastroEntradaProdutoBean2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntradasProdutos entradasProdutos;

	@Inject
	private Produtos produtos;

	private EntradaAlmoxarifado entrada;
	private EntradaProduto entradaProduto;
	private Filtro filtro = new Filtro();
	private LazyDataModel<EntradaProduto> model;

	private List<Produto> listaProduto;

	public void init() {
		entradaProduto = new EntradaProduto();
		entradaProduto.setEntrada(entrada);
		initLazyDataModel();
		listarProduto();
		System.out.println("entrada.getId() = " + entrada.getId());
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<EntradaProduto>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<EntradaProduto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(entradasProdutos.quantidadeFiltrados(filtro));

				return entradasProdutos.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (entradaProduto != null) {
			EntradaProduto entradaProdutoCadastrado = entradasProdutos.pesquisarPorCodigo(entradaProduto.getCodigo());

			if (entradaProdutoCadastrado != null) {
				entradaProduto = entradaProdutoCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Inclusão já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getEntradaProduto() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setEntradaProduto(new EntradaProduto());
		entradaProduto.setEntrada(entrada);
	}

	public void salvar() {
		if (getEntradaProduto() != null) {
			entradaProduto.setValorTotal(entradaProduto.getValorUnitario()* entradaProduto.getQuantidade());
			setEntradaProduto(entradasProdutos.salvar(getEntradaProduto()));

			if (getEntradaProduto().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Produto inserido com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao retirar o Produto. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	private void listarProduto() {
		listaProduto = produtos.todos();
	}

	public EntradasProdutos getEntradasProdutos() {
		return entradasProdutos;
	}

	public void setEntradasProdutos(EntradasProdutos entradasProdutos) {
		this.entradasProdutos = entradasProdutos;
	}

	public EntradaAlmoxarifado getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradaAlmoxarifado entrada) {
		this.entrada = entrada;
	}

	public EntradaProduto getEntradaProduto() {
		return entradaProduto;
	}

	public void setEntradaProduto(EntradaProduto entradaProduto) {
		this.entradaProduto = entradaProduto;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<EntradaProduto> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<EntradaProduto> model) {
		this.model = model;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

}