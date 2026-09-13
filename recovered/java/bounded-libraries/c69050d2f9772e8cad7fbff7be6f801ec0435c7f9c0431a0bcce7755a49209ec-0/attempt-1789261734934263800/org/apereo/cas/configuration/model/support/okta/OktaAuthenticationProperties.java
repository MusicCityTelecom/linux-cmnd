/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.okta;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.okta.BaseOktaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-okta-authentication")
@JsonFilter(value="OktaAuthenticationProperties")
public class OktaAuthenticationProperties
extends BaseOktaProperties {
    private static final long serialVersionUID = -13245764438426360L;
    private String name;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String credentialCriteria;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public String getName() {
        return this.name;
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
    public String getCredentialCriteria() {
        return this.credentialCriteria;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public OktaAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public OktaAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public OktaAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public OktaAuthenticationProperties setCredentialCriteria(String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
        return this;
    }

    @Generated
    public OktaAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

