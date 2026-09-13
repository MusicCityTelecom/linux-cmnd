/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.triggers;

import java.util.Date;
import org.quartz.Calendar;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CoreTrigger;

public class SimpleTriggerImpl
extends AbstractTrigger<SimpleTrigger>
implements SimpleTrigger,
CoreTrigger {
    private static final long serialVersionUID = -3735980074222850397L;
    private static final int YEAR_TO_GIVEUP_SCHEDULING_AT = java.util.Calendar.getInstance().get(1) + 100;
    private Date startTime = null;
    private Date endTime = null;
    private Date nextFireTime = null;
    private Date previousFireTime = null;
    private int repeatCount = 0;
    private long repeatInterval = 0L;
    private int timesTriggered = 0;
    private boolean complete = false;

    public SimpleTriggerImpl() {
    }

    @Deprecated
    public SimpleTriggerImpl(String name) {
        this(name, (String)null);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, String group) {
        this(name, group, new Date(), null, 0, 0L);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, int repeatCount, long repeatInterval) {
        this(name, null, repeatCount, repeatInterval);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, String group, int repeatCount, long repeatInterval) {
        this(name, group, new Date(), null, repeatCount, repeatInterval);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, Date startTime) {
        this(name, null, startTime);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, String group, Date startTime) {
        this(name, group, startTime, null, 0, 0L);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
        this(name, null, startTime, endTime, repeatCount, repeatInterval);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, String group, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
        super(name, group);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setRepeatCount(repeatCount);
        this.setRepeatInterval(repeatInterval);
    }

    @Deprecated
    public SimpleTriggerImpl(String name, String group, String jobName, String jobGroup, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
        super(name, group, jobName, jobGroup);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setRepeatCount(repeatCount);
        this.setRepeatInterval(repeatInterval);
    }

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }
        Date eTime = this.getEndTime();
        if (eTime != null && startTime != null && eTime.before(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
        return this.endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        Date sTime = this.getStartTime();
        if (sTime != null && endTime != null && sTime.after(endTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        this.endTime = endTime;
    }

    @Override
    public int getRepeatCount() {
        return this.repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        if (repeatCount < 0 && repeatCount != -1) {
            throw new IllegalArgumentException("Repeat count must be >= 0, use the constant REPEAT_INDEFINITELY for infinite.");
        }
        this.repeatCount = repeatCount;
    }

    @Override
    public long getRepeatInterval() {
        return this.repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        if (repeatInterval < 0L) {
            throw new IllegalArgumentException("Repeat interval must be >= 0");
        }
        this.repeatInterval = repeatInterval;
    }

    @Override
    public int getTimesTriggered() {
        return this.timesTriggered;
    }

    public void setTimesTriggered(int timesTriggered) {
        this.timesTriggered = timesTriggered;
    }

    @Override
    protected boolean validateMisfireInstruction(int misfireInstruction) {
        if (misfireInstruction < -1) {
            return false;
        }
        return misfireInstruction <= 5;
    }

    @Override
    public void updateAfterMisfire(Calendar cal) {
        int instr = this.getMisfireInstruction();
        if (instr == -1) {
            return;
        }
        if (instr == 0) {
            instr = this.getRepeatCount() == 0 ? 1 : (this.getRepeatCount() == -1 ? 4 : 2);
        } else if (instr == 1 && this.getRepeatCount() != 0) {
            instr = 3;
        }
        if (instr == 1) {
            this.setNextFireTime(new Date());
        } else if (instr == 5) {
            Date newFireTime = this.getFireTimeAfter(new Date());
            while (newFireTime != null && cal != null && !cal.isTimeIncluded(newFireTime.getTime()) && (newFireTime = this.getFireTimeAfter(newFireTime)) != null) {
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(newFireTime);
                if (c.get(1) <= YEAR_TO_GIVEUP_SCHEDULING_AT) continue;
                newFireTime = null;
            }
            this.setNextFireTime(newFireTime);
        } else if (instr == 4) {
            Date newFireTime = this.getFireTimeAfter(new Date());
            while (newFireTime != null && cal != null && !cal.isTimeIncluded(newFireTime.getTime()) && (newFireTime = this.getFireTimeAfter(newFireTime)) != null) {
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(newFireTime);
                if (c.get(1) <= YEAR_TO_GIVEUP_SCHEDULING_AT) continue;
                newFireTime = null;
            }
            if (newFireTime != null) {
                int timesMissed = this.computeNumTimesFiredBetween(this.nextFireTime, newFireTime);
                this.setTimesTriggered(this.getTimesTriggered() + timesMissed);
            }
            this.setNextFireTime(newFireTime);
        } else if (instr == 2) {
            Date newFireTime = new Date();
            if (this.repeatCount != 0 && this.repeatCount != -1) {
                this.setRepeatCount(this.getRepeatCount() - this.getTimesTriggered());
                this.setTimesTriggered(0);
            }
            if (this.getEndTime() != null && this.getEndTime().before(newFireTime)) {
                this.setNextFireTime(null);
            } else {
                this.setStartTime(newFireTime);
                this.setNextFireTime(newFireTime);
            }
        } else if (instr == 3) {
            Date newFireTime = new Date();
            int timesMissed = this.computeNumTimesFiredBetween(this.nextFireTime, newFireTime);
            if (this.repeatCount != 0 && this.repeatCount != -1) {
                int remainingCount = this.getRepeatCount() - (this.getTimesTriggered() + timesMissed);
                if (remainingCount <= 0) {
                    remainingCount = 0;
                }
                this.setRepeatCount(remainingCount);
                this.setTimesTriggered(0);
            }
            if (this.getEndTime() != null && this.getEndTime().before(newFireTime)) {
                this.setNextFireTime(null);
            } else {
                this.setStartTime(newFireTime);
                this.setNextFireTime(newFireTime);
            }
        }
    }

    @Override
    public void triggered(Calendar calendar) {
        ++this.timesTriggered;
        this.previousFireTime = this.nextFireTime;
        this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
        while (this.nextFireTime != null && calendar != null && !calendar.isTimeIncluded(this.nextFireTime.getTime())) {
            this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
            if (this.nextFireTime == null) break;
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(this.nextFireTime);
            if (c.get(1) <= YEAR_TO_GIVEUP_SCHEDULING_AT) continue;
            this.nextFireTime = null;
        }
    }

    @Override
    public void updateWithNewCalendar(Calendar calendar, long misfireThreshold) {
        this.nextFireTime = this.getFireTimeAfter(this.previousFireTime);
        if (this.nextFireTime == null || calendar == null) {
            return;
        }
        Date now = new Date();
        while (this.nextFireTime != null && !calendar.isTimeIncluded(this.nextFireTime.getTime())) {
            long diff;
            this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
            if (this.nextFireTime == null) break;
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(this.nextFireTime);
            if (c.get(1) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
                this.nextFireTime = null;
            }
            if (this.nextFireTime == null || !this.nextFireTime.before(now) || (diff = now.getTime() - this.nextFireTime.getTime()) < misfireThreshold) continue;
            this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
        }
    }

    @Override
    public Date computeFirstFireTime(Calendar calendar) {
        this.nextFireTime = this.getStartTime();
        while (this.nextFireTime != null && calendar != null && !calendar.isTimeIncluded(this.nextFireTime.getTime())) {
            this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
            if (this.nextFireTime == null) break;
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(this.nextFireTime);
            if (c.get(1) <= YEAR_TO_GIVEUP_SCHEDULING_AT) continue;
            return null;
        }
        return this.nextFireTime;
    }

    @Override
    public Date getNextFireTime() {
        return this.nextFireTime;
    }

    @Override
    public Date getPreviousFireTime() {
        return this.previousFireTime;
    }

    @Override
    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    @Override
    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    @Override
    public Date getFireTimeAfter(Date afterTime) {
        long endMillis;
        if (this.complete) {
            return null;
        }
        if (this.timesTriggered > this.repeatCount && this.repeatCount != -1) {
            return null;
        }
        if (afterTime == null) {
            afterTime = new Date();
        }
        if (this.repeatCount == 0 && afterTime.compareTo(this.getStartTime()) >= 0) {
            return null;
        }
        long startMillis = this.getStartTime().getTime();
        long afterMillis = afterTime.getTime();
        long l = endMillis = this.getEndTime() == null ? Long.MAX_VALUE : this.getEndTime().getTime();
        if (endMillis <= afterMillis) {
            return null;
        }
        if (afterMillis < startMillis) {
            return new Date(startMillis);
        }
        long numberOfTimesExecuted = (afterMillis - startMillis) / this.repeatInterval + 1L;
        if (numberOfTimesExecuted > (long)this.repeatCount && this.repeatCount != -1) {
            return null;
        }
        Date time = new Date(startMillis + numberOfTimesExecuted * this.repeatInterval);
        if (endMillis <= time.getTime()) {
            return null;
        }
        return time;
    }

    public Date getFireTimeBefore(Date end) {
        if (end.getTime() < this.getStartTime().getTime()) {
            return null;
        }
        int numFires = this.computeNumTimesFiredBetween(this.getStartTime(), end);
        return new Date(this.getStartTime().getTime() + (long)numFires * this.repeatInterval);
    }

    public int computeNumTimesFiredBetween(Date start, Date end) {
        if (this.repeatInterval < 1L) {
            return 0;
        }
        long time = end.getTime() - start.getTime();
        return (int)(time / this.repeatInterval);
    }

    @Override
    public Date getFinalFireTime() {
        if (this.repeatCount == 0) {
            return this.startTime;
        }
        if (this.repeatCount == -1) {
            return this.getEndTime() == null ? null : this.getFireTimeBefore(this.getEndTime());
        }
        long lastTrigger = this.startTime.getTime() + (long)this.repeatCount * this.repeatInterval;
        if (this.getEndTime() == null || lastTrigger < this.getEndTime().getTime()) {
            return new Date(lastTrigger);
        }
        return this.getFireTimeBefore(this.getEndTime());
    }

    @Override
    public boolean mayFireAgain() {
        return this.getNextFireTime() != null;
    }

    @Override
    public void validate() throws SchedulerException {
        super.validate();
        if (this.repeatCount != 0 && this.repeatInterval < 1L) {
            throw new SchedulerException("Repeat Interval cannot be zero.");
        }
    }

    @Override
    public boolean hasAdditionalProperties() {
        return false;
    }

    @Override
    public ScheduleBuilder<SimpleTrigger> getScheduleBuilder() {
        SimpleScheduleBuilder sb = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(this.getRepeatInterval()).withRepeatCount(this.getRepeatCount());
        switch (this.getMisfireInstruction()) {
            case 1: {
                sb.withMisfireHandlingInstructionFireNow();
                break;
            }
            case 5: {
                sb.withMisfireHandlingInstructionNextWithExistingCount();
                break;
            }
            case 4: {
                sb.withMisfireHandlingInstructionNextWithRemainingCount();
                break;
            }
            case 2: {
                sb.withMisfireHandlingInstructionNowWithExistingCount();
                break;
            }
            case 3: {
                sb.withMisfireHandlingInstructionNowWithRemainingCount();
            }
        }
        return sb;
    }
}

