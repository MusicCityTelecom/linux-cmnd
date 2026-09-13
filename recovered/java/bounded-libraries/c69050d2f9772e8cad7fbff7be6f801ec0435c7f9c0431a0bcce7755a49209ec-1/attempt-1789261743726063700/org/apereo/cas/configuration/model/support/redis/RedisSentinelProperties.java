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
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-redis-core")
@JsonFilter(value="RedisSentinelProperties")
public class RedisSentinelProperties
implements Serializable {
    private static final long serialVersionUID = 5434823157764550831L;
    @RequiredProperty
    private String master;
    private List<String> node = new ArrayList<String>(0);

    @Generated
    public String getMaster() {
        return this.master;
    }

    @Generated
    public List<String> getNode() {
        return this.node;
    }

    @Generated
    public RedisSentinelProperties setMaster(String master) {
        this.master = master;
        return this;
    }

    @Generated
    public RedisSentinelProperties setNode(List<String> node) {
        this.node = node;
        return this;
    }
}

