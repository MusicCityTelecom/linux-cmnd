/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-authentication", automated=true)
public class GroovyAuthenticationHandlerResolutionProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 8079027843747126083L;
    private int order;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public GroovyAuthenticationHandlerResolutionProperties setOrder(int order) {
        this.order = order;
        return this;
    }
}

