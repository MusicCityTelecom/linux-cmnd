/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import java.util.HashMap;
import java.util.Map;
import org.terracotta.management.resource.VersionedEntity;

public class CacheManagerEntity
extends VersionedEntity {
    private String name;
    private String agentId;
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAgentId() {
        return this.agentId;
    }

    @Override
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}

