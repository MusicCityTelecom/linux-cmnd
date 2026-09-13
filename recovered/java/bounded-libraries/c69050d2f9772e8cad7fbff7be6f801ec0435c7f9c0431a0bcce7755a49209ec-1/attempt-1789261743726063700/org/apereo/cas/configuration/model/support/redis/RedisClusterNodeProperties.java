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
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-redis-core")
@JsonFilter(value="RedisClusterNodeProperties")
public class RedisClusterNodeProperties
implements Serializable {
    private static final long serialVersionUID = 2912983343579258662L;
    @RequiredProperty
    private String host;
    @RequiredProperty
    private int port;
    @RequiredProperty
    private String replicaOf;
    private String id;
    private String name;
    @RequiredProperty
    private String type;

    @Generated
    public String getHost() {
        return this.host;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public String getReplicaOf() {
        return this.replicaOf;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public RedisClusterNodeProperties setHost(String host) {
        this.host = host;
        return this;
    }

    @Generated
    public RedisClusterNodeProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public RedisClusterNodeProperties setReplicaOf(String replicaOf) {
        this.replicaOf = replicaOf;
        return this;
    }

    @Generated
    public RedisClusterNodeProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public RedisClusterNodeProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public RedisClusterNodeProperties setType(String type) {
        this.type = type;
        return this;
    }
}

