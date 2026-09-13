/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.redis;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.redis.RedisClusterNodeProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-redis-core")
@JsonFilter(value="RedisClusterProperties")
public class RedisClusterProperties
implements Serializable {
    private static final long serialVersionUID = 5236837157740950831L;
    private List<RedisClusterNodeProperties> nodes = new ArrayList<RedisClusterNodeProperties>(0);
    @RequiredProperty
    private String password;
    private int maxRedirects;
    private boolean dynamicRefreshSources = true;
    @DurationCapable
    private String topologyRefreshPeriod;
    private boolean adaptiveTopologyRefresh;

    @Generated
    public List<RedisClusterNodeProperties> getNodes() {
        return this.nodes;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public int getMaxRedirects() {
        return this.maxRedirects;
    }

    @Generated
    public boolean isDynamicRefreshSources() {
        return this.dynamicRefreshSources;
    }

    @Generated
    public String getTopologyRefreshPeriod() {
        return this.topologyRefreshPeriod;
    }

    @Generated
    public boolean isAdaptiveTopologyRefresh() {
        return this.adaptiveTopologyRefresh;
    }

    @Generated
    public RedisClusterProperties setNodes(List<RedisClusterNodeProperties> nodes) {
        this.nodes = nodes;
        return this;
    }

    @Generated
    public RedisClusterProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public RedisClusterProperties setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
        return this;
    }

    @Generated
    public RedisClusterProperties setDynamicRefreshSources(boolean dynamicRefreshSources) {
        this.dynamicRefreshSources = dynamicRefreshSources;
        return this;
    }

    @Generated
    public RedisClusterProperties setTopologyRefreshPeriod(String topologyRefreshPeriod) {
        this.topologyRefreshPeriod = topologyRefreshPeriod;
        return this;
    }

    @Generated
    public RedisClusterProperties setAdaptiveTopologyRefresh(boolean adaptiveTopologyRefresh) {
        this.adaptiveTopologyRefresh = adaptiveTopologyRefresh;
        return this;
    }
}

