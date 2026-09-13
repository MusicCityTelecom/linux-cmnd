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
public class Pac4jDelegatedAuthenticationTwitterProperties
extends Pac4jIdentifiableClientProperties {
    private static final long serialVersionUID = 6906343970517008092L;
    private boolean includeEmail;

    public Pac4jDelegatedAuthenticationTwitterProperties() {
        this.setClientName("Twitter");
    }

    @Generated
    public boolean isIncludeEmail() {
        return this.includeEmail;
    }

    @Generated
    public Pac4jDelegatedAuthenticationTwitterProperties setIncludeEmail(boolean includeEmail) {
        this.includeEmail = includeEmail;
        return this;
    }
}

