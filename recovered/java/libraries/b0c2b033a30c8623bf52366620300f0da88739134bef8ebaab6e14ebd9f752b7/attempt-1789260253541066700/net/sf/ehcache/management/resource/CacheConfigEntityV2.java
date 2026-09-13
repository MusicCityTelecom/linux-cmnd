/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import org.terracotta.management.resource.AbstractEntityV2;

public class CacheConfigEntityV2
extends AbstractEntityV2 {
    private String cacheName;
    private String cacheManagerName;
    private String xml;

    public String getCacheManagerName() {
        return this.cacheManagerName;
    }

    public void setCacheManagerName(String cacheManagerName) {
        this.cacheManagerName = cacheManagerName;
    }

    public String getXml() {
        return this.xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getCacheName() {
        return this.cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}

