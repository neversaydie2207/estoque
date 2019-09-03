package com.fullstack.sic.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.fullstack.sic.model.Grupo;

@Named
@SessionScoped
public class ControlaPerfilBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Grupo grupoSelecionado;

	/* METODOS DIVERSOS */

	public String escolherSessaoOnPerfil()
	{
		if (grupoSelecionado != null)
		{
			if (grupoSelecionado.getNome().equals("ADMINISTRADOR"))
			{
				return "home";
			} 
			else if (grupoSelecionado.getNome().equals("CLIENTE"))
			{
				return "homeuser";
			} 
			else if (grupoSelecionado.getNome().equals("COMERCIAL"))
			{
				/*
				 * if(seguranca.getUsuarioLogado().getUsuario().getEmpresas().
				 * size() <= 1) { empresaSelecionada =
				 * seguranca.getUsuarioLogado().getUsuario().getEmpresas().get(0
				 * ); return "homecomercial"; } else { return "selecao_empresa";
				 * }
				 */

				return "selecao_empresa";

			} else
			{
				return null;
			}
		} else
		{
			return "selecao_perfil";
		}

	}

	public Grupo getGrupoSelecionado()
	{
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado)
	{
		this.grupoSelecionado = grupoSelecionado;
	}

}
