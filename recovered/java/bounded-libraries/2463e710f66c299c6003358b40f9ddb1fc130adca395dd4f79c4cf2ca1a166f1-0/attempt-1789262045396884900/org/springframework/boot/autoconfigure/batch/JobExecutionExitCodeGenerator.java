/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.batch.core.JobExecution
 *  org.springframework.boot.ExitCodeGenerator
 *  org.springframework.context.ApplicationListener
 */
package org.springframework.boot.autoconfigure.batch;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.context.ApplicationListener;

public class JobExecutionExitCodeGenerator
implements ApplicationListener<JobExecutionEvent>,
ExitCodeGenerator {
    private final List<JobExecution> executions = new CopyOnWriteArrayList<JobExecution>();

    public void onApplicationEvent(JobExecutionEvent event) {
        this.executions.add(event.getJobExecution());
    }

    public int getExitCode() {
        for (JobExecution execution : this.executions) {
            if (execution.getStatus().ordinal() <= 0) continue;
            return execution.getStatus().ordinal();
        }
        return 0;
    }
}

