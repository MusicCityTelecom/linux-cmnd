/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.redis;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-redis-authentication")
@JsonFilter(value="RedisPrincipalAttributesProperties")
public class RedisPrincipalAttributesProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -2373755681488251678L;
    private int order;
    private String id;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public RedisPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public RedisPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }
}

