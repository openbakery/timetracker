package org.openbakery.timetracker.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

public interface Persistence {
	public void flush() throws PersistenceException;

	public void close() throws PersistenceException;

	public <T> T store(T object) throws PersistenceException;

	public <T> List<T> query(String query, Class<T> type) throws PersistenceException;

	public <T extends Object> T querySingle(String query, Class<T> type) throws PersistenceException;

	public <T> List<T> query(String queryString, Map<String, Object> parameters, Class<T> type) throws PersistenceException;

	public List<? extends Object> queryNative(String query, String name) throws PersistenceException;

	public List<? extends Object> queryNative(String query, Class<? extends Object> clazz) throws PersistenceException;

	public <T> T delete(T object) throws PersistenceException;

}
