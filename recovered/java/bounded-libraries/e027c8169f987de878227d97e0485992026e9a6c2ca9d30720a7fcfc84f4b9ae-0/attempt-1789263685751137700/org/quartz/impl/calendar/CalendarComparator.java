/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.calendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

class CalendarComparator
implements Comparator<Calendar>,
Serializable {
    private static final long serialVersionUID = 7346867105876610961L;

    @Override
    public int compare(Calendar c1, Calendar c2) {
        int month1 = c1.get(2);
        int month2 = c2.get(2);
        int day1 = c1.get(5);
        int day2 = c2.get(5);
        if (month1 < month2) {
            return -1;
        }
        if (month1 > month2) {
            return 1;
        }
        if (day1 < day2) {
            return -1;
        }
        if (day1 > day2) {
            return 1;
        }
        return 0;
    }
}

