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

import com.fullstack.sic.model.OrgaoOrcamentario;
import com.fullstack.sic.model.UnidadeOrcamentaria;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.OrgaoOrcamentarios;
import com.fullstack.sic.repository.UnidadeOrcamentarias;

@Named
@ViewScoped
public class CadastroUnidadeOrcamentariaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeOrcamentarias unidadeOrcamentarias;
	
	@Inject
	private OrgaoOrcamentarios orgaoOrcamentarios;

	private UnidadeOrcamentaria unidadeOrcamentaria;
	private Filtro filtro = new Filtro();
	private LazyDataModel<UnidadeOrcamentaria> model;
	
	private List<OrgaoOrcamentario> listaOrgaoOrcamentario;

	public void init() {
		unidadeOrcamentaria = new UnidadeOrcamentaria();
		initLazyDataModel();
		listaOrgaoOrcamentario();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<UnidadeOrcamentaria>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<UnidadeOrcamentaria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(unidadeOrcamentarias.quantidadeFiltrados(filtro));

				return unidadeOrcamentarias.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (unidadeOrcamentaria != null) {
			UnidadeOrcamentaria UnidadeOrcamentariaCadastrado = unidadeOrcamentarias
					.pesquisarPorCodigo(unidadeOrcamentaria.getCodigo());

			if (UnidadeOrcamentariaCadastrado != null) {
				unidadeOrcamentaria = UnidadeOrcamentariaCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Unidade Orçamentária já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getUnidadeOrcamentaria() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setUnidadeOrcamentaria(new UnidadeOrcamentaria());
	}

	public void salvar() {
		if (getUnidadeOrcamentaria() != null) {
			setUnidadeOrcamentaria(unidadeOrcamentarias.salvar(getUnidadeOrcamentaria()));

			if (getUnidadeOrcamentaria().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Unidade Orçamentária cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Unidade Orçamentária. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}
	
	private void listaOrgaoOrcamentario() {
		listaOrgaoOrcamentario = orgaoOrcamentarios.todos();
	}

	public UnidadeOrcamentarias getUnidadeOrcamentarias() {
		return unidadeOrcamentarias;
	}

	public void setUnidadeOrcamentarias(UnidadeOrcamentarias unidadeOrcamentarias) {
		this.unidadeOrcamentarias = unidadeOrcamentarias;
	}

	public UnidadeOrcamentaria getUnidadeOrcamentaria() {
		return unidadeOrcamentaria;
	}

	public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
		this.unidadeOrcamentaria = unidadeOrcamentaria;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<UnidadeOrcamentaria> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<UnidadeOrcamentaria> model) {
		this.model = model;
	}

	public List<OrgaoOrcamentario> getListaOrgaoOrcamentario() {
		return listaOrgaoOrcamentario;
	}
	
}
