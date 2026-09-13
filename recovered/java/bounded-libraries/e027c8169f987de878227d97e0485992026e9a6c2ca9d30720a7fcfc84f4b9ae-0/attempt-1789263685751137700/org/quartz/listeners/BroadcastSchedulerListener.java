/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.listeners;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class BroadcastSchedulerListener
implements SchedulerListener {
    private List<SchedulerListener> listeners = new LinkedList<SchedulerListener>();

    public BroadcastSchedulerListener() {
    }

    public BroadcastSchedulerListener(List<SchedulerListener> listeners) {
        this();
        this.listeners.addAll(listeners);
    }

    public void addListener(SchedulerListener listener) {
        this.listeners.add(listener);
    }

    public boolean removeListener(SchedulerListener listener) {
        return this.listeners.remove(listener);
    }

    public List<SchedulerListener> getListeners() {
        return Collections.unmodifiableList(this.listeners);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        for (SchedulerListener l : this.listeners) {
            l.jobAdded(jobDetail);
        }
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        for (SchedulerListener l : this.listeners) {
            l.jobDeleted(jobKey);
        }
    }

    @Override
    public void jobScheduled(Trigger trigger) {
        for (SchedulerListener l : this.listeners) {
            l.jobScheduled(trigger);
        }
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        for (SchedulerListener l : this.listeners) {
            l.jobUnscheduled(triggerKey);
        }
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        for (SchedulerListener l : this.listeners) {
            l.triggerFinalized(trigger);
        }
    }

    @Override
    public void triggerPaused(TriggerKey key) {
        for (SchedulerListener l : this.listeners) {
            l.triggerPaused(key);
        }
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        for (SchedulerListener l : this.listeners) {
            l.triggersPaused(triggerGroup);
        }
    }

    @Override
    public void triggerResumed(TriggerKey key) {
        for (SchedulerListener l : this.listeners) {
            l.triggerResumed(key);
        }
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        for (SchedulerListener l : this.listeners) {
            l.triggersResumed(triggerGroup);
        }
    }

    @Override
    public void schedulingDataCleared() {
        for (SchedulerListener l : this.listeners) {
            l.schedulingDataCleared();
        }
    }

    @Override
    public void jobPaused(JobKey key) {
        for (SchedulerListener l : this.listeners) {
            l.jobPaused(key);
        }
    }

    @Override
    public void jobsPaused(String jobGroup) {
        for (SchedulerListener l : this.listeners) {
            l.jobsPaused(jobGroup);
        }
    }

    @Override
    public void jobResumed(JobKey key) {
        for (SchedulerListener l : this.listeners) {
            l.jobResumed(key);
        }
    }

    @Override
    public void jobsResumed(String jobGroup) {
        for (SchedulerListener l : this.listeners) {
            l.jobsResumed(jobGroup);
        }
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        for (SchedulerListener l : this.listeners) {
            l.schedulerError(msg, cause);
        }
    }

    @Override
    public void schedulerStarted() {
        for (SchedulerListener l : this.listeners) {
            l.schedulerStarted();
        }
    }

    @Override
    public void schedulerStarting() {
        for (SchedulerListener l : this.listeners) {
            l.schedulerStarting();
        }
    }

    @Override
    public void schedulerInStandbyMode() {
        for (SchedulerListener l : this.listeners) {
            l.schedulerInStandbyMode();
        }
    }

    @Override
    public void schedulerShutdown() {
        for (SchedulerListener l : this.listeners) {
            l.schedulerShutdown();
        }
    }

    @Override
    public void schedulerShuttingdown() {
        for (SchedulerListener l : this.listeners) {
            l.schedulerShuttingdown();
        }
    }
}

