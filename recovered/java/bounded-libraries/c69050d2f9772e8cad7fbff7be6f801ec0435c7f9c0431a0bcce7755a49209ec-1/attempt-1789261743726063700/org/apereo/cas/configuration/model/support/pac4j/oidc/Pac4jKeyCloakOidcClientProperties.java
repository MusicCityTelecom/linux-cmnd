/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.oidc.BasePac4jOidcClientProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jKeyCloakOidcClientProperties")
public class Pac4jKeyCloakOidcClientProperties
extends BasePac4jOidcClientProperties {
    private static final long serialVersionUID = 3209382317533639638L;
    @RequiredProperty
    private String realm;
    @RequiredProperty
    private String baseUri;

    @Generated
    public String getRealm() {
        return this.realm;
    }

    @Generated
    public String getBaseUri() {
        return this.baseUri;
    }

    @Generated
    public Pac4jKeyCloakOidcClientProperties setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    @Generated
    public Pac4jKeyCloakOidcClientProperties setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }
}

