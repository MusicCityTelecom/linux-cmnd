/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.CacheException
 *  net.sf.ehcache.CacheManager
 *  net.sf.ehcache.terracotta.ClusteredInstanceFactory
 *  net.sf.ehcache.terracotta.TerracottaClient
 */
package net.sf.ehcache;

import java.lang.reflect.Field;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.terracotta.ClusteredInstanceFactory;
import net.sf.ehcache.terracotta.TerracottaClient;

public class ClusteredInstanceFactoryAccessor {
    public static ClusteredInstanceFactory getClusteredInstanceFactory(CacheManager cacheManager) {
        try {
            Field field = CacheManager.class.getDeclaredField("terracottaClient");
            field.setAccessible(true);
            TerracottaClient terracottaClient = (TerracottaClient)field.get(cacheManager);
            return terracottaClient.getClusteredInstanceFactory();
        }
        catch (NoSuchFieldException nsfe) {
            throw new CacheException((Throwable)nsfe);
        }
        catch (IllegalAccessException iae) {
            throw new CacheException((Throwable)iae);
        }
    }

    public static void setTerracottaClient(CacheManager cacheManager, TerracottaClient terracottaClient) {
        try {
            Field field = CacheManager.class.getDeclaredField("terracottaClient");
            field.setAccessible(true);
            field.set(cacheManager, terracottaClient);
        }
        catch (NoSuchFieldException nsfe) {
            throw new CacheException((Throwable)nsfe);
        }
        catch (IllegalAccessException iae) {
            throw new CacheException((Throwable)iae);
        }
    }
}

