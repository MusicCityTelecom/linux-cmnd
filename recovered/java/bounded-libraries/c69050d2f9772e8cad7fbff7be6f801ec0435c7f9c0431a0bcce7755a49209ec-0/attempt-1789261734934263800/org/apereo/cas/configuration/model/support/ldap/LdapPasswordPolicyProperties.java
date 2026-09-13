/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
@JsonFilter(value="LdapPasswordPolicyProperties")
public class LdapPasswordPolicyProperties
extends PasswordPolicyProperties {
    private static final long serialVersionUID = -1878237508646993100L;
    private String customPolicyClass;
    private AbstractLdapProperties.LdapType type = AbstractLdapProperties.LdapType.GENERIC;
    private int passwordExpirationNumberOfDays = 180;

    @Generated
    public String getCustomPolicyClass() {
        return this.customPolicyClass;
    }

    @Generated
    public AbstractLdapProperties.LdapType getType() {
        return this.type;
    }

    @Generated
    public int getPasswordExpirationNumberOfDays() {
        return this.passwordExpirationNumberOfDays;
    }

    @Generated
    public LdapPasswordPolicyProperties setCustomPolicyClass(String customPolicyClass) {
        this.customPolicyClass = customPolicyClass;
        return this;
    }

    @Generated
    public LdapPasswordPolicyProperties setType(AbstractLdapProperties.LdapType type) {
        this.type = type;
        return this;
    }

    @Generated
    public LdapPasswordPolicyProperties setPasswordExpirationNumberOfDays(int passwordExpirationNumberOfDays) {
        this.passwordExpirationNumberOfDays = passwordExpirationNumberOfDays;
        return this;
    }
}

