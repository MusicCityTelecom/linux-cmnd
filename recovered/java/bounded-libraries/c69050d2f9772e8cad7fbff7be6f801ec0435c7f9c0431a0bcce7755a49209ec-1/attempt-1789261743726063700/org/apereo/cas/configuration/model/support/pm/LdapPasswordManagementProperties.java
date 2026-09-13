/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapProperties;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pm-ldap")
@JsonFilter(value="LdapPasswordManagementProperties")
public class LdapPasswordManagementProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -2610186056194686825L;
    private Map<String, String> securityQuestionsAttributes = new LinkedHashMap<String, String>(0);
    @RequiredProperty
    private String accountLockedAttribute = "pwdLockout";
    private String[] accountUnlockedAttributeValues = new String[]{"FALSE"};
    @RequiredProperty
    private AbstractLdapProperties.LdapType type = AbstractLdapProperties.LdapType.AD;
    @RequiredProperty
    private String usernameAttribute = "uid";

    @Generated
    public Map<String, String> getSecurityQuestionsAttributes() {
        return this.securityQuestionsAttributes;
    }

    @Generated
    public String getAccountLockedAttribute() {
        return this.accountLockedAttribute;
    }

    @Generated
    public String[] getAccountUnlockedAttributeValues() {
        return this.accountUnlockedAttributeValues;
    }

    @Generated
    public AbstractLdapProperties.LdapType getType() {
        return this.type;
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public LdapPasswordManagementProperties setSecurityQuestionsAttributes(Map<String, String> securityQuestionsAttributes) {
        this.securityQuestionsAttributes = securityQuestionsAttributes;
        return this;
    }

    @Generated
    public LdapPasswordManagementProperties setAccountLockedAttribute(String accountLockedAttribute) {
        this.accountLockedAttribute = accountLockedAttribute;
        return this;
    }

    @Generated
    public LdapPasswordManagementProperties setAccountUnlockedAttributeValues(String[] accountUnlockedAttributeValues) {
        this.accountUnlockedAttributeValues = accountUnlockedAttributeValues;
        return this;
    }

    @Generated
    public LdapPasswordManagementProperties setType(AbstractLdapProperties.LdapType type) {
        this.type = type;
        return this;
    }

    @Generated
    public LdapPasswordManagementProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }
}

