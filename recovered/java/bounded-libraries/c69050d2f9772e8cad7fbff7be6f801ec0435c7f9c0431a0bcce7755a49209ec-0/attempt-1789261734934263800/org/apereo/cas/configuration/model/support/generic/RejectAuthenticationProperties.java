/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.generic;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-generic")
public class RejectAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -3228601837221178711L;
    private String users;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String name;

    @Generated
    public String getUsers() {
        return this.users;
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
    public RejectAuthenticationProperties setUsers(String users) {
        this.users = users;
        return this;
    }

    @Generated
    public RejectAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public RejectAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public RejectAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }
}

