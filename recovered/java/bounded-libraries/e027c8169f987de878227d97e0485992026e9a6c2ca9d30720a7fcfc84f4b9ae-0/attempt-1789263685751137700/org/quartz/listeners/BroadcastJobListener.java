/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.listeners;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class BroadcastJobListener
implements JobListener {
    private String name;
    private List<JobListener> listeners;

    public BroadcastJobListener(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Listener name cannot be null!");
        }
        this.name = name;
        this.listeners = new LinkedList<JobListener>();
    }

    public BroadcastJobListener(String name, List<JobListener> listeners) {
        this(name);
        this.listeners.addAll(listeners);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addListener(JobListener listener) {
        this.listeners.add(listener);
    }

    public boolean removeListener(JobListener listener) {
        return this.listeners.remove(listener);
    }

    public boolean removeListener(String listenerName) {
        Iterator<JobListener> itr = this.listeners.iterator();
        while (itr.hasNext()) {
            JobListener jl = itr.next();
            if (!jl.getName().equals(listenerName)) continue;
            itr.remove();
            return true;
        }
        return false;
    }

    public List<JobListener> getListeners() {
        return Collections.unmodifiableList(this.listeners);
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        for (JobListener jl : this.listeners) {
            jl.jobToBeExecuted(context);
        }
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        for (JobListener jl : this.listeners) {
            jl.jobExecutionVetoed(context);
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        for (JobListener jl : this.listeners) {
            jl.jobWasExecuted(context, jobException);
        }
    }
}

