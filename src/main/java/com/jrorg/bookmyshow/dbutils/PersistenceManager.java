package com.jrorg.bookmyshow.dbutils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	public static interface Transaction<T>{
		public T perform(EntityManager entitymanager)throws Exception;
	}
	public static <T> T perform(Transaction<T> transaction)throws Exception {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "BookyShow_Repo" );
		EntityManager entitymanager = emfactory.createEntityManager( );
		entitymanager.getTransaction().begin();
		T entity = transaction.perform(entitymanager);
		entitymanager.getTransaction().commit();
		emfactory.close();
		return entity;
	}
}
