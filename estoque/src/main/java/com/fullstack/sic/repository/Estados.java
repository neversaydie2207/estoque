package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fullstack.sic.model.Estado;

public class Estados implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Estado porId(Long id)
	{
		return manager.find(Estado.class, id);
	}
	
	public List<Estado> buscarEstados()
	{
		return manager.createQuery("from Estado e order by e.nome", Estado.class)
				.getResultList();
	}

	public List<Estado> buscarPorCodigo(Long codigo)
	{
		return manager.createQuery("from Estado e where e.id = :codigo order by e.nome", Estado.class)
				.setParameter("codigo", codigo)
				.getResultList();
	}
	
}
