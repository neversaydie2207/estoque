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
import com.fullstack.sic.model.Solicitacao;
import com.fullstack.sic.model.StatusSolicitacao;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Almoxarifados;
import com.fullstack.sic.repository.Pessoas;
import com.fullstack.sic.repository.Solicitacoes;
import com.fullstack.sic.repository.StatusSolicitacoes;

@Named
@ViewScoped
public class CadastroSolicitacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Solicitacoes solicitacoes;

	@Inject
	private Pessoas pessoas;

	@Inject
	private Almoxarifados almoxarifados;

	@Inject
	private StatusSolicitacoes statusSolicitacoes;

	private Solicitacao solicitacao;
	private Filtro filtro = new Filtro();
	private LazyDataModel<Solicitacao> model;

	private List<Pessoa> listaSolicitante;
	private List<Almoxarifado> listaAlmoxarifado;
	private List<StatusSolicitacao> listaStatusSolicitacao;

	public void init() {
		solicitacao = new Solicitacao();
		initLazyDataModel();
		listarSolicitante();
		listarAlmoxarifado();
		listarStatusSolicitacao();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<Solicitacao>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Solicitacao> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(solicitacoes.quantidadeFiltrados(filtro));

				return solicitacoes.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (solicitacao != null) {
			Solicitacao SolicitacaoCadastrado = solicitacoes.pesquisarPorCodigo(solicitacao.getCodigo());

			if (SolicitacaoCadastrado != null) {
				solicitacao = SolicitacaoCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Solicitacao já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getSolicitacao() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setSolicitacao(new Solicitacao());
	}

	public void salvar() {

		if (getSolicitacao() != null) {

			if (verificaOrigemDestinoIguais()) {
				return;
			}

			setSolicitacao(solicitacoes.salvar(getSolicitacao()));

			if (getSolicitacao().getId() != null) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Solicitacao cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Solicitacao. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public boolean verificaOrigemDestinoIguais() {
		if (solicitacao.getOrigem().equals(solicitacao.getDestino())) {
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'A Origem não pode ser igual ao Destino. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			return true;
		}
		return false;
	}

	private void listarSolicitante() {
		listaSolicitante = pessoas.todos();
	}

	private void listarAlmoxarifado() {
		listaAlmoxarifado = almoxarifados.todos();
	}

	private void listarStatusSolicitacao() {
		listaStatusSolicitacao = statusSolicitacoes.todos();
	}

	public Solicitacoes getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Solicitacoes solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public Pessoas getPessoas() {
		return pessoas;
	}

	public void setPessoas(Pessoas pessoas) {
		this.pessoas = pessoas;
	}

	public Almoxarifados getAlmoxarifados() {
		return almoxarifados;
	}

	public void setAlmoxarifados(Almoxarifados almoxarifados) {
		this.almoxarifados = almoxarifados;
	}

	public StatusSolicitacoes getStatusSolicitacoes() {
		return statusSolicitacoes;
	}

	public void setStatusSolicitacoes(StatusSolicitacoes statusSolicitacoes) {
		this.statusSolicitacoes = statusSolicitacoes;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Solicitacao> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Solicitacao> model) {
		this.model = model;
	}

	public List<Pessoa> getListaSolicitante() {
		return listaSolicitante;
	}

	public List<Almoxarifado> getListaAlmoxarifado() {
		return listaAlmoxarifado;
	}

	public List<StatusSolicitacao> getListaStatusSolicitacao() {
		return listaStatusSolicitacao;
	}

}