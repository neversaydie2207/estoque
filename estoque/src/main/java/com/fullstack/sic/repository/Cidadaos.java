package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.omnifaces.util.Messages;

import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.model.filtros.FiltroServidor;
import com.fullstack.sic.util.jpa.Transactional;

public class Cidadaos implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cidadao porId(Long id)
	{
		return manager.find(Cidadao.class, id);
	}
	
	public List<Cidadao> todos()
	{
		try
		{
			return manager.createQuery("from Cidadao s order by s.nome ASC", Cidadao.class)
				.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public Cidadao porCpf(Cidadao servidor)
	{
		try
		{
			return manager.createQuery("from Cidadao where cpf = :cpf", Cidadao.class)
					.setParameter("cpf", servidor.getCpf())
					.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
		
	}
	
	
	@Transactional
	public Cidadao salvar(Cidadao servidor)
	{
		return manager.merge(servidor);
	}
	
	@Transactional
	public boolean excluir(Cidadao servidorSelecionado)
	{
		try
		{
			servidorSelecionado = porId(servidorSelecionado.getId());
			
			manager.remove(servidorSelecionado);
			manager.flush();
			
			return true;
			
		}
		catch(PersistenceException e)
		{
			Messages.addError(null,"Cidadao não pode ser excluído! "
					+ "Tente novamente ou contate o administrador do sistema.");
			return false;
		}
		
	}

	
	/* LAZY COM CRITERIA */
	@SuppressWarnings("unchecked")
	public List<Cidadao> filtrados(FiltroServidor filtro) 
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) 
		{
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} 
		else if (filtro.getPropriedadeOrdenacao() != null) 
		{
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(FiltroServidor filtro) 
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroServidor filtro) 
	{
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cidadao.class);
		
	
		if(filtro.isAtualizado())
		{
			criteria.add(Restrictions.eq("isAtualizado", filtro.isAtualizado()));
		}
		
		if (StringUtils.isNotEmpty(filtro.getNome())) 
		{
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotEmpty(filtro.getCpf())) 
		{
			criteria.add(Restrictions.ilike("cpf", filtro.getCpf(), MatchMode.ANYWHERE));
		}
		
		return criteria;
	}

}