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
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap-core")
@JsonFilter(value="AbstractLdapAuthenticationProperties")
public abstract class AbstractLdapAuthenticationProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 3849857270054289852L;
    @RequiredProperty
    private AuthenticationTypes type = AuthenticationTypes.AUTHENTICATED;
    private String principalAttributePassword;
    private String dnFormat;
    private boolean enhanceWithEntryResolver = true;
    private String derefAliases;
    private String resolveFromAttribute;

    @Generated
    public AuthenticationTypes getType() {
        return this.type;
    }

    @Generated
    public String getPrincipalAttributePassword() {
        return this.principalAttributePassword;
    }

    @Generated
    public String getDnFormat() {
        return this.dnFormat;
    }

    @Generated
    public boolean isEnhanceWithEntryResolver() {
        return this.enhanceWithEntryResolver;
    }

    @Generated
    public String getDerefAliases() {
        return this.derefAliases;
    }

    @Generated
    public String getResolveFromAttribute() {
        return this.resolveFromAttribute;
    }

    @Generated
    public AbstractLdapAuthenticationProperties setType(AuthenticationTypes type) {
        this.type = type;
        return this;
    }

    @Generated
    public AbstractLdapAuthenticationProperties setPrincipalAttributePassword(String principalAttributePassword) {
        this.principalAttributePassword = principalAttributePassword;
        return this;
    }

    @Generated
    public AbstractLdapAuthenticationProperties setDnFormat(String dnFormat) {
        this.dnFormat = dnFormat;
        return this;
    }

    @Generated
    public AbstractLdapAuthenticationProperties setEnhanceWithEntryResolver(boolean enhanceWithEntryResolver) {
        this.enhanceWithEntryResolver = enhanceWithEntryResolver;
        return this;
    }

    @Generated
    public AbstractLdapAuthenticationProperties setDerefAliases(String derefAliases) {
        this.derefAliases = derefAliases;
        return this;
    }

    @Generated
    public AbstractLdapAuthenticationProperties setResolveFromAttribute(String resolveFromAttribute) {
        this.resolveFromAttribute = resolveFromAttribute;
        return this;
    }

    public static enum AuthenticationTypes {
        AD,
        AUTHENTICATED,
        DIRECT,
        ANONYMOUS;

    }
}

