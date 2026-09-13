/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.mfa.yubikey;

import org.apereo.cas.configuration.model.support.redis.BaseRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-yubikey-redis")
public class YubiKeyRedisMultifactorProperties
extends BaseRedisProperties {
    private static final long serialVersionUID = -1261683393319585262L;
}

