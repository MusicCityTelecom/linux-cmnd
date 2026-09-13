/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.pac4j.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jBaseClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oidc.BasePac4jOidcClientProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jGenericOidcClientProperties")
public class Pac4jGenericOidcClientProperties
extends BasePac4jOidcClientProperties {
    private static final long serialVersionUID = 3359382317533639638L;

    public Pac4jGenericOidcClientProperties() {
        this.setCallbackUrlType(Pac4jBaseClientProperties.CallbackUrlTypes.PATH_PARAMETER);
    }
}

