/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast.discovery;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-discovery-zookeeper")
@JsonFilter(value="HazelcastZooKeeperDiscoveryProperties")
public class HazelcastZooKeeperDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = 235372431457637272L;
    @RequiredProperty
    private String url;
    @RequiredProperty
    private String group;
    @RequiredProperty
    private String path = "/discovery/hazelcast";

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getGroup() {
        return this.group;
    }

    @Generated
    public String getPath() {
        return this.path;
    }

    @Generated
    public HazelcastZooKeeperDiscoveryProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public HazelcastZooKeeperDiscoveryProperties setGroup(String group) {
        this.group = group;
        return this;
    }

    @Generated
    public HazelcastZooKeeperDiscoveryProperties setPath(String path) {
        this.path = path;
        return this;
    }
}

