package com.fullstack.sic.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.fullstack.sic.model.Grupo;
import com.fullstack.sic.model.Usuario;
import com.fullstack.sic.model.filtros.FiltroUsuario;
import com.fullstack.sic.repository.Usuarios;
import com.fullstack.sic.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	public Grupo buscarGrupo(String nome)
	{
		return usuarios.buscarGrupo(nome);
	}
	
	public Usuario buscarPorCpf(String cpf)
	{
		return usuarios.porCpf(cpf);
	}
	
	public Usuario porEmailAtivo(String email)
	{
		if(email!= null && !email.isEmpty())
		{
			return usuarios.porEmailAtivo(email);
		}
		else
		{
			PrimeFaces.current().executeScript("amges.showNotification('top', 'right', "
					+ "'Por favor, informe um e-mail para continuar!', 'warning')");
			return null;
		}
	}
	
	@Transactional
	public Usuario salvar(Usuario usuario)
	{
		Usuario usuarioRegistrado = usuarios.porEmail(usuario.getEmail());
		
		if(usuarioRegistrado != null && !usuarioRegistrado.equals(usuario))
		{
			throw new NegocioException("Desculpe, mas o e-mail " + usuario.getEmail() + " já encontra-se "
					+ "cadastrado em nossa base de dados!");
		}
		else
		{
			return usuarios.salvar(usuario);
		}
		
	}
	
	@Transactional
	public Usuario salvarOnLojista(Usuario usuario)
	{
		Usuario usuarioRegistrado = usuarios.porCpf(usuario.getCpf());
		Usuario usuarioEmailRegistrado = usuarios.porEmail(usuario.getEmail());
		
		if(usuarioEmailRegistrado != null)
		{
			throw new NegocioException("Desculpe, mas o E-MAIL " + usuario.getEmail() + " já encontra-se "
					+ "cadastrado em nossa base de dados!");
			
		}
		else
		{
			if(usuarioRegistrado != null && !usuarioRegistrado.equals(usuario))
			{
				throw new NegocioException("Desculpe, mas o CPF " + usuario.getCpf() + " já encontra-se "
						+ "cadastrado em nossa base de dados!");
			}
			else
			{
				return usuarios.salvar(usuario);
			}
		}
		
		
	}
	
	@Transactional
	public Usuario confirmarEmail(String codigo)
	{
		Usuario usuarioValido = usuarios.porCodigoConfirmacao(codigo);
		
		if(usuarioValido != null && usuarioValido.getAtivo() == true)
		{
			throw new NegocioException("Usuário: " + usuarioValido.getEmail() + " Já confirmado!");
		}
		
		usuarioValido.setAtivo(true);
		
		return usuarios.salvar(usuarioValido);
	}
	
	/* LAZY COM CRITERIA */
	public List<Usuario> filtrados(FiltroUsuario filtro) 
	{
		return usuarios.filtrados(filtro);
	}
	
	public int quantidadeFiltrados(FiltroUsuario filtro) 
	{
		return usuarios.quantidadeFiltrados(filtro);
	}

}
