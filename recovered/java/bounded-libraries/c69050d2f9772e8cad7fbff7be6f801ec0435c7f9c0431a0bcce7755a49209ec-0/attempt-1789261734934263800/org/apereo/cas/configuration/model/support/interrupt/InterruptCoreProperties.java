/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.interrupt;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-interrupt-webflow")
@JsonFilter(value="InterruptCoreProperties")
public class InterruptCoreProperties
implements Serializable {
    private static final long serialVersionUID = 4263941933003310968L;
    private boolean forceExecution;
    private InterruptTriggerModes triggerMode = InterruptTriggerModes.AFTER_AUTHENTICATION;

    @Generated
    public boolean isForceExecution() {
        return this.forceExecution;
    }

    @Generated
    public InterruptTriggerModes getTriggerMode() {
        return this.triggerMode;
    }

    @Generated
    public InterruptCoreProperties setForceExecution(boolean forceExecution) {
        this.forceExecution = forceExecution;
        return this;
    }

    @Generated
    public InterruptCoreProperties setTriggerMode(InterruptTriggerModes triggerMode) {
        this.triggerMode = triggerMode;
        return this;
    }

    public static enum InterruptTriggerModes {
        AFTER_AUTHENTICATION,
        AFTER_SSO;

    }
}

