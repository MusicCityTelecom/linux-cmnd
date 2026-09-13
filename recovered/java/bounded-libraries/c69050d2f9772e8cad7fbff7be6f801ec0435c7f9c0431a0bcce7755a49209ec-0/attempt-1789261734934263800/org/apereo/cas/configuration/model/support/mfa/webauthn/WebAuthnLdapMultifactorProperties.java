/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn-ldap")
@JsonFilter(value="WebAuthnLdapMultifactorProperties")
public class WebAuthnLdapMultifactorProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -1161683393319585262L;
    @RequiredProperty
    private String accountAttributeName = "casWebAuthnRecord";

    @Generated
    public String getAccountAttributeName() {
        return this.accountAttributeName;
    }

    @Generated
    public WebAuthnLdapMultifactorProperties setAccountAttributeName(String accountAttributeName) {
        this.accountAttributeName = accountAttributeName;
        return this;
    }
}

