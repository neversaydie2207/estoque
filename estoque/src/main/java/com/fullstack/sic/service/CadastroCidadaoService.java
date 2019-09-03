package com.fullstack.sic.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import com.fullstack.sic.model.Cidadao;
import com.fullstack.sic.model.filtros.FiltroServidor;
import com.fullstack.sic.repository.Cidadaos;

public class CadastroCidadaoService implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cidadaos cidadaos;
	
	public Cidadao salvar(Cidadao cidadao)
	{
		if(cidadao != null)
		{
			Cidadao cidadaoRegistrado = cidadaos.porCpf(cidadao);
			
			if(cidadaoRegistrado != null && !cidadaoRegistrado.equals(cidadao))
			{
				Messages.addWarn(null,"CPF já cadastrado. Informações disponíveis para edição.");
				return cidadaoRegistrado;
				
			}
			else
			{
				cidadaos.salvar(cidadao);
				
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Cidadao cadastrado com sucesso!', 'success')");
				
				PrimeFaces.current().ajax().addCallbackParam("isValid", true);
				return null;
			}
		}
		else
		{
			PrimeFaces.current().ajax().addCallbackParam("isValid", false);
			throw new NegocioException("Cidadao não pode está vazio!");
		}
		
	}
	
	public void excluir(Cidadao cidadao)
	{
		if(cidadao != null)
		{
			boolean registroExcluido = cidadaos.excluir(cidadao);
			
			if(registroExcluido)
			{
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Cidadao Excluído com sucesso.', 'success')");
				
				PrimeFaces.current().executeScript("amges.closeModal('modalExcluirServidor')");
			}
			else
			{
				PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
						+ "'Não foi possível excluír o Cidadao.', 'danger')");
			}
		}
	}
	
	// METODOS DE LISTAGEM
	
	public Cidadao pesquisarPorCpf(Cidadao cidadao)
	{
		if(cidadao != null)
		{
			Cidadao servidorRegistrado = cidadaos.porCpf(cidadao);
			
			if(servidorRegistrado != null)
			{
				return servidorRegistrado;
			}
			else 
			{
				return null;
			}
			
		}
		else
		{
			throw new NegocioException("Por favor, informe o CPF para este Cidadao");
		}
		
	}
	
	public List<Cidadao> todos()
	{
		return cidadaos.todos();
	}
	
	
	/* LAZY */
	public int quantidadeFiltrados(FiltroServidor filtro)
	{
		return cidadaos.quantidadeFiltrados(filtro);
	}

	public List<Cidadao> filtrados(FiltroServidor filtro)
	{
		return cidadaos.filtrados(filtro);
	}
	

}
