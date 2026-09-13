/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

public interface Trigger
extends Serializable,
Cloneable,
Comparable<Trigger> {
    public static final long serialVersionUID = -3904243490805975570L;
    public static final int MISFIRE_INSTRUCTION_SMART_POLICY = 0;
    public static final int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;
    public static final int DEFAULT_PRIORITY = 5;

    public TriggerKey getKey();

    public JobKey getJobKey();

    public String getDescription();

    public String getCalendarName();

    public JobDataMap getJobDataMap();

    public int getPriority();

    public boolean mayFireAgain();

    public Date getStartTime();

    public Date getEndTime();

    public Date getNextFireTime();

    public Date getPreviousFireTime();

    public Date getFireTimeAfter(Date var1);

    public Date getFinalFireTime();

    public int getMisfireInstruction();

    public TriggerBuilder<? extends Trigger> getTriggerBuilder();

    public ScheduleBuilder<? extends Trigger> getScheduleBuilder();

    public boolean equals(Object var1);

    @Override
    public int compareTo(Trigger var1);

    public static class TriggerTimeComparator
    implements Comparator<Trigger>,
    Serializable {
        private static final long serialVersionUID = -3904243490805975570L;

        public static int compare(Date nextFireTime1, int priority1, TriggerKey key1, Date nextFireTime2, int priority2, TriggerKey key2) {
            int comp;
            if (nextFireTime1 != null || nextFireTime2 != null) {
                if (nextFireTime1 == null) {
                    return 1;
                }
                if (nextFireTime2 == null) {
                    return -1;
                }
                if (nextFireTime1.before(nextFireTime2)) {
                    return -1;
                }
                if (nextFireTime1.after(nextFireTime2)) {
                    return 1;
                }
            }
            if ((comp = priority2 - priority1) != 0) {
                return comp;
            }
            return key1.compareTo(key2);
        }

        @Override
        public int compare(Trigger t1, Trigger t2) {
            return TriggerTimeComparator.compare(t1.getNextFireTime(), t1.getPriority(), t1.getKey(), t2.getNextFireTime(), t2.getPriority(), t2.getKey());
        }
    }

    public static enum CompletedExecutionInstruction {
        NOOP,
        RE_EXECUTE_JOB,
        SET_TRIGGER_COMPLETE,
        DELETE_TRIGGER,
        SET_ALL_JOB_TRIGGERS_COMPLETE,
        SET_TRIGGER_ERROR,
        SET_ALL_JOB_TRIGGERS_ERROR;

    }

    public static enum TriggerState {
        NONE,
        NORMAL,
        PAUSED,
        COMPLETE,
        ERROR,
        BLOCKED;

    }
}

