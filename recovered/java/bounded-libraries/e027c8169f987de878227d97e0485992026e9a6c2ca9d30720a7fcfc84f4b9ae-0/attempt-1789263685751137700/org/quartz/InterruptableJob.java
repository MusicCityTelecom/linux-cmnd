/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.Job;
import org.quartz.UnableToInterruptJobException;

public interface InterruptableJob
extends Job {
    public void interrupt() throws UnableToInterruptJobException;
}

