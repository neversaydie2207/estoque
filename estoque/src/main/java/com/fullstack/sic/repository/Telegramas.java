package com.fullstack.sic.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.fullstack.sic.model.UserTelegram;

public class Telegramas implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public List<UserTelegram> buscarUsuariosRegistrados()
	{
		try
		{
			return manager.createQuery("from UserTelegram u order by u.firstName",UserTelegram.class)
					.getResultList();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}


}
