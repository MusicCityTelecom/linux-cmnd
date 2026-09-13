/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.cognito;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-aws-cognito-authentication")
public class AmazonCognitoAuthenticationProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = -4748558614314096213L;
    private String name;
    private int order = Integer.MAX_VALUE;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @RequiredProperty
    private String clientId;
    @RequiredProperty
    private String userPoolId;
    private Map<String, String> mappedAttributes = new LinkedHashMap<String, String>();
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
    public String getUserPoolId() {
        return this.userPoolId;
    }

    @Generated
    public Map<String, String> getMappedAttributes() {
        return this.mappedAttributes;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setUserPoolId(String userPoolId) {
        this.userPoolId = userPoolId;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setMappedAttributes(Map<String, String> mappedAttributes) {
        this.mappedAttributes = mappedAttributes;
        return this;
    }

    @Generated
    public AmazonCognitoAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

