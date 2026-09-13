/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management.resource;

import org.terracotta.management.resource.VersionedEntity;

public class QueryResultsEntity
extends VersionedEntity {
    private String name;
    private String agentId;
    private Object[][] data;

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

    public void setData(Object[][] data) {
        this.data = data;
    }

    public Object[][] getData() {
        return this.data;
    }
}

