/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.generic;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication")
@JsonFilter(value="AcceptAuthenticationProperties")
public class AcceptAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 2448007503183227617L;
    private boolean enabled = true;
    private String users = "";
    private int order = Integer.MAX_VALUE;
    private String name;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    private String credentialCriteria;
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @NestedConfigurationProperty
    private PasswordPolicyProperties passwordPolicy = new PasswordPolicyProperties();
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getUsers() {
        return this.users;
    }

    @Generated
    public int getOrder() {
        return this.order;
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
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public PasswordPolicyProperties getPasswordPolicy() {
        return this.passwordPolicy;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public AcceptAuthenticationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setUsers(String users) {
        this.users = users;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setCredentialCriteria(String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setPasswordPolicy(PasswordPolicyProperties passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    @Generated
    public AcceptAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

