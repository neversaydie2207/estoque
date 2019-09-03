package com.fullstack.sic.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.fullstack.sic.model.TokenSenha;
import com.fullstack.sic.util.jpa.Transactional;

public class TokenSenhas implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	 
	public TokenSenha porId(Long id)
	{
		return manager.find(TokenSenha.class, id);
	}
	
	public TokenSenha porTokenHabilitado(String token)
	{
		try
		{
			return manager.createQuery("from TokenSenha t where t.token = :token and t.isHabilitado = true", TokenSenha.class)
					.setParameter("token", token).getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public TokenSenha porEmail(String email)
	{
		try
		{
			return manager.createQuery("from TokenSenha t where t.email = :email and t.isHabilitado = true", TokenSenha.class)
					.setParameter("email", email)
					.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	@Transactional
	public TokenSenha salvar(TokenSenha token)
	{
		return manager.merge(token);
	}

}
