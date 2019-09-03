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

import com.fullstack.sic.model.Fornecedor;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Fornecedores;

@Named
@ViewScoped
public class CadastroFornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Fornecedores fornecedores;

	private Fornecedor fornecedor;
	private Filtro filtro = new Filtro();
	private LazyDataModel<Fornecedor> model;

	public void init() {
		fornecedor = new Fornecedor();
		initLazyDataModel();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<Fornecedor>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Fornecedor> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(fornecedores.quantidadeFiltrados(filtro));

				return fornecedores.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (fornecedor != null) {
			Fornecedor FornecedorCadastrado = fornecedores.pesquisarPorCodigo(fornecedor.getCodigo());

			if (FornecedorCadastrado != null) {
				fornecedor = FornecedorCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Fornecedor já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getFornecedor() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setFornecedor(new Fornecedor());
	}

	public void salvar() {
		if (getFornecedor() != null) {
			setFornecedor(fornecedores.salvar(getFornecedor()));

			if (getFornecedor().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Fornecedor cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar o Fornecedor. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public Fornecedores getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(Fornecedores fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Fornecedor> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Fornecedor> model) {
		this.model = model;
	}

}
