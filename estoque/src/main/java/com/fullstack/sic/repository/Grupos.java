package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fullstack.sic.model.Grupo;

public class Grupos implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Grupo porId(Integer id)
	{
		return manager.find(Grupo.class, id);
	}
	
	public List<Grupo> buscarGrupos()
	{
		return manager.createQuery("from Grupo g order by g.nome", Grupo.class)
				.getResultList();
	}
		
}
