package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.fullstack.sic.model.Ano;
import com.fullstack.sic.model.Competencia;

public class Competencias implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Competencia porId(Long id)
	{
		return manager.find(Competencia.class, id);
	}
	
	public List<Competencia> todos()
	{
		try
		{
			return manager.createQuery("from Competencia c order by c.ano, c.mes ASC", Competencia.class)
				.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

	public List<Competencia> porAno(Ano anoSelecionado)
	{
		try
		{
			return manager.createQuery("from Competencia c where c.ano = :ano ", Competencia.class)
				.setParameter("ano", anoSelecionado)
				.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

}