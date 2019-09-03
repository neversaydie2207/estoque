package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.fullstack.sic.model.Ano;

public class Anos implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Ano porId(Long id)
	{
		return manager.find(Ano.class, id);
	}
	
	public List<Ano> todos()
	{
		try
		{
			return manager.createQuery("from Ano a order by a.nome ASC", Ano.class)
				.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public List<Ano> vigente()
	{
		try
		{
			return manager.createQuery("from Ano a where a.anoVigente = true order by a.nome ASC", Ano.class)
				.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
}