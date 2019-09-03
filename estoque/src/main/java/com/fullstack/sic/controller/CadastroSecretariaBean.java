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

import com.fullstack.sic.model.Secretaria;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.Secretarias;

@Named
@ViewScoped
public class CadastroSecretariaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Secretarias secretarias;

	private Secretaria secretaria;
	private Filtro filtro = new Filtro();
	private LazyDataModel<Secretaria> model;

	public void init() {
		secretaria = new Secretaria();
		initLazyDataModel();
	}

	public void initLazyDataModel() {
		
		model = new LazyDataModel<Secretaria>()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public List<Secretaria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters)
			{

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(secretarias.quantidadeFiltrados(filtro));

				return secretarias.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (secretaria != null) {
			Secretaria SecretariaCadastrado = secretarias
					.pesquisarPorCodigo(secretaria.getCodigo());

			if (SecretariaCadastrado != null) {
				secretaria = SecretariaCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Secretária já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getSecretaria() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setSecretaria(new Secretaria());
	}

	public void salvar() {
		if (getSecretaria() != null) {
			setSecretaria(secretarias.salvar(getSecretaria()));

			if (getSecretaria().getId() != null) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Secretária cadastrada com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar a Secretária. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public Secretarias getSecretarias() {
		return secretarias;
	}

	public void setSecretarias(Secretarias secretarias) {
		this.secretarias = secretarias;
	}

	public Secretaria getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<Secretaria> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Secretaria> model) {
		this.model = model;
	}

}
