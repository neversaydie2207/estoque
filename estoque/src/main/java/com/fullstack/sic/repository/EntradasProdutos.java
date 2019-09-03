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

import com.fullstack.sic.model.EntradaAlmoxarifado;
import com.fullstack.sic.model.EntradaProduto;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.service.NegocioException;
import com.fullstack.sic.util.jpa.Transactional;

public class EntradasProdutos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public EntradaProduto porId(Long id) {
		return manager.find(EntradaProduto.class, id);
	}

	public List<EntradaProduto> todos() {
		try {
			return manager.createQuery("from EntradaProduto ep order by ep.id ASC", EntradaProduto.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public EntradaProduto pesquisarPorCodigo(String codigo) {
		if (codigo != null) {
			EntradaProduto entradaProdutoRegistrado = porCodigo(codigo);

			if (entradaProdutoRegistrado != null) {
				return entradaProdutoRegistrado;
			} else {
				return null;
			}

		} else {
			throw new NegocioException("Por favor, informe o CÓDIGO VÁLIDO para esta pesquisa!");
		}
	}

	public EntradaProduto porCodigo(String codigo) {
		try {
			return manager.createQuery("from EntradaProduto ep where ep.codigo = :codigo", EntradaProduto.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public List<EntradaProduto> porEntrada(EntradaAlmoxarifado entrada) {
		try {
			return manager.createQuery("from EntradaProduto ep where ep.entrada = :entrada", EntradaProduto.class)
					.setParameter("entrada", entrada).getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Transactional
	public EntradaProduto salvar(EntradaProduto almoxarifado) {
		return manager.merge(almoxarifado);
	}

	@Transactional
	public boolean excluir(EntradaProduto entradaProduto) {
		try {
			entradaProduto = porId(entradaProduto.getId());

			manager.remove(entradaProduto);
			manager.flush();

			return true;

		} catch (PersistenceException e) {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<EntradaProduto> filtrados(Filtro filtro) {
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
		Criteria criteria = session.createCriteria(EntradaProduto.class);

		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria;
	}

}