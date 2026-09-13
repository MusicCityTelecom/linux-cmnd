/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f-redis")
@JsonFilter(value="U2FRedisMultifactorAuthenticationProperties")
public class U2FRedisMultifactorAuthenticationProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -1261683393319585262L;
}

