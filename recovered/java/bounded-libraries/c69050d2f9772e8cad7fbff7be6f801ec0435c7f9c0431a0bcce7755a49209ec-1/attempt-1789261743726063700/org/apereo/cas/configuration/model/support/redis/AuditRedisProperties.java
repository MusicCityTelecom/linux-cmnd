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

@RequiresModule(name="cas-server-support-audit-redis")
@JsonFilter(value="AuditRedisProperties")
public class AuditRedisProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -8112996050439638782L;
    private boolean asynchronous = true;

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public AuditRedisProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }
}

