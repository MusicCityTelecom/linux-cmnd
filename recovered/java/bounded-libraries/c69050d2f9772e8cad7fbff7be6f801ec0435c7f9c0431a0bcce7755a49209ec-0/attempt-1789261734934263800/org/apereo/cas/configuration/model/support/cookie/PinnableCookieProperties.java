/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.cookie;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.cookie.CookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-cookie", automated=true)
@JsonFilter(value="PinnableCookieProperties")
public class PinnableCookieProperties
extends CookieProperties {
    private static final long serialVersionUID = -7643955577897341936L;
    private boolean pinToSession = true;
    private String allowedIpAddressesPattern;

    @Generated
    public boolean isPinToSession() {
        return this.pinToSession;
    }

    @Generated
    public String getAllowedIpAddressesPattern() {
        return this.allowedIpAddressesPattern;
    }

    @Generated
    public PinnableCookieProperties setPinToSession(boolean pinToSession) {
        this.pinToSession = pinToSession;
        return this;
    }

    @Generated
    public PinnableCookieProperties setAllowedIpAddressesPattern(String allowedIpAddressesPattern) {
        this.allowedIpAddressesPattern = allowedIpAddressesPattern;
        return this;
    }
}

