/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pac4j.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.oidc.Pac4jAppleOidcClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oidc.Pac4jAzureOidcClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oidc.Pac4jGenericOidcClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oidc.Pac4jGoogleOidcClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oidc.Pac4jKeyCloakOidcClientProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jOidcClientProperties")
public class Pac4jOidcClientProperties
implements Serializable {
    private static final long serialVersionUID = 3359382317533639638L;
    @NestedConfigurationProperty
    private Pac4jAzureOidcClientProperties azure = new Pac4jAzureOidcClientProperties();
    @NestedConfigurationProperty
    private Pac4jGoogleOidcClientProperties google = new Pac4jGoogleOidcClientProperties();
    @NestedConfigurationProperty
    private Pac4jKeyCloakOidcClientProperties keycloak = new Pac4jKeyCloakOidcClientProperties();
    @NestedConfigurationProperty
    private Pac4jAppleOidcClientProperties apple = new Pac4jAppleOidcClientProperties();
    @NestedConfigurationProperty
    private Pac4jGenericOidcClientProperties generic = new Pac4jGenericOidcClientProperties();

    @Generated
    public Pac4jAzureOidcClientProperties getAzure() {
        return this.azure;
    }

    @Generated
    public Pac4jGoogleOidcClientProperties getGoogle() {
        return this.google;
    }

    @Generated
    public Pac4jKeyCloakOidcClientProperties getKeycloak() {
        return this.keycloak;
    }

    @Generated
    public Pac4jAppleOidcClientProperties getApple() {
        return this.apple;
    }

    @Generated
    public Pac4jGenericOidcClientProperties getGeneric() {
        return this.generic;
    }

    @Generated
    public Pac4jOidcClientProperties setAzure(Pac4jAzureOidcClientProperties azure) {
        this.azure = azure;
        return this;
    }

    @Generated
    public Pac4jOidcClientProperties setGoogle(Pac4jGoogleOidcClientProperties google) {
        this.google = google;
        return this;
    }

    @Generated
    public Pac4jOidcClientProperties setKeycloak(Pac4jKeyCloakOidcClientProperties keycloak) {
        this.keycloak = keycloak;
        return this;
    }

    @Generated
    public Pac4jOidcClientProperties setApple(Pac4jAppleOidcClientProperties apple) {
        this.apple = apple;
        return this;
    }

    @Generated
    public Pac4jOidcClientProperties setGeneric(Pac4jGenericOidcClientProperties generic) {
        this.generic = generic;
        return this;
    }
}

