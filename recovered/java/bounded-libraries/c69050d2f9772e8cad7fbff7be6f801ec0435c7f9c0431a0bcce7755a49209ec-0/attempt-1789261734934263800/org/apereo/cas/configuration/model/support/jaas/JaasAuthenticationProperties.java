/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.jaas;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="JaasAuthenticationProperties")
public class JaasAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 4643338626978471986L;
    @RequiredProperty
    private String realm;
    private String kerberosRealmSystemProperty;
    private String kerberosKdcSystemProperty;
    private String credentialCriteria;
    private String loginConfigType;
    private String loginConfigurationFile;
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    @NestedConfigurationProperty
    private PasswordPolicyProperties passwordPolicy = new PasswordPolicyProperties();
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PersonDirectoryPrincipalResolverProperties principal = new PersonDirectoryPrincipalResolverProperties();
    private String name;
    private int order = Integer.MAX_VALUE;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public String getRealm() {
        return this.realm;
    }

    @Generated
    public String getKerberosRealmSystemProperty() {
        return this.kerberosRealmSystemProperty;
    }

    @Generated
    public String getKerberosKdcSystemProperty() {
        return this.kerberosKdcSystemProperty;
    }

    @Generated
    public String getCredentialCriteria() {
        return this.credentialCriteria;
    }

    @Generated
    public String getLoginConfigType() {
        return this.loginConfigType;
    }

    @Generated
    public String getLoginConfigurationFile() {
        return this.loginConfigurationFile;
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
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties getPrincipal() {
        return this.principal;
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
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public JaasAuthenticationProperties setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setKerberosRealmSystemProperty(String kerberosRealmSystemProperty) {
        this.kerberosRealmSystemProperty = kerberosRealmSystemProperty;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setKerberosKdcSystemProperty(String kerberosKdcSystemProperty) {
        this.kerberosKdcSystemProperty = kerberosKdcSystemProperty;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setCredentialCriteria(String credentialCriteria) {
        this.credentialCriteria = credentialCriteria;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setLoginConfigType(String loginConfigType) {
        this.loginConfigType = loginConfigType;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setLoginConfigurationFile(String loginConfigurationFile) {
        this.loginConfigurationFile = loginConfigurationFile;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setPasswordPolicy(PasswordPolicyProperties passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setPrincipal(PersonDirectoryPrincipalResolverProperties principal) {
        this.principal = principal;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public JaasAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

