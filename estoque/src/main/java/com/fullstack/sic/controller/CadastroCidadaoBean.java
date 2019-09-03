package com.fullstack.sic.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.model.Cidade;
import com.fullstack.sic.model.Distrito;
import com.fullstack.sic.model.EmissorRcc;
import com.fullstack.sic.model.Estado;
import com.fullstack.sic.model.Sexo;
import com.fullstack.sic.model.enums.EstadoCivil;
import com.fullstack.sic.model.enums.Etnia;
import com.fullstack.sic.model.enums.OrgaoEmissorIdentidade;
import com.fullstack.sic.model.enums.PoderPublico;
import com.fullstack.sic.model.enums.TipoVeiculo;
import com.fullstack.sic.repository.Cidades;
import com.fullstack.sic.repository.Distritos;
import com.fullstack.sic.repository.EmissoresRcc;
import com.fullstack.sic.repository.Estados;
import com.fullstack.sic.repository.Sexos;
import com.fullstack.sic.security.Seguranca;
import com.fullstack.sic.service.CadastroCidadaoService;
import com.fullstack.sic.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCidadaoBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Seguranca seguranca;
	
	@Inject
	private Sexos serviceSexo;
	
	@Inject
	private Estados estados;
	
	@Inject
	private Cidades cidades;

	@Inject
	private Distritos distritos;
	
	@Inject
	private CadastroCidadaoService serviceCidadao;
	
	@Inject
	private EmissoresRcc emissoresRcc;
	
	private Cidadao cidadao;
	
	private List<Etnia> listEtnia;
	private List<Sexo> listSexo;
	
	private List<Estado> listEstados;
	private List<Cidade> listCidadesNascimento;
	
	private List<OrgaoEmissorIdentidade> listOrgaoEmissor;
	private List<EstadoCivil> listEstadoCivil;
	
	private List<Cidade> listCidades;
	
	private List<Distrito> listDistritos;
	
	private List<PoderPublico> listPoderes;
	
	private List<TipoVeiculo> listTiposVeiculos;
	
	private List<EmissorRcc> listOrgaoEmissorRcc;
	
	/* add nivel de escolaridade (ensino fundamental, ensino medio.... */
	
	private transient Part arquivoFoto;
	
	/* METODOS INICIAIS */
	public void init()
	{
		cidadao = new Cidadao();
		
		listarEtnias();
		listarSexo();
		listarEstados();
		listarOrgaoEmissor();
		listarEstadoCivil();
		listarPoderes();
		listarTiposVeiculo();
		listarOrgaoEmissorRcc();
		
	}
	
	/* METODOS DIVERSOS */
	
	public void resetCamposTransporte()
	{
		if(cidadao != null)
		{
			cidadao.getTransporteEscolar().setResponsabilidadeTransporteEscolar(null);
			cidadao.getTransporteEscolar().setTipoVeiculoTrasporteEscolar(null);
		}
	}
	
	
	/* METODOS TRATAMENTO IMAGEM */
	public void uploadFoto()
	{
		if(getArquivoFoto() != null)
		{
			try
			{
				byte[] fotoCidadao = IOUtils.toByteArray(getArquivoFoto().getInputStream());
				cidadao.setFoto(fotoCidadao);
				FacesUtil.addInfoMessage("Foto do Cidadao " 
						+ getArquivoFoto().getName() + " adicionada!");
			}
			catch(Exception e)
			{
				FacesUtil.addErrorMessage("Erro ao enviar Foto: "+ e);
			}
		}
	}

	
	/* METODOS DE PESQUSIA */
	public List<Cidade> completaCidadeNascimento(String cidade)
	{
		List<Cidade> results = new ArrayList<Cidade>();
		
		if(listCidadesNascimento != null)
		{
			for(Cidade c : listCidadesNascimento)
			{
				if(c.getNome().toLowerCase().contains(cidade.toLowerCase()))
				{
					results.add(c);
				}
			}	
		}
		else
		{
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Campo UF NASCIMENTO precisa estar selecionado!', 'warning')");
		}
		
		return results;
	}
	
	public List<Cidade> completaCidade(String cidade)
	{
		List<Cidade> results = new ArrayList<Cidade>();
		
		if(listCidades != null)
		{
			for(Cidade c : listCidades)
			{
				if(c.getNome().toLowerCase().contains(cidade.toLowerCase()))
				{
					results.add(c);
				}
			}	
		}
		else
		{
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Campo UF precisa estar selecionado!', 'warning')");
		}
		
		
		return results;
	}

	
	
	/* METODOS DE LISTAGEM */
	
	public void listarEtnias()
	{
		listEtnia = Arrays.asList(Etnia.values());
	}
	
	public void listarSexo()
	{
		listSexo = serviceSexo.buscarSexos();
	}
	
	public void listarEstados()
	{
		listEstados = estados.buscarEstados();
	}
	
	public void listarPoderes()
	{
		listPoderes = Arrays.asList(PoderPublico.values());
	}
	
	public void listarTiposVeiculo()
	{
		listTiposVeiculos = Arrays.asList(TipoVeiculo.values());
	}
	
	public void listarCidadesNascimento()
	{
		if(cidadao != null && cidadao.getUfNascimento() != null)
		{
			listCidadesNascimento = cidades.buscarPorEstado(cidadao.getUfNascimento());
		}
		else
		{
			FacesUtil.addErrorMessage("Campo UF DE NASCIMENTO precisa está selecionado");
		}
	}
	
	public void listarCidadesEnderecoPorEstado()
	{
		if(cidadao != null && cidadao.getEndereco().getEstado() != null)
		{
			listCidades = cidades.buscarPorEstado(cidadao.getEndereco().getEstado());
		}
	}
	
	public void listarDistritosPorCidade()
	{
		if (cidadao != null && cidadao.getEndereco().getCidade() != null)
		{
			listDistritos = distritos.buscarPorCidade(cidadao.getEndereco().getCidade());
		}
		else
		{
			FacesUtil.addErrorMessage("Campo Cidade tem que está selecionado");
		}
		
	}
	
	public void listarOrgaoEmissor()
	{
		listOrgaoEmissor = Arrays.asList(OrgaoEmissorIdentidade.values());
	}
	
	public void listarEstadoCivil()
	{
		listEstadoCivil = Arrays.asList(EstadoCivil.values());
	}
	
	
	public void listarOrgaoEmissorRcc()
	{
		listOrgaoEmissorRcc = emissoresRcc.todos();
	}
	
	/* METODOS DE PESQUISA */
	public void pesquisarPorCpf()
	{
		if(cidadao != null)
		{
			Cidadao cidadaoCadastrado = serviceCidadao.pesquisarPorCpf(cidadao);
			if(cidadaoCadastrado != null)
			{
				Messages.addWarn(null,"CPF já cadastrado. Informações disponíveis para edição.");
				cidadao = cidadaoCadastrado;
				listarCidadesNascimento();
				listarCidadesEnderecoPorEstado();
				listarDistritosPorCidade();
				reloadJs();
				
			}
		}
	}
	
	/* METODOS DAO */
	
	public void salvar()
	{
		if(cidadao != null)
		{
			cidadao.setResponsavelPorAtualizar(seguranca.getUsuarioLogado().getUsuario());
			cidadao.setDataAtualizacaoCadastral(new Date());
			cidadao.setAtualizado(true);
			
			serviceCidadao.salvar(cidadao);
			novo();
			
		}
	
		
	}
	
	public void novo()
	{
		cidadao = new Cidadao();
		reloadJs();
	}
	
	public void reloadJs()
	{
		Ajax.load("javascripts", "amges.js");
		Ajax.load("layout", "js/material-dashboard.js");
		Ajax.load("javascripts", "vendors/bootstrap-filestyle.min.js");
	}
	
	
	/* METODOS SET/GET */
	public Part getArquivoFoto()
	{
		return arquivoFoto;
	}

	public void setArquivoFoto(Part arquivoFoto)
	{
		this.arquivoFoto = arquivoFoto;
	}

	public Cidadao getCidadao()
	{
		return cidadao;
	}

	public void setCidadao(Cidadao cidadao)
	{
		this.cidadao = cidadao;
	}

	public List<Etnia> getListEtnia()
	{
		return listEtnia;
	}

	public void setListEtnia(List<Etnia> listEtnia)
	{
		this.listEtnia = listEtnia;
	}

	public List<Sexo> getListSexo()
	{
		return listSexo;
	}

	public void setListSexo(List<Sexo> listSexo)
	{
		this.listSexo = listSexo;
	}

	public List<Estado> getListEstados()
	{
		return listEstados;
	}

	public void setListEstados(List<Estado> listEstados)
	{
		this.listEstados = listEstados;
	}

	public List<Cidade> getListCidadesNascimento()
	{
		return listCidadesNascimento;
	}

	public void setListCidadesNascimento(List<Cidade> listCidadesNascimento)
	{
		this.listCidadesNascimento = listCidadesNascimento;
	}

	public List<OrgaoEmissorIdentidade> getListOrgaoEmissor()
	{
		return listOrgaoEmissor;
	}

	public void setListOrgaoEmissor(List<OrgaoEmissorIdentidade> listOrgaoEmissor)
	{
		this.listOrgaoEmissor = listOrgaoEmissor;
	}

	public List<EstadoCivil> getListEstadoCivil()
	{
		return listEstadoCivil;
	}

	public void setListEstadoCivil(List<EstadoCivil> listEstadoCivil)
	{
		this.listEstadoCivil = listEstadoCivil;
	}

	public List<Cidade> getListCidades()
	{
		return listCidades;
	}

	public void setListCidades(List<Cidade> listCidades)
	{
		this.listCidades = listCidades;
	}

	public List<Distrito> getListDistritos()
	{
		return listDistritos;
	}

	public void setListDistritos(List<Distrito> listDistritos)
	{
		this.listDistritos = listDistritos;
	}

	public List<PoderPublico> getListPoderes()
	{
		return listPoderes;
	}

	public void setListPoderes(List<PoderPublico> listPoderes)
	{
		this.listPoderes = listPoderes;
	}

	public List<TipoVeiculo> getListTiposVeiculos()
	{
		return listTiposVeiculos;
	}

	public void setListTiposVeiculos(List<TipoVeiculo> listTiposVeiculos)
	{
		this.listTiposVeiculos = listTiposVeiculos;
	}
	
	public List<EmissorRcc> getListOrgaoEmissorRcc()
	{
		return listOrgaoEmissorRcc;
	}

	public void setListOrgaoEmissorRcc(List<EmissorRcc> listOrgaoEmissorRcc)
	{
		this.listOrgaoEmissorRcc = listOrgaoEmissorRcc;
	}
	
}
