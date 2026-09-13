/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.service;

import net.sf.ehcache.management.resource.CacheEntityV2;
import org.terracotta.management.ServiceExecutionException;

public interface CacheServiceV2 {
    public void createOrUpdateCache(String var1, String var2, CacheEntityV2 var3) throws ServiceExecutionException;

    public void clearCache(String var1, String var2) throws ServiceExecutionException;
}

