/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.impl.triggers;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.quartz.Calendar;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.ScheduleBuilder;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CoreTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronTriggerImpl
extends AbstractTrigger<CronTrigger>
implements CronTrigger,
CoreTrigger {
    private static final long serialVersionUID = -8644953146451592766L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CronTriggerImpl.class);
    protected static final int YEAR_TO_GIVEUP_SCHEDULING_AT = CronExpression.MAX_YEAR;
    private CronExpression cronEx = null;
    private Date startTime = null;
    private Date endTime = null;
    private Date nextFireTime = null;
    private Date previousFireTime = null;
    private transient TimeZone timeZone = null;

    public CronTriggerImpl() {
        this.setStartTime(new Date());
        this.setTimeZone(TimeZone.getDefault());
    }

    @Deprecated
    public CronTriggerImpl(String name) {
        this(name, null);
    }

    @Deprecated
    public CronTriggerImpl(String name, String group) {
        super(name, group);
        this.setStartTime(new Date());
        this.setTimeZone(TimeZone.getDefault());
    }

    @Deprecated
    public CronTriggerImpl(String name, String group, String cronExpression) throws ParseException {
        super(name, group);
        this.setCronExpression(cronExpression);
        this.setStartTime(new Date());
        this.setTimeZone(TimeZone.getDefault());
    }

    @Deprecated
    public CronTriggerImpl(String name, String group, String jobName, String jobGroup) {
        super(name, group, jobName, jobGroup);
        this.setStartTime(new Date());
        this.setTimeZone(TimeZone.getDefault());
    }

    @Deprecated
    public CronTriggerImpl(String name, String group, String jobName, String jobGroup, String cronExpression) throws ParseException {
        this(name, group, jobName, jobGroup, null, null, cronExpression, TimeZone.getDefault());
    }

    @Deprecated
    public CronTriggerImpl(String name, String group, String jobName, String jobGroup, String cronExpression, TimeZone timeZone) throws ParseException {
        this(name, group, jobName, jobGroup, null, null, cronExpression, timeZone);
    }

    @Deprecated
    public CronTriggerImpl(String name, String group, String jobName, String jobGroup, Date startTime, Date endTime, String cronExpression) throws ParseException {
        super(name, group, jobName, jobGroup);
        this.setCronExpression(cronExpression);
        if (startTime == null) {
            startTime = new Date();
        }
        this.setStartTime(startTime);
        if (endTime != null) {
            this.setEndTime(endTime);
        }
        this.setTimeZone(TimeZone.getDefault());
    }

    @Deprecated
    public CronTriggerImpl(String name, String group, String jobName, String jobGroup, Date startTime, Date endTime, String cronExpression, TimeZone timeZone) throws ParseException {
        super(name, group, jobName, jobGroup);
        this.setCronExpression(cronExpression);
        if (startTime == null) {
            startTime = new Date();
        }
        this.setStartTime(startTime);
        if (endTime != null) {
            this.setEndTime(endTime);
        }
        if (timeZone == null) {
            this.setTimeZone(TimeZone.getDefault());
        } else {
            this.setTimeZone(timeZone);
        }
    }

    @Override
    public Object clone() {
        CronTriggerImpl copy = (CronTriggerImpl)super.clone();
        if (this.cronEx != null) {
            copy.setCronExpression(new CronExpression(this.cronEx));
        }
        return copy;
    }

    public void setCronExpression(String cronExpression) throws ParseException {
        TimeZone origTz = this.getTimeZone();
        this.cronEx = new CronExpression(cronExpression);
        this.cronEx.setTimeZone(origTz);
    }

    @Override
    public String getCronExpression() {
        return this.cronEx == null ? null : this.cronEx.getCronExpression();
    }

    public void setCronExpression(CronExpression cronExpression) {
        this.cronEx = cronExpression;
        this.timeZone = cronExpression.getTimeZone();
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
        if (eTime != null && eTime.before(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        java.util.Calendar cl = java.util.Calendar.getInstance();
        cl.setTime(startTime);
        cl.set(14, 0);
        this.startTime = cl.getTime();
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
    public TimeZone getTimeZone() {
        if (this.cronEx != null) {
            return this.cronEx.getTimeZone();
        }
        if (this.timeZone == null) {
            this.timeZone = TimeZone.getDefault();
        }
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        if (this.cronEx != null) {
            this.cronEx.setTimeZone(timeZone);
        }
        this.timeZone = timeZone;
    }

    @Override
    public Date getFireTimeAfter(Date afterTime) {
        if (afterTime == null) {
            afterTime = new Date();
        }
        if (this.getStartTime().after(afterTime)) {
            afterTime = new Date(this.getStartTime().getTime() - 1000L);
        }
        if (this.getEndTime() != null && afterTime.compareTo(this.getEndTime()) >= 0) {
            return null;
        }
        Date pot = this.getTimeAfter(afterTime);
        if (this.getEndTime() != null && pot != null && pot.after(this.getEndTime())) {
            return null;
        }
        return pot;
    }

    @Override
    public Date getFinalFireTime() {
        Date resultTime;
        if (this.getEndTime() != null) {
            resultTime = this.getTimeBefore(new Date(this.getEndTime().getTime() + 1000L));
        } else {
            Date date = resultTime = this.cronEx == null ? null : this.cronEx.getFinalFireTime();
        }
        if (resultTime != null && this.getStartTime() != null && resultTime.before(this.getStartTime())) {
            return null;
        }
        return resultTime;
    }

    @Override
    public boolean mayFireAgain() {
        return this.getNextFireTime() != null;
    }

    @Override
    protected boolean validateMisfireInstruction(int misfireInstruction) {
        return misfireInstruction >= -1 && misfireInstruction <= 2;
    }

    @Override
    public void updateAfterMisfire(Calendar cal) {
        int instr = this.getMisfireInstruction();
        if (instr == -1) {
            return;
        }
        if (instr == 0) {
            instr = 1;
        }
        if (instr == 2) {
            Date newFireTime = this.getFireTimeAfter(new Date());
            while (newFireTime != null && cal != null && !cal.isTimeIncluded(newFireTime.getTime())) {
                newFireTime = this.getFireTimeAfter(newFireTime);
            }
            this.setNextFireTime(newFireTime);
        } else if (instr == 1) {
            this.setNextFireTime(new Date());
        }
    }

    public boolean willFireOn(java.util.Calendar test) {
        return this.willFireOn(test, false);
    }

    public boolean willFireOn(java.util.Calendar test, boolean dayOnly) {
        test = (java.util.Calendar)test.clone();
        test.set(14, 0);
        if (dayOnly) {
            test.set(11, 0);
            test.set(12, 0);
            test.set(13, 0);
        }
        Date testTime = test.getTime();
        Date fta = this.getFireTimeAfter(new Date(test.getTime().getTime() - 1000L));
        if (fta == null) {
            return false;
        }
        java.util.Calendar p = java.util.Calendar.getInstance(test.getTimeZone());
        p.setTime(fta);
        int year = p.get(1);
        int month = p.get(2);
        int day = p.get(5);
        if (dayOnly) {
            return year == test.get(1) && month == test.get(2) && day == test.get(5);
        }
        while (fta.before(testTime)) {
            fta = this.getFireTimeAfter(fta);
        }
        return fta.equals(testTime);
    }

    @Override
    public void triggered(Calendar calendar) {
        this.previousFireTime = this.nextFireTime;
        this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
        while (this.nextFireTime != null && calendar != null && !calendar.isTimeIncluded(this.nextFireTime.getTime())) {
            this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
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
            GregorianCalendar c = new GregorianCalendar();
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
        this.nextFireTime = this.getFireTimeAfter(new Date(this.getStartTime().getTime() - 1000L));
        while (this.nextFireTime != null && calendar != null && !calendar.isTimeIncluded(this.nextFireTime.getTime())) {
            this.nextFireTime = this.getFireTimeAfter(this.nextFireTime);
        }
        return this.nextFireTime;
    }

    @Override
    public String getExpressionSummary() {
        return this.cronEx == null ? null : this.cronEx.getExpressionSummary();
    }

    @Override
    public boolean hasAdditionalProperties() {
        return false;
    }

    @Override
    public ScheduleBuilder<CronTrigger> getScheduleBuilder() {
        CronScheduleBuilder cb = CronScheduleBuilder.cronSchedule(this.getCronExpression()).inTimeZone(this.getTimeZone());
        int misfireInstruction = this.getMisfireInstruction();
        switch (misfireInstruction) {
            case 0: {
                break;
            }
            case 2: {
                cb.withMisfireHandlingInstructionDoNothing();
                break;
            }
            case 1: {
                cb.withMisfireHandlingInstructionFireAndProceed();
                break;
            }
            case -1: {
                cb.withMisfireHandlingInstructionIgnoreMisfires();
                break;
            }
            default: {
                LOGGER.warn("Unrecognized misfire policy {}. Derived builder will use the default cron trigger behavior (MISFIRE_INSTRUCTION_FIRE_ONCE_NOW)", (Object)misfireInstruction);
            }
        }
        return cb;
    }

    protected Date getTimeAfter(Date afterTime) {
        return this.cronEx == null ? null : this.cronEx.getTimeAfter(afterTime);
    }

    protected Date getTimeBefore(Date eTime) {
        return this.cronEx == null ? null : this.cronEx.getTimeBefore(eTime);
    }
}

