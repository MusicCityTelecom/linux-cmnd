/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mongo;

import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-mongo")
public class MongoDbAuthenticationProperties
extends SingleCollectionMongoDbProperties {
    private static final long serialVersionUID = -7304734732383722585L;
    private String attributes = "";
    private String usernameAttribute = "username";
    private String passwordAttribute = "password";
    private String principalIdAttribute;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String name;
    private int order = Integer.MAX_VALUE;

    public MongoDbAuthenticationProperties() {
        this.setCollection("users");
    }

    @Generated
    public String getAttributes() {
        return this.attributes;
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
    public String getPrincipalIdAttribute() {
        return this.principalIdAttribute;
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
    public int getOrder() {
        return this.order;
    }

    @Generated
    public MongoDbAuthenticationProperties setAttributes(String attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setPasswordAttribute(String passwordAttribute) {
        this.passwordAttribute = passwordAttribute;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setPrincipalIdAttribute(String principalIdAttribute) {
        this.principalIdAttribute = principalIdAttribute;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public MongoDbAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }
}

