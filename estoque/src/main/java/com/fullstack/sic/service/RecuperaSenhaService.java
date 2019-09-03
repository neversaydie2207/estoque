package com.fullstack.sic.service;


import java.io.Serializable;

import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.fullstack.sic.model.TokenSenha;
import com.fullstack.sic.repository.TokenSenhas;
import com.fullstack.sic.util.jpa.Transactional;

public class RecuperaSenhaService implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TokenSenhas tokens;
	
	public TokenSenha validarToken(String token)
	{
		return tokens.porTokenHabilitado(token);
	}
	
	public TokenSenha verificarTokenPorEmail(String email)
	{
		if(email != null && !email.isEmpty())
		{
			return tokens.porEmail(email);
		}
		else
		{
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Por favor, informe um e-mail para continuar!', 'warning')");
			return null;
		}
	}
	
	@Transactional
	public TokenSenha salvar(TokenSenha token)
	{
		TokenSenha tokenRegistrado = tokens.porTokenHabilitado(token.getToken());
		
		if(tokenRegistrado != null && !tokenRegistrado.equals(token))
		{
			throw new NegocioException("Desculpe-nos, mas este token não está mais habilitado!");
		}
		else
		{
			return tokens.salvar(token);
		}
		
	}
	
}