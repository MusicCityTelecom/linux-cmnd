/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.azuread;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-azuread-authentication")
@JsonFilter(value="AzureActiveDirectoryAuthenticationProperties")
public class AzureActiveDirectoryAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -21355975558426360L;
    private String name;
    private int order = Integer.MAX_VALUE;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @RequiredProperty
    private String clientId;
    private String loginUrl = "https://login.microsoftonline.com/common/";
    private String resource = "https://graph.microsoft.com/";
    private String credentialCriteria;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public String getClientId() {
        return this.clientId;
    }

    @Generated
    public String getLoginUrl() {
        return this.loginUrl;
    }

    @Generated
    public String getResource() {
        return this.resource;
    }

    @Generated
    public String getCredentialCriteria() {
        return this.credentialCriteria;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setResource(String resource) {
        this.resource = resource;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setCredentialCriteria(String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
        return this;
    }

    @Generated
    public AzureActiveDirectoryAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

