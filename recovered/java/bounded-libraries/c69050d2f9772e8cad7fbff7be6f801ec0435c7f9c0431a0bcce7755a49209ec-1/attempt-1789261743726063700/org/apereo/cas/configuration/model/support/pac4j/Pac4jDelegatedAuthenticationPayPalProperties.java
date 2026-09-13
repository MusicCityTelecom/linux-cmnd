/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.configuration.model.support.pac4j;

import org.apereo.cas.configuration.model.support.pac4j.Pac4jIdentifiableClientProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
public class Pac4jDelegatedAuthenticationPayPalProperties
extends Pac4jIdentifiableClientProperties {
    private static final long serialVersionUID = -5663033494303169583L;

    public Pac4jDelegatedAuthenticationPayPalProperties() {
        this.setClientName("Paypal");
    }
}

