/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa")
public abstract class BaseDeviceFingerprintComponentProperties
implements Serializable {
    private static final long serialVersionUID = 46126170193036440L;
    private boolean enabled;
    private int order;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public BaseDeviceFingerprintComponentProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public BaseDeviceFingerprintComponentProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    protected BaseDeviceFingerprintComponentProperties() {
    }
}

