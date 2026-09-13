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
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.passwordless.account.PasswordlessAuthenticationGroovyAccountsProperties;
import org.apereo.cas.configuration.model.support.passwordless.account.PasswordlessAuthenticationJsonAccountsProperties;
import org.apereo.cas.configuration.model.support.passwordless.account.PasswordlessAuthenticationLdapAccountsProperties;
import org.apereo.cas.configuration.model.support.passwordless.account.PasswordlessAuthenticationMongoDbAccountsProperties;
import org.apereo.cas.configuration.model.support.passwordless.account.PasswordlessAuthenticationRestAccountsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-passwordless")
@JsonFilter(value="PasswordlessAuthenticationAccountsProperties")
public class PasswordlessAuthenticationAccountsProperties
implements Serializable {
    private static final long serialVersionUID = -8424650395669337488L;
    @NestedConfigurationProperty
    private PasswordlessAuthenticationRestAccountsProperties rest = new PasswordlessAuthenticationRestAccountsProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationLdapAccountsProperties ldap = new PasswordlessAuthenticationLdapAccountsProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationGroovyAccountsProperties groovy = new PasswordlessAuthenticationGroovyAccountsProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationJsonAccountsProperties json = new PasswordlessAuthenticationJsonAccountsProperties();
    @NestedConfigurationProperty
    private PasswordlessAuthenticationMongoDbAccountsProperties mongo = new PasswordlessAuthenticationMongoDbAccountsProperties();
    private Map<String, String> simple = new LinkedHashMap<String, String>(2);

    @Generated
    public PasswordlessAuthenticationRestAccountsProperties getRest() {
        return this.rest;
    }

    @Generated
    public PasswordlessAuthenticationLdapAccountsProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public PasswordlessAuthenticationGroovyAccountsProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public PasswordlessAuthenticationJsonAccountsProperties getJson() {
        return this.json;
    }

    @Generated
    public PasswordlessAuthenticationMongoDbAccountsProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public Map<String, String> getSimple() {
        return this.simple;
    }

    @Generated
    public PasswordlessAuthenticationAccountsProperties setRest(PasswordlessAuthenticationRestAccountsProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationAccountsProperties setLdap(PasswordlessAuthenticationLdapAccountsProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationAccountsProperties setGroovy(PasswordlessAuthenticationGroovyAccountsProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationAccountsProperties setJson(PasswordlessAuthenticationJsonAccountsProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationAccountsProperties setMongo(PasswordlessAuthenticationMongoDbAccountsProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationAccountsProperties setSimple(Map<String, String> simple) {
        this.simple = simple;
        return this;
    }
}

