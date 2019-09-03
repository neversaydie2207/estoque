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
import com.fullstack.sic.model.EntradaProduto;
import com.fullstack.sic.model.Estoque;
import com.fullstack.sic.model.Movimentacao;
import com.fullstack.sic.model.Pessoa;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Almoxarifados;
import com.fullstack.sic.repository.Entradas;
import com.fullstack.sic.repository.EntradasProdutos;
import com.fullstack.sic.repository.Estoques;
import com.fullstack.sic.repository.Movimentacoes;
import com.fullstack.sic.repository.Pessoas;

@Named
@ViewScoped
public class CadastroEntradaAlmoxarifadoBean implements Serializable {

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
		
		System.out.println(entrada.getArmazenado());
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
		
		if (verificaArmazenado()) {
			return;
		}
		
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

	public void armazenaEntrada() {

		if (getEntrada() != null) {

			if (verificaArmazenado()) {
				return;
			}
			
			if (listarEntradaProdutos()) {
				entrada.setArmazenado(true);
				setEntrada(entradas.salvar(getEntrada()));
			}

			if (getEntrada().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Entrada armazenada com sucesso!', 'success')");
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao armazenar Entrada. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}
	
	public Boolean verificaArmazenado() {
		if (entrada.getArmazenado() == true) {
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Entrada armazenada anteriormente. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			return true;
		}
		
		return false;
		
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

	// teste para armazenar os produtos da entrada em estoque
	@Inject
	private EntradasProdutos entradasProdutos;

	@Inject
	private Estoques estoques;

	private List<EntradaProduto> listaEntradaProdutos;

	public Boolean listarEntradaProdutos() {

		listaEntradaProdutos = entradasProdutos.porEntrada(entrada);

		try {

			for (EntradaProduto e : listaEntradaProdutos) {
				
				Estoque estoque = new Estoque();
				estoque.setAlmoxarifado(entrada.getAlmoxarifado());
				estoque.setProduto(e.getProduto());
				estoque.setQuantidade(e.getQuantidade());

				estoques.salvar(estoque);
			}
			
			return true;

		} catch (Exception e) {
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Ocorreu um erro ao armazenar Entrada. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			return false;
		}

	}

	public List<EntradaProduto> getListaEntradaProdutos() {
		return listaEntradaProdutos;
	}

}