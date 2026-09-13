/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.oidc.OidcClientRegistrationProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcCoreProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcDiscoveryProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcIdTokenProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcLogoutProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcPushedAuthorizationProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcWebFingerProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.OidcJsonWebKeystoreProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcProperties")
public class OidcProperties
implements Serializable {
    private static final long serialVersionUID = 813028615694269276L;
    @NestedConfigurationProperty
    private OidcJsonWebKeystoreProperties jwks = new OidcJsonWebKeystoreProperties();
    @NestedConfigurationProperty
    private OidcCoreProperties core = new OidcCoreProperties();
    @NestedConfigurationProperty
    private OidcIdTokenProperties idToken = new OidcIdTokenProperties();
    @NestedConfigurationProperty
    private OidcWebFingerProperties webfinger = new OidcWebFingerProperties();
    @NestedConfigurationProperty
    private OidcLogoutProperties logout = new OidcLogoutProperties();
    @NestedConfigurationProperty
    private OidcDiscoveryProperties discovery = new OidcDiscoveryProperties();
    @NestedConfigurationProperty
    private OidcPushedAuthorizationProperties par = new OidcPushedAuthorizationProperties();
    @NestedConfigurationProperty
    private OidcClientRegistrationProperties registration = new OidcClientRegistrationProperties();

    @Generated
    public OidcJsonWebKeystoreProperties getJwks() {
        return this.jwks;
    }

    @Generated
    public OidcCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public OidcIdTokenProperties getIdToken() {
        return this.idToken;
    }

    @Generated
    public OidcWebFingerProperties getWebfinger() {
        return this.webfinger;
    }

    @Generated
    public OidcLogoutProperties getLogout() {
        return this.logout;
    }

    @Generated
    public OidcDiscoveryProperties getDiscovery() {
        return this.discovery;
    }

    @Generated
    public OidcPushedAuthorizationProperties getPar() {
        return this.par;
    }

    @Generated
    public OidcClientRegistrationProperties getRegistration() {
        return this.registration;
    }

    @Generated
    public OidcProperties setJwks(OidcJsonWebKeystoreProperties jwks) {
        this.jwks = jwks;
        return this;
    }

    @Generated
    public OidcProperties setCore(OidcCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public OidcProperties setIdToken(OidcIdTokenProperties idToken) {
        this.idToken = idToken;
        return this;
    }

    @Generated
    public OidcProperties setWebfinger(OidcWebFingerProperties webfinger) {
        this.webfinger = webfinger;
        return this;
    }

    @Generated
    public OidcProperties setLogout(OidcLogoutProperties logout) {
        this.logout = logout;
        return this;
    }

    @Generated
    public OidcProperties setDiscovery(OidcDiscoveryProperties discovery) {
        this.discovery = discovery;
        return this;
    }

    @Generated
    public OidcProperties setPar(OidcPushedAuthorizationProperties par) {
        this.par = par;
        return this;
    }

    @Generated
    public OidcProperties setRegistration(OidcClientRegistrationProperties registration) {
        this.registration = registration;
        return this;
    }
}

