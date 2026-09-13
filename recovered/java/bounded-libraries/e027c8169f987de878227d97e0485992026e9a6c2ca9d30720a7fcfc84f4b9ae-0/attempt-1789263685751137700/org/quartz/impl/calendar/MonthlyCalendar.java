/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.calendar;

import java.io.Serializable;
import java.util.TimeZone;
import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;

public class MonthlyCalendar
extends BaseCalendar
implements Calendar,
Serializable {
    static final long serialVersionUID = 419164961091807944L;
    private static final int MAX_DAYS_IN_MONTH = 31;
    private boolean[] excludeDays = new boolean[31];
    private boolean excludeAll = this.areAllDaysExcluded();

    public MonthlyCalendar() {
        this(null, null);
    }

    public MonthlyCalendar(Calendar baseCalendar) {
        this(baseCalendar, null);
    }

    public MonthlyCalendar(TimeZone timeZone) {
        this(null, timeZone);
    }

    public MonthlyCalendar(Calendar baseCalendar, TimeZone timeZone) {
        super(baseCalendar, timeZone);
    }

    @Override
    public Object clone() {
        MonthlyCalendar clone = (MonthlyCalendar)super.clone();
        clone.excludeDays = (boolean[])this.excludeDays.clone();
        return clone;
    }

    public boolean[] getDaysExcluded() {
        return this.excludeDays;
    }

    public boolean isDayExcluded(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("The day parameter must be in the range of 1 to 31");
        }
        return this.excludeDays[day - 1];
    }

    public void setDaysExcluded(boolean[] days) {
        if (days == null) {
            throw new IllegalArgumentException("The days parameter cannot be null.");
        }
        if (days.length < 31) {
            throw new IllegalArgumentException("The days parameter must have a length of at least 31 elements.");
        }
        this.excludeDays = days;
        this.excludeAll = this.areAllDaysExcluded();
    }

    public void setDayExcluded(int day, boolean exclude) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("The day parameter must be in the range of 1 to 31");
        }
        this.excludeDays[day - 1] = exclude;
        this.excludeAll = this.areAllDaysExcluded();
    }

    public boolean areAllDaysExcluded() {
        for (int i = 1; i <= 31; ++i) {
            if (this.isDayExcluded(i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isTimeIncluded(long timeStamp) {
        if (this.excludeAll) {
            return false;
        }
        if (!super.isTimeIncluded(timeStamp)) {
            return false;
        }
        java.util.Calendar cl = this.createJavaCalendar(timeStamp);
        int day = cl.get(5);
        return !this.isDayExcluded(day);
    }

    @Override
    public long getNextIncludedTime(long timeStamp) {
        java.util.Calendar cl;
        int day;
        if (this.excludeAll) {
            return 0L;
        }
        long baseTime = super.getNextIncludedTime(timeStamp);
        if (baseTime > 0L && baseTime > timeStamp) {
            timeStamp = baseTime;
        }
        if (!this.isDayExcluded(day = (cl = this.getStartOfDayJavaCalendar(timeStamp)).get(5))) {
            return timeStamp;
        }
        while (this.isDayExcluded(day)) {
            cl.add(5, 1);
            day = cl.get(5);
        }
        return cl.getTime().getTime();
    }
}

