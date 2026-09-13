/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.quartz.Calendar;
import org.quartz.spi.OperableTrigger;

public class TriggerUtils {
    private TriggerUtils() {
    }

    public static List<Date> computeFireTimes(OperableTrigger trigg, Calendar cal, int numTimes) {
        Date d;
        LinkedList<Date> lst = new LinkedList<Date>();
        OperableTrigger t = (OperableTrigger)trigg.clone();
        if (t.getNextFireTime() == null) {
            t.computeFirstFireTime(cal);
        }
        for (int i = 0; i < numTimes && (d = t.getNextFireTime()) != null; ++i) {
            lst.add(d);
            t.triggered(cal);
        }
        return Collections.unmodifiableList(lst);
    }

    public static Date computeEndTimeToAllowParticularNumberOfFirings(OperableTrigger trigg, Calendar cal, int numTimes) {
        Date d;
        OperableTrigger t = (OperableTrigger)trigg.clone();
        if (t.getNextFireTime() == null) {
            t.computeFirstFireTime(cal);
        }
        int c = 0;
        Date endTime = null;
        for (int i = 0; i < numTimes && (d = t.getNextFireTime()) != null; ++i) {
            t.triggered(cal);
            if (++c != numTimes) continue;
            endTime = d;
        }
        if (endTime == null) {
            return null;
        }
        endTime = new Date(endTime.getTime() + 1000L);
        return endTime;
    }

    public static List<Date> computeFireTimesBetween(OperableTrigger trigg, Calendar cal, Date from, Date to) {
        Date d;
        LinkedList<Date> lst = new LinkedList<Date>();
        OperableTrigger t = (OperableTrigger)trigg.clone();
        if (t.getNextFireTime() == null) {
            t.setStartTime(from);
            t.setEndTime(to);
            t.computeFirstFireTime(cal);
        }
        while ((d = t.getNextFireTime()) != null) {
            if (d.before(from)) {
                t.triggered(cal);
                continue;
            }
            if (d.after(to)) break;
            lst.add(d);
            t.triggered(cal);
        }
        return Collections.unmodifiableList(lst);
    }
}

