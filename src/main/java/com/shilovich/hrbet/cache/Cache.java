package com.shilovich.hrbet.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache {
    private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

    public Object getCache(String type) {
        return cache.get(type);
    }

    public void addCache(String type, Object object) {
        this.cache.putIfAbsent(type, object);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
