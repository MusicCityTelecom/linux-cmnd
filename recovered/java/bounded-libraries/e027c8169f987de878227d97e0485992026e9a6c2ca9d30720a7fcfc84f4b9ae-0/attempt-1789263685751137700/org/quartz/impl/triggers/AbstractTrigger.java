/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.triggers;

import java.util.Date;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.spi.OperableTrigger;

public abstract class AbstractTrigger<T extends Trigger>
implements OperableTrigger {
    private static final long serialVersionUID = -3904243490805975570L;
    private String name;
    private String group = "DEFAULT";
    private String jobName;
    private String jobGroup = "DEFAULT";
    private String description;
    private JobDataMap jobDataMap;
    private boolean volatility = false;
    private String calendarName = null;
    private String fireInstanceId = null;
    private int misfireInstruction = 0;
    private int priority = 5;
    private transient TriggerKey key = null;

    public AbstractTrigger() {
    }

    public AbstractTrigger(String name) {
        this.setName(name);
        this.setGroup(null);
    }

    public AbstractTrigger(String name, String group) {
        this.setName(name);
        this.setGroup(group);
    }

    public AbstractTrigger(String name, String group, String jobName, String jobGroup) {
        this.setName(name);
        this.setGroup(group);
        this.setJobName(jobName);
        this.setJobGroup(jobGroup);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Trigger name cannot be null or empty.");
        }
        this.name = name;
        this.key = null;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        if (group != null && group.trim().length() == 0) {
            throw new IllegalArgumentException("Group name cannot be an empty string.");
        }
        if (group == null) {
            group = "DEFAULT";
        }
        this.group = group;
        this.key = null;
    }

    @Override
    public void setKey(TriggerKey key) {
        this.setName(key.getName());
        this.setGroup(key.getGroup());
        this.key = key;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setJobName(String jobName) {
        if (jobName == null || jobName.trim().length() == 0) {
            throw new IllegalArgumentException("Job name cannot be null or empty.");
        }
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return this.jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        if (jobGroup != null && jobGroup.trim().length() == 0) {
            throw new IllegalArgumentException("Group name cannot be null or empty.");
        }
        if (jobGroup == null) {
            jobGroup = "DEFAULT";
        }
        this.jobGroup = jobGroup;
    }

    @Override
    public void setJobKey(JobKey key) {
        this.setJobName(key.getName());
        this.setJobGroup(key.getGroup());
    }

    public String getFullName() {
        return this.group + "." + this.name;
    }

    @Override
    public TriggerKey getKey() {
        if (this.key == null) {
            if (this.getName() == null) {
                return null;
            }
            this.key = new TriggerKey(this.getName(), this.getGroup());
        }
        return this.key;
    }

    @Override
    public JobKey getJobKey() {
        if (this.getJobName() == null) {
            return null;
        }
        return new JobKey(this.getJobName(), this.getJobGroup());
    }

    public String getFullJobName() {
        return this.jobGroup + "." + this.jobName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    @Override
    public String getCalendarName() {
        return this.calendarName;
    }

    @Override
    public JobDataMap getJobDataMap() {
        if (this.jobDataMap == null) {
            this.jobDataMap = new JobDataMap();
        }
        return this.jobDataMap;
    }

    @Override
    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public abstract void triggered(Calendar var1);

    @Override
    public abstract Date computeFirstFireTime(Calendar var1);

    @Override
    public Trigger.CompletedExecutionInstruction executionComplete(JobExecutionContext context, JobExecutionException result) {
        if (result != null && result.refireImmediately()) {
            return Trigger.CompletedExecutionInstruction.RE_EXECUTE_JOB;
        }
        if (result != null && result.unscheduleFiringTrigger()) {
            return Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE;
        }
        if (result != null && result.unscheduleAllTriggers()) {
            return Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE;
        }
        if (!this.mayFireAgain()) {
            return Trigger.CompletedExecutionInstruction.DELETE_TRIGGER;
        }
        return Trigger.CompletedExecutionInstruction.NOOP;
    }

    @Override
    public abstract boolean mayFireAgain();

    @Override
    public abstract Date getStartTime();

    @Override
    public abstract void setStartTime(Date var1);

    @Override
    public abstract void setEndTime(Date var1);

    @Override
    public abstract Date getEndTime();

    @Override
    public abstract Date getNextFireTime();

    @Override
    public abstract Date getPreviousFireTime();

    @Override
    public abstract Date getFireTimeAfter(Date var1);

    @Override
    public abstract Date getFinalFireTime();

    @Override
    public void setMisfireInstruction(int misfireInstruction) {
        if (!this.validateMisfireInstruction(misfireInstruction)) {
            throw new IllegalArgumentException("The misfire instruction code is invalid for this type of trigger.");
        }
        this.misfireInstruction = misfireInstruction;
    }

    protected abstract boolean validateMisfireInstruction(int var1);

    @Override
    public int getMisfireInstruction() {
        return this.misfireInstruction;
    }

    @Override
    public abstract void updateAfterMisfire(Calendar var1);

    @Override
    public abstract void updateWithNewCalendar(Calendar var1, long var2);

    @Override
    public void validate() throws SchedulerException {
        if (this.name == null) {
            throw new SchedulerException("Trigger's name cannot be null");
        }
        if (this.group == null) {
            throw new SchedulerException("Trigger's group cannot be null");
        }
        if (this.jobName == null) {
            throw new SchedulerException("Trigger's related Job's name cannot be null");
        }
        if (this.jobGroup == null) {
            throw new SchedulerException("Trigger's related Job's group cannot be null");
        }
    }

    @Override
    public void setFireInstanceId(String id) {
        this.fireInstanceId = id;
    }

    @Override
    public String getFireInstanceId() {
        return this.fireInstanceId;
    }

    public String toString() {
        return "Trigger '" + this.getFullName() + "':  triggerClass: '" + this.getClass().getName() + " calendar: '" + this.getCalendarName() + "' misfireInstruction: " + this.getMisfireInstruction() + " nextFireTime: " + this.getNextFireTime();
    }

    @Override
    public int compareTo(Trigger other) {
        if (other.getKey() == null && this.getKey() == null) {
            return 0;
        }
        if (other.getKey() == null) {
            return -1;
        }
        if (this.getKey() == null) {
            return 1;
        }
        return this.getKey().compareTo(other.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Trigger)) {
            return false;
        }
        Trigger other = (Trigger)o;
        return other.getKey() != null && this.getKey() != null && this.getKey().equals(other.getKey());
    }

    public int hashCode() {
        if (this.getKey() == null) {
            return super.hashCode();
        }
        return this.getKey().hashCode();
    }

    @Override
    public Object clone() {
        AbstractTrigger copy;
        try {
            copy = (AbstractTrigger)super.clone();
            if (this.jobDataMap != null) {
                copy.jobDataMap = (JobDataMap)this.jobDataMap.clone();
            }
        }
        catch (CloneNotSupportedException ex) {
            throw new IncompatibleClassChangeError("Not Cloneable.");
        }
        return copy;
    }

    public TriggerBuilder<T> getTriggerBuilder() {
        return TriggerBuilder.newTrigger().forJob(this.getJobKey()).modifiedByCalendar(this.getCalendarName()).usingJobData(this.getJobDataMap()).withDescription(this.getDescription()).endAt(this.getEndTime()).withIdentity(this.getKey()).withPriority(this.getPriority()).startAt(this.getStartTime()).withSchedule(this.getScheduleBuilder());
    }

    public abstract ScheduleBuilder<T> getScheduleBuilder();
}

