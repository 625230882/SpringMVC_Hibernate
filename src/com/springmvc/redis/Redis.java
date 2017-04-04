package com.springmvc.redis;

public interface Redis {

	public Object get(Object key);
	
	public void put(Object key, Object value);
	
	 public void deleteKey(Object key);
}
