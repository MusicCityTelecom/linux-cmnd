/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.generic;

import java.util.HashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-shiro-authentication")
@Deprecated(since="6.6.0")
public class ShiroAuthenticationProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 8997401036330472417L;
    private Set<String> requiredRoles = new HashSet<String>(0);
    private Set<String> requiredPermissions = new HashSet<String>(0);
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String name;

    @Generated
    public Set<String> getRequiredRoles() {
        return this.requiredRoles;
    }

    @Generated
    public Set<String> getRequiredPermissions() {
        return this.requiredPermissions;
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
    public ShiroAuthenticationProperties setRequiredRoles(Set<String> requiredRoles) {
        this.requiredRoles = requiredRoles;
        return this;
    }

    @Generated
    public ShiroAuthenticationProperties setRequiredPermissions(Set<String> requiredPermissions) {
        this.requiredPermissions = requiredPermissions;
        return this;
    }

    @Generated
    public ShiroAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public ShiroAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public ShiroAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public ShiroAuthenticationProperties() {
    }
}

