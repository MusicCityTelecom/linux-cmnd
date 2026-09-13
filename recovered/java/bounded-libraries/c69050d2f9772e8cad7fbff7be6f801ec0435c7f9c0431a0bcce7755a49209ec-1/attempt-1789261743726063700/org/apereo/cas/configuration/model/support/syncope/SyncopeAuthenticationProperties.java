/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.syncope;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.syncope.AbstractSyncopeProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-syncope-authentication")
@JsonFilter(value="SyncopeAuthenticationProperties")
public class SyncopeAuthenticationProperties
extends AbstractSyncopeProperties
implements CasFeatureModule {
    private static final long serialVersionUID = -2446926316502297496L;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;
    private String name;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    private String credentialCriteria;
    private int order = Integer.MAX_VALUE;
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public String getCredentialCriteria() {
        return this.credentialCriteria;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public SyncopeAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }

    @Generated
    public SyncopeAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public SyncopeAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public SyncopeAuthenticationProperties setCredentialCriteria(String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
        return this;
    }

    @Generated
    public SyncopeAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public SyncopeAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }
}

