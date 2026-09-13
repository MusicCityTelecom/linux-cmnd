/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gauth-redis")
@JsonFilter(value="RedisGoogleAuthenticatorMultifactorProperties")
public class RedisGoogleAuthenticatorMultifactorProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -1260683393319585262L;
}

