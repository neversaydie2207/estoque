package com.fullstack.sic.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.fullstack.sic.model.UserTelegram;

public class UsuariosTelegram implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public UsuariosTelegram()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("academiajovemPU");
		manager = emf.createEntityManager();
	}
	
	
	public UserTelegram porId(Long id)
	{
		return manager.find(UserTelegram.class, id);
	}

	public UserTelegram buscarPorUserId(int tel_id)
	{
		try
		{
			return manager.createQuery("from UserTelegram where telId = :tel_id", UserTelegram.class)
					.setParameter("tel_id", tel_id)
					.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}

	}
	
	public UserTelegram buscarPorChatId(int chat_id)
	{
		try
		{
			return manager.createQuery("from UserTelegram where chatId = :chat_id", UserTelegram.class)
					.setParameter("chat_id", chat_id)
					.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}

	}
	
	public boolean salvar(UserTelegram usuario)
	{
				
		UserTelegram usuarioRegistrado = this.buscarPorUserId(usuario.getTelId());
		
		if(usuarioRegistrado != null && !usuarioRegistrado.equals(usuario))
		{
			//Usuario não salvo porque já encontra-se na base de dados por este id
			return false;
		}
		else
		{
			//Usuario salvo porque se trata de um novo usuario
			
			manager.getTransaction().begin();
			manager.persist(usuario);
			manager.getTransaction().commit();
			
			//manager.close();
			
			return true;
			
		}
			
	}

}
