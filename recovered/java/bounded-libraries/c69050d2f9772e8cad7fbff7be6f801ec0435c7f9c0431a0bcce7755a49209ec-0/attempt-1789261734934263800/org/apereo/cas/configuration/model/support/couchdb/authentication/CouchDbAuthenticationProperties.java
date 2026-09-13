/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.couchdb.authentication;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.couchdb.BaseCouchDbProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-couchdb-authentication")
public class CouchDbAuthenticationProperties
extends BaseCouchDbProperties {
    private static final long serialVersionUID = 1830797033934229732L;
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String attributes;
    private String name;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    private int order = Integer.MAX_VALUE;
    @RequiredProperty
    private String usernameAttribute = "username";
    @RequiredProperty
    private String passwordAttribute = "password";

    public CouchDbAuthenticationProperties() {
        this.setDbName("users");
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public String getAttributes() {
        return this.attributes;
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
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public String getPasswordAttribute() {
        return this.passwordAttribute;
    }

    @Generated
    public CouchDbAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public CouchDbAuthenticationProperties setAttributes(String attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public CouchDbAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public CouchDbAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public CouchDbAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public CouchDbAuthenticationProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public CouchDbAuthenticationProperties setPasswordAttribute(String passwordAttribute) {
        this.passwordAttribute = passwordAttribute;
        return this;
    }
}

