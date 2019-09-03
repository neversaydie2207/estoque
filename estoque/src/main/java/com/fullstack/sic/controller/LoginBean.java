package com.fullstack.sic.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullstack.sic.security.Seguranca;
import com.fullstack.sic.util.jsf.FacesUtil;

@Named
@RequestScoped
public class LoginBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	@Inject
	private ControlaPerfilBean managerPerfil;

	@Inject
	private Seguranca seguranca;

	private String usuario;

	public void initPerfil()
	{
		if (managerPerfil.getGrupoSelecionado() == null)
		{
			managerPerfil.setGrupoSelecionado(seguranca.getUsuarioLogado().getUsuario().getGrupo());
		}
	}

	public void preRender()
	{
		if ("true".equals(request.getParameter("invalid")))
		{
			FacesUtil.addErrorMessage("Usuário ou senha inválido!");
		}
	}

	public void login() throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsf");

		dispatcher.forward(request, response);

		facesContext.responseComplete();
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

}
