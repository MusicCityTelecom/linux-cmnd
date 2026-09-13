/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import org.terracotta.management.resource.AbstractEntityV2;

public abstract class AbstractCacheEntityV2
extends AbstractEntityV2 {
    private String name;
    private String cacheManagerName;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCacheManagerName() {
        return this.cacheManagerName;
    }

    public void setCacheManagerName(String cacheManagerName) {
        this.cacheManagerName = cacheManagerName;
    }
}

