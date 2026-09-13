/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.triggers;

import java.util.Date;
import java.util.TimeZone;
import org.quartz.Calendar;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.DateBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CoreTrigger;

public class CalendarIntervalTriggerImpl
extends AbstractTrigger<CalendarIntervalTrigger>
implements CalendarIntervalTrigger,
CoreTrigger {
    private static final long serialVersionUID = -2635982274232850343L;
    private static final int YEAR_TO_GIVEUP_SCHEDULING_AT = java.util.Calendar.getInstance().get(1) + 100;
    private Date startTime = null;
    private Date endTime = null;
    private Date nextFireTime = null;
    private Date previousFireTime = null;
    private int repeatInterval = 0;
    private DateBuilder.IntervalUnit repeatIntervalUnit = DateBuilder.IntervalUnit.DAY;
    private TimeZone timeZone;
    private boolean preserveHourOfDayAcrossDaylightSavings = false;
    private boolean skipDayIfHourDoesNotExist = false;
    private int timesTriggered = 0;
    private boolean complete = false;

    public CalendarIntervalTriggerImpl() {
    }

    public CalendarIntervalTriggerImpl(String name, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        this(name, null, intervalUnit, repeatInterval);
    }

    public CalendarIntervalTriggerImpl(String name, String group, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        this(name, group, new Date(), null, intervalUnit, repeatInterval);
    }

    public CalendarIntervalTriggerImpl(String name, Date startTime, Date endTime, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        this(name, null, startTime, endTime, intervalUnit, repeatInterval);
    }

    public CalendarIntervalTriggerImpl(String name, String group, Date startTime, Date endTime, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        super(name, group);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setRepeatIntervalUnit(intervalUnit);
        this.setRepeatInterval(repeatInterval);
    }

    public CalendarIntervalTriggerImpl(String name, String group, String jobName, String jobGroup, Date startTime, Date endTime, DateBuilder.IntervalUnit intervalUnit, int repeatInterval) {
        super(name, group, jobName, jobGroup);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setRepeatIntervalUnit(intervalUnit);
        this.setRepeatInterval(repeatInterval);
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
    public TimeZone getTimeZone() {
        if (this.timeZone == null) {
            this.timeZone = TimeZone.getDefault();
        }
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public boolean isPreserveHourOfDayAcrossDaylightSavings() {
        return this.preserveHourOfDayAcrossDaylightSavings;
    }

    public void setPreserveHourOfDayAcrossDaylightSavings(boolean preserveHourOfDayAcrossDaylightSavings) {
        this.preserveHourOfDayAcrossDaylightSavings = preserveHourOfDayAcrossDaylightSavings;
    }

    @Override
    public boolean isSkipDayIfHourDoesNotExist() {
        return this.skipDayIfHourDoesNotExist;
    }

    public void setSkipDayIfHourDoesNotExist(boolean skipDayIfHourDoesNotExist) {
        this.skipDayIfHourDoesNotExist = skipDayIfHourDoesNotExist;
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
        return misfireInstruction <= 2;
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
        return this.getFireTimeAfter(afterTime, false);
    }

    protected Date getFireTimeAfter(Date afterTime, boolean ignoreEndTime) {
        long endMillis;
        if (this.complete) {
            return null;
        }
        if (afterTime == null) {
            afterTime = new Date();
        }
        long startMillis = this.getStartTime().getTime();
        long afterMillis = afterTime.getTime();
        long l = endMillis = this.getEndTime() == null ? Long.MAX_VALUE : this.getEndTime().getTime();
        if (!ignoreEndTime && endMillis <= afterMillis) {
            return null;
        }
        if (afterMillis < startMillis) {
            return new Date(startMillis);
        }
        long secondsAfterStart = 1L + (afterMillis - startMillis) / 1000L;
        Date time = null;
        long repeatLong = this.getRepeatInterval();
        java.util.Calendar aTime = java.util.Calendar.getInstance();
        aTime.setTime(afterTime);
        java.util.Calendar sTime = java.util.Calendar.getInstance();
        if (this.timeZone != null) {
            sTime.setTimeZone(this.timeZone);
        }
        sTime.setTime(this.getStartTime());
        sTime.setLenient(true);
        if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.SECOND)) {
            long jumpCount = secondsAfterStart / repeatLong;
            if (secondsAfterStart % repeatLong != 0L) {
                ++jumpCount;
            }
            sTime.add(13, this.getRepeatInterval() * (int)jumpCount);
            time = sTime.getTime();
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.MINUTE)) {
            long jumpCount = secondsAfterStart / (repeatLong * 60L);
            if (secondsAfterStart % (repeatLong * 60L) != 0L) {
                ++jumpCount;
            }
            sTime.add(12, this.getRepeatInterval() * (int)jumpCount);
            time = sTime.getTime();
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.HOUR)) {
            long jumpCount = secondsAfterStart / (repeatLong * 60L * 60L);
            if (secondsAfterStart % (repeatLong * 60L * 60L) != 0L) {
                ++jumpCount;
            }
            sTime.add(11, this.getRepeatInterval() * (int)jumpCount);
            time = sTime.getTime();
        } else {
            int initialHourOfDay = sTime.get(11);
            if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.DAY)) {
                sTime.setLenient(true);
                long jumpCount = secondsAfterStart / (repeatLong * 24L * 60L * 60L);
                if (jumpCount > 20L) {
                    jumpCount = jumpCount < 50L ? (long)((double)jumpCount * 0.8) : (jumpCount < 500L ? (long)((double)jumpCount * 0.9) : (long)((double)jumpCount * 0.95));
                    sTime.add(6, (int)((long)this.getRepeatInterval() * jumpCount));
                }
                while (!sTime.getTime().after(afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(6, this.getRepeatInterval());
                }
                while (this.daylightSavingHourShiftOccurredAndAdvanceNeeded(sTime, initialHourOfDay, afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(6, this.getRepeatInterval());
                }
                time = sTime.getTime();
            } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.WEEK)) {
                sTime.setLenient(true);
                long jumpCount = secondsAfterStart / (repeatLong * 7L * 24L * 60L * 60L);
                if (jumpCount > 20L) {
                    jumpCount = jumpCount < 50L ? (long)((double)jumpCount * 0.8) : (jumpCount < 500L ? (long)((double)jumpCount * 0.9) : (long)((double)jumpCount * 0.95));
                    sTime.add(3, (int)((long)this.getRepeatInterval() * jumpCount));
                }
                while (!sTime.getTime().after(afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(3, this.getRepeatInterval());
                }
                while (this.daylightSavingHourShiftOccurredAndAdvanceNeeded(sTime, initialHourOfDay, afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(3, this.getRepeatInterval());
                }
                time = sTime.getTime();
            } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.MONTH)) {
                sTime.setLenient(true);
                while (!sTime.getTime().after(afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(2, this.getRepeatInterval());
                }
                while (this.daylightSavingHourShiftOccurredAndAdvanceNeeded(sTime, initialHourOfDay, afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(2, this.getRepeatInterval());
                }
                time = sTime.getTime();
            } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.YEAR)) {
                while (!sTime.getTime().after(afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(1, this.getRepeatInterval());
                }
                while (this.daylightSavingHourShiftOccurredAndAdvanceNeeded(sTime, initialHourOfDay, afterTime) && sTime.get(1) < YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    sTime.add(1, this.getRepeatInterval());
                }
                time = sTime.getTime();
            }
        }
        if (!ignoreEndTime && endMillis <= time.getTime()) {
            return null;
        }
        return time;
    }

    private boolean daylightSavingHourShiftOccurredAndAdvanceNeeded(java.util.Calendar newTime, int initialHourOfDay, Date afterTime) {
        if (this.isPreserveHourOfDayAcrossDaylightSavings() && newTime.get(11) != initialHourOfDay) {
            newTime.set(11, initialHourOfDay);
            if (newTime.get(11) != initialHourOfDay) {
                return this.isSkipDayIfHourDoesNotExist();
            }
            return !newTime.getTime().after(afterTime);
        }
        return false;
    }

    @Override
    public Date getFinalFireTime() {
        if (this.complete || this.getEndTime() == null) {
            return null;
        }
        Date fTime = new Date(this.getEndTime().getTime() - 1000L);
        if ((fTime = this.getFireTimeAfter(fTime, true)).equals(this.getEndTime())) {
            return fTime;
        }
        java.util.Calendar lTime = java.util.Calendar.getInstance();
        if (this.timeZone != null) {
            lTime.setTimeZone(this.timeZone);
        }
        lTime.setTime(fTime);
        lTime.setLenient(true);
        if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.SECOND)) {
            lTime.add(13, -1 * this.getRepeatInterval());
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.MINUTE)) {
            lTime.add(12, -1 * this.getRepeatInterval());
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.HOUR)) {
            lTime.add(11, -1 * this.getRepeatInterval());
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.DAY)) {
            lTime.add(6, -1 * this.getRepeatInterval());
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.WEEK)) {
            lTime.add(3, -1 * this.getRepeatInterval());
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.MONTH)) {
            lTime.add(2, -1 * this.getRepeatInterval());
        } else if (this.getRepeatIntervalUnit().equals((Object)DateBuilder.IntervalUnit.YEAR)) {
            lTime.add(1, -1 * this.getRepeatInterval());
        }
        return lTime.getTime();
    }

    @Override
    public boolean mayFireAgain() {
        return this.getNextFireTime() != null;
    }

    @Override
    public void validate() throws SchedulerException {
        super.validate();
        if (this.repeatInterval < 1) {
            throw new SchedulerException("Repeat Interval cannot be zero.");
        }
    }

    @Override
    public ScheduleBuilder<CalendarIntervalTrigger> getScheduleBuilder() {
        CalendarIntervalScheduleBuilder cb = CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withInterval(this.getRepeatInterval(), this.getRepeatIntervalUnit());
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
}

