/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication.passwordsync;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
public class LdapPasswordSynchronizationProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -2521286056194686825L;
    private boolean enabled;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public LdapPasswordSynchronizationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

