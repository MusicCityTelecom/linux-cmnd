/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.TimeZone;
import org.quartz.DateBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public interface CalendarIntervalTrigger
extends Trigger {
    public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
    public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

    public DateBuilder.IntervalUnit getRepeatIntervalUnit();

    public int getRepeatInterval();

    public int getTimesTriggered();

    public TimeZone getTimeZone();

    public boolean isPreserveHourOfDayAcrossDaylightSavings();

    public boolean isSkipDayIfHourDoesNotExist();

    public TriggerBuilder<CalendarIntervalTrigger> getTriggerBuilder();
}

