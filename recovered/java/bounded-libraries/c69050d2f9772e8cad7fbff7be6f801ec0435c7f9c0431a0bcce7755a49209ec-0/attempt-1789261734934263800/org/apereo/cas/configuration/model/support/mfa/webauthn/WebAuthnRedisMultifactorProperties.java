/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn-redis")
@JsonFilter(value="WebAuthnRedisMultifactorProperties")
public class WebAuthnRedisMultifactorProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -2261683393319585262L;
}

