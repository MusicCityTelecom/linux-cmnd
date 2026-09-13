/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cache.Cache
 *  org.springframework.cache.CacheManager
 *  org.springframework.lang.Nullable
 */
package org.springframework.boot.actuate.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.cache.NonUniqueCacheException;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

@Endpoint(id="caches")
public class CachesEndpoint {
    private final Map<String, CacheManager> cacheManagers;

    public CachesEndpoint(Map<String, CacheManager> cacheManagers) {
        this.cacheManagers = new LinkedHashMap<String, CacheManager>(cacheManagers);
    }

    @ReadOperation
    public CachesReport caches() {
        LinkedHashMap<String, Map> descriptors = new LinkedHashMap<String, Map>();
        this.getCacheEntries(this.matchAll(), this.matchAll()).forEach(entry -> {
            String cacheName = entry.getName();
            String cacheManager = entry.getCacheManager();
            Map cacheManagerDescriptors = descriptors.computeIfAbsent(cacheManager, key -> new LinkedHashMap());
            cacheManagerDescriptors.put(cacheName, new CacheDescriptor(entry.getTarget()));
        });
        LinkedHashMap<String, CacheManagerDescriptor> cacheManagerDescriptors = new LinkedHashMap<String, CacheManagerDescriptor>();
        descriptors.forEach((name, entries) -> cacheManagerDescriptors.put((String)name, new CacheManagerDescriptor((Map<String, CacheDescriptor>)entries)));
        return new CachesReport(cacheManagerDescriptors);
    }

    @ReadOperation
    public CacheEntry cache(@Selector String cache, @Nullable String cacheManager) {
        return this.extractUniqueCacheEntry(cache, this.getCacheEntries((String name) -> name.equals(cache), this.isNameMatch(cacheManager)));
    }

    @DeleteOperation
    public void clearCaches() {
        this.getCacheEntries(this.matchAll(), this.matchAll()).forEach(this::clearCache);
    }

    @DeleteOperation
    public boolean clearCache(@Selector String cache, @Nullable String cacheManager) {
        CacheEntry entry = this.extractUniqueCacheEntry(cache, this.getCacheEntries((String name) -> name.equals(cache), this.isNameMatch(cacheManager)));
        return entry != null && this.clearCache(entry);
    }

    private List<CacheEntry> getCacheEntries(Predicate<String> cacheNamePredicate, Predicate<String> cacheManagerNamePredicate) {
        return this.cacheManagers.keySet().stream().filter(cacheManagerNamePredicate).flatMap(cacheManagerName -> this.getCacheEntries((String)cacheManagerName, cacheNamePredicate).stream()).collect(Collectors.toList());
    }

    private List<CacheEntry> getCacheEntries(String cacheManagerName, Predicate<String> cacheNamePredicate) {
        CacheManager cacheManager = this.cacheManagers.get(cacheManagerName);
        return cacheManager.getCacheNames().stream().filter(cacheNamePredicate).map(arg_0 -> ((CacheManager)cacheManager).getCache(arg_0)).filter(Objects::nonNull).map(cache -> new CacheEntry((Cache)cache, cacheManagerName)).collect(Collectors.toList());
    }

    private CacheEntry extractUniqueCacheEntry(String cache, List<CacheEntry> entries) {
        if (entries.size() > 1) {
            throw new NonUniqueCacheException(cache, entries.stream().map(CacheEntry::getCacheManager).distinct().collect(Collectors.toList()));
        }
        return !entries.isEmpty() ? entries.get(0) : null;
    }

    private boolean clearCache(CacheEntry entry) {
        String cacheName = entry.getName();
        String cacheManager = entry.getCacheManager();
        Cache cache = this.cacheManagers.get(cacheManager).getCache(cacheName);
        if (cache != null) {
            cache.clear();
            return true;
        }
        return false;
    }

    private Predicate<String> isNameMatch(String name) {
        return name != null ? requested -> requested.equals(name) : this.matchAll();
    }

    private Predicate<String> matchAll() {
        return name -> true;
    }

    public static final class CacheEntry
    extends CacheDescriptor {
        private final String name;
        private final String cacheManager;

        public CacheEntry(Cache cache, String cacheManager) {
            super(cache.getNativeCache().getClass().getName());
            this.name = cache.getName();
            this.cacheManager = cacheManager;
        }

        public String getName() {
            return this.name;
        }

        public String getCacheManager() {
            return this.cacheManager;
        }
    }

    public static class CacheDescriptor {
        private final String target;

        public CacheDescriptor(String target) {
            this.target = target;
        }

        public String getTarget() {
            return this.target;
        }
    }

    public static final class CacheManagerDescriptor {
        private final Map<String, CacheDescriptor> caches;

        public CacheManagerDescriptor(Map<String, CacheDescriptor> caches) {
            this.caches = caches;
        }

        public Map<String, CacheDescriptor> getCaches() {
            return this.caches;
        }
    }

    public static final class CachesReport {
        private final Map<String, CacheManagerDescriptor> cacheManagers;

        public CachesReport(Map<String, CacheManagerDescriptor> cacheManagers) {
            this.cacheManagers = cacheManagers;
        }

        public Map<String, CacheManagerDescriptor> getCacheManagers() {
            return this.cacheManagers;
        }
    }
}

