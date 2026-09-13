/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ehcache;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ehcache3-ticket-registry")
@JsonFilter(value="Ehcache3TerracottaProperties")
public class Ehcache3TerracottaProperties
implements Serializable {
    private static final long serialVersionUID = 1112510035918976450L;
    private String terracottaClusterUri;
    private String defaultServerResource = "main";
    private String resourcePoolName = "cas-ticket-pool";
    private String resourcePoolSize = "15MB";
    private long clusterReadWriteTimeout = 5L;
    private long clusterConnectionTimeout = 150L;
    private Consistency clusteredCacheConsistency = Consistency.STRONG;

    @Generated
    public String getTerracottaClusterUri() {
        return this.terracottaClusterUri;
    }

    @Generated
    public String getDefaultServerResource() {
        return this.defaultServerResource;
    }

    @Generated
    public String getResourcePoolName() {
        return this.resourcePoolName;
    }

    @Generated
    public String getResourcePoolSize() {
        return this.resourcePoolSize;
    }

    @Generated
    public long getClusterReadWriteTimeout() {
        return this.clusterReadWriteTimeout;
    }

    @Generated
    public long getClusterConnectionTimeout() {
        return this.clusterConnectionTimeout;
    }

    @Generated
    public Consistency getClusteredCacheConsistency() {
        return this.clusteredCacheConsistency;
    }

    @Generated
    public Ehcache3TerracottaProperties setTerracottaClusterUri(String terracottaClusterUri) {
        this.terracottaClusterUri = terracottaClusterUri;
        return this;
    }

    @Generated
    public Ehcache3TerracottaProperties setDefaultServerResource(String defaultServerResource) {
        this.defaultServerResource = defaultServerResource;
        return this;
    }

    @Generated
    public Ehcache3TerracottaProperties setResourcePoolName(String resourcePoolName) {
        this.resourcePoolName = resourcePoolName;
        return this;
    }

    @Generated
    public Ehcache3TerracottaProperties setResourcePoolSize(String resourcePoolSize) {
        this.resourcePoolSize = resourcePoolSize;
        return this;
    }

    @Generated
    public Ehcache3TerracottaProperties setClusterReadWriteTimeout(long clusterReadWriteTimeout) {
        this.clusterReadWriteTimeout = clusterReadWriteTimeout;
        return this;
    }

    @Generated
    public Ehcache3TerracottaProperties setClusterConnectionTimeout(long clusterConnectionTimeout) {
        this.clusterConnectionTimeout = clusterConnectionTimeout;
        return this;
    }

    @Generated
    public Ehcache3TerracottaProperties setClusteredCacheConsistency(Consistency clusteredCacheConsistency) {
        this.clusteredCacheConsistency = clusteredCacheConsistency;
        return this;
    }

    public static enum Consistency {
        EVENTUAL,
        STRONG;

    }
}

