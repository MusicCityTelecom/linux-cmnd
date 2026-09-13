/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-redis")
@JsonFilter(value="RedisAcceptableUsagePolicyProperties")
public class RedisAcceptableUsagePolicyProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -2147683393318585262L;
}

