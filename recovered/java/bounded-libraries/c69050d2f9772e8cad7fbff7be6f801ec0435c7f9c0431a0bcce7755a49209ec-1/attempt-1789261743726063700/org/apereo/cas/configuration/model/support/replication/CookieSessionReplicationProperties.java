/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.replication;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-api")
@JsonFilter(value="CookieSessionReplicationProperties")
public class CookieSessionReplicationProperties
extends PinnableCookieProperties {
    public static final String DEFAULT_COOKIE_NAME = "DISSESSION";
    private static final long serialVersionUID = 6165162204295764362L;
    private boolean autoConfigureCookiePath = true;

    @Generated
    public boolean isAutoConfigureCookiePath() {
        return this.autoConfigureCookiePath;
    }

    @Generated
    public CookieSessionReplicationProperties setAutoConfigureCookiePath(boolean autoConfigureCookiePath) {
        this.autoConfigureCookiePath = autoConfigureCookiePath;
        return this;
    }
}

