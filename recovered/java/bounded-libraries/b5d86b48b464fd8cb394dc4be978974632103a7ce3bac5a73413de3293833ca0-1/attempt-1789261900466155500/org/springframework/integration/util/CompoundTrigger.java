/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.TriggerContext
 *  org.springframework.util.Assert
 */
package org.springframework.integration.util;

import java.util.Date;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.util.Assert;

public class CompoundTrigger
implements Trigger {
    private volatile Trigger primary;
    private volatile Trigger override;

    public CompoundTrigger(Trigger primary) {
        this.setPrimary(primary);
    }

    public final void setPrimary(Trigger primary) {
        Assert.notNull((Object)primary, (String)"'primary' cannot be null");
        this.primary = primary;
    }

    public void setOverride(Trigger override) {
        this.override = override;
    }

    public Date nextExecutionTime(TriggerContext triggerContext) {
        if (this.override != null) {
            return this.override.nextExecutionTime(triggerContext);
        }
        return this.primary.nextExecutionTime(triggerContext);
    }
}

