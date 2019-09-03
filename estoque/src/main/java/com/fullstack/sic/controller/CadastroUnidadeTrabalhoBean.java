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

import com.fullstack.sic.model.UnidadeTrabalho;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.UnidadesTrabalhos;

@Named
@ViewScoped
public class CadastroUnidadeTrabalhoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadesTrabalhos unidadesTrabalhos;

	private UnidadeTrabalho unidadeTrabalho;
	private Filtro filtro = new Filtro();
	private LazyDataModel<UnidadeTrabalho> model;

	public void init() {
		unidadeTrabalho = new UnidadeTrabalho();
		initLazyDataModel();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<UnidadeTrabalho>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<UnidadeTrabalho> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(unidadesTrabalhos.quantidadeFiltrados(filtro));

				return unidadesTrabalhos.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (unidadeTrabalho != null) {
			UnidadeTrabalho UnidadeTrabalhoCadastrado = unidadesTrabalhos
					.pesquisarPorCodigo(unidadeTrabalho.getCodigo());

			if (UnidadeTrabalhoCadastrado != null) {
				unidadeTrabalho = UnidadeTrabalhoCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Unidade Trabalho já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getUnidadeTrabalho() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setUnidadeTrabalho(new UnidadeTrabalho());
	}

	public void salvar() {
		if (getUnidadeTrabalho() != null) {
			setUnidadeTrabalho(unidadesTrabalhos.salvar(getUnidadeTrabalho()));

			if (getUnidadeTrabalho().getId() != null) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Unidade de Trabalho cadastrado com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Unidade de Trabalho. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public UnidadesTrabalhos getUnidadesTrabalhos() {
		return unidadesTrabalhos;
	}

	public void setUnidadesTrabalhos(UnidadesTrabalhos unidadesTrabalhos) {
		this.unidadesTrabalhos = unidadesTrabalhos;
	}

	public UnidadeTrabalho getUnidadeTrabalho() {
		return unidadeTrabalho;
	}

	public void setUnidadeTrabalho(UnidadeTrabalho unidadeTrabalho) {
		this.unidadeTrabalho = unidadeTrabalho;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<UnidadeTrabalho> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<UnidadeTrabalho> model) {
		this.model = model;
	}

}
