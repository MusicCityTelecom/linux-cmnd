/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

import net.sf.ehcache.management.resource.CacheEntity;
import org.terracotta.management.ServiceExecutionException;

public interface CacheService {
    public void createOrUpdateCache(String var1, String var2, CacheEntity var3) throws ServiceExecutionException;

    public void clearCache(String var1, String var2) throws ServiceExecutionException;
}

