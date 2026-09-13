/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-throttle-ldap")
@JsonFilter(value="LdapThrottleProperties")
public class LdapThrottleProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 7519847618333749780L;
    @RequiredProperty
    private String accountLockedAttribute = "pwdLockout";

    @Generated
    public String getAccountLockedAttribute() {
        return this.accountLockedAttribute;
    }

    @Generated
    public LdapThrottleProperties setAccountLockedAttribute(String accountLockedAttribute) {
        this.accountLockedAttribute = accountLockedAttribute;
        return this;
    }
}

