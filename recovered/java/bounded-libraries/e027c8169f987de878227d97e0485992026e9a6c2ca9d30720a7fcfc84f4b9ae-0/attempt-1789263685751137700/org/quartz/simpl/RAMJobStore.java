/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.simpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.simpl.JobWrapper;
import org.quartz.simpl.TriggerWrapper;
import org.quartz.simpl.TriggerWrapperComparator;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.JobStore;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.spi.TriggerFiredResult;
import org.quartz.utils.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAMJobStore
implements JobStore {
    protected HashMap<JobKey, JobWrapper> jobsByKey = new HashMap(1000);
    protected HashMap<TriggerKey, TriggerWrapper> triggersByKey = new HashMap(1000);
    protected HashMap<String, HashMap<JobKey, JobWrapper>> jobsByGroup = new HashMap(25);
    protected HashMap<String, HashMap<TriggerKey, TriggerWrapper>> triggersByGroup = new HashMap(25);
    protected TreeSet<TriggerWrapper> timeTriggers = new TreeSet<TriggerWrapper>(new TriggerWrapperComparator());
    protected HashMap<String, Calendar> calendarsByName = new HashMap(25);
    protected Map<JobKey, List<TriggerWrapper>> triggersByJob = new HashMap<JobKey, List<TriggerWrapper>>(1000);
    protected final Object lock = new Object();
    protected HashSet<String> pausedTriggerGroups = new HashSet();
    protected HashSet<String> pausedJobGroups = new HashSet();
    protected HashSet<JobKey> blockedJobs = new HashSet();
    protected long misfireThreshold = 5000L;
    protected SchedulerSignaler signaler;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final AtomicLong ftrCtr = new AtomicLong(System.currentTimeMillis());

    protected Logger getLog() {
        return this.log;
    }

    @Override
    public void initialize(ClassLoadHelper loadHelper, SchedulerSignaler schedSignaler) {
        this.signaler = schedSignaler;
        this.getLog().info("RAMJobStore initialized.");
    }

    @Override
    public void schedulerStarted() {
    }

    @Override
    public void schedulerPaused() {
    }

    @Override
    public void schedulerResumed() {
    }

    public long getMisfireThreshold() {
        return this.misfireThreshold;
    }

    public void setMisfireThreshold(long misfireThreshold) {
        if (misfireThreshold < 1L) {
            throw new IllegalArgumentException("Misfire threshold must be larger than 0");
        }
        this.misfireThreshold = misfireThreshold;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public boolean supportsPersistence() {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearAllSchedulingData() throws JobPersistenceException {
        Object object = this.lock;
        synchronized (object) {
            Set<Key> keys;
            List<String> lst = this.getTriggerGroupNames();
            for (String group : lst) {
                keys = this.getTriggerKeys(GroupMatcher.triggerGroupEquals(group));
                for (TriggerKey triggerKey : keys) {
                    this.removeTrigger(triggerKey);
                }
            }
            lst = this.getJobGroupNames();
            for (String group : lst) {
                keys = this.getJobKeys(GroupMatcher.jobGroupEquals(group));
                for (JobKey jobKey : keys) {
                    this.removeJob(jobKey);
                }
            }
            lst = this.getCalendarNames();
            for (String name : lst) {
                this.removeCalendar(name);
            }
        }
    }

    @Override
    public void storeJobAndTrigger(JobDetail newJob, OperableTrigger newTrigger) throws JobPersistenceException {
        this.storeJob(newJob, false);
        this.storeTrigger(newTrigger, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeJob(JobDetail newJob, boolean replaceExisting) throws ObjectAlreadyExistsException {
        JobWrapper jw = new JobWrapper((JobDetail)newJob.clone());
        boolean repl = false;
        Object object = this.lock;
        synchronized (object) {
            if (this.jobsByKey.get(jw.key) != null) {
                if (!replaceExisting) {
                    throw new ObjectAlreadyExistsException(newJob);
                }
                repl = true;
            }
            if (!repl) {
                HashMap<JobKey, JobWrapper> grpMap = this.jobsByGroup.get(newJob.getKey().getGroup());
                if (grpMap == null) {
                    grpMap = new HashMap(100);
                    this.jobsByGroup.put(newJob.getKey().getGroup(), grpMap);
                }
                grpMap.put(newJob.getKey(), jw);
                this.jobsByKey.put(jw.key, jw);
            } else {
                JobWrapper orig = this.jobsByKey.get(jw.key);
                orig.jobDetail = jw.jobDetail;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJob(JobKey jobKey) {
        boolean found = false;
        Object object = this.lock;
        synchronized (object) {
            HashMap<JobKey, JobWrapper> grpMap;
            List<OperableTrigger> triggersOfJob = this.getTriggersForJob(jobKey);
            for (OperableTrigger trig : triggersOfJob) {
                this.removeTrigger(trig.getKey());
                found = true;
            }
            found = this.jobsByKey.remove(jobKey) != null | found;
            if (found && (grpMap = this.jobsByGroup.get(jobKey.getGroup())) != null) {
                grpMap.remove(jobKey);
                if (grpMap.size() == 0) {
                    this.jobsByGroup.remove(jobKey.getGroup());
                }
            }
        }
        return found;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJobs(List<JobKey> jobKeys) throws JobPersistenceException {
        boolean allFound = true;
        Object object = this.lock;
        synchronized (object) {
            for (JobKey key : jobKeys) {
                allFound = this.removeJob(key) && allFound;
            }
        }
        return allFound;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeTriggers(List<TriggerKey> triggerKeys) throws JobPersistenceException {
        boolean allFound = true;
        Object object = this.lock;
        synchronized (object) {
            for (TriggerKey key : triggerKeys) {
                allFound = this.removeTrigger(key) && allFound;
            }
        }
        return allFound;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeJobsAndTriggers(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws JobPersistenceException {
        Object object = this.lock;
        synchronized (object) {
            if (!replace) {
                for (Map.Entry<JobDetail, Set<? extends Trigger>> e : triggersAndJobs.entrySet()) {
                    if (this.checkExists(e.getKey().getKey())) {
                        throw new ObjectAlreadyExistsException(e.getKey());
                    }
                    for (Trigger trigger : e.getValue()) {
                        if (!this.checkExists(trigger.getKey())) continue;
                        throw new ObjectAlreadyExistsException(trigger);
                    }
                }
            }
            for (Map.Entry<JobDetail, Set<? extends Trigger>> e : triggersAndJobs.entrySet()) {
                this.storeJob(e.getKey(), true);
                for (Trigger trigger : e.getValue()) {
                    this.storeTrigger((OperableTrigger)trigger, true);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeTrigger(OperableTrigger newTrigger, boolean replaceExisting) throws JobPersistenceException {
        TriggerWrapper tw = new TriggerWrapper((OperableTrigger)newTrigger.clone());
        Object object = this.lock;
        synchronized (object) {
            if (this.triggersByKey.get(tw.key) != null) {
                if (!replaceExisting) {
                    throw new ObjectAlreadyExistsException(newTrigger);
                }
                this.removeTrigger(newTrigger.getKey(), false);
            }
            if (this.retrieveJob(newTrigger.getJobKey()) == null) {
                throw new JobPersistenceException("The job (" + newTrigger.getJobKey() + ") referenced by the trigger does not exist.");
            }
            List<TriggerWrapper> jobList = this.triggersByJob.get(tw.jobKey);
            if (jobList == null) {
                jobList = new ArrayList<TriggerWrapper>(1);
                this.triggersByJob.put(tw.jobKey, jobList);
            }
            jobList.add(tw);
            HashMap<TriggerKey, TriggerWrapper> grpMap = this.triggersByGroup.get(newTrigger.getKey().getGroup());
            if (grpMap == null) {
                grpMap = new HashMap(100);
                this.triggersByGroup.put(newTrigger.getKey().getGroup(), grpMap);
            }
            grpMap.put(newTrigger.getKey(), tw);
            this.triggersByKey.put(tw.key, tw);
            if (this.pausedTriggerGroups.contains(newTrigger.getKey().getGroup()) || this.pausedJobGroups.contains(newTrigger.getJobKey().getGroup())) {
                tw.state = 4;
                if (this.blockedJobs.contains(tw.jobKey)) {
                    tw.state = 6;
                }
            } else if (this.blockedJobs.contains(tw.jobKey)) {
                tw.state = 5;
            } else {
                this.timeTriggers.add(tw);
            }
        }
    }

    @Override
    public boolean removeTrigger(TriggerKey triggerKey) {
        return this.removeTrigger(triggerKey, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean removeTrigger(TriggerKey key, boolean removeOrphanedJob) {
        boolean found;
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.remove(key);
            boolean bl = found = tw != null;
            if (found) {
                List<TriggerWrapper> jobList;
                HashMap<TriggerKey, TriggerWrapper> grpMap = this.triggersByGroup.get(key.getGroup());
                if (grpMap != null) {
                    grpMap.remove(key);
                    if (grpMap.size() == 0) {
                        this.triggersByGroup.remove(key.getGroup());
                    }
                }
                if ((jobList = this.triggersByJob.get(tw.jobKey)) != null) {
                    jobList.remove(tw);
                    if (jobList.isEmpty()) {
                        this.triggersByJob.remove(tw.jobKey);
                    }
                }
                this.timeTriggers.remove(tw);
                if (removeOrphanedJob) {
                    JobWrapper jw = this.jobsByKey.get(tw.jobKey);
                    List<OperableTrigger> trigs = this.getTriggersForJob(tw.jobKey);
                    if ((trigs == null || trigs.size() == 0) && !jw.jobDetail.isDurable() && this.removeJob(jw.key)) {
                        this.signaler.notifySchedulerListenersJobDeleted(jw.key);
                    }
                }
            }
        }
        return found;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean replaceTrigger(TriggerKey triggerKey, OperableTrigger newTrigger) throws JobPersistenceException {
        boolean found;
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.remove(triggerKey);
            boolean bl = found = tw != null;
            if (found) {
                List<TriggerWrapper> jobList;
                if (!tw.getTrigger().getJobKey().equals(newTrigger.getJobKey())) {
                    throw new JobPersistenceException("New trigger is not related to the same job as the old trigger.");
                }
                HashMap<TriggerKey, TriggerWrapper> grpMap = this.triggersByGroup.get(triggerKey.getGroup());
                if (grpMap != null) {
                    grpMap.remove(triggerKey);
                    if (grpMap.size() == 0) {
                        this.triggersByGroup.remove(triggerKey.getGroup());
                    }
                }
                if ((jobList = this.triggersByJob.get(tw.jobKey)) != null) {
                    jobList.remove(tw);
                    if (jobList.isEmpty()) {
                        this.triggersByJob.remove(tw.jobKey);
                    }
                }
                this.timeTriggers.remove(tw);
                try {
                    this.storeTrigger(newTrigger, false);
                }
                catch (JobPersistenceException jpe) {
                    this.storeTrigger(tw.getTrigger(), false);
                    throw jpe;
                }
            }
        }
        return found;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JobDetail retrieveJob(JobKey jobKey) {
        Object object = this.lock;
        synchronized (object) {
            JobWrapper jw = this.jobsByKey.get(jobKey);
            return jw != null ? (JobDetail)jw.jobDetail.clone() : null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public OperableTrigger retrieveTrigger(TriggerKey triggerKey) {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(triggerKey);
            return tw != null ? (OperableTrigger)tw.getTrigger().clone() : null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean checkExists(JobKey jobKey) throws JobPersistenceException {
        Object object = this.lock;
        synchronized (object) {
            JobWrapper jw = this.jobsByKey.get(jobKey);
            return jw != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean checkExists(TriggerKey triggerKey) throws JobPersistenceException {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(triggerKey);
            return tw != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws JobPersistenceException {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(triggerKey);
            if (tw == null) {
                return Trigger.TriggerState.NONE;
            }
            if (tw.state == 3) {
                return Trigger.TriggerState.COMPLETE;
            }
            if (tw.state == 4) {
                return Trigger.TriggerState.PAUSED;
            }
            if (tw.state == 6) {
                return Trigger.TriggerState.PAUSED;
            }
            if (tw.state == 5) {
                return Trigger.TriggerState.BLOCKED;
            }
            if (tw.state == 7) {
                return Trigger.TriggerState.ERROR;
            }
            return Trigger.TriggerState.NORMAL;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws JobPersistenceException {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(triggerKey);
            if (tw == null || tw.trigger == null) {
                return;
            }
            if (tw.state != 7) {
                return;
            }
            if (this.pausedTriggerGroups.contains(triggerKey.getGroup())) {
                tw.state = 4;
            } else {
                tw.state = 0;
                this.timeTriggers.add(tw);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeCalendar(String name, Calendar calendar, boolean replaceExisting, boolean updateTriggers) throws ObjectAlreadyExistsException {
        calendar = (Calendar)calendar.clone();
        Object object = this.lock;
        synchronized (object) {
            Calendar obj = this.calendarsByName.get(name);
            if (obj != null && !replaceExisting) {
                throw new ObjectAlreadyExistsException("Calendar with name '" + name + "' already exists.");
            }
            if (obj != null) {
                this.calendarsByName.remove(name);
            }
            this.calendarsByName.put(name, calendar);
            if (obj != null && updateTriggers) {
                for (TriggerWrapper tw : this.getTriggerWrappersForCalendar(name)) {
                    OperableTrigger trig = tw.getTrigger();
                    boolean removed = this.timeTriggers.remove(tw);
                    trig.updateWithNewCalendar(calendar, this.getMisfireThreshold());
                    if (!removed) continue;
                    this.timeTriggers.add(tw);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeCalendar(String calName) throws JobPersistenceException {
        int numRefs = 0;
        Object object = this.lock;
        synchronized (object) {
            for (TriggerWrapper trigger : this.triggersByKey.values()) {
                OperableTrigger trigg = trigger.trigger;
                if (trigg.getCalendarName() == null || !trigg.getCalendarName().equals(calName)) continue;
                ++numRefs;
            }
        }
        if (numRefs > 0) {
            throw new JobPersistenceException("Calender cannot be removed if it referenced by a Trigger!");
        }
        return this.calendarsByName.remove(calName) != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Calendar retrieveCalendar(String calName) {
        Object object = this.lock;
        synchronized (object) {
            Calendar cal = this.calendarsByName.get(calName);
            if (cal != null) {
                return (Calendar)cal.clone();
            }
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getNumberOfJobs() {
        Object object = this.lock;
        synchronized (object) {
            return this.jobsByKey.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getNumberOfTriggers() {
        Object object = this.lock;
        synchronized (object) {
            return this.triggersByKey.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getNumberOfCalendars() {
        Object object = this.lock;
        synchronized (object) {
            return this.calendarsByName.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) {
        HashSet<Object> outList = null;
        Object object = this.lock;
        synchronized (object) {
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            String compareToValue = matcher.getCompareToValue();
            switch (operator) {
                case EQUALS: {
                    HashMap<JobKey, JobWrapper> grpMap = this.jobsByGroup.get(compareToValue);
                    if (grpMap == null) break;
                    outList = new HashSet();
                    for (JobWrapper jw : grpMap.values()) {
                        if (jw == null) continue;
                        outList.add(jw.jobDetail.getKey());
                    }
                    break;
                }
                default: {
                    for (Map.Entry<String, HashMap<JobKey, JobWrapper>> entry : this.jobsByGroup.entrySet()) {
                        if (!operator.evaluate(entry.getKey(), compareToValue) || entry.getValue() == null) continue;
                        if (outList == null) {
                            outList = new HashSet();
                        }
                        for (JobWrapper jobWrapper : entry.getValue().values()) {
                            if (jobWrapper == null) continue;
                            outList.add(jobWrapper.jobDetail.getKey());
                        }
                    }
                }
            }
        }
        return outList == null ? Collections.emptySet() : outList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getCalendarNames() {
        Object object = this.lock;
        synchronized (object) {
            return new LinkedList<String>(this.calendarsByName.keySet());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) {
        HashSet<Object> outList = null;
        Object object = this.lock;
        synchronized (object) {
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            String compareToValue = matcher.getCompareToValue();
            switch (operator) {
                case EQUALS: {
                    HashMap<TriggerKey, TriggerWrapper> grpMap = this.triggersByGroup.get(compareToValue);
                    if (grpMap == null) break;
                    outList = new HashSet();
                    for (TriggerWrapper tw : grpMap.values()) {
                        if (tw == null) continue;
                        outList.add(tw.trigger.getKey());
                    }
                    break;
                }
                default: {
                    for (Map.Entry<String, HashMap<TriggerKey, TriggerWrapper>> entry : this.triggersByGroup.entrySet()) {
                        if (!operator.evaluate(entry.getKey(), compareToValue) || entry.getValue() == null) continue;
                        if (outList == null) {
                            outList = new HashSet();
                        }
                        for (TriggerWrapper triggerWrapper : entry.getValue().values()) {
                            if (triggerWrapper == null) continue;
                            outList.add(triggerWrapper.trigger.getKey());
                        }
                    }
                }
            }
        }
        return outList == null ? Collections.emptySet() : outList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getJobGroupNames() {
        LinkedList<String> outList;
        Object object = this.lock;
        synchronized (object) {
            outList = new LinkedList<String>(this.jobsByGroup.keySet());
        }
        return outList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getTriggerGroupNames() {
        LinkedList<String> outList;
        Object object = this.lock;
        synchronized (object) {
            outList = new LinkedList<String>(this.triggersByGroup.keySet());
        }
        return outList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> getTriggersForJob(JobKey jobKey) {
        ArrayList<OperableTrigger> trigList = new ArrayList<OperableTrigger>();
        Object object = this.lock;
        synchronized (object) {
            List<TriggerWrapper> jobList = this.triggersByJob.get(jobKey);
            if (jobList != null) {
                for (TriggerWrapper tw : jobList) {
                    trigList.add((OperableTrigger)tw.trigger.clone());
                }
            }
        }
        return trigList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ArrayList<TriggerWrapper> getTriggerWrappersForJob(JobKey jobKey) {
        ArrayList<TriggerWrapper> trigList = new ArrayList<TriggerWrapper>();
        Object object = this.lock;
        synchronized (object) {
            List<TriggerWrapper> jobList = this.triggersByJob.get(jobKey);
            if (jobList != null) {
                for (TriggerWrapper trigger : jobList) {
                    trigList.add(trigger);
                }
            }
        }
        return trigList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ArrayList<TriggerWrapper> getTriggerWrappersForCalendar(String calName) {
        ArrayList<TriggerWrapper> trigList = new ArrayList<TriggerWrapper>();
        Object object = this.lock;
        synchronized (object) {
            for (TriggerWrapper tw : this.triggersByKey.values()) {
                String tcalName = tw.getTrigger().getCalendarName();
                if (tcalName == null || !tcalName.equals(calName)) continue;
                trigList.add(tw);
            }
        }
        return trigList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void pauseTrigger(TriggerKey triggerKey) {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(triggerKey);
            if (tw == null || tw.trigger == null) {
                return;
            }
            if (tw.state == 3) {
                return;
            }
            tw.state = tw.state == 5 ? 6 : 4;
            this.timeTriggers.remove(tw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> pauseTriggers(GroupMatcher<TriggerKey> matcher) {
        LinkedList<String> pausedGroups;
        Object object = this.lock;
        synchronized (object) {
            pausedGroups = new LinkedList<String>();
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            switch (operator) {
                case EQUALS: {
                    if (!this.pausedTriggerGroups.add(matcher.getCompareToValue())) break;
                    pausedGroups.add(matcher.getCompareToValue());
                    break;
                }
                default: {
                    for (String group : this.triggersByGroup.keySet()) {
                        if (!operator.evaluate(group, matcher.getCompareToValue()) || !this.pausedTriggerGroups.add(matcher.getCompareToValue())) continue;
                        pausedGroups.add(group);
                    }
                }
            }
            for (String pausedGroup : pausedGroups) {
                Set<TriggerKey> keys = this.getTriggerKeys(GroupMatcher.triggerGroupEquals(pausedGroup));
                for (TriggerKey key : keys) {
                    this.pauseTrigger(key);
                }
            }
        }
        return pausedGroups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void pauseJob(JobKey jobKey) {
        Object object = this.lock;
        synchronized (object) {
            List<OperableTrigger> triggersOfJob = this.getTriggersForJob(jobKey);
            for (OperableTrigger trigger : triggersOfJob) {
                this.pauseTrigger(trigger.getKey());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> pauseJobs(GroupMatcher<JobKey> matcher) {
        LinkedList<String> pausedGroups = new LinkedList<String>();
        Object object = this.lock;
        synchronized (object) {
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            switch (operator) {
                case EQUALS: {
                    if (!this.pausedJobGroups.add(matcher.getCompareToValue())) break;
                    pausedGroups.add(matcher.getCompareToValue());
                    break;
                }
                default: {
                    for (String group : this.jobsByGroup.keySet()) {
                        if (!operator.evaluate(group, matcher.getCompareToValue()) || !this.pausedJobGroups.add(group)) continue;
                        pausedGroups.add(group);
                    }
                }
            }
            for (String groupName : pausedGroups) {
                for (JobKey jobKey : this.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    List<OperableTrigger> triggersOfJob = this.getTriggersForJob(jobKey);
                    for (OperableTrigger trigger : triggersOfJob) {
                        this.pauseTrigger(trigger.getKey());
                    }
                }
            }
        }
        return pausedGroups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resumeTrigger(TriggerKey triggerKey) {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(triggerKey);
            if (tw == null || tw.trigger == null) {
                return;
            }
            OperableTrigger trig = tw.getTrigger();
            if (tw.state != 4 && tw.state != 6) {
                return;
            }
            tw.state = this.blockedJobs.contains(trig.getJobKey()) ? 5 : 0;
            this.applyMisfire(tw);
            if (tw.state == 0) {
                this.timeTriggers.add(tw);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> resumeTriggers(GroupMatcher<TriggerKey> matcher) {
        HashSet<String> groups = new HashSet<String>();
        Object object = this.lock;
        synchronized (object) {
            Set<TriggerKey> keys = this.getTriggerKeys(matcher);
            for (TriggerKey triggerKey : keys) {
                String jobGroup;
                groups.add(triggerKey.getGroup());
                if (this.triggersByKey.get(triggerKey) != null && this.pausedJobGroups.contains(jobGroup = this.triggersByKey.get((Object)triggerKey).jobKey.getGroup())) continue;
                this.resumeTrigger(triggerKey);
            }
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            LinkedList<String> pausedGroups = new LinkedList<String>();
            String matcherGroup = matcher.getCompareToValue();
            switch (operator) {
                case EQUALS: {
                    if (!this.pausedTriggerGroups.contains(matcherGroup)) break;
                    pausedGroups.add(matcher.getCompareToValue());
                    break;
                }
                default: {
                    for (String group : this.pausedTriggerGroups) {
                        if (!operator.evaluate(group, matcherGroup)) continue;
                        pausedGroups.add(group);
                    }
                }
            }
            for (String pausedGroup : pausedGroups) {
                this.pausedTriggerGroups.remove(pausedGroup);
            }
        }
        return new ArrayList<String>(groups);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resumeJob(JobKey jobKey) {
        Object object = this.lock;
        synchronized (object) {
            List<OperableTrigger> triggersOfJob = this.getTriggersForJob(jobKey);
            for (OperableTrigger trigger : triggersOfJob) {
                this.resumeTrigger(trigger.getKey());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> resumeJobs(GroupMatcher<JobKey> matcher) {
        HashSet<String> resumedGroups = new HashSet<String>();
        Object object = this.lock;
        synchronized (object) {
            Set<JobKey> keys = this.getJobKeys(matcher);
            for (String pausedJobGroup : this.pausedJobGroups) {
                if (!matcher.getCompareWithOperator().evaluate(pausedJobGroup, matcher.getCompareToValue())) continue;
                resumedGroups.add(pausedJobGroup);
            }
            for (String resumedGroup : resumedGroups) {
                this.pausedJobGroups.remove(resumedGroup);
            }
            for (JobKey key : keys) {
                List<OperableTrigger> triggersOfJob = this.getTriggersForJob(key);
                for (OperableTrigger trigger : triggersOfJob) {
                    this.resumeTrigger(trigger.getKey());
                }
            }
        }
        return resumedGroups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void pauseAll() {
        Object object = this.lock;
        synchronized (object) {
            List<String> names = this.getTriggerGroupNames();
            for (String name : names) {
                this.pauseTriggers((GroupMatcher)GroupMatcher.triggerGroupEquals(name));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resumeAll() {
        Object object = this.lock;
        synchronized (object) {
            this.pausedJobGroups.clear();
            this.resumeTriggers((GroupMatcher)GroupMatcher.anyTriggerGroup());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean applyMisfire(TriggerWrapper tw) {
        Date tnft;
        long misfireTime = System.currentTimeMillis();
        if (this.getMisfireThreshold() > 0L) {
            misfireTime -= this.getMisfireThreshold();
        }
        if ((tnft = tw.trigger.getNextFireTime()) == null || tnft.getTime() > misfireTime || tw.trigger.getMisfireInstruction() == -1) {
            return false;
        }
        Calendar cal = null;
        if (tw.trigger.getCalendarName() != null) {
            cal = this.retrieveCalendar(tw.trigger.getCalendarName());
        }
        this.signaler.notifyTriggerListenersMisfired((OperableTrigger)tw.trigger.clone());
        tw.trigger.updateAfterMisfire(cal);
        if (tw.trigger.getNextFireTime() == null) {
            tw.state = 3;
            this.signaler.notifySchedulerListenersFinalized(tw.trigger);
            Object object = this.lock;
            synchronized (object) {
                this.timeTriggers.remove(tw);
            }
        } else if (tnft.equals(tw.trigger.getNextFireTime())) {
            return false;
        }
        return true;
    }

    protected String getFiredTriggerRecordId() {
        return String.valueOf(ftrCtr.incrementAndGet());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> acquireNextTriggers(long noLaterThan, int maxCount, long timeWindow) {
        Object object = this.lock;
        synchronized (object) {
            ArrayList<OperableTrigger> result = new ArrayList<OperableTrigger>();
            HashSet<JobKey> acquiredJobKeysForNoConcurrentExec = new HashSet<JobKey>();
            HashSet<TriggerWrapper> excludedTriggers = new HashSet<TriggerWrapper>();
            long batchEnd = noLaterThan;
            if (this.timeTriggers.size() == 0) {
                return result;
            }
            while (true) {
                TriggerWrapper tw;
                try {
                    tw = this.timeTriggers.first();
                    if (tw == null) break;
                    this.timeTriggers.remove(tw);
                }
                catch (NoSuchElementException nsee) {
                    break;
                }
                if (tw.trigger.getNextFireTime() == null) continue;
                if (this.applyMisfire(tw)) {
                    if (tw.trigger.getNextFireTime() == null) continue;
                    this.timeTriggers.add(tw);
                    continue;
                }
                if (tw.getTrigger().getNextFireTime().getTime() > batchEnd) {
                    this.timeTriggers.add(tw);
                    break;
                }
                JobKey jobKey = tw.trigger.getJobKey();
                JobDetail job = this.jobsByKey.get((Object)tw.trigger.getJobKey()).jobDetail;
                if (job.isConcurrentExectionDisallowed()) {
                    if (acquiredJobKeysForNoConcurrentExec.contains(jobKey)) {
                        excludedTriggers.add(tw);
                        continue;
                    }
                    acquiredJobKeysForNoConcurrentExec.add(jobKey);
                }
                tw.state = 1;
                tw.trigger.setFireInstanceId(this.getFiredTriggerRecordId());
                OperableTrigger trig = (OperableTrigger)tw.trigger.clone();
                if (result.isEmpty()) {
                    batchEnd = Math.max(tw.trigger.getNextFireTime().getTime(), System.currentTimeMillis()) + timeWindow;
                }
                result.add(trig);
                if (result.size() == maxCount) break;
            }
            if (excludedTriggers.size() > 0) {
                this.timeTriggers.addAll(excludedTriggers);
            }
            return result;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseAcquiredTrigger(OperableTrigger trigger) {
        Object object = this.lock;
        synchronized (object) {
            TriggerWrapper tw = this.triggersByKey.get(trigger.getKey());
            if (tw != null && tw.state == 1) {
                tw.state = 0;
                this.timeTriggers.add(tw);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerFiredResult> triggersFired(List<OperableTrigger> firedTriggers) {
        Object object = this.lock;
        synchronized (object) {
            ArrayList<TriggerFiredResult> results = new ArrayList<TriggerFiredResult>();
            for (OperableTrigger trigger : firedTriggers) {
                TriggerWrapper tw = this.triggersByKey.get(trigger.getKey());
                if (tw == null || tw.trigger == null || tw.state != 1) continue;
                Calendar cal = null;
                if (tw.trigger.getCalendarName() != null && (cal = this.retrieveCalendar(tw.trigger.getCalendarName())) == null) continue;
                Date prevFireTime = trigger.getPreviousFireTime();
                this.timeTriggers.remove(tw);
                tw.trigger.triggered(cal);
                trigger.triggered(cal);
                tw.state = 0;
                TriggerFiredBundle bndle = new TriggerFiredBundle(this.retrieveJob(tw.jobKey), trigger, cal, false, new Date(), trigger.getPreviousFireTime(), prevFireTime, trigger.getNextFireTime());
                JobDetail job = bndle.getJobDetail();
                if (job.isConcurrentExectionDisallowed()) {
                    ArrayList<TriggerWrapper> trigs = this.getTriggerWrappersForJob(job.getKey());
                    for (TriggerWrapper ttw : trigs) {
                        if (ttw.state == 0) {
                            ttw.state = 5;
                        }
                        if (ttw.state == 4) {
                            ttw.state = 6;
                        }
                        this.timeTriggers.remove(ttw);
                    }
                    this.blockedJobs.add(job.getKey());
                } else if (tw.trigger.getNextFireTime() != null) {
                    Object object2 = this.lock;
                    synchronized (object2) {
                        this.timeTriggers.add(tw);
                    }
                }
                results.add(new TriggerFiredResult(bndle));
            }
            return results;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void triggeredJobComplete(OperableTrigger trigger, JobDetail jobDetail, Trigger.CompletedExecutionInstruction triggerInstCode) {
        Object object = this.lock;
        synchronized (object) {
            JobWrapper jw = this.jobsByKey.get(jobDetail.getKey());
            TriggerWrapper tw = this.triggersByKey.get(trigger.getKey());
            if (jw != null) {
                JobDetail jd = jw.jobDetail;
                if (jd.isPersistJobDataAfterExecution()) {
                    JobDataMap newData = jobDetail.getJobDataMap();
                    if (newData != null) {
                        newData = (JobDataMap)newData.clone();
                        newData.clearDirtyFlag();
                    }
                    jw.jobDetail = jd = jd.getJobBuilder().setJobData(newData).build();
                }
                if (jd.isConcurrentExectionDisallowed()) {
                    this.blockedJobs.remove(jd.getKey());
                    ArrayList<TriggerWrapper> trigs = this.getTriggerWrappersForJob(jd.getKey());
                    for (TriggerWrapper ttw : trigs) {
                        if (ttw.state == 5) {
                            ttw.state = 0;
                            this.timeTriggers.add(ttw);
                        }
                        if (ttw.state != 6) continue;
                        ttw.state = 4;
                    }
                    this.signaler.signalSchedulingChange(0L);
                }
            } else {
                this.blockedJobs.remove(jobDetail.getKey());
            }
            if (tw != null) {
                if (triggerInstCode == Trigger.CompletedExecutionInstruction.DELETE_TRIGGER) {
                    if (trigger.getNextFireTime() == null) {
                        if (tw.getTrigger().getNextFireTime() == null) {
                            this.removeTrigger(trigger.getKey());
                        }
                    } else {
                        this.removeTrigger(trigger.getKey());
                        this.signaler.signalSchedulingChange(0L);
                    }
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
                    tw.state = 3;
                    this.timeTriggers.remove(tw);
                    this.signaler.signalSchedulingChange(0L);
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_ERROR) {
                    this.getLog().info("Trigger " + trigger.getKey() + " set to ERROR state.");
                    tw.state = 7;
                    this.signaler.signalSchedulingChange(0L);
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR) {
                    this.getLog().info("All triggers of Job " + trigger.getJobKey() + " set to ERROR state.");
                    this.setAllTriggersOfJobToState(trigger.getJobKey(), 7);
                    this.signaler.signalSchedulingChange(0L);
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
                    this.setAllTriggersOfJobToState(trigger.getJobKey(), 3);
                    this.signaler.signalSchedulingChange(0L);
                }
            }
        }
    }

    @Override
    public long getAcquireRetryDelay(int failureCount) {
        return 20L;
    }

    protected void setAllTriggersOfJobToState(JobKey jobKey, int state) {
        ArrayList<TriggerWrapper> tws = this.getTriggerWrappersForJob(jobKey);
        for (TriggerWrapper tw : tws) {
            tw.state = state;
            if (state == 0) continue;
            this.timeTriggers.remove(tw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String peekTriggers() {
        StringBuilder str = new StringBuilder();
        Object object = this.lock;
        synchronized (object) {
            for (TriggerWrapper triggerWrapper : this.triggersByKey.values()) {
                str.append(triggerWrapper.trigger.getKey().getName());
                str.append("/");
            }
        }
        str.append(" | ");
        object = this.lock;
        synchronized (object) {
            for (TriggerWrapper timeTrigger : this.timeTriggers) {
                str.append(timeTrigger.trigger.getKey().getName());
                str.append("->");
            }
        }
        return str.toString();
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws JobPersistenceException {
        HashSet<String> set = new HashSet<String>();
        set.addAll(this.pausedTriggerGroups);
        return set;
    }

    @Override
    public void setInstanceId(String schedInstId) {
    }

    @Override
    public void setInstanceName(String schedName) {
    }

    @Override
    public void setThreadPoolSize(int poolSize) {
    }

    @Override
    public long getEstimatedTimeToReleaseAndAcquireTrigger() {
        return 5L;
    }

    @Override
    public boolean isClustered() {
        return false;
    }
}

