package com.fullstack.sic.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer 
{
	private EntityManagerFactory factory;
	
	public EntityManagerProducer()
	{
		factory = Persistence.createEntityManagerFactory("projetomasterPU");
	}
	
	@Produces @RequestScoped
	public EntityManager createEntityManager()
	{
		return factory.createEntityManager();
	}
	
	//@Disposes = quando o @RequestScoped acabar o @Dispose é chamado pra ser executado
	public void closeEntityManager(@Disposes EntityManager manager)
	{
		manager.close();
	}

}