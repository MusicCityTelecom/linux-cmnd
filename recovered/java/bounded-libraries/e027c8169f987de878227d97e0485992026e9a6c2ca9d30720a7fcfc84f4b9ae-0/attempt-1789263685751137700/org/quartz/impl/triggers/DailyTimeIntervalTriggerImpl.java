/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.triggers;

import java.util.Date;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.DateBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.TimeOfDay;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CoreTrigger;

public class DailyTimeIntervalTriggerImpl
extends AbstractTrigger<DailyTimeIntervalTrigger>
implements DailyTimeIntervalTrigger,
CoreTrigger {
    private static final long serialVersionUID = -632667786771388749L;
    private static final int YEAR_TO_GIVEUP_SCHEDULING_AT = java.util.Calendar.getInstance().get(1) + 100;
    private Date startTime = null;
    private Date endTime = null;
    private Date nextFireTime = null;
    private Date previousFireTime = null;
    private int repeatCount = -1;
    private int repeatInterval = 1;
    private DateBuilder.IntervalUnit repeatIntervalUnit = DateBuilder.IntervalUnit.MINUTE;
    private Set<Integer> daysOfWeek;
    private TimeOfDay startTimeOfDay;
    private TimeOfDay endTimeOfDay;
    private int timesTriggered = 0;
    private boolean complete = false;

    public DailyTimeIntervalTriggerImpl() {
    }

    public DailyTimeIntervalTriggerImpl(String name, TimeOfDay startTimeOfDay, TimeOfDay endTimeOfDay, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        this(name, null, startTimeOfDay, endTimeOfDay, intervalUnit, repeatInterval);
    }

    public DailyTimeIntervalTriggerImpl(String name, String group, TimeOfDay startTimeOfDay, TimeOfDay endTimeOfDay, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        this(name, group, new Date(), null, startTimeOfDay, endTimeOfDay, intervalUnit, repeatInterval);
    }

    public DailyTimeIntervalTriggerImpl(String name, Date startTime, Date endTime, TimeOfDay startTimeOfDay, TimeOfDay endTimeOfDay, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        this(name, null, startTime, endTime, startTimeOfDay, endTimeOfDay, intervalUnit, repeatInterval);
    }

    public DailyTimeIntervalTriggerImpl(String name, String group, Date startTime, Date endTime, TimeOfDay startTimeOfDay, TimeOfDay endTimeOfDay, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        super(name, group);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setRepeatIntervalUnit(intervalUnit);
        this.setRepeatInterval(repeatInterval);
        this.setStartTimeOfDay(startTimeOfDay);
        this.setEndTimeOfDay(endTimeOfDay);
    }

    public DailyTimeIntervalTriggerImpl(String name, String group, String jobName, String jobGroup, Date startTime, Date endTime, TimeOfDay startTimeOfDay, TimeOfDay endTimeOfDay, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        super(name, group, jobName, jobGroup);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setRepeatIntervalUnit(intervalUnit);
        this.setRepeatInterval(repeatInterval);
        this.setStartTimeOfDay(startTimeOfDay);
        this.setEndTimeOfDay(endTimeOfDay);
    }

    @Override
    public Date getStartTime() {
        if (this.startTime == null) {
            this.startTime = new Date();
        }
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
    public DateBuilder.IntervalUnit getRepeatIntervalUnit() {
        return this.repeatIntervalUnit;
    }

    public void setRepeatIntervalUnit(DateBuilder.IntervalUnit intervalUnit) {
        if (this.repeatIntervalUnit == null || !this.repeatIntervalUnit.equals((Object)DateBuilder.IntervalUnit.SECOND) && !this.repeatIntervalUnit.equals((Object)DateBuilder.IntervalUnit.MINUTE) && !this.repeatIntervalUnit.equals((Object)DateBuilder.IntervalUnit.HOUR)) {
            throw new IllegalArgumentException("Invalid repeat IntervalUnit (must be SECOND, MINUTE or HOUR).");
        }
        this.repeatIntervalUnit = intervalUnit;
    }

    @Override
    public int getRepeatInterval() {
        return this.repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        if (repeatInterval < 0) {
            throw new IllegalArgumentException("Repeat interval must be >= 1");
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
        if (this.nextFireTime == null) {
            this.complete = true;
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
        this.nextFireTime = this.getFireTimeAfter(new Date(this.getStartTime().getTime() - 1000L));
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

    private java.util.Calendar createCalendarTime(Date dateTime) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(dateTime);
        return cal;
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
        Date fireTime;
        if (this.complete) {
            return null;
        }
        if (this.repeatCount != -1 && this.timesTriggered > this.repeatCount) {
            return null;
        }
        if ((afterTime = afterTime == null ? new Date(System.currentTimeMillis() + 1000L) : new Date(afterTime.getTime() + 1000L)).before(this.startTime)) {
            afterTime = this.startTime;
        }
        boolean afterTimePastEndTimeOfDay = false;
        if (this.endTimeOfDay != null) {
            boolean bl = afterTimePastEndTimeOfDay = afterTime.getTime() > this.endTimeOfDay.getTimeOfDayForDate(afterTime).getTime();
        }
        if ((fireTime = this.advanceToNextDayOfWeekIfNecessary(afterTime, afterTimePastEndTimeOfDay)) == null) {
            return null;
        }
        Date fireTimeEndDate = null;
        fireTimeEndDate = this.endTimeOfDay == null ? new TimeOfDay(23, 59, 59).getTimeOfDayForDate(fireTime) : this.endTimeOfDay.getTimeOfDayForDate(fireTime);
        Date fireTimeStartDate = this.startTimeOfDay.getTimeOfDayForDate(fireTime);
        if (fireTime.before(fireTimeStartDate)) {
            return fireTimeStartDate;
        }
        long fireMillis = fireTime.getTime();
        long startMillis = fireTimeStartDate.getTime();
        long secondsAfterStart = (fireMillis - startMillis) / 1000L;
        long repeatLong = this.getRepeatInterval();
        java.util.Calendar sTime = this.createCalendarTime(fireTimeStartDate);
        DateBuilder.IntervalUnit repeatUnit = this.getRepeatIntervalUnit();
        if (repeatUnit.equals((Object)DateBuilder.IntervalUnit.SECOND)) {
            long jumpCount = secondsAfterStart / repeatLong;
            if (secondsAfterStart % repeatLong != 0L) {
                ++jumpCount;
            }
            sTime.add(13, this.getRepeatInterval() * (int)jumpCount);
            fireTime = sTime.getTime();
        } else if (repeatUnit.equals((Object)DateBuilder.IntervalUnit.MINUTE)) {
            long jumpCount = secondsAfterStart / (repeatLong * 60L);
            if (secondsAfterStart % (repeatLong * 60L) != 0L) {
                ++jumpCount;
            }
            sTime.add(12, this.getRepeatInterval() * (int)jumpCount);
            fireTime = sTime.getTime();
        } else if (repeatUnit.equals((Object)DateBuilder.IntervalUnit.HOUR)) {
            long jumpCount = secondsAfterStart / (repeatLong * 60L * 60L);
            if (secondsAfterStart % (repeatLong * 60L * 60L) != 0L) {
                ++jumpCount;
            }
            sTime.add(11, this.getRepeatInterval() * (int)jumpCount);
            fireTime = sTime.getTime();
        }
        if (fireTime.after(fireTimeEndDate)) {
            fireTime = this.advanceToNextDayOfWeekIfNecessary(fireTime, this.isSameDay(fireTime, fireTimeEndDate));
            fireTime = this.startTimeOfDay.getTimeOfDayForDate(fireTime);
        }
        return fireTime;
    }

    private boolean isSameDay(Date d1, Date d2) {
        java.util.Calendar c1 = this.createCalendarTime(d1);
        java.util.Calendar c2 = this.createCalendarTime(d2);
        return c1.get(1) == c2.get(1) && c1.get(6) == c2.get(6);
    }

    private Date advanceToNextDayOfWeekIfNecessary(Date fireTime, boolean forceToAdvanceNextDay) {
        Date eTime;
        TimeOfDay sTimeOfDay = this.getStartTimeOfDay();
        Date fireTimeStartDate = sTimeOfDay.getTimeOfDayForDate(fireTime);
        java.util.Calendar fireTimeStartDateCal = this.createCalendarTime(fireTimeStartDate);
        int dayOfWeekOfFireTime = fireTimeStartDateCal.get(7);
        Set<Integer> daysOfWeekToFire = this.getDaysOfWeek();
        if (forceToAdvanceNextDay || !daysOfWeekToFire.contains(dayOfWeekOfFireTime)) {
            for (int i = 1; i <= 7; ++i) {
                fireTimeStartDateCal.add(5, 1);
                dayOfWeekOfFireTime = fireTimeStartDateCal.get(7);
                if (!daysOfWeekToFire.contains(dayOfWeekOfFireTime)) continue;
                fireTime = fireTimeStartDateCal.getTime();
                break;
            }
        }
        if ((eTime = this.getEndTime()) != null && fireTime.getTime() > eTime.getTime()) {
            return null;
        }
        return fireTime;
    }

    @Override
    public Date getFinalFireTime() {
        if (this.complete || this.getEndTime() == null) {
            return null;
        }
        Date eTime = this.getEndTime();
        if (this.endTimeOfDay != null) {
            Date endTimeOfDayDate = this.endTimeOfDay.getTimeOfDayForDate(eTime);
            if (eTime.getTime() < endTimeOfDayDate.getTime()) {
                eTime = endTimeOfDayDate;
            }
        }
        return eTime;
    }

    @Override
    public boolean mayFireAgain() {
        return this.getNextFireTime() != null;
    }

    @Override
    public void validate() throws SchedulerException {
        super.validate();
        if (this.repeatIntervalUnit == null || !this.repeatIntervalUnit.equals((Object)DateBuilder.IntervalUnit.SECOND) && !this.repeatIntervalUnit.equals((Object)DateBuilder.IntervalUnit.MINUTE) && !this.repeatIntervalUnit.equals((Object)DateBuilder.IntervalUnit.HOUR)) {
            throw new SchedulerException("Invalid repeat IntervalUnit (must be SECOND, MINUTE or HOUR).");
        }
        if (this.repeatInterval < 1) {
            throw new SchedulerException("Repeat Interval cannot be zero.");
        }
        long secondsInHour = 86400L;
        if (this.repeatIntervalUnit == DateBuilder.IntervalUnit.SECOND && (long)this.repeatInterval > secondsInHour) {
            throw new SchedulerException("repeatInterval can not exceed 24 hours (" + secondsInHour + " seconds). Given " + this.repeatInterval);
        }
        if (this.repeatIntervalUnit == DateBuilder.IntervalUnit.MINUTE && (long)this.repeatInterval > secondsInHour / 60L) {
            throw new SchedulerException("repeatInterval can not exceed 24 hours (" + secondsInHour / 60L + " minutes). Given " + this.repeatInterval);
        }
        if (this.repeatIntervalUnit == DateBuilder.IntervalUnit.HOUR && this.repeatInterval > 24) {
            throw new SchedulerException("repeatInterval can not exceed 24 hours. Given " + this.repeatInterval + " hours.");
        }
        if (this.getEndTimeOfDay() != null && !this.getStartTimeOfDay().equals(this.getEndTimeOfDay()) && !this.getStartTimeOfDay().before(this.getEndTimeOfDay())) {
            throw new SchedulerException("StartTimeOfDay " + this.startTimeOfDay + " should not come after endTimeOfDay " + this.endTimeOfDay);
        }
    }

    @Override
    public Set<Integer> getDaysOfWeek() {
        if (this.daysOfWeek == null) {
            this.daysOfWeek = DailyTimeIntervalScheduleBuilder.ALL_DAYS_OF_THE_WEEK;
        }
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(Set<Integer> daysOfWeek) {
        if (daysOfWeek == null || daysOfWeek.size() == 0) {
            throw new IllegalArgumentException("DaysOfWeek set must be a set that contains at least one day.");
        }
        if (daysOfWeek.size() == 0) {
            throw new IllegalArgumentException("DaysOfWeek set must contain at least one day.");
        }
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public TimeOfDay getStartTimeOfDay() {
        if (this.startTimeOfDay == null) {
            this.startTimeOfDay = new TimeOfDay(0, 0, 0);
        }
        return this.startTimeOfDay;
    }

    public void setStartTimeOfDay(TimeOfDay startTimeOfDay) {
        if (startTimeOfDay == null) {
            throw new IllegalArgumentException("Start time of day cannot be null");
        }
        TimeOfDay eTime = this.getEndTimeOfDay();
        if (eTime != null && eTime.before(startTimeOfDay)) {
            throw new IllegalArgumentException("End time of day cannot be before start time of day");
        }
        this.startTimeOfDay = startTimeOfDay;
    }

    @Override
    public TimeOfDay getEndTimeOfDay() {
        return this.endTimeOfDay;
    }

    public void setEndTimeOfDay(TimeOfDay endTimeOfDay) {
        if (endTimeOfDay == null) {
            throw new IllegalArgumentException("End time of day cannot be null");
        }
        TimeOfDay sTime = this.getStartTimeOfDay();
        if (sTime != null && endTimeOfDay.before(endTimeOfDay)) {
            throw new IllegalArgumentException("End time of day cannot be before start time of day");
        }
        this.endTimeOfDay = endTimeOfDay;
    }

    @Override
    public ScheduleBuilder<DailyTimeIntervalTrigger> getScheduleBuilder() {
        DailyTimeIntervalScheduleBuilder cb = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule().withInterval(this.getRepeatInterval(), this.getRepeatIntervalUnit()).onDaysOfTheWeek(this.getDaysOfWeek()).startingDailyAt(this.getStartTimeOfDay()).endingDailyAt(this.getEndTimeOfDay());
        switch (this.getMisfireInstruction()) {
            case 2: {
                cb.withMisfireHandlingInstructionDoNothing();
                break;
            }
            case 1: {
                cb.withMisfireHandlingInstructionFireAndProceed();
            }
        }
        return cb;
    }

    @Override
    public boolean hasAdditionalProperties() {
        return false;
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
}

