/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.gauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gauth-ldap")
@JsonFilter(value="LdapGoogleAuthenticatorMultifactorProperties")
public class LdapGoogleAuthenticatorMultifactorProperties
extends AbstractLdapSearchProperties
implements CasFeatureModule {
    private static final long serialVersionUID = -100556119517414696L;
    @RequiredProperty
    private String accountAttributeName = "casGAuthRecord";

    @Generated
    public String getAccountAttributeName() {
        return this.accountAttributeName;
    }

    @Generated
    public LdapGoogleAuthenticatorMultifactorProperties setAccountAttributeName(String accountAttributeName) {
        this.accountAttributeName = accountAttributeName;
        return this;
    }
}

