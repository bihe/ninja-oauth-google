package net.binggl.ninja.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ninja.Context;
import ninja.Result;
import ninja.session.Session;

/**
 * helper class for unit-testing
 * @author henrik
 */
public class FakeSession implements Session {

	private Map<String, String> store;
	private UUID id;
	
	
	@Override
	public void init(Context context) {
		this.store = new HashMap<>();
		this.id = UUID.randomUUID();
	}

	@Override
	public String getId() {
		return this.id.toString();
	}

	@Override
	public Map<String, String> getData() {
		return this.store;
	}

	@Override
	public String getAuthenticityToken() {
		return this.id.toString();
	}

	@Override
	public void save(Context context, Result result) {
	}

	@Override
	public void put(String key, String value) {
		this.store.put(key, value);
	}

	@Override
	public String get(String key) {
		return this.store.get(key);
	}

	@Override
	public String remove(String key) {
		return this.store.remove(key);
	}

	@Override
	public void clear() {
		this.store.clear();
	}

	@Override
	public boolean isEmpty() {
		return this.store == null || this.store.size() == 0;
	}

}
