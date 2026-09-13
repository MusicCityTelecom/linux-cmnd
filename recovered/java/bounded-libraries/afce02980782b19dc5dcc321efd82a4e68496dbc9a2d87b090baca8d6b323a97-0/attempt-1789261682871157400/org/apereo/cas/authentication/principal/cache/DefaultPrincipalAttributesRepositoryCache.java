/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalAttributesRepositoryCache
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.DigestUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.io.Closeable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalAttributesRepositoryCache;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPrincipalAttributesRepositoryCache
implements PrincipalAttributesRepositoryCache,
Closeable {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPrincipalAttributesRepositoryCache.class);
    private static final int DEFAULT_MAXIMUM_CACHE_SIZE = 1000;
    private static final String DEFAULT_CACHE_EXPIRATION_UNIT = TimeUnit.HOURS.name();
    private final Map<String, Cache<String, Map<String, List<Object>>>> registeredServicesCache = new HashMap<String, Cache<String, Map<String, List<Object>>>>(0);

    private static String buildRegisteredServiceCacheKey(RegisteredService registeredService) {
        String key = registeredService.getId() + 64L + registeredService.getName();
        return DigestUtils.sha512((String)key);
    }

    private static Cache<String, Map<String, List<Object>>> initializeCache(RegisteredServicePrincipalAttributesRepository repository) {
        CachingPrincipalAttributesRepository cachedRepository = (CachingPrincipalAttributesRepository)CachingPrincipalAttributesRepository.class.cast(repository);
        TimeUnit unit = TimeUnit.valueOf(StringUtils.defaultString((String)cachedRepository.getTimeUnit(), (String)DEFAULT_CACHE_EXPIRATION_UNIT));
        return Caffeine.newBuilder().initialCapacity(1000).maximumSize(1000L).expireAfterWrite(cachedRepository.getExpiration(), unit).build(s -> new TreeMap(String.CASE_INSENSITIVE_ORDER));
    }

    @Override
    public void close() {
        this.invalidate();
    }

    public void invalidate() {
        this.registeredServicesCache.values().forEach(Cache::invalidateAll);
    }

    public Map<String, List<Object>> fetchAttributes(RegisteredService registeredService, RegisteredServicePrincipalAttributesRepository repository, Principal principal) {
        Cache<String, Map<String, List<Object>>> cache = this.getRegisteredServiceCacheInstance(registeredService, repository);
        return (Map)cache.get((Object)principal.getId(), s -> {
            LOGGER.debug("No cached attributes could be found for [{}]", (Object)principal.getId());
            return new TreeMap(String.CASE_INSENSITIVE_ORDER);
        });
    }

    public void putAttributes(RegisteredService registeredService, RegisteredServicePrincipalAttributesRepository repository, String id, Map<String, List<Object>> attributes) {
        Cache<String, Map<String, List<Object>>> cache = this.getRegisteredServiceCacheInstance(registeredService, repository);
        cache.put((Object)id, attributes);
    }

    private Cache<String, Map<String, List<Object>>> getRegisteredServiceCacheInstance(RegisteredService registeredService, RegisteredServicePrincipalAttributesRepository repository) {
        String key = DefaultPrincipalAttributesRepositoryCache.buildRegisteredServiceCacheKey(registeredService);
        if (this.registeredServicesCache.containsKey(key)) {
            return this.registeredServicesCache.get(key);
        }
        Cache<String, Map<String, List<Object>>> cache = DefaultPrincipalAttributesRepositoryCache.initializeCache(repository);
        this.registeredServicesCache.put(key, cache);
        return cache;
    }
}

