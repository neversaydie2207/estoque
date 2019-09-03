package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fullstack.sic.model.Cidade;
import com.fullstack.sic.model.Estado;

public class Cidades implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cidade porId(Long id)
	{
		return manager.find(Cidade.class, id);
	}
	
	public List<Cidade> buscarPorEstado(Estado estado)
	{
		return manager.createQuery("from Cidade c where c.estado = :estado order by c.nome", Cidade.class)
				.setParameter("estado", estado)
				.getResultList();
	}

	public List<Cidade> buscarPorCodigo(Long codigo)
	{
		return manager.createQuery("from Cidade c where c.id = :codigo order by c.nome", Cidade.class)
				.setParameter("codigo", codigo)
				.getResultList();
		
	}
}
