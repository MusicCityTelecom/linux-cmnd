/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pm-webflow")
@JsonFilter(value="PasswordHistoryCoreProperties")
public class PasswordHistoryCoreProperties
implements Serializable {
    private static final long serialVersionUID = 2212199066765183587L;
    private boolean enabled;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public PasswordHistoryCoreProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

