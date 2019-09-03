package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.fullstack.sic.model.Sexo;

public class Sexos implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Sexo porId(Long id)
	{
		return manager.find(Sexo.class, id);
	}
	
	public List<Sexo> buscarSexos()
	{
		try
		{
			return manager.createQuery("from Sexo s order by s.nome DESC", Sexo.class)
				.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

}
