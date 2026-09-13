/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.calendar;

import java.io.Serializable;
import java.util.TimeZone;
import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;

public class WeeklyCalendar
extends BaseCalendar
implements Calendar,
Serializable {
    static final long serialVersionUID = -6809298821229007586L;
    private boolean[] excludeDays = new boolean[8];
    private boolean excludeAll = false;

    public WeeklyCalendar() {
        this(null, null);
    }

    public WeeklyCalendar(Calendar baseCalendar) {
        this(baseCalendar, null);
    }

    public WeeklyCalendar(TimeZone timeZone) {
        super(null, timeZone);
    }

    public WeeklyCalendar(Calendar baseCalendar, TimeZone timeZone) {
        super(baseCalendar, timeZone);
        this.excludeDays[1] = true;
        this.excludeDays[7] = true;
        this.excludeAll = this.areAllDaysExcluded();
    }

    @Override
    public Object clone() {
        WeeklyCalendar clone = (WeeklyCalendar)super.clone();
        clone.excludeDays = (boolean[])this.excludeDays.clone();
        return clone;
    }

    public boolean[] getDaysExcluded() {
        return this.excludeDays;
    }

    public boolean isDayExcluded(int wday) {
        return this.excludeDays[wday];
    }

    public void setDaysExcluded(boolean[] weekDays) {
        if (weekDays == null) {
            return;
        }
        this.excludeDays = weekDays;
        this.excludeAll = this.areAllDaysExcluded();
    }

    public void setDayExcluded(int wday, boolean exclude) {
        this.excludeDays[wday] = exclude;
        this.excludeAll = this.areAllDaysExcluded();
    }

    public boolean areAllDaysExcluded() {
        return this.isDayExcluded(1) && this.isDayExcluded(2) && this.isDayExcluded(3) && this.isDayExcluded(4) && this.isDayExcluded(5) && this.isDayExcluded(6) && this.isDayExcluded(7);
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
        int wday = cl.get(7);
        return !this.isDayExcluded(wday);
    }

    @Override
    public long getNextIncludedTime(long timeStamp) {
        java.util.Calendar cl;
        int wday;
        if (this.excludeAll) {
            return 0L;
        }
        long baseTime = super.getNextIncludedTime(timeStamp);
        if (baseTime > 0L && baseTime > timeStamp) {
            timeStamp = baseTime;
        }
        if (!this.isDayExcluded(wday = (cl = this.getStartOfDayJavaCalendar(timeStamp)).get(7))) {
            return timeStamp;
        }
        while (this.isDayExcluded(wday)) {
            cl.add(5, 1);
            wday = cl.get(7);
        }
        return cl.getTime().getTime();
    }
}

