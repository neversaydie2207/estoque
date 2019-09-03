package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fullstack.sic.model.Grupo;
import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.model.filtros.FiltroUsuario;
import com.fullstack.sic.util.jpa.Transactional;

public class Usuarios implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	 
	public Usuario porId(Long id)
	{
		return manager.find(Usuario.class, id);
	}

	public Grupo buscarGrupo(String nome)
	{
		try
		{
			return manager.createQuery("from Grupo g where g.nome = :nome", Grupo.class)
					.setParameter("nome", nome).getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}

	}
	
	public Usuario porEmail(String email)
	{
		try
		{
			return manager.createQuery("from Usuario u where u.email = :email", Usuario.class)
					.setParameter("email", email).getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public Usuario porCpf(String cpf)
	{
		try
		{
			return manager.createQuery("from Usuario u where u.cpf = :cpf", Usuario.class)
					.setParameter("cpf", cpf)
					.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

	public Usuario porCodigoConfirmacao(String codigo)
	{
		try
		{
			return manager.createQuery("from Usuario where cc = :codigo", Usuario.class)
					.setParameter("codigo", codigo)
					.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public Usuario buscarUsuarioOnUsername(String username) 
	{
		Usuario usuario = null;
		
		try
		{
			usuario = this.manager.createQuery("from Usuario u where u.ativo = true and u.email = :email", Usuario.class)
					.setParameter("email", username).getSingleResult();
		}
		catch(NoResultException e)
		{
			System.out.println("Nenhum Usuario encontrado: "+e.getMessage());
		}
		
		return usuario;
	}
	
	public Usuario porEmailAtivo(String email) 
	{
		Usuario usuario = null;
		
		try
		{
			usuario = this.manager.createQuery("from Usuario u where u.ativo = true and u.email = :email", Usuario.class)
					.setParameter("email", email).getSingleResult();
		}
		catch(NoResultException e)
		{
			System.out.println("Nenhum Usuario encontrado: "+e.getMessage());
		}
		
		return usuario;
	}
	
	
	
	public List<Usuario> porEmailOnAtivo(String email)
	{
		try
		{
			return manager.createQuery("from Usuario u where u.ativo = true and u.email = :email", Usuario.class)
					.setParameter("email", email)
					.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

	public List<Usuario> porNome(String nomeUsuario)
	{
		return manager.createQuery("from Usuario u "
				+ "where u.nome like :nome "
				+ "or u.usuario like :email "
				+ "and u.ativo = true ORDER BY u.nome", Usuario.class)
				.setParameter("nome", "%" + nomeUsuario.toUpperCase() + "%")
				.setParameter("email","%" + nomeUsuario.toLowerCase() + "%")
				.getResultList();
	}
		
	
	@Transactional
	public Usuario salvar(Usuario usuario)
	{
		return manager.merge(usuario);
	}
	
	/* LAZY COM CRITERIA */
	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(FiltroUsuario filtro) 
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
	
	public int quantidadeFiltrados(FiltroUsuario filtro) 
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroUsuario filtro) 
	{
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		if (StringUtils.isNotEmpty(filtro.getNome())) 
		{
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotEmpty(filtro.getEmail())) 
		{
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		}
		
		return criteria;
	}
	
}
