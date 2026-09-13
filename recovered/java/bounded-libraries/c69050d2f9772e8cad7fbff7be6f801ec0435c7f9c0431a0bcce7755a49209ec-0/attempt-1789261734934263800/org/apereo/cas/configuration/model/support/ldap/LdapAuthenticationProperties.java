/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapAuthenticationProperties;
import org.apereo.cas.configuration.model.support.ldap.LdapPasswordPolicyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ldap")
@JsonFilter(value="LdapAuthenticationProperties")
public class LdapAuthenticationProperties
extends AbstractLdapAuthenticationProperties {
    private static final long serialVersionUID = -5357843463521189892L;
    @NestedConfigurationProperty
    private LdapPasswordPolicyProperties passwordPolicy = new LdapPasswordPolicyProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    private String credentialCriteria;
    private String principalAttributeId;
    private String principalDnAttributeName = "principalLdapDn";
    private List<String> principalAttributeList = new ArrayList<String>(0);
    private boolean allowMultiplePrincipalAttributeValues;
    private List<String> additionalAttributes = new ArrayList<String>(0);
    private boolean allowMissingPrincipalAttributeValue = true;
    private boolean collectDnAttribute;
    private Integer order;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public LdapPasswordPolicyProperties getPasswordPolicy() {
        return this.passwordPolicy;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
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
    public String getPrincipalAttributeId() {
        return this.principalAttributeId;
    }

    @Generated
    public String getPrincipalDnAttributeName() {
        return this.principalDnAttributeName;
    }

    @Generated
    public List<String> getPrincipalAttributeList() {
        return this.principalAttributeList;
    }

    @Generated
    public boolean isAllowMultiplePrincipalAttributeValues() {
        return this.allowMultiplePrincipalAttributeValues;
    }

    @Generated
    public List<String> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    @Generated
    public boolean isAllowMissingPrincipalAttributeValue() {
        return this.allowMissingPrincipalAttributeValue;
    }

    @Generated
    public boolean isCollectDnAttribute() {
        return this.collectDnAttribute;
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
    public LdapAuthenticationProperties setPasswordPolicy(LdapPasswordPolicyProperties passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setCredentialCriteria(String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setPrincipalAttributeId(String principalAttributeId) {
        this.principalAttributeId = principalAttributeId;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setPrincipalDnAttributeName(String principalDnAttributeName) {
        this.principalDnAttributeName = principalDnAttributeName;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setPrincipalAttributeList(List<String> principalAttributeList) {
        this.principalAttributeList = principalAttributeList;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setAllowMultiplePrincipalAttributeValues(boolean allowMultiplePrincipalAttributeValues) {
        this.allowMultiplePrincipalAttributeValues = allowMultiplePrincipalAttributeValues;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setAdditionalAttributes(List<String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setAllowMissingPrincipalAttributeValue(boolean allowMissingPrincipalAttributeValue) {
        this.allowMissingPrincipalAttributeValue = allowMissingPrincipalAttributeValue;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setCollectDnAttribute(boolean collectDnAttribute) {
        this.collectDnAttribute = collectDnAttribute;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Generated
    public LdapAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

