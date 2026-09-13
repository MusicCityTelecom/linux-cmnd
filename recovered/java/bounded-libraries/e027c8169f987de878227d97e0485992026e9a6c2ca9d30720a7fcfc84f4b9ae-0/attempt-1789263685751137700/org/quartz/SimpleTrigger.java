/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public interface SimpleTrigger
extends Trigger {
    public static final long serialVersionUID = -3735980074222850397L;
    public static final int MISFIRE_INSTRUCTION_FIRE_NOW = 1;
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT = 2;
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT = 3;
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT = 4;
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT = 5;
    public static final int REPEAT_INDEFINITELY = -1;

    public int getRepeatCount();

    public long getRepeatInterval();

    public int getTimesTriggered();

    public TriggerBuilder<SimpleTrigger> getTriggerBuilder();
}

