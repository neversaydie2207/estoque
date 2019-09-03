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
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.repository.OrgaoOrcamentarios;

@Named
@ViewScoped
public class CadastroOrgaoOrcamentarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrgaoOrcamentarios orgaoOrcamentarios;

	private OrgaoOrcamentario orgaoOrcamentario;
	private Filtro filtro = new Filtro();
	private LazyDataModel<OrgaoOrcamentario> model;

	public void init() {
		orgaoOrcamentario = new OrgaoOrcamentario();
		initLazyDataModel();
	}

	public void initLazyDataModel() {

		model = new LazyDataModel<OrgaoOrcamentario>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrgaoOrcamentario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(orgaoOrcamentarios.quantidadeFiltrados(filtro));

				return orgaoOrcamentarios.filtrados(filtro);
			}

		};

	}

	public void pesquisarPorCodigo() {

		if (orgaoOrcamentario != null) {
			OrgaoOrcamentario OrgaoOrcamentarioCadastrado = orgaoOrcamentarios
					.pesquisarPorCodigo(orgaoOrcamentario.getCodigo());

			if (OrgaoOrcamentarioCadastrado != null) {
				orgaoOrcamentario = OrgaoOrcamentarioCadastrado;

				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Órgão Orçamentário já associado à este código. Informações disponíveis para edição!', 'warning')");
			}

		}

	}

	public void editar() {
		if (getOrgaoOrcamentario() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setOrgaoOrcamentario(new OrgaoOrcamentario());
	}

	public void salvar() {
		if (getOrgaoOrcamentario() != null) {
			setOrgaoOrcamentario(orgaoOrcamentarios.salvar(getOrgaoOrcamentario()));

			if (getOrgaoOrcamentario().getId() != null) {
				PrimeFaces.current().executeScript(
						"amges.showNotification('top', 'right', " + "'Órgão Orçamentario cadastrado com sucesso!', 'success')");
				novo();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao cadastar o Órgão Orçamentario. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public OrgaoOrcamentarios getOrgaoOrcamentarios() {
		return orgaoOrcamentarios;
	}

	public void setOrgaoOrcamentarios(OrgaoOrcamentarios orgaoOrcamentarios) {
		this.orgaoOrcamentarios = orgaoOrcamentarios;
	}

	public OrgaoOrcamentario getOrgaoOrcamentario() {
		return orgaoOrcamentario;
	}

	public void setOrgaoOrcamentario(OrgaoOrcamentario orgaoOrcamentario) {
		this.orgaoOrcamentario = orgaoOrcamentario;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public LazyDataModel<OrgaoOrcamentario> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<OrgaoOrcamentario> model) {
		this.model = model;
	}

}