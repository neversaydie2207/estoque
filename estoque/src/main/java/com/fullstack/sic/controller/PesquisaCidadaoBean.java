package com.fullstack.sic.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.model.enums.TipoPesquisa;
import com.fullstack.sic.model.filtros.FiltroServidor;
import com.fullstack.sic.service.CadastroCidadaoService;

@Named
@ViewScoped
public class PesquisaCidadaoBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroCidadaoService serviceServidor;
	
	private TipoPesquisa tipoPesquisa;
	private List<TipoPesquisa> listTiposPesquisa;
	
	private boolean habilitaPesquisaPorNome = false;
	private boolean habilitaPesquisaPorCpf = true;

	private FiltroServidor filtro = new FiltroServidor();
	private LazyDataModel<Cidadao> model;
	
	private Cidadao servidor;
	
	// METODOS INICIAIS
	public void init()
	{
		initLazyDataModel();
		listarTiposPesquisa();
		tipoPesquisa = TipoPesquisa.POR_CPF;
	}
	
	public void initLazyDataModel()
	{
		model = new LazyDataModel<Cidadao>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Cidadao> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				getFiltro().setPrimeiroRegistro(first);
				getFiltro().setQuantidadeRegistros(pageSize);
				getFiltro().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				getFiltro().setPropriedadeOrdenacao(sortField);
				
				setRowCount(serviceServidor.quantidadeFiltrados(getFiltro()));
				
				return serviceServidor.filtrados(getFiltro());
			}
			
		};
	}
	
	/* METODOS DE PESQUISA */
	public void verificaTipoPesquisa()
	{
		if(tipoPesquisa.equals(TipoPesquisa.POR_NOME))
		{
			habilitaPesquisaPorNome = true;
			habilitaPesquisaPorCpf = false;
			filtro.setCpf(null);
			filtro.setAtualizado(false);
		}
		else if (tipoPesquisa.equals(TipoPesquisa.POR_CPF))
		{
			habilitaPesquisaPorNome = false;
			habilitaPesquisaPorCpf = true;
			filtro.setNome(null);
			filtro.setAtualizado(false);
		}
		else if(tipoPesquisa.equals(TipoPesquisa.ATUALIZADOS))
		{
			filtro.setAtualizado(true);
			filtro.setNome(null);
			filtro.setCpf(null);
			habilitaPesquisaPorNome = false;
			habilitaPesquisaPorCpf = false;
		}
		
	}
		
	public void pesquisarPorCpf()
	{
		if (servidor != null)
		{
			Cidadao servidorCadastrado = serviceServidor.pesquisarPorCpf(servidor);
			if (servidorCadastrado != null)
			{
				servidor = servidorCadastrado;
			}
		}
	}
	
	/* METODOS DE LISTAGEM */
	public void listarTiposPesquisa()
	{
		listTiposPesquisa = Arrays.asList(TipoPesquisa.values());
	}
	

	// METODOS SET/GET
	public FiltroServidor getFiltro()
	{
		return filtro;
	}

	public void setFiltro(FiltroServidor filtro)
	{
		this.filtro = filtro;
	}

	public LazyDataModel<Cidadao> getModel()
	{
		return model;
	}

	public void setModel(LazyDataModel<Cidadao> model)
	{
		this.model = model;
	}

	public TipoPesquisa getTipoPesquisa()
	{
		return tipoPesquisa;
	}

	public void setTipoPesquisa(TipoPesquisa tipoPesquisa)
	{
		this.tipoPesquisa = tipoPesquisa;
	}

	public boolean isHabilitaPesquisaPorNome()
	{
		return habilitaPesquisaPorNome;
	}

	public void setHabilitaPesquisaPorNome(boolean habilitaPesquisaPorNome)
	{
		this.habilitaPesquisaPorNome = habilitaPesquisaPorNome;
	}

	public boolean isHabilitaPesquisaPorCpf()
	{
		return habilitaPesquisaPorCpf;
	}

	public void setHabilitaPesquisaPorCpf(boolean habilitaPesquisaPorCpf)
	{
		this.habilitaPesquisaPorCpf = habilitaPesquisaPorCpf;
	}

	public List<TipoPesquisa> getListTiposPesquisa()
	{
		return listTiposPesquisa;
	}

	public void setListTiposPesquisa(List<TipoPesquisa> listTiposPesquisa)
	{
		this.listTiposPesquisa = listTiposPesquisa;
	}

	public Cidadao getServidor()
	{
		return servidor;
	}

	public void setServidor(Cidadao servidor)
	{
		this.servidor = servidor;
	}

}