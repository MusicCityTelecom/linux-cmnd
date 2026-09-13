/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.ListenerManager;
import org.quartz.Matcher;
import org.quartz.SchedulerListener;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.EverythingMatcher;

public class ListenerManagerImpl
implements ListenerManager {
    private Map<String, JobListener> globalJobListeners = new LinkedHashMap<String, JobListener>(10);
    private Map<String, TriggerListener> globalTriggerListeners = new LinkedHashMap<String, TriggerListener>(10);
    private Map<String, List<Matcher<JobKey>>> globalJobListenersMatchers = new LinkedHashMap<String, List<Matcher<JobKey>>>(10);
    private Map<String, List<Matcher<TriggerKey>>> globalTriggerListenersMatchers = new LinkedHashMap<String, List<Matcher<TriggerKey>>>(10);
    private ArrayList<SchedulerListener> schedulerListeners = new ArrayList(10);

    @Override
    public void addJobListener(JobListener jobListener, Matcher<JobKey> ... matchers) {
        this.addJobListener(jobListener, Arrays.asList(matchers));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addJobListener(JobListener jobListener, List<Matcher<JobKey>> matchers) {
        if (jobListener.getName() == null || jobListener.getName().length() == 0) {
            throw new IllegalArgumentException("JobListener name cannot be empty.");
        }
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            this.globalJobListeners.put(jobListener.getName(), jobListener);
            LinkedList<Matcher<JobKey>> matchersL = new LinkedList<Matcher<JobKey>>();
            if (matchers != null && matchers.size() > 0) {
                matchersL.addAll(matchers);
            } else {
                matchersL.add(EverythingMatcher.allJobs());
            }
            this.globalJobListenersMatchers.put(jobListener.getName(), matchersL);
        }
    }

    @Override
    public void addJobListener(JobListener jobListener) {
        this.addJobListener(jobListener, (Matcher<JobKey>)EverythingMatcher.allJobs());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addJobListener(JobListener jobListener, Matcher<JobKey> matcher) {
        if (jobListener.getName() == null || jobListener.getName().length() == 0) {
            throw new IllegalArgumentException("JobListener name cannot be empty.");
        }
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            this.globalJobListeners.put(jobListener.getName(), jobListener);
            LinkedList<Matcher<JobKey>> matchersL = new LinkedList<Matcher<JobKey>>();
            if (matcher != null) {
                matchersL.add(matcher);
            } else {
                matchersL.add(EverythingMatcher.allJobs());
            }
            this.globalJobListenersMatchers.put(jobListener.getName(), matchersL);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addJobListenerMatcher(String listenerName, Matcher<JobKey> matcher) {
        if (matcher == null) {
            throw new IllegalArgumentException("Null value not acceptable.");
        }
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            List<Matcher<JobKey>> matchers = this.globalJobListenersMatchers.get(listenerName);
            if (matchers == null) {
                return false;
            }
            matchers.add(matcher);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJobListenerMatcher(String listenerName, Matcher<JobKey> matcher) {
        if (matcher == null) {
            throw new IllegalArgumentException("Non-null value not acceptable.");
        }
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            List<Matcher<JobKey>> matchers = this.globalJobListenersMatchers.get(listenerName);
            if (matchers == null) {
                return false;
            }
            return matchers.remove(matcher);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Matcher<JobKey>> getJobListenerMatchers(String listenerName) {
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            List<Matcher<JobKey>> matchers = this.globalJobListenersMatchers.get(listenerName);
            if (matchers == null) {
                return null;
            }
            return Collections.unmodifiableList(matchers);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean setJobListenerMatchers(String listenerName, List<Matcher<JobKey>> matchers) {
        if (matchers == null) {
            throw new IllegalArgumentException("Non-null value not acceptable.");
        }
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            List<Matcher<JobKey>> oldMatchers = this.globalJobListenersMatchers.get(listenerName);
            if (oldMatchers == null) {
                return false;
            }
            this.globalJobListenersMatchers.put(listenerName, matchers);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJobListener(String name) {
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            return this.globalJobListeners.remove(name) != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<JobListener> getJobListeners() {
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            return Collections.unmodifiableList(new LinkedList<JobListener>(this.globalJobListeners.values()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JobListener getJobListener(String name) {
        Map<String, JobListener> map = this.globalJobListeners;
        synchronized (map) {
            return this.globalJobListeners.get(name);
        }
    }

    @Override
    public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> ... matchers) {
        this.addTriggerListener(triggerListener, Arrays.asList(matchers));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addTriggerListener(TriggerListener triggerListener, List<Matcher<TriggerKey>> matchers) {
        if (triggerListener.getName() == null || triggerListener.getName().length() == 0) {
            throw new IllegalArgumentException("TriggerListener name cannot be empty.");
        }
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            this.globalTriggerListeners.put(triggerListener.getName(), triggerListener);
            LinkedList<Matcher<TriggerKey>> matchersL = new LinkedList<Matcher<TriggerKey>>();
            if (matchers != null && matchers.size() > 0) {
                matchersL.addAll(matchers);
            } else {
                matchersL.add(EverythingMatcher.allTriggers());
            }
            this.globalTriggerListenersMatchers.put(triggerListener.getName(), matchersL);
        }
    }

    @Override
    public void addTriggerListener(TriggerListener triggerListener) {
        this.addTriggerListener(triggerListener, (Matcher<TriggerKey>)EverythingMatcher.allTriggers());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> matcher) {
        if (matcher == null) {
            throw new IllegalArgumentException("Null value not acceptable for matcher.");
        }
        if (triggerListener.getName() == null || triggerListener.getName().length() == 0) {
            throw new IllegalArgumentException("TriggerListener name cannot be empty.");
        }
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            this.globalTriggerListeners.put(triggerListener.getName(), triggerListener);
            LinkedList<Matcher<TriggerKey>> matchers = new LinkedList<Matcher<TriggerKey>>();
            matchers.add(matcher);
            this.globalTriggerListenersMatchers.put(triggerListener.getName(), matchers);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher) {
        if (matcher == null) {
            throw new IllegalArgumentException("Non-null value not acceptable.");
        }
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            List<Matcher<TriggerKey>> matchers = this.globalTriggerListenersMatchers.get(listenerName);
            if (matchers == null) {
                return false;
            }
            matchers.add(matcher);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher) {
        if (matcher == null) {
            throw new IllegalArgumentException("Non-null value not acceptable.");
        }
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            List<Matcher<TriggerKey>> matchers = this.globalTriggerListenersMatchers.get(listenerName);
            if (matchers == null) {
                return false;
            }
            return matchers.remove(matcher);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Matcher<TriggerKey>> getTriggerListenerMatchers(String listenerName) {
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            List<Matcher<TriggerKey>> matchers = this.globalTriggerListenersMatchers.get(listenerName);
            if (matchers == null) {
                return null;
            }
            return Collections.unmodifiableList(matchers);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean setTriggerListenerMatchers(String listenerName, List<Matcher<TriggerKey>> matchers) {
        if (matchers == null) {
            throw new IllegalArgumentException("Non-null value not acceptable.");
        }
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            List<Matcher<TriggerKey>> oldMatchers = this.globalTriggerListenersMatchers.get(listenerName);
            if (oldMatchers == null) {
                return false;
            }
            this.globalTriggerListenersMatchers.put(listenerName, matchers);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeTriggerListener(String name) {
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            return this.globalTriggerListeners.remove(name) != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerListener> getTriggerListeners() {
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            return Collections.unmodifiableList(new LinkedList<TriggerListener>(this.globalTriggerListeners.values()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TriggerListener getTriggerListener(String name) {
        Map<String, TriggerListener> map = this.globalTriggerListeners;
        synchronized (map) {
            return this.globalTriggerListeners.get(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSchedulerListener(SchedulerListener schedulerListener) {
        ArrayList<SchedulerListener> arrayList = this.schedulerListeners;
        synchronized (arrayList) {
            this.schedulerListeners.add(schedulerListener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeSchedulerListener(SchedulerListener schedulerListener) {
        ArrayList<SchedulerListener> arrayList = this.schedulerListeners;
        synchronized (arrayList) {
            return this.schedulerListeners.remove(schedulerListener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<SchedulerListener> getSchedulerListeners() {
        ArrayList<SchedulerListener> arrayList = this.schedulerListeners;
        synchronized (arrayList) {
            return Collections.unmodifiableList(new ArrayList<SchedulerListener>(this.schedulerListeners));
        }
    }
}

