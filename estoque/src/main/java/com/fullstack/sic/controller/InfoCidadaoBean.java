package com.fullstack.sic.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.service.CadastroCidadaoService;

@Named
@ViewScoped
public class InfoCidadaoBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroCidadaoService cidadaoService;
	
	private Cidadao cidadao;
	private Cidadao cidadaoSelecionado;
	
	public void init()
	{
		if(cidadaoSelecionado != null)
		{
			cidadao = cidadaoService.pesquisarPorCpf(cidadaoSelecionado);
		}
	}

	public Cidadao getCidadao()
	{
		return cidadao;
	}

	public void setCidadao(Cidadao cidadao)
	{
		this.cidadao = cidadao;
	}

	public Cidadao getCidadaoSelecionado()
	{
		return cidadaoSelecionado;
	}

	public void setCidadaoSelecionado(Cidadao cidadaoSelecionado)
	{
		this.cidadaoSelecionado = cidadaoSelecionado;
	}

}
