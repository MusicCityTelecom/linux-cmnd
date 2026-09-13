/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.slo;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class SingleLogOutProperties
implements Serializable {
    private static final long serialVersionUID = 3676710533477055700L;
    private boolean asynchronous = true;
    private boolean disabled;

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public boolean isDisabled() {
        return this.disabled;
    }

    @Generated
    public SingleLogOutProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }

    @Generated
    public SingleLogOutProperties setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }
}

