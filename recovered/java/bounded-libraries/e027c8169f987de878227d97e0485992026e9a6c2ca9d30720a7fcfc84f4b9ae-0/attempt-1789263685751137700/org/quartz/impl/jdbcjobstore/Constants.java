/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

public interface Constants {
    public static final String TABLE_JOB_DETAILS = "JOB_DETAILS";
    public static final String TABLE_TRIGGERS = "TRIGGERS";
    public static final String TABLE_SIMPLE_TRIGGERS = "SIMPLE_TRIGGERS";
    public static final String TABLE_CRON_TRIGGERS = "CRON_TRIGGERS";
    public static final String TABLE_BLOB_TRIGGERS = "BLOB_TRIGGERS";
    public static final String TABLE_FIRED_TRIGGERS = "FIRED_TRIGGERS";
    public static final String TABLE_CALENDARS = "CALENDARS";
    public static final String TABLE_PAUSED_TRIGGERS = "PAUSED_TRIGGER_GRPS";
    public static final String TABLE_LOCKS = "LOCKS";
    public static final String TABLE_SCHEDULER_STATE = "SCHEDULER_STATE";
    public static final String COL_SCHEDULER_NAME = "SCHED_NAME";
    public static final String COL_JOB_NAME = "JOB_NAME";
    public static final String COL_JOB_GROUP = "JOB_GROUP";
    public static final String COL_IS_DURABLE = "IS_DURABLE";
    public static final String COL_IS_VOLATILE = "IS_VOLATILE";
    public static final String COL_IS_NONCONCURRENT = "IS_NONCONCURRENT";
    public static final String COL_IS_UPDATE_DATA = "IS_UPDATE_DATA";
    public static final String COL_REQUESTS_RECOVERY = "REQUESTS_RECOVERY";
    public static final String COL_JOB_DATAMAP = "JOB_DATA";
    public static final String COL_JOB_CLASS = "JOB_CLASS_NAME";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_TRIGGER_NAME = "TRIGGER_NAME";
    public static final String COL_TRIGGER_GROUP = "TRIGGER_GROUP";
    public static final String COL_NEXT_FIRE_TIME = "NEXT_FIRE_TIME";
    public static final String COL_PREV_FIRE_TIME = "PREV_FIRE_TIME";
    public static final String COL_TRIGGER_STATE = "TRIGGER_STATE";
    public static final String COL_TRIGGER_TYPE = "TRIGGER_TYPE";
    public static final String COL_START_TIME = "START_TIME";
    public static final String COL_END_TIME = "END_TIME";
    public static final String COL_PRIORITY = "PRIORITY";
    public static final String COL_MISFIRE_INSTRUCTION = "MISFIRE_INSTR";
    public static final String ALIAS_COL_NEXT_FIRE_TIME = "ALIAS_NXT_FR_TM";
    public static final String COL_REPEAT_COUNT = "REPEAT_COUNT";
    public static final String COL_REPEAT_INTERVAL = "REPEAT_INTERVAL";
    public static final String COL_TIMES_TRIGGERED = "TIMES_TRIGGERED";
    public static final String COL_CRON_EXPRESSION = "CRON_EXPRESSION";
    public static final String COL_BLOB = "BLOB_DATA";
    public static final String COL_TIME_ZONE_ID = "TIME_ZONE_ID";
    public static final String COL_INSTANCE_NAME = "INSTANCE_NAME";
    public static final String COL_FIRED_TIME = "FIRED_TIME";
    public static final String COL_SCHED_TIME = "SCHED_TIME";
    public static final String COL_ENTRY_ID = "ENTRY_ID";
    public static final String COL_ENTRY_STATE = "STATE";
    public static final String COL_CALENDAR_NAME = "CALENDAR_NAME";
    public static final String COL_CALENDAR = "CALENDAR";
    public static final String COL_LOCK_NAME = "LOCK_NAME";
    public static final String COL_LAST_CHECKIN_TIME = "LAST_CHECKIN_TIME";
    public static final String COL_CHECKIN_INTERVAL = "CHECKIN_INTERVAL";
    public static final String DEFAULT_TABLE_PREFIX = "QRTZ_";
    public static final String STATE_WAITING = "WAITING";
    public static final String STATE_ACQUIRED = "ACQUIRED";
    public static final String STATE_EXECUTING = "EXECUTING";
    public static final String STATE_COMPLETE = "COMPLETE";
    public static final String STATE_BLOCKED = "BLOCKED";
    public static final String STATE_ERROR = "ERROR";
    public static final String STATE_PAUSED = "PAUSED";
    public static final String STATE_PAUSED_BLOCKED = "PAUSED_BLOCKED";
    public static final String STATE_DELETED = "DELETED";
    public static final String STATE_MISFIRED = "MISFIRED";
    public static final String ALL_GROUPS_PAUSED = "_$_ALL_GROUPS_PAUSED_$_";
    public static final String TTYPE_SIMPLE = "SIMPLE";
    public static final String TTYPE_CRON = "CRON";
    public static final String TTYPE_CAL_INT = "CAL_INT";
    public static final String TTYPE_DAILY_TIME_INT = "DAILY_I";
    public static final String TTYPE_BLOB = "BLOB";
}

