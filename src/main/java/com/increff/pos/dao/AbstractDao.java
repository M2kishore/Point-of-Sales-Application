package com.increff.pos.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class AbstractDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	protected <T> T getSingle(TypedQuery<T> query) {
		return query.getResultList().stream().findFirst().orElse(null);
	}
	
	protected <T> TypedQuery<T> getQuery(String jpql, Class<T> clazz) {
		return entityManager.createQuery(jpql, clazz);
	}
	
	protected EntityManager entityManager() {
		return entityManager;
	}

}
