package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.fullstack.sic.model.Almoxarifado;
import com.fullstack.sic.model.Solicitacao;
import com.fullstack.sic.model.SolicitacaoProduto;
import com.fullstack.sic.model.filtros.Filtro;
import com.fullstack.sic.model.nativequery.ProdutoEstoque;
import com.fullstack.sic.service.NegocioException;
import com.fullstack.sic.util.jpa.Transactional;

public class SolicitacoesProdutos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public SolicitacaoProduto porId(Long id) {
		return manager.find(SolicitacaoProduto.class, id);
	}

	public List<SolicitacaoProduto> todos() {
		try {
			return manager.createQuery("from SolicitacaoProduto s order by s.id ASC", SolicitacaoProduto.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<SolicitacaoProduto> porSolicitacao(Solicitacao solicitacao) {
		try {
			return manager.createQuery("from SolicitacaoProduto s where s.solicitacao = :solicitacao order by s.id ASC", SolicitacaoProduto.class)
					.setParameter("solicitacao", solicitacao)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public SolicitacaoProduto salvar(SolicitacaoProduto solicitacoProduto) {
		return manager.merge(solicitacoProduto);
	}
	
	@Transactional
	public boolean excluir(SolicitacaoProduto solicitacoProduto) {
		try {
			solicitacoProduto = porId(solicitacoProduto.getId());

			manager.remove(solicitacoProduto);
			manager.flush();

			return true;

		} catch (PersistenceException e) {
			return false;
		}

	}

	public SolicitacaoProduto pesquisarPorCodigo(String codigo) {
		if (codigo != null) {
			SolicitacaoProduto solicitacaoProdutoRegistrado = porCodigo(codigo);

			if (solicitacaoProdutoRegistrado != null) {
				return solicitacaoProdutoRegistrado;
			} else {
				return null;
			}

		} else {
			throw new NegocioException("Por favor, informe o CÓDIGO VÁLIDO para esta pesquisa!");
		}
	}

	public SolicitacaoProduto porCodigo(String codigo) {
		try {
			return manager.createQuery("from SolicitacaoProduto s where s.codigo = :codigo", SolicitacaoProduto.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoProduto> filtrados(Filtro filtro) {
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
		Criteria criteria = session.createCriteria(SolicitacaoProduto.class);

		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}

		return criteria;
	}
	
	public List<ProdutoEstoque> QuantidadeProdutoDisponivel(Almoxarifado almoxarifado) {

		List<Object[]> listaObjeto;

		List<ProdutoEstoque> listaProdutoEstoque = new ArrayList<ProdutoEstoque>();

		try {
			Query q = manager.createNativeQuery("select p.id id,\r\n" + 
												" p.nome produto,\r\n" +
												" sum(e.quantidade) total\r\n" + 
												" from tb_estoque e\r\n" + 
												" inner join tb_produto p on p.id = e.produto_id\r\n" + 
												" where 1=1\r\n" + 
												" and e.almoxarifado_id = :id\r\n" + 
												" group by p.id,\r\n" + 
												" p.nome\r\n" + 
												" order by p.nome").setParameter("id", almoxarifado.getId());

			listaObjeto = q.getResultList();

			for (Object[] a : listaObjeto) {

				Long id = Long.parseLong(a[0].toString()) ;
				String produto = a[1].toString();
				Integer total = Integer.parseInt(a[2].toString());

				ProdutoEstoque produtoEstoque = new ProdutoEstoque(id, produto, total);

				try {
					listaProdutoEstoque.add(produtoEstoque);
				} catch (Exception e) {
					System.out.println("erro " + e);
				}
			}

			return listaProdutoEstoque;

		} catch (NoResultException e) {
			System.out.println("NoResultException");
			return null;
		}
	}

}