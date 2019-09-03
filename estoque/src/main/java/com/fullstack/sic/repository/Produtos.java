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

import com.fullstack.sic.model.Almoxarifado;
import com.fullstack.sic.model.Produto;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.service.NegocioException;
import com.fullstack.sic.util.jpa.Transactional;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

	public List<Produto> todos() {
		try {
			return manager.createQuery("from Produto p order by p.id ASC", Produto.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Produto salvar(Produto Produto) {
		return manager.merge(Produto);
	}

	public Produto pesquisarPorCodigo(String codigo) {
		if (codigo != null) {
			Produto ProdutoRegistrado = porCodigo(codigo);

			if (ProdutoRegistrado != null) {
				return ProdutoRegistrado;
			} else {
				return null;
			}

		} else {
			throw new NegocioException("Por favor, informe o CÓDIGO VÁLIDO para esta pesquisa!");
		}
	}

	public Produto porCodigo(String codigo) {
		try {
			return manager.createQuery("from Produto p where p.codigo = :codigo", Produto.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(Filtro filtro) {
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
		Criteria criteria = session.createCriteria(Produto.class);

		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria;
	}
	
	public List<Produto> produtosEmEstoque(Almoxarifado almoxarifado) {
		try {
			return manager.createQuery("select p from Produto p\r\n" + 
										"join p.entradaProduto ep\r\n" + 
										"join ep.entrada e\r\n" + 
										"join e.almoxarifado a\r\n" + 
										"where a.id = :id\r\n" +
										"and e.armazenado = true\r\n" + 
										"order by p.id ASC", Produto.class)
										.setParameter("id", almoxarifado.getId()).getResultList();
		} catch (Exception e) {
			System.out.println("Erro: " + e.toString());
			return null;
		}
	}

}