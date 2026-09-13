/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.passwordless;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.passwordless.PasswordlessAuthenticationAccountsProperties;
import org.apereo.cas.configuration.model.support.passwordless.PasswordlessAuthenticationCoreProperties;
import org.apereo.cas.configuration.model.support.passwordless.PasswordlessAuthenticationTokensProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-passwordless")
@JsonFilter(value="PasswordlessAuthenticationProperties")
public class PasswordlessAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 8726382874579042117L;
    @NestedConfigurationProperty
    private PasswordlessAuthenticationAccountsProperties accounts = new PasswordlessAuthenticationAccountsProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationTokensProperties tokens = new PasswordlessAuthenticationTokensProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationCoreProperties core = new PasswordlessAuthenticationCoreProperties();

    @Generated
    public PasswordlessAuthenticationAccountsProperties getAccounts() {
        return this.accounts;
    }

    @Generated
    public PasswordlessAuthenticationTokensProperties getTokens() {
        return this.tokens;
    }

    @Generated
    public PasswordlessAuthenticationCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public PasswordlessAuthenticationProperties setAccounts(PasswordlessAuthenticationAccountsProperties accounts) {
        this.accounts = accounts;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationProperties setTokens(PasswordlessAuthenticationTokensProperties tokens) {
        this.tokens = tokens;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationProperties setCore(PasswordlessAuthenticationCoreProperties core) {
        this.core = core;
        return this;
    }
}

