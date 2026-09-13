/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;
import org.codehaus.groovy.vmplugin.v8.MethodHandleWrapper;

public class CacheableCallSite
extends MutableCallSite {
    private static final int CACHE_SIZE = SystemUtil.getIntegerSafe("groovy.indy.callsite.cache.size", 4);
    private static final float LOAD_FACTOR = 0.75f;
    private static final int INITIAL_CAPACITY = (int)Math.ceil((float)CACHE_SIZE / 0.75f) + 1;
    private volatile MethodHandleWrapper latestHitMethodHandleWrapper = null;
    private final AtomicLong fallbackCount = new AtomicLong();
    private MethodHandle defaultTarget;
    private MethodHandle fallbackTarget;
    private final Map<String, MethodHandleWrapper> lruCache = new LinkedHashMap<String, MethodHandleWrapper>(INITIAL_CAPACITY, 0.75f, true){
        private static final long serialVersionUID = 7785958879964294463L;

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return this.size() > CACHE_SIZE;
        }
    };

    public CacheableCallSite(MethodType type) {
        super(type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MethodHandleWrapper getAndPut(String className, MemoizeCache.ValueProvider<? super String, ? extends MethodHandleWrapper> valueProvider) {
        MethodHandleWrapper result;
        Map<String, MethodHandleWrapper> map = this.lruCache;
        synchronized (map) {
            result = this.lruCache.computeIfAbsent(className, valueProvider::provide);
        }
        MethodHandleWrapper lhmh = this.latestHitMethodHandleWrapper;
        if (lhmh == result) {
            result.incrementLatestHitCount();
        } else {
            result.resetLatestHitCount();
            if (null != lhmh) {
                lhmh.resetLatestHitCount();
            }
            this.latestHitMethodHandleWrapper = result;
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MethodHandleWrapper put(String name, MethodHandleWrapper mhw) {
        Map<String, MethodHandleWrapper> map = this.lruCache;
        synchronized (map) {
            return this.lruCache.put(name, mhw);
        }
    }

    public long incrementFallbackCount() {
        return this.fallbackCount.incrementAndGet();
    }

    public void resetFallbackCount() {
        this.fallbackCount.set(0L);
    }

    public MethodHandle getDefaultTarget() {
        return this.defaultTarget;
    }

    public void setDefaultTarget(MethodHandle defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    public MethodHandle getFallbackTarget() {
        return this.fallbackTarget;
    }

    public void setFallbackTarget(MethodHandle fallbackTarget) {
        this.fallbackTarget = fallbackTarget;
    }
}

