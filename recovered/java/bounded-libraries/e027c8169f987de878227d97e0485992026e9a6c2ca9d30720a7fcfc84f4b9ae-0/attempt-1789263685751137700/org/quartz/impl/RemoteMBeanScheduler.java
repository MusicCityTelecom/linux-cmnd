/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.spi.JobFactory;

public abstract class RemoteMBeanScheduler
implements Scheduler {
    private ObjectName schedulerObjectName;

    protected ObjectName getSchedulerObjectName() {
        return this.schedulerObjectName;
    }

    public void setSchedulerObjectName(String schedulerObjectName) throws SchedulerException {
        try {
            this.schedulerObjectName = new ObjectName(schedulerObjectName);
        }
        catch (MalformedObjectNameException e) {
            throw new SchedulerException("Failed to parse Scheduler MBean name: " + schedulerObjectName, e);
        }
    }

    public void setSchedulerObjectName(ObjectName schedulerObjectName) throws SchedulerException {
        this.schedulerObjectName = schedulerObjectName;
    }

    public abstract void initialize() throws SchedulerException;

    protected abstract Object getAttribute(String var1) throws SchedulerException;

    protected abstract AttributeList getAttributes(String[] var1) throws SchedulerException;

    protected abstract Object invoke(String var1, Object[] var2, String[] var3) throws SchedulerException;

    @Override
    public String getSchedulerName() throws SchedulerException {
        return (String)this.getAttribute("SchedulerName");
    }

    @Override
    public String getSchedulerInstanceId() throws SchedulerException {
        return (String)this.getAttribute("SchedulerInstanceId");
    }

    @Override
    public SchedulerMetaData getMetaData() throws SchedulerException {
        AttributeList attributeList = this.getAttributes(new String[]{"SchedulerName", "SchedulerInstanceId", "StandbyMode", "Shutdown", "JobStoreClassName", "ThreadPoolClassName", "ThreadPoolSize", "Version", "PerformanceMetrics"});
        try {
            return new SchedulerMetaData((String)this.getAttribute(attributeList, 0).getValue(), (String)this.getAttribute(attributeList, 1).getValue(), this.getClass(), true, false, (Boolean)this.getAttribute(attributeList, 2).getValue(), (Boolean)this.getAttribute(attributeList, 3).getValue(), null, Integer.parseInt(((Map)this.getAttribute(attributeList, 8).getValue()).get("JobsExecuted").toString()), Class.forName((String)this.getAttribute(attributeList, 4).getValue()), false, false, Class.forName((String)this.getAttribute(attributeList, 5).getValue()), (Integer)this.getAttribute(attributeList, 6).getValue(), (String)this.getAttribute(attributeList, 7).getValue());
        }
        catch (ClassNotFoundException e) {
            throw new SchedulerException(e);
        }
    }

    private Attribute getAttribute(AttributeList attributeList, int index) {
        return (Attribute)attributeList.get(index);
    }

    @Override
    public SchedulerContext getContext() throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public void start() throws SchedulerException {
        this.invoke("start", new Object[0], new String[0]);
    }

    @Override
    public void startDelayed(int seconds) throws SchedulerException {
        this.invoke("startDelayed", new Object[]{seconds}, new String[]{Integer.TYPE.getName()});
    }

    @Override
    public void standby() throws SchedulerException {
        this.invoke("standby", new Object[0], new String[0]);
    }

    @Override
    public boolean isStarted() throws SchedulerException {
        return (Boolean)this.getAttribute("Started");
    }

    @Override
    public boolean isInStandbyMode() throws SchedulerException {
        return (Boolean)this.getAttribute("StandbyMode");
    }

    @Override
    public void shutdown() throws SchedulerException {
        String schedulerName = this.getSchedulerName();
        this.invoke("shutdown", new Object[0], new String[0]);
        SchedulerRepository.getInstance().remove(schedulerName);
    }

    @Override
    public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public boolean isShutdown() throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public Date scheduleJob(Trigger trigger) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
        this.invoke("addJob", new Object[]{JobDetailSupport.toCompositeData(jobDetail), replace}, new String[]{CompositeData.class.getName(), Boolean.TYPE.getName()});
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling) throws SchedulerException {
        this.invoke("addJob", new Object[]{JobDetailSupport.toCompositeData(jobDetail), replace, storeNonDurableWhileAwaitingScheduling}, new String[]{CompositeData.class.getName(), Boolean.TYPE.getName(), Boolean.TYPE.getName()});
    }

    @Override
    public boolean deleteJob(JobKey jobKey) throws SchedulerException {
        return (Boolean)this.invoke("deleteJob", new Object[]{jobKey.getName(), jobKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException {
        return (Boolean)this.invoke("unscheduleJob", new Object[]{triggerKey.getName(), triggerKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public void triggerJob(JobKey jobKey) throws SchedulerException {
        this.triggerJob(jobKey, null);
    }

    @Override
    public void triggerJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.invoke("pauseTrigger", new Object[]{triggerKey.getName(), triggerKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        String operation = null;
        switch (matcher.getCompareWithOperator()) {
            case EQUALS: {
                operation = "pauseTriggerGroup";
                break;
            }
            case CONTAINS: {
                operation = "pauseTriggersContaining";
                break;
            }
            case STARTS_WITH: {
                operation = "pauseTriggersStartingWith";
                break;
            }
            case ENDS_WITH: {
                operation = "pauseTriggersEndingWith";
            }
            case ANYTHING: {
                operation = "pauseTriggersAll";
            }
        }
        if (operation == null) {
            throw new SchedulerException("Unsupported GroupMatcher kind for pausing triggers: " + (Object)((Object)matcher.getCompareWithOperator()));
        }
        this.invoke(operation, new Object[]{matcher.getCompareToValue()}, new String[]{String.class.getName()});
    }

    @Override
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        this.invoke("pauseJob", new Object[]{jobKey.getName(), jobKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void pauseJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        String operation = null;
        switch (matcher.getCompareWithOperator()) {
            case EQUALS: {
                operation = "pauseJobGroup";
                break;
            }
            case STARTS_WITH: {
                operation = "pauseJobsStartingWith";
                break;
            }
            case ENDS_WITH: {
                operation = "pauseJobsEndingWith";
                break;
            }
            case CONTAINS: {
                operation = "pauseJobsContaining";
            }
            case ANYTHING: {
                operation = "pauseJobsAll";
            }
        }
        this.invoke(operation, new Object[]{matcher.getCompareToValue()}, new String[]{String.class.getName()});
    }

    @Override
    public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.invoke("resumeTrigger", new Object[]{triggerKey.getName(), triggerKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        String operation = null;
        switch (matcher.getCompareWithOperator()) {
            case EQUALS: {
                operation = "resumeTriggerGroup";
                break;
            }
            case CONTAINS: {
                operation = "resumeTriggersContaining";
                break;
            }
            case STARTS_WITH: {
                operation = "resumeTriggersStartingWith";
                break;
            }
            case ENDS_WITH: {
                operation = "resumeTriggersEndingWith";
            }
            case ANYTHING: {
                operation = "resumeTriggersAll";
            }
        }
        if (operation == null) {
            throw new SchedulerException("Unsupported GroupMatcher kind for resuming triggers: " + (Object)((Object)matcher.getCompareWithOperator()));
        }
        this.invoke(operation, new Object[]{matcher.getCompareToValue()}, new String[]{String.class.getName()});
    }

    @Override
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        this.invoke("resumeJob", new Object[]{jobKey.getName(), jobKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        String operation = null;
        switch (matcher.getCompareWithOperator()) {
            case EQUALS: {
                operation = "resumeJobGroup";
                break;
            }
            case STARTS_WITH: {
                operation = "resumeJobsStartingWith";
                break;
            }
            case ENDS_WITH: {
                operation = "resumeJobsEndingWith";
                break;
            }
            case CONTAINS: {
                operation = "resumeJobsContaining";
            }
            case ANYTHING: {
                operation = "resumeJobsAll";
            }
        }
        this.invoke(operation, new Object[]{matcher.getCompareToValue()}, new String[]{String.class.getName()});
    }

    @Override
    public void pauseAll() throws SchedulerException {
        this.invoke("pauseAllTriggers", new Object[0], new String[0]);
    }

    @Override
    public void resumeAll() throws SchedulerException {
        this.invoke("resumeAllTriggers", new Object[0], new String[0]);
    }

    @Override
    public List<String> getJobGroupNames() throws SchedulerException {
        return (List)this.getAttribute("JobGroupNames");
    }

    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException {
        if (matcher.getCompareWithOperator().equals((Object)StringMatcher.StringOperatorName.EQUALS)) {
            List keys = (List)this.invoke("getJobNames", new Object[]{matcher.getCompareToValue()}, new String[]{String.class.getName()});
            return new HashSet<JobKey>(keys);
        }
        throw new SchedulerException("Only equals matcher are supported for looking up JobKeys");
    }

    public List<Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public List<String> getTriggerGroupNames() throws SchedulerException {
        return (List)this.getAttribute("TriggerGroupNames");
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
        try {
            return JobDetailSupport.newJobDetail((CompositeData)this.invoke("getJobDetail", new Object[]{jobKey.getName(), jobKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()}));
        }
        catch (ClassNotFoundException e) {
            throw new SchedulerException("Unable to resolve job class", e);
        }
    }

    @Override
    public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public boolean checkExists(JobKey jobKey) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
        return (Boolean)this.invoke("checkExists", new Object[]{triggerKey}, new String[]{TriggerKey.class.getName()});
    }

    @Override
    public void clear() throws SchedulerException {
        this.invoke("clear", new Object[0], new String[0]);
    }

    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException {
        return Trigger.TriggerState.valueOf((String)this.invoke("getTriggerState", new Object[]{triggerKey.getName(), triggerKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()}));
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws SchedulerException {
        this.invoke("resetTriggerFromErrorState", new Object[]{triggerKey.getName(), triggerKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
    }

    @Override
    public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers) throws SchedulerException {
        this.invoke("addCalendar", new Object[]{calName, calendar, replace, updateTriggers}, new String[]{String.class.getName(), Calendar.class.getName(), Boolean.TYPE.getName(), Boolean.TYPE.getName()});
    }

    @Override
    public boolean deleteCalendar(String calName) throws SchedulerException {
        this.invoke("deleteCalendar", new Object[]{calName}, new String[]{String.class.getName()});
        return true;
    }

    @Override
    public Calendar getCalendar(String calName) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public List<String> getCalendarNames() throws SchedulerException {
        return (List)this.getAttribute("CalendarNames");
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws SchedulerException {
        return (Set)this.getAttribute("PausedTriggerGroups");
    }

    @Override
    public ListenerManager getListenerManager() throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException {
        try {
            return (Boolean)this.invoke("interruptJob", new Object[]{jobKey.getName(), jobKey.getGroup()}, new String[]{String.class.getName(), String.class.getName()});
        }
        catch (SchedulerException se) {
            throw new UnableToInterruptJobException(se);
        }
    }

    @Override
    public boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException {
        try {
            return (Boolean)this.invoke("interruptJob", new Object[]{fireInstanceId}, new String[]{String.class.getName()});
        }
        catch (SchedulerException se) {
            throw new UnableToInterruptJobException(se);
        }
    }

    @Override
    public void setJobFactory(JobFactory factory) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }
}

