package org.openbakery.timetracker.web;

import java.util.Map;

import org.apache.wicket.model.IModel;

public class MapModel<T> implements IModel<T> {

	private static final long serialVersionUID = 1L;
	private Map<String, T> map;
	private String key;

	public MapModel(Map<String, T> map, String key) {
		this.map = map;
		this.key = key;
	}

	@Override
	public T getObject() {
		return map.get(key);
	}

	@Override
	public void setObject(T object) {
		map.put(key, object);
	}

	@Override
	public void detach() {
	}

}
