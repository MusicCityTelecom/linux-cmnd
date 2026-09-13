/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.policy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.RetryCacheCapacityExceededException;
import org.springframework.retry.policy.RetryContextCache;

public class MapRetryContextCache
implements RetryContextCache {
    public static final int DEFAULT_CAPACITY = 4096;
    private Map<Object, RetryContext> map = Collections.synchronizedMap(new HashMap());
    private int capacity;

    public MapRetryContextCache() {
        this(4096);
    }

    public MapRetryContextCache(int defaultCapacity) {
        this.capacity = defaultCapacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public RetryContext get(Object key) {
        return this.map.get(key);
    }

    @Override
    public void put(Object key, RetryContext context) {
        if (this.map.size() >= this.capacity) {
            throw new RetryCacheCapacityExceededException("Retry cache capacity limit breached. Do you need to re-consider the implementation of the key generator, or the equals and hashCode of the items that failed?");
        }
        this.map.put(key, context);
    }

    @Override
    public void remove(Object key) {
        this.map.remove(key);
    }
}

