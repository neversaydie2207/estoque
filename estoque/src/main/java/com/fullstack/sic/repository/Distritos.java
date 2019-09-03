package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fullstack.sic.model.Cidade;
import com.fullstack.sic.model.Distrito;
import com.fullstack.sic.util.jpa.Transactional;

public class Distritos implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Distrito porId(Long id)
	{
		return manager.find(Distrito.class, id);
	}
	
	public List<Distrito> buscarPorCidade(Cidade cidade)
	{
		return manager.createQuery("from Distrito d where d.cidade = :cidade order by d.nome", Distrito.class)
				.setParameter("cidade", cidade)
				.getResultList();
	}
	
	@Transactional
	public Distrito salvar(Distrito distrito)
	{
		return manager.merge(distrito);
	}
}
