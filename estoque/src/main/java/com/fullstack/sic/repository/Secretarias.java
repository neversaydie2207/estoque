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

import com.fullstack.sic.model.Secretaria;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.service.NegocioException;
import com.fullstack.sic.util.jpa.Transactional;

public class Secretarias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Secretaria porId(Long id) {
		return manager.find(Secretaria.class, id);
	}

	public List<Secretaria> todos() {
		try {
			return manager.createQuery("from Secretaria s order by s.id ASC", Secretaria.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Secretaria salvar(Secretaria secretaria) {
		return manager.merge(secretaria);
	}

	public Secretaria pesquisarPorCodigo(String codigo) {
		if (codigo != null) {
			Secretaria SecretariaRegistrado = porCodigo(codigo);

			if (SecretariaRegistrado != null) {
				return SecretariaRegistrado;
			} else {
				return null;
			}

		} else {
			throw new NegocioException("Por favor, informe o CÓDIGO VÁLIDO para esta pesquisa!");
		}
	}

	public Secretaria porCodigo(String codigo) {
		try {
			return manager.createQuery("from Secretaria s where s.codigo = :codigo", Secretaria.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Secretaria> filtrados(Filtro filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	public int quantidadeFiltrados(Filtro filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	private Criteria criarCriteriaParaFiltro(Filtro filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Secretaria.class);

		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria;
	}

}
