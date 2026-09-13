/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

import net.sf.ehcache.management.resource.CacheManagerEntityV2;
import org.terracotta.management.ServiceExecutionException;
import org.terracotta.management.resource.ResponseEntityV2;

public interface CacheManagerServiceV2 {
    public void updateCacheManager(String var1, CacheManagerEntityV2 var2) throws ServiceExecutionException;

    public ResponseEntityV2 executeQuery(String var1, String var2) throws ServiceExecutionException;
}

