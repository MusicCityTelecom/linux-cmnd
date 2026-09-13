/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication.passwordsync;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.passwordsync.LdapPasswordSynchronizationProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class PasswordSynchronizationProperties
implements Serializable {
    private static final long serialVersionUID = -3878237508646993100L;
    private boolean enabled = true;
    private List<LdapPasswordSynchronizationProperties> ldap = new ArrayList<LdapPasswordSynchronizationProperties>(0);

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public List<LdapPasswordSynchronizationProperties> getLdap() {
        return this.ldap;
    }

    @Generated
    public PasswordSynchronizationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public PasswordSynchronizationProperties setLdap(List<LdapPasswordSynchronizationProperties> ldap) {
        this.ldap = ldap;
        return this;
    }
}

