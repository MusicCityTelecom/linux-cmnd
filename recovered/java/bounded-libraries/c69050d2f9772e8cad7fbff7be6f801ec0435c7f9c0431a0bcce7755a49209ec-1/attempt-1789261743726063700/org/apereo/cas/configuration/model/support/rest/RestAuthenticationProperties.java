/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.rest;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-rest-authentication")
public class RestAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -6122859176355467060L;
    @RequiredProperty
    private String uri;
    private String charset = "US-ASCII";
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    private String name;
    private Integer order;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public String getUri() {
        return this.uri;
    }

    @Generated
    public String getCharset() {
        return this.charset;
    }

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public RestAuthenticationProperties setUri(String uri) {
        this.uri = uri;
        return this;
    }

    @Generated
    public RestAuthenticationProperties setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    @Generated
    public RestAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public RestAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public RestAuthenticationProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Generated
    public RestAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

