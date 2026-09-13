/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimeZone;
import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.impl.calendar.CalendarComparator;

public class AnnualCalendar
extends BaseCalendar
implements Calendar,
Serializable {
    static final long serialVersionUID = 7346867105876610961L;
    private ArrayList<java.util.Calendar> excludeDays = new ArrayList();
    private boolean dataSorted = false;

    public AnnualCalendar() {
    }

    public AnnualCalendar(Calendar baseCalendar) {
        super(baseCalendar);
    }

    public AnnualCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public AnnualCalendar(Calendar baseCalendar, TimeZone timeZone) {
        super(baseCalendar, timeZone);
    }

    @Override
    public Object clone() {
        AnnualCalendar clone = (AnnualCalendar)super.clone();
        clone.excludeDays = new ArrayList<java.util.Calendar>(this.excludeDays);
        return clone;
    }

    public ArrayList<java.util.Calendar> getDaysExcluded() {
        return this.excludeDays;
    }

    public boolean isDayExcluded(java.util.Calendar day) {
        if (day == null) {
            throw new IllegalArgumentException("Parameter day must not be null");
        }
        if (!super.isTimeIncluded(day.getTime().getTime())) {
            return true;
        }
        int dmonth = day.get(2);
        int dday = day.get(5);
        if (!this.dataSorted) {
            Collections.sort(this.excludeDays, new CalendarComparator());
            this.dataSorted = true;
        }
        for (java.util.Calendar cl : this.excludeDays) {
            if (dmonth < cl.get(2)) {
                return false;
            }
            if (dday != cl.get(5) || dmonth != cl.get(2)) continue;
            return true;
        }
        return false;
    }

    public void setDaysExcluded(ArrayList<java.util.Calendar> days) {
        this.excludeDays = days == null ? new ArrayList() : days;
        this.dataSorted = false;
    }

    public void setDayExcluded(java.util.Calendar day, boolean exclude) {
        if (exclude) {
            if (this.isDayExcluded(day)) {
                return;
            }
            this.excludeDays.add(day);
            this.dataSorted = false;
        } else {
            if (!this.isDayExcluded(day)) {
                return;
            }
            this.removeExcludedDay(day, true);
        }
    }

    public void removeExcludedDay(java.util.Calendar day) {
        this.removeExcludedDay(day, false);
    }

    private void removeExcludedDay(java.util.Calendar day, boolean isChecked) {
        if (!isChecked && !this.isDayExcluded(day)) {
            return;
        }
        if (this.excludeDays.remove(day)) {
            return;
        }
        int dmonth = day.get(2);
        int dday = day.get(5);
        for (java.util.Calendar cl : this.excludeDays) {
            if (dmonth != cl.get(2) || dday != cl.get(5)) continue;
            day = cl;
            break;
        }
        this.excludeDays.remove(day);
    }

    @Override
    public boolean isTimeIncluded(long timeStamp) {
        if (!super.isTimeIncluded(timeStamp)) {
            return false;
        }
        java.util.Calendar day = this.createJavaCalendar(timeStamp);
        return !this.isDayExcluded(day);
    }

    @Override
    public long getNextIncludedTime(long timeStamp) {
        java.util.Calendar day;
        long baseTime = super.getNextIncludedTime(timeStamp);
        if (baseTime > 0L && baseTime > timeStamp) {
            timeStamp = baseTime;
        }
        if (!this.isDayExcluded(day = this.getStartOfDayJavaCalendar(timeStamp))) {
            return timeStamp;
        }
        while (this.isDayExcluded(day)) {
            day.add(5, 1);
        }
        return day.getTime().getTime();
    }
}

