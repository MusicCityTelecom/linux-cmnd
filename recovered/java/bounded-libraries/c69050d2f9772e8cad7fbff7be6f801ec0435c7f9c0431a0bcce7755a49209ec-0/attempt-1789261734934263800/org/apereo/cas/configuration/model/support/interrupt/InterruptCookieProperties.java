/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.interrupt;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-interrupt-webflow", automated=true)
@JsonFilter(value="InterruptCookieProperties")
public class InterruptCookieProperties
extends PinnableCookieProperties {
    private static final long serialVersionUID = -266090748600049578L;
    private boolean autoConfigureCookiePath = true;

    public InterruptCookieProperties() {
        super.setName("CASINTERRUPT");
    }

    @Generated
    public boolean isAutoConfigureCookiePath() {
        return this.autoConfigureCookiePath;
    }

    @Generated
    public InterruptCookieProperties setAutoConfigureCookiePath(boolean autoConfigureCookiePath) {
        this.autoConfigureCookiePath = autoConfigureCookiePath;
        return this;
    }
}

