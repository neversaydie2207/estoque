package com.fullstack.sic.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.fullstack.sic.model.Produto;
import com.fullstack.sic.model.Solicitacao;
import com.fullstack.sic.model.SolicitacaoProduto;
import com.fullstack.sic.model.nativequery.ProdutoEstoque;
import com.fullstack.sic.repository.Produtos;
import com.fullstack.sic.repository.SolicitacoesProdutos;

@Named
@ViewScoped
public class CadastroSolicitacaoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Solicitacao solicitacao;

	@Inject
	private SolicitacoesProdutos solicitacoesProdutos;

	@Inject
	private Produtos produtos;

	private SolicitacaoProduto solicitacaoProduto;

	private List<Produto> listaProdutosEstoque;
	private List<ProdutoEstoque> listaQuantidadeProdutoDisponivel;
	private List<SolicitacaoProduto> produtoSolicitados;

	public void init() {
		solicitacaoProduto = new SolicitacaoProduto();
		solicitacaoProduto.setSolicitacao(solicitacao);
		listarProdutoEstoque();
		listarQuantidadeProdutoDisponivel();
		listarProdutoSolicitado();
	}

	public void editar() {
		if (getSolicitacaoProduto() != null) {
			PrimeFaces.current().executeScript("amges.nextTab('#myTabs', '#tabCadastroCargo', '#nome')");
		}
	}

	public void novo() {
		setSolicitacaoProduto(new SolicitacaoProduto());
		solicitacaoProduto.setSolicitacao(solicitacao);
	}

	public void salvar() {

		if (!verificaProdutoIncluso()) {
			return;
		}

		if (!verificaQuantidade()) {
			return;
		}

		if (getSolicitacaoProduto() != null) {
			setSolicitacaoProduto(solicitacoesProdutos.salvar(getSolicitacaoProduto()));

			if (getSolicitacaoProduto().getId() != null) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Produto inserido com sucesso à solicitação!', 'success')");
				novo();
				listarProdutoSolicitado();
			} else {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Ocorreu um erro ao inserir o Produto. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
			}
		}

	}

	public void excluir(SolicitacaoProduto solicitacaoProduto) {

		if (solicitacoesProdutos.excluir(solicitacaoProduto)) {
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Produto excluído com sucesso da Solicitação!', 'success')");
			novo();
			listarProdutoSolicitado();
		} else {
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Ocorreu um erro ao excluir o Produto da Solicitação. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
		}

	}

	public boolean verificaProdutoIncluso() {

		for (SolicitacaoProduto p : produtoSolicitados) {

			if (solicitacaoProduto.getProduto().getId().equals(p.getProduto().getId())) {
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Produto já incluído na solicitação. Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
				return false;
			}
		}
		return true;

	}

	public boolean verificaQuantidade() {

		for (ProdutoEstoque p : listaQuantidadeProdutoDisponivel) {

			if (solicitacaoProduto.getProduto().getId().equals(p.getId())) {

				if (solicitacaoProduto.getQuantidade() > p.getTotal()) {
					PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
							+ "'Quantidade não disponível. Total de " + p.getTotal()
							+ " itens na origem.  Por favor, tente novamente ou entre em contato com o administrador!', 'danger')");
					return false;
				}
			}
		}
		return true;

	}

	public void listarProdutoEstoque() {
		listaProdutosEstoque = produtos.produtosEmEstoque(solicitacao.getOrigem());
	}

	public void listarQuantidadeProdutoDisponivel() {
		listaQuantidadeProdutoDisponivel = solicitacoesProdutos.QuantidadeProdutoDisponivel(solicitacao.getOrigem());
	}

	public void listarProdutoSolicitado() {
		produtoSolicitados = solicitacoesProdutos.porSolicitacao(solicitacao);
	}

	public SolicitacaoProduto getSolicitacaoProduto() {
		return solicitacaoProduto;
	}

	public void setSolicitacaoProduto(SolicitacaoProduto solicitacaoProduto) {
		this.solicitacaoProduto = solicitacaoProduto;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public List<Produto> getListaProdutosEstoque() {
		return listaProdutosEstoque;
	}

	public List<ProdutoEstoque> getListaQuantidadeProdutoDisponivel() {
		return listaQuantidadeProdutoDisponivel;
	}

	public List<SolicitacaoProduto> getProdutoSolicitados() {
		return produtoSolicitados;
	}

}