/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.CacheManager
 */
package net.sf.ehcache.management.service;

import net.sf.ehcache.CacheManager;

public interface ManagementServerLifecycle {
    public void register(CacheManager var1);

    public void unregister(CacheManager var1);

    public boolean hasRegistered();

    public void dispose();
}

