package com.manage.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 曾佳容
 * @ClassName
 * @Description
 * @date 2020/5/18
 */
public class RedisCacheMapper {

	private final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		if (key == null ) {
			return false;
		}
		cacheMap.put(key,value);
		return true;
	}

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public boolean get(String key) {
		if (key == null && !cacheMap.containsKey(key)) {
			return false;
		}
		cacheMap.get(key);
		return true;
	}

	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	public boolean remove(String key) {
		if (key == null) {
			return false;
		}
		cacheMap.remove(key);
		return true;
	}

	/**
	 * 清除缓存
	 */
	public void clear(){
		cacheMap.clear();
	}

	/**
	 * 缓存的数量
	 */
	public void size(){
		cacheMap.size();
	}

}
