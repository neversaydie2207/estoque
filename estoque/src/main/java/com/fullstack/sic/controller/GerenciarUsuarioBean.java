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

import com.fullstack.sic.model.Grupo;
import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.model.Sexo;
import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.model.filtros.FiltroUsuario;
import com.fullstack.sic.repository.Grupos;
import com.fullstack.sic.repository.Sexos;
import com.fullstack.sic.service.CadastroCidadaoService;
import com.fullstack.sic.service.CadastroUsuarioService;

@Named
@ViewScoped
public class GerenciarUsuarioBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroUsuarioService serviceUsuario;

	@Inject
	private CadastroCidadaoService serviceServidor;

	@Inject
	private Sexos sexos;

	@Inject
	Grupos perfis;

	private Usuario usuario;
	private LazyDataModel<Usuario> model;

	private List<Sexo> listSexo;
	private List<Grupo> listPerfis;

	private Cidadao servidor;

	private FiltroUsuario filtro = new FiltroUsuario();

	/* METODOS DE INICIALIZACAO */
	public void init()
	{
		novo();
		initLazyDataModel();
		listarSexo();
		listarPerfis();
	}

	public void initLazyDataModel()
	{
		setModel(new LazyDataModel<Usuario>()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters)
			{

				getFiltro().setPrimeiroRegistro(first);
				getFiltro().setQuantidadeRegistros(pageSize);
				getFiltro().setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				getFiltro().setPropriedadeOrdenacao(sortField);

				setRowCount(serviceUsuario.quantidadeFiltrados(getFiltro()));

				return serviceUsuario.filtrados(getFiltro());
			}

		});
	}

	/* METODOS DE PESQUISA */
	public void pesquisarPorCpf()
	{
		if (servidor != null && servidor.getCpf() != null)
		{
			if (!servidor.getCpf().isEmpty())
			{
				Cidadao servidorCadastrado = serviceServidor.pesquisarPorCpf(getServidor());
				if (servidorCadastrado != null)
				{
					setServidor(servidorCadastrado);
				} else
				{
					PrimeFaces.current().executeScript(
							"amges.showNotification('top', 'right', " + "'CPF Não Localizado!', 'warning')");

					servidor = new Cidadao();

				}
			}
		}
	}

	/* METODOS DE LISTAGEM */
	private void listarSexo()
	{
		listSexo = sexos.buscarSexos();
	}

	private void listarPerfis()
	{
		listPerfis = perfis.buscarGrupos();
	}

	/* METODOS DAO */

	public void desvincularServidor()
	{
		servidor = new Cidadao();
		usuario.setServidor(servidor);
	}

	public void editar()
	{
		if (getUsuario() != null)
		{
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs','#tabGerenciarUsuario', '#nome')");

			if (usuario.getServidor() != null)
			{
				servidor = usuario.getServidor();
			}
			pesquisarPorCpf();
		}

	}

	public void novo()
	{
		setUsuario(new Usuario());
		setServidor(new Cidadao());

		PrimeFaces.current().executeScript("amges.nextTab('#myTabs','#tabUsuariosCadastradas', '#pesquisaNome')");
	}

	public void salvar()
	{
		if (servidor.getId() != null)
		{
			usuario.setServidor(servidor);
			serviceUsuario.salvar(usuario);

			PrimeFaces.current().executeScript(
					"amges.showNotification('top', 'right', " + "'Usuário Atualizado com Sucesso!', 'success')");

			novo();
		} else
		{
			usuario.setServidor(null);
			serviceUsuario.salvar(usuario);

			PrimeFaces.current().executeScript(
					"amges.showNotification('top', 'right', " + "'Usuário Atualizado com Sucesso!', 'success')");

			novo();

		}
	}

	/* METODOS SET/GET */

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public LazyDataModel<Usuario> getModel()
	{
		return model;
	}

	public void setModel(LazyDataModel<Usuario> model)
	{
		this.model = model;
	}

	public FiltroUsuario getFiltro()
	{
		return filtro;
	}

	public void setFiltro(FiltroUsuario filtro)
	{
		this.filtro = filtro;
	}

	public List<Sexo> getListSexo()
	{
		return listSexo;
	}

	public void setListSexo(List<Sexo> listSexo)
	{
		this.listSexo = listSexo;
	}

	public List<Grupo> getListPerfis()
	{
		return listPerfis;
	}

	public void setListPerfis(List<Grupo> listPerfis)
	{
		this.listPerfis = listPerfis;
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
