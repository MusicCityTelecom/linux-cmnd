/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.calendar;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;

public class HolidayCalendar
extends BaseCalendar
implements Calendar,
Serializable {
    static final long serialVersionUID = -7590908752291814693L;
    private TreeSet<Date> dates = new TreeSet();

    public HolidayCalendar() {
    }

    public HolidayCalendar(Calendar baseCalendar) {
        super(baseCalendar);
    }

    public HolidayCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public HolidayCalendar(Calendar baseCalendar, TimeZone timeZone) {
        super(baseCalendar, timeZone);
    }

    @Override
    public Object clone() {
        HolidayCalendar clone = (HolidayCalendar)super.clone();
        clone.dates = new TreeSet<Date>((SortedSet<Date>)this.dates);
        return clone;
    }

    @Override
    public boolean isTimeIncluded(long timeStamp) {
        if (!super.isTimeIncluded(timeStamp)) {
            return false;
        }
        Date lookFor = this.getStartOfDayJavaCalendar(timeStamp).getTime();
        return !this.dates.contains(lookFor);
    }

    @Override
    public long getNextIncludedTime(long timeStamp) {
        long baseTime = super.getNextIncludedTime(timeStamp);
        if (baseTime > 0L && baseTime > timeStamp) {
            timeStamp = baseTime;
        }
        java.util.Calendar day = this.getStartOfDayJavaCalendar(timeStamp);
        while (!this.isTimeIncluded(day.getTime().getTime())) {
            day.add(5, 1);
        }
        return day.getTime().getTime();
    }

    public void addExcludedDate(Date excludedDate) {
        Date date = this.getStartOfDayJavaCalendar(excludedDate.getTime()).getTime();
        this.dates.add(date);
    }

    public void removeExcludedDate(Date dateToRemove) {
        Date date = this.getStartOfDayJavaCalendar(dateToRemove.getTime()).getTime();
        this.dates.remove(date);
    }

    public SortedSet<Date> getExcludedDates() {
        return Collections.unmodifiableSortedSet(this.dates);
    }
}

