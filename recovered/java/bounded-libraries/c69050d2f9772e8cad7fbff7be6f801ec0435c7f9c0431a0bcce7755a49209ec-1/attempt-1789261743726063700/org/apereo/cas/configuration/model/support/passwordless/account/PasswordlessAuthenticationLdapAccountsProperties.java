/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.passwordless.account;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-passwordless-ldap")
@JsonFilter(value="PasswordlessAuthenticationLdapAccountsProperties")
public class PasswordlessAuthenticationLdapAccountsProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -1102345678378393382L;
    private String emailAttribute = "mail";
    private String phoneAttribute = "phoneNumber";
    private String nameAttribute = "cn";

    @Generated
    public String getEmailAttribute() {
        return this.emailAttribute;
    }

    @Generated
    public String getPhoneAttribute() {
        return this.phoneAttribute;
    }

    @Generated
    public String getNameAttribute() {
        return this.nameAttribute;
    }

    @Generated
    public PasswordlessAuthenticationLdapAccountsProperties setEmailAttribute(String emailAttribute) {
        this.emailAttribute = emailAttribute;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationLdapAccountsProperties setPhoneAttribute(String phoneAttribute) {
        this.phoneAttribute = phoneAttribute;
        return this;
    }

    @Generated
    public PasswordlessAuthenticationLdapAccountsProperties setNameAttribute(String nameAttribute) {
        this.nameAttribute = nameAttribute;
        return this;
    }
}

