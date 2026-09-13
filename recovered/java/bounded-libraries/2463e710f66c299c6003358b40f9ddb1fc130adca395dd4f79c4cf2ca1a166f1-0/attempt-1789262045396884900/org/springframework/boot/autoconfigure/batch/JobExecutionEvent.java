/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.batch.core.JobExecution
 *  org.springframework.context.ApplicationEvent
 */
package org.springframework.boot.autoconfigure.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.context.ApplicationEvent;

public class JobExecutionEvent
extends ApplicationEvent {
    private final JobExecution execution;

    public JobExecutionEvent(JobExecution execution) {
        super((Object)execution);
        this.execution = execution;
    }

    public JobExecution getJobExecution() {
        return this.execution;
    }
}

