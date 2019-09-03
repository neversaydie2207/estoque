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
import com.fullstack.sic.model.EntradaAlmoxarifado;
import com.fullstack.sic.model.Movimentacao;
import com.fullstack.sic.model.Pessoa;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Almoxarifados;
import com.fullstack.sic.repository.Entradas;
import com.fullstack.sic.repository.Movimentacoes;
import com.fullstack.sic.repository.Pessoas;

@Named
@ViewScoped
public class CadastroEntradaSolicitacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Entradas entradas;

	@Inject
	private Pessoas pessoas;

	@Inject
	private Almoxarifados almoxarifados;

	@Inject
	private Movimentacoes movimentacoes;

	private EntradaAlmoxarifado entrada;
	private Filtro filtro = new Filtro();
	private LazyDataModel<EntradaAlmoxarifado> model;

	private List<Pessoa> listaResponsavel;
	private List<Almoxarifado> listaAlmoxarifado;
	private List<Movimentacao> listaMovimentacao;

	public void init() {
		entrada = new EntradaAlmoxarifado();
		initLazyDataModel();
		listarResponsavel();
		listarAlmoxarifado();
		listarMovimentacao();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<EntradaAlmoxarifado>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<EntradaAlmoxarifado> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(entradas.quantidadeFiltrados(filtro));

				return entradas.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (entrada != null) {
			EntradaAlmoxarifado EntradaCadastrado = entradas.pesquisarPorCodigo(entrada.getCodigo());

			if (EntradaCadastrado != null) {
				entrada = EntradaCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Entrada já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getEntrada() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setEntrada(new EntradaAlmoxarifado());
	}

	public void salvar() {
		if (getEntrada() != null) {
			setEntrada(entradas.salvar(getEntrada()));

			if (getEntrada().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Entrada cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Entrada. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	private void listarResponsavel() {
		listaResponsavel = pessoas.todos();
	}

	private void listarAlmoxarifado() {
		listaAlmoxarifado = almoxarifados.todos();
	}

	private void listarMovimentacao() {
		listaMovimentacao = movimentacoes.todos();
	}

	public Entradas getEntradas() {
		return entradas;
	}

	public void setEntradas(Entradas entradas) {
		this.entradas = entradas;
	}

	public EntradaAlmoxarifado getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradaAlmoxarifado entrada) {
		this.entrada = entrada;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<EntradaAlmoxarifado> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<EntradaAlmoxarifado> model) {
		this.model = model;
	}

	public List<Pessoa> getListaResponsavel() {
		return listaResponsavel;
	}

	public List<Almoxarifado> getListaAlmoxarifado() {
		return listaAlmoxarifado;
	}

	public List<Movimentacao> getListaMovimentacao() {
		return listaMovimentacao;
	}

}