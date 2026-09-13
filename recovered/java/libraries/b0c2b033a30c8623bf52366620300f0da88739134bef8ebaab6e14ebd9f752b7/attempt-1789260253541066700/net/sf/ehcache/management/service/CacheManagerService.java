/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

import java.util.Collection;
import net.sf.ehcache.management.resource.CacheManagerEntity;
import net.sf.ehcache.management.resource.QueryResultsEntity;
import org.terracotta.management.ServiceExecutionException;

public interface CacheManagerService {
    public void updateCacheManager(String var1, CacheManagerEntity var2) throws ServiceExecutionException;

    public Collection<QueryResultsEntity> executeQuery(String var1, String var2) throws ServiceExecutionException;
}

