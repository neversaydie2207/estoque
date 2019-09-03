package com.fullstack.sic.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	public String getCargoUsuario()
	{
		String cargo = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null)
		{
			//cargo = usuarioLogado.getUsuario().getNomeGrupo();
			cargo = usuarioLogado.getUsuario().getGrupo().getNome();
			
		}
		
		return cargo;
	}
	
	public String getNomeUsuario()
	{
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null)
		{
			nome = usuarioLogado.getUsuario().getNome(); //+ " " + usuarioLogado.getUsuario().getSobrenome();	
		}
		
		return nome;
	}
	
	public String getApelido()
	{
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null)
		{
			nome = usuarioLogado.getUsuario().getApelido(); //+ " " + usuarioLogado.getUsuario().getSobrenome();	
		}
		
		return nome;
	}
	
	public String getNomeSobrenome()
	{
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null)
		{
			
			String [] nomeSobrenome = usuarioLogado.getUsuario().getNome().split("\\s");
			String primeiroNome = "";
			String ultimoNome = "";
			
			for(int i = 0; i<= nomeSobrenome.length; i++)
			{
				if(i == 0)
				{
					primeiroNome += nomeSobrenome[i];
					
					primeiroNome = primeiroNome.substring(0,1).toUpperCase() + primeiroNome.substring(1).toLowerCase();
					
				}
				else if(i == (nomeSobrenome.length -1))
				{
					ultimoNome += nomeSobrenome[i];
					ultimoNome = ultimoNome.substring(0,1).toUpperCase() + ultimoNome.substring(1).toLowerCase();
				}
			}
			
			nome = primeiroNome + " " + ultimoNome;
		
		}
		
		return nome;
	}
	
	public String getNomeUsuarioCompelto()
	{
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null)
		{
			nome = usuarioLogado.getUsuario().getNome() ;	
		}
		
		return nome;
	}
	
	public Long getIdUsuario()
	{
		Long idusuario = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null)
		{
			idusuario = usuarioLogado.getUsuario().getId();	
		}
		
		return idusuario;
	}

	/* MUDAR POSTERIORMENTE PARA PRIVATE */
	public UsuarioSistema getUsuarioLogado() 
	{
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null)
		{
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuario;
	}
	

}
