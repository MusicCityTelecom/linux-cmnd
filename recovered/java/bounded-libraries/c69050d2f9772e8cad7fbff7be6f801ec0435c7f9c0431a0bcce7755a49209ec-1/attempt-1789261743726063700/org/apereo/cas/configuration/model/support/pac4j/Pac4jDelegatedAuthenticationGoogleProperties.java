/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jIdentifiableClientProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
public class Pac4jDelegatedAuthenticationGoogleProperties
extends Pac4jIdentifiableClientProperties {
    private static final long serialVersionUID = -5663033494303169583L;
    private String scope;

    public Pac4jDelegatedAuthenticationGoogleProperties() {
        this.setClientName("Google");
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public Pac4jDelegatedAuthenticationGoogleProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }
}

