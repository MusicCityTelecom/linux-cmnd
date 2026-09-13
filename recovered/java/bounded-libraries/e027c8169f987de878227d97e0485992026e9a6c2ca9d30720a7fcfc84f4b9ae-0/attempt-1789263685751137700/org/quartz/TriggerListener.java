/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

public interface TriggerListener {
    public String getName();

    public void triggerFired(Trigger var1, JobExecutionContext var2);

    public boolean vetoJobExecution(Trigger var1, JobExecutionContext var2);

    public void triggerMisfired(Trigger var1);

    public void triggerComplete(Trigger var1, JobExecutionContext var2, Trigger.CompletedExecutionInstruction var3);
}

