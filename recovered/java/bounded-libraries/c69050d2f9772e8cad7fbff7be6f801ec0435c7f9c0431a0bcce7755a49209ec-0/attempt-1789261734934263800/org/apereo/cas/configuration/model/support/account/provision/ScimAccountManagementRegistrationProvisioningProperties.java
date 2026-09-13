/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.account.provision;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-account-mgmt")
@JsonFilter(value="ScimAccountManagementRegistrationProvisioningProperties")
public class ScimAccountManagementRegistrationProvisioningProperties
implements Serializable {
    private static final long serialVersionUID = 6833936824474022021L;
    private boolean enabled;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public ScimAccountManagementRegistrationProvisioningProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

