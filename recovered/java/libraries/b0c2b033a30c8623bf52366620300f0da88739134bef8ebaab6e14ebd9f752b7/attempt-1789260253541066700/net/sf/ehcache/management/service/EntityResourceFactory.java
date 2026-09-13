/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

import java.util.Collection;
import java.util.Set;
import net.sf.ehcache.management.resource.CacheConfigEntity;
import net.sf.ehcache.management.resource.CacheEntity;
import net.sf.ehcache.management.resource.CacheManagerConfigEntity;
import net.sf.ehcache.management.resource.CacheManagerEntity;
import net.sf.ehcache.management.resource.CacheStatisticSampleEntity;
import org.terracotta.management.ServiceExecutionException;

public interface EntityResourceFactory {
    public Collection<CacheManagerEntity> createCacheManagerEntities(Set<String> var1, Set<String> var2) throws ServiceExecutionException;

    public Collection<CacheManagerConfigEntity> createCacheManagerConfigEntities(Set<String> var1) throws ServiceExecutionException;

    public Collection<CacheEntity> createCacheEntities(Set<String> var1, Set<String> var2, Set<String> var3) throws ServiceExecutionException;

    public Collection<CacheConfigEntity> createCacheConfigEntities(Set<String> var1, Set<String> var2) throws ServiceExecutionException;

    public Collection<CacheStatisticSampleEntity> createCacheStatisticSampleEntity(Set<String> var1, Set<String> var2, Set<String> var3) throws ServiceExecutionException;
}

