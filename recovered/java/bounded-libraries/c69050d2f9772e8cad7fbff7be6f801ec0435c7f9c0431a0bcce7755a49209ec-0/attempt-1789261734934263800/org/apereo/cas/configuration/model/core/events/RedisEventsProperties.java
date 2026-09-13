/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-events-redis")
@JsonFilter(value="RedisEventsProperties")
public class RedisEventsProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = 9027696961101634818L;
}

