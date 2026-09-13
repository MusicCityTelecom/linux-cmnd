/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.cookie.CookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
public class Pac4jDelegatedAuthenticationCookieProperties
extends CookieProperties {
    private static final long serialVersionUID = -1460460726554772979L;
    private boolean autoConfigureCookiePath = true;
    private boolean enabled;

    public Pac4jDelegatedAuthenticationCookieProperties() {
        this.setName("DelegatedAuthn");
    }

    @Generated
    public boolean isAutoConfigureCookiePath() {
        return this.autoConfigureCookiePath;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCookieProperties setAutoConfigureCookiePath(boolean autoConfigureCookiePath) {
        this.autoConfigureCookiePath = autoConfigureCookiePath;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCookieProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

