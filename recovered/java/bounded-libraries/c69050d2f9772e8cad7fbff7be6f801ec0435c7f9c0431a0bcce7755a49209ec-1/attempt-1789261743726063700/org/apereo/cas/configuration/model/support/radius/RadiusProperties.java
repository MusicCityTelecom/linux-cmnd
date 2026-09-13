/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.radius;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.radius.RadiusClientProperties;
import org.apereo.cas.configuration.model.support.radius.RadiusServerProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-radius")
@JsonFilter(value="RadiusProperties")
public class RadiusProperties
implements Serializable {
    private static final long serialVersionUID = 5244307919878753714L;
    private boolean failoverOnException;
    private boolean failoverOnAuthenticationFailure;
    @NestedConfigurationProperty
    private RadiusServerProperties server = new RadiusServerProperties();
    @NestedConfigurationProperty
    private RadiusClientProperties client = new RadiusClientProperties();
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String name;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public boolean isFailoverOnException() {
        return this.failoverOnException;
    }

    @Generated
    public boolean isFailoverOnAuthenticationFailure() {
        return this.failoverOnAuthenticationFailure;
    }

    @Generated
    public RadiusServerProperties getServer() {
        return this.server;
    }

    @Generated
    public RadiusClientProperties getClient() {
        return this.client;
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
    public String getName() {
        return this.name;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public RadiusProperties setFailoverOnException(boolean failoverOnException) {
        this.failoverOnException = failoverOnException;
        return this;
    }

    @Generated
    public RadiusProperties setFailoverOnAuthenticationFailure(boolean failoverOnAuthenticationFailure) {
        this.failoverOnAuthenticationFailure = failoverOnAuthenticationFailure;
        return this;
    }

    @Generated
    public RadiusProperties setServer(RadiusServerProperties server) {
        this.server = server;
        return this;
    }

    @Generated
    public RadiusProperties setClient(RadiusClientProperties client) {
        this.client = client;
        return this;
    }

    @Generated
    public RadiusProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public RadiusProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public RadiusProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public RadiusProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

