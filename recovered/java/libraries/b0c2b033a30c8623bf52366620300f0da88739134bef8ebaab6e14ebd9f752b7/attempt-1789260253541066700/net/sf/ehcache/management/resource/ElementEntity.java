/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import org.terracotta.management.resource.VersionedEntity;

public class ElementEntity
extends VersionedEntity {
    private String agentId;
    private String cacheName;

    @Override
    public String getAgentId() {
        return this.agentId;
    }

    @Override
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCacheName() {
        return this.cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}

