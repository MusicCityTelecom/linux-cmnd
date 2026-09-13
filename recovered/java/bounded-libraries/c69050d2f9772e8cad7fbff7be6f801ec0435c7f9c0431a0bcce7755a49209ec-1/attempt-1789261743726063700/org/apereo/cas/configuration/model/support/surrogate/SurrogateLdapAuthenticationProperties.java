/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-authentication-ldap")
@JsonFilter(value="SurrogateLdapAuthenticationProperties")
public class SurrogateLdapAuthenticationProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -3848837302921751926L;
    @RequiredProperty
    private String surrogateSearchFilter;
    @RequiredProperty
    private String memberAttributeName;
    private String memberAttributeValueRegex;
    private String surrogateValidationFilter;

    @Generated
    public String getSurrogateSearchFilter() {
        return this.surrogateSearchFilter;
    }

    @Generated
    public String getMemberAttributeName() {
        return this.memberAttributeName;
    }

    @Generated
    public String getMemberAttributeValueRegex() {
        return this.memberAttributeValueRegex;
    }

    @Generated
    public String getSurrogateValidationFilter() {
        return this.surrogateValidationFilter;
    }

    @Generated
    public SurrogateLdapAuthenticationProperties setSurrogateSearchFilter(String surrogateSearchFilter) {
        this.surrogateSearchFilter = surrogateSearchFilter;
        return this;
    }

    @Generated
    public SurrogateLdapAuthenticationProperties setMemberAttributeName(String memberAttributeName) {
        this.memberAttributeName = memberAttributeName;
        return this;
    }

    @Generated
    public SurrogateLdapAuthenticationProperties setMemberAttributeValueRegex(String memberAttributeValueRegex) {
        this.memberAttributeValueRegex = memberAttributeValueRegex;
        return this;
    }

    @Generated
    public SurrogateLdapAuthenticationProperties setSurrogateValidationFilter(String surrogateValidationFilter) {
        this.surrogateValidationFilter = surrogateValidationFilter;
        return this;
    }
}

