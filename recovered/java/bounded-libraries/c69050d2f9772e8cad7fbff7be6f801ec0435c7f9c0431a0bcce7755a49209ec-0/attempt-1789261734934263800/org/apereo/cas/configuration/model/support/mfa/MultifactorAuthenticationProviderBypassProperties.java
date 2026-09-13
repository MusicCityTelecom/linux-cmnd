/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.GroovyMultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.configuration.model.support.mfa.RestfulMultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="MultifactorAuthenticationProviderBypassProperties")
public class MultifactorAuthenticationProviderBypassProperties
implements Serializable {
    private static final long serialVersionUID = -9181362378365850397L;
    private String principalAttributeName;
    private String principalAttributeValue;
    private String authenticationAttributeName;
    private String authenticationAttributeValue;
    private String authenticationHandlerName;
    private String authenticationMethodName;
    private String credentialClassType;
    private String httpRequestRemoteAddress;
    private String httpRequestHeaders;
    @NestedConfigurationProperty
    private GroovyMultifactorAuthenticationProviderBypassProperties groovy = new GroovyMultifactorAuthenticationProviderBypassProperties();
    @NestedConfigurationProperty
    private RestfulMultifactorAuthenticationProviderBypassProperties rest = new RestfulMultifactorAuthenticationProviderBypassProperties();

    @Generated
    public String getPrincipalAttributeName() {
        return this.principalAttributeName;
    }

    @Generated
    public String getPrincipalAttributeValue() {
        return this.principalAttributeValue;
    }

    @Generated
    public String getAuthenticationAttributeName() {
        return this.authenticationAttributeName;
    }

    @Generated
    public String getAuthenticationAttributeValue() {
        return this.authenticationAttributeValue;
    }

    @Generated
    public String getAuthenticationHandlerName() {
        return this.authenticationHandlerName;
    }

    @Generated
    public String getAuthenticationMethodName() {
        return this.authenticationMethodName;
    }

    @Generated
    public String getCredentialClassType() {
        return this.credentialClassType;
    }

    @Generated
    public String getHttpRequestRemoteAddress() {
        return this.httpRequestRemoteAddress;
    }

    @Generated
    public String getHttpRequestHeaders() {
        return this.httpRequestHeaders;
    }

    @Generated
    public GroovyMultifactorAuthenticationProviderBypassProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public RestfulMultifactorAuthenticationProviderBypassProperties getRest() {
        return this.rest;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setPrincipalAttributeName(String principalAttributeName) {
        this.principalAttributeName = principalAttributeName;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setPrincipalAttributeValue(String principalAttributeValue) {
        this.principalAttributeValue = principalAttributeValue;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setAuthenticationAttributeName(String authenticationAttributeName) {
        this.authenticationAttributeName = authenticationAttributeName;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setAuthenticationAttributeValue(String authenticationAttributeValue) {
        this.authenticationAttributeValue = authenticationAttributeValue;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setAuthenticationHandlerName(String authenticationHandlerName) {
        this.authenticationHandlerName = authenticationHandlerName;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setAuthenticationMethodName(String authenticationMethodName) {
        this.authenticationMethodName = authenticationMethodName;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setCredentialClassType(String credentialClassType) {
        this.credentialClassType = credentialClassType;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setHttpRequestRemoteAddress(String httpRequestRemoteAddress) {
        this.httpRequestRemoteAddress = httpRequestRemoteAddress;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setHttpRequestHeaders(String httpRequestHeaders) {
        this.httpRequestHeaders = httpRequestHeaders;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setGroovy(GroovyMultifactorAuthenticationProviderBypassProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProviderBypassProperties setRest(RestfulMultifactorAuthenticationProviderBypassProperties rest) {
        this.rest = rest;
        return this;
    }
}

