package org.openbakery.timetracker.persistence;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabasePersistence implements Persistence {

	private static Logger log = LoggerFactory.getLogger(DatabasePersistence.class);

	private EntityManagerFactory entityManagerFactory;

	public DatabasePersistence(String persistence) {
		entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory(persistence);
	}

	public <T> T store(T object) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		object = entityManager.merge(object);
		transaction.commit();
		entityManager.close();
		return object;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(String query, Class<T> type) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<T> result = (List<T>) entityManager.createQuery(query).getResultList();
		entityManager.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T querySingle(String query, Class<T> type) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		T result = (T) entityManager.createQuery(query).getSingleResult();
		entityManager.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> query(String queryString, Map<String, Object> parameters, Class<T> type) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		log.debug("queryString={}", queryString);
		log.debug("parameters={}", parameters);

		Query query = entityManager.createQuery(queryString);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			if (entry.getValue() instanceof Date) {
				query.setParameter(entry.getKey(), (Date)entry.getValue(), TemporalType.TIMESTAMP);
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		List<T> result = (List<T>) query.getResultList();
		entityManager.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<? extends Object> queryNative(String query, String name) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<? extends Object> result = entityManager.createNativeQuery(query, name).getResultList();
		entityManager.close();
		return result;
	}

	public List<? extends Object> queryNamed(String name) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<?> result = entityManager.createNamedQuery(name).getResultList();
		entityManager.close();
		return result;
	}

	public void close() throws PersistenceException {
		entityManagerFactory.close();
	}

	public void flush() throws PersistenceException {
	}

	@SuppressWarnings("unchecked")
	public List<? extends Object> queryNative(String query, Class<?> clazz) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<? extends Object> result = entityManager.createNativeQuery(query, clazz).getResultList();
		entityManager.close();
		return result;
	}

	public <T> T delete(T object) throws PersistenceException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Object newObject = entityManager.merge(object);
		entityManager.remove(newObject);
		transaction.commit();
		entityManager.close();
		return object;
	}

}
