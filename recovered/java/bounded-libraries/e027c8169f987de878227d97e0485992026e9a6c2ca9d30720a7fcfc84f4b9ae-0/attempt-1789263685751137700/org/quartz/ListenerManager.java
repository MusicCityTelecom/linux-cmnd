/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.List;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.SchedulerListener;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;

public interface ListenerManager {
    public void addJobListener(JobListener var1);

    public void addJobListener(JobListener var1, Matcher<JobKey> var2);

    public void addJobListener(JobListener var1, Matcher<JobKey> ... var2);

    public void addJobListener(JobListener var1, List<Matcher<JobKey>> var2);

    public boolean addJobListenerMatcher(String var1, Matcher<JobKey> var2);

    public boolean removeJobListenerMatcher(String var1, Matcher<JobKey> var2);

    public boolean setJobListenerMatchers(String var1, List<Matcher<JobKey>> var2);

    public List<Matcher<JobKey>> getJobListenerMatchers(String var1);

    public boolean removeJobListener(String var1);

    public List<JobListener> getJobListeners();

    public JobListener getJobListener(String var1);

    public void addTriggerListener(TriggerListener var1);

    public void addTriggerListener(TriggerListener var1, Matcher<TriggerKey> var2);

    public void addTriggerListener(TriggerListener var1, Matcher<TriggerKey> ... var2);

    public void addTriggerListener(TriggerListener var1, List<Matcher<TriggerKey>> var2);

    public boolean addTriggerListenerMatcher(String var1, Matcher<TriggerKey> var2);

    public boolean removeTriggerListenerMatcher(String var1, Matcher<TriggerKey> var2);

    public boolean setTriggerListenerMatchers(String var1, List<Matcher<TriggerKey>> var2);

    public List<Matcher<TriggerKey>> getTriggerListenerMatchers(String var1);

    public boolean removeTriggerListener(String var1);

    public List<TriggerListener> getTriggerListeners();

    public TriggerListener getTriggerListener(String var1);

    public void addSchedulerListener(SchedulerListener var1);

    public boolean removeSchedulerListener(SchedulerListener var1);

    public List<SchedulerListener> getSchedulerListeners();
}

