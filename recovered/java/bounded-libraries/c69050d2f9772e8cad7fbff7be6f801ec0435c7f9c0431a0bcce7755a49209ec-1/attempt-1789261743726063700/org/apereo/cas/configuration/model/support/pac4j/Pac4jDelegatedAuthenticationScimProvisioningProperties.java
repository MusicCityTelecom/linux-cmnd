/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-scim")
@JsonFilter(value="Pac4jDelegatedAuthenticationScimProvisioningProperties")
public class Pac4jDelegatedAuthenticationScimProvisioningProperties
implements Serializable {
    private static final long serialVersionUID = -1102345678378393382L;
    private boolean enabled;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public Pac4jDelegatedAuthenticationScimProvisioningProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

