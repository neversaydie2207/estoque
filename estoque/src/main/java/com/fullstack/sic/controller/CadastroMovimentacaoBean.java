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

import com.fullstack.sic.model.Movimentacao;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Movimentacoes;

@Named
@ViewScoped
public class CadastroMovimentacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Movimentacoes movimentacoes;

	private Movimentacao movimentacao;
	private Filtro filtro = new Filtro();
	private LazyDataModel<Movimentacao> model;

	public void init() {
		movimentacao = new Movimentacao();
		initLazyDataModel();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<Movimentacao>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Movimentacao> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(movimentacoes.quantidadeFiltrados(filtro));

				return movimentacoes.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (movimentacao != null) {
			Movimentacao MovimentacaoCadastrado = movimentacoes.pesquisarPorCodigo(movimentacao.getCodigo());

			if (MovimentacaoCadastrado != null) {
				movimentacao = MovimentacaoCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Movimentacao já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getMovimentacao() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setMovimentacao(new Movimentacao());
	}

	public void salvar() {
		if (getMovimentacao() != null) {
			setMovimentacao(movimentacoes.salvar(getMovimentacao()));

			if (getMovimentacao().getId() != null) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Movimentaçãoo cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Movimentaçãoo. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public Movimentacoes getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(Movimentacoes movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Movimentacao> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Movimentacao> model) {
		this.model = model;
	}

}