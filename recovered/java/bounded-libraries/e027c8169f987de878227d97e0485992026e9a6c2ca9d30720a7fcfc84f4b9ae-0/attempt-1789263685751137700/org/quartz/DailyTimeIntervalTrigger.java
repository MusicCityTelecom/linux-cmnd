/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.Set;
import org.quartz.DateBuilder;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public interface DailyTimeIntervalTrigger
extends Trigger {
    public static final int REPEAT_INDEFINITELY = -1;
    public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
    public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

    public DateBuilder.IntervalUnit getRepeatIntervalUnit();

    public int getRepeatCount();

    public int getRepeatInterval();

    public TimeOfDay getStartTimeOfDay();

    public TimeOfDay getEndTimeOfDay();

    public Set<Integer> getDaysOfWeek();

    public int getTimesTriggered();

    public TriggerBuilder<DailyTimeIntervalTrigger> getTriggerBuilder();
}

