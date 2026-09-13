/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

import java.util.Set;
import org.terracotta.management.ServiceExecutionException;
import org.terracotta.management.resource.ResponseEntityV2;

public interface EntityResourceFactoryV2 {
    public ResponseEntityV2 createCacheManagerEntities(Set<String> var1, Set<String> var2) throws ServiceExecutionException;

    public ResponseEntityV2 createCacheManagerConfigEntities(Set<String> var1) throws ServiceExecutionException;

    public ResponseEntityV2 createCacheEntities(Set<String> var1, Set<String> var2, Set<String> var3) throws ServiceExecutionException;

    public ResponseEntityV2 createCacheConfigEntities(Set<String> var1, Set<String> var2) throws ServiceExecutionException;

    public ResponseEntityV2 createCacheStatisticSampleEntity(Set<String> var1, Set<String> var2, Set<String> var3) throws ServiceExecutionException;
}

