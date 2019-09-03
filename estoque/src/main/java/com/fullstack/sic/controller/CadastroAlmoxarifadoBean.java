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

import com.fullstack.sic.model.Almoxarifado;
import com.fullstack.sic.model.Pessoa;
import com.fullstack.sic.model.UnidadeOrcamentaria;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Almoxarifados;
import com.fullstack.sic.repository.Pessoas;
import com.fullstack.sic.repository.UnidadeOrcamentarias;

@Named
@ViewScoped
public class CadastroAlmoxarifadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Almoxarifados almoxarifados;

	@Inject
	private Pessoas pessoas;

	@Inject
	private UnidadeOrcamentarias unidadeOrcamentarias;

	private Almoxarifado almoxarifado;
	private Filtro filtro = new Filtro();
	private LazyDataModel<Almoxarifado> model;

	private List<Pessoa> listaResponsavel;
	private List<UnidadeOrcamentaria> listaUnidadeOrcamentaria;

	public void init() {
		almoxarifado = new Almoxarifado();
		initLazyDataModel();
		listarResponsavel();
		listarUnidadeOrcamentaria();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<Almoxarifado>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Almoxarifado> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(almoxarifados.quantidadeFiltrados(filtro));

				return almoxarifados.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (almoxarifado != null) {
			Almoxarifado AlmoxarifadoCadastrado = almoxarifados.pesquisarPorCodigo(almoxarifado.getCodigo());

			if (AlmoxarifadoCadastrado != null) {
				almoxarifado = AlmoxarifadoCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Almoxarifado já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getAlmoxarifado() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setAlmoxarifado(new Almoxarifado());
	}

	public void salvar() {
		if (getAlmoxarifado() != null) {
			setAlmoxarifado(almoxarifados.salvar(getAlmoxarifado()));

			if (getAlmoxarifado().getId() != null) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Almoxarifado cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Almoxarifado. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	private void listarResponsavel() {
		listaResponsavel = pessoas.todos();
	}
	
	private void listarUnidadeOrcamentaria() {
		listaUnidadeOrcamentaria = unidadeOrcamentarias.todos();
	}

	public Almoxarifado getAlmoxarifados() {
		return almoxarifado;
	}

	public void setAlmoxarifados(Almoxarifados almoxarifados) {
		this.almoxarifados = almoxarifados;
	}

	public Almoxarifado getAlmoxarifado() {
		return almoxarifado;
	}

	public void setAlmoxarifado(Almoxarifado almoxarifado) {
		this.almoxarifado = almoxarifado;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Almoxarifado> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Almoxarifado> model) {
		this.model = model;
	}

	public List<Pessoa> getListaResponsavel() {
		return listaResponsavel;
	}

	public List<UnidadeOrcamentaria> getListaUnidadeOrcamentaria() {
		return listaUnidadeOrcamentaria;
	}

}