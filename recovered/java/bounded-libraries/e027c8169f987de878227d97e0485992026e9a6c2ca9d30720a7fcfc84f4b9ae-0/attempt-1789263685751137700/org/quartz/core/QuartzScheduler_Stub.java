/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.core.RemotableQuartzScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.OperableTrigger;

public final class QuartzScheduler_Stub
extends RemoteStub
implements RemotableQuartzScheduler {
    private static final long serialVersionUID = 2L;
    private static Method $method_addCalendar_0;
    private static Method $method_addJob_1;
    private static Method $method_addJob_2;
    private static Method $method_checkExists_3;
    private static Method $method_checkExists_4;
    private static Method $method_clear_5;
    private static Method $method_deleteCalendar_6;
    private static Method $method_deleteJob_7;
    private static Method $method_deleteJobs_8;
    private static Method $method_getCalendar_9;
    private static Method $method_getCalendarNames_10;
    private static Method $method_getCurrentlyExecutingJobs_11;
    private static Method $method_getJobDetail_12;
    private static Method $method_getJobGroupNames_13;
    private static Method $method_getJobKeys_14;
    private static Method $method_getJobStoreClass_15;
    private static Method $method_getPausedTriggerGroups_16;
    private static Method $method_getSchedulerContext_17;
    private static Method $method_getSchedulerInstanceId_18;
    private static Method $method_getSchedulerName_19;
    private static Method $method_getThreadPoolClass_20;
    private static Method $method_getThreadPoolSize_21;
    private static Method $method_getTrigger_22;
    private static Method $method_getTriggerGroupNames_23;
    private static Method $method_getTriggerKeys_24;
    private static Method $method_getTriggerState_25;
    private static Method $method_getTriggersOfJob_26;
    private static Method $method_getVersion_27;
    private static Method $method_interrupt_28;
    private static Method $method_interrupt_29;
    private static Method $method_isClustered_30;
    private static Method $method_isInStandbyMode_31;
    private static Method $method_isShutdown_32;
    private static Method $method_numJobsExecuted_33;
    private static Method $method_pauseAll_34;
    private static Method $method_pauseJob_35;
    private static Method $method_pauseJobs_36;
    private static Method $method_pauseTrigger_37;
    private static Method $method_pauseTriggers_38;
    private static Method $method_rescheduleJob_39;
    private static Method $method_resetTriggerFromErrorState_40;
    private static Method $method_resumeAll_41;
    private static Method $method_resumeJob_42;
    private static Method $method_resumeJobs_43;
    private static Method $method_resumeTrigger_44;
    private static Method $method_resumeTriggers_45;
    private static Method $method_runningSince_46;
    private static Method $method_scheduleJob_47;
    private static Method $method_scheduleJob_48;
    private static Method $method_scheduleJob_49;
    private static Method $method_scheduleJobs_50;
    private static Method $method_shutdown_51;
    private static Method $method_shutdown_52;
    private static Method $method_standby_53;
    private static Method $method_start_54;
    private static Method $method_startDelayed_55;
    private static Method $method_supportsPersistence_56;
    private static Method $method_triggerJob_57;
    private static Method $method_triggerJob_58;
    private static Method $method_unscheduleJob_59;
    private static Method $method_unscheduleJobs_60;
    static /* synthetic */ Class class$org$quartz$core$RemotableQuartzScheduler;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$org$quartz$Calendar;
    static /* synthetic */ Class class$org$quartz$JobDetail;
    static /* synthetic */ Class class$org$quartz$JobKey;
    static /* synthetic */ Class class$org$quartz$TriggerKey;
    static /* synthetic */ Class class$java$util$List;
    static /* synthetic */ Class class$org$quartz$impl$matchers$GroupMatcher;
    static /* synthetic */ Class class$org$quartz$Trigger;
    static /* synthetic */ Class class$java$util$Set;
    static /* synthetic */ Class class$java$util$Map;
    static /* synthetic */ Class class$org$quartz$JobDataMap;
    static /* synthetic */ Class class$org$quartz$spi$OperableTrigger;

    static {
        try {
            $method_addCalendar_0 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("addCalendar", class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = QuartzScheduler_Stub.class$("java.lang.String")), class$org$quartz$Calendar != null ? class$org$quartz$Calendar : (class$org$quartz$Calendar = QuartzScheduler_Stub.class$("org.quartz.Calendar")), Boolean.TYPE, Boolean.TYPE);
            $method_addJob_1 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("addJob", class$org$quartz$JobDetail != null ? class$org$quartz$JobDetail : (class$org$quartz$JobDetail = QuartzScheduler_Stub.class$("org.quartz.JobDetail")), Boolean.TYPE);
            $method_addJob_2 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("addJob", class$org$quartz$JobDetail != null ? class$org$quartz$JobDetail : (class$org$quartz$JobDetail = QuartzScheduler_Stub.class$("org.quartz.JobDetail")), Boolean.TYPE, Boolean.TYPE);
            $method_checkExists_3 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("checkExists", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_checkExists_4 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("checkExists", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_clear_5 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("clear", new Class[0]);
            $method_deleteCalendar_6 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("deleteCalendar", class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = QuartzScheduler_Stub.class$("java.lang.String")));
            $method_deleteJob_7 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("deleteJob", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_deleteJobs_8 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("deleteJobs", class$java$util$List != null ? class$java$util$List : (class$java$util$List = QuartzScheduler_Stub.class$("java.util.List")));
            $method_getCalendar_9 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getCalendar", class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = QuartzScheduler_Stub.class$("java.lang.String")));
            $method_getCalendarNames_10 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getCalendarNames", new Class[0]);
            $method_getCurrentlyExecutingJobs_11 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getCurrentlyExecutingJobs", new Class[0]);
            $method_getJobDetail_12 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getJobDetail", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_getJobGroupNames_13 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getJobGroupNames", new Class[0]);
            $method_getJobKeys_14 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getJobKeys", class$org$quartz$impl$matchers$GroupMatcher != null ? class$org$quartz$impl$matchers$GroupMatcher : (class$org$quartz$impl$matchers$GroupMatcher = QuartzScheduler_Stub.class$("org.quartz.impl.matchers.GroupMatcher")));
            $method_getJobStoreClass_15 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getJobStoreClass", new Class[0]);
            $method_getPausedTriggerGroups_16 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getPausedTriggerGroups", new Class[0]);
            $method_getSchedulerContext_17 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getSchedulerContext", new Class[0]);
            $method_getSchedulerInstanceId_18 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getSchedulerInstanceId", new Class[0]);
            $method_getSchedulerName_19 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getSchedulerName", new Class[0]);
            $method_getThreadPoolClass_20 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getThreadPoolClass", new Class[0]);
            $method_getThreadPoolSize_21 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getThreadPoolSize", new Class[0]);
            $method_getTrigger_22 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getTrigger", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_getTriggerGroupNames_23 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getTriggerGroupNames", new Class[0]);
            $method_getTriggerKeys_24 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getTriggerKeys", class$org$quartz$impl$matchers$GroupMatcher != null ? class$org$quartz$impl$matchers$GroupMatcher : (class$org$quartz$impl$matchers$GroupMatcher = QuartzScheduler_Stub.class$("org.quartz.impl.matchers.GroupMatcher")));
            $method_getTriggerState_25 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getTriggerState", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_getTriggersOfJob_26 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getTriggersOfJob", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_getVersion_27 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("getVersion", new Class[0]);
            $method_interrupt_28 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("interrupt", class$java$lang$String != null ? class$java$lang$String : (class$java$lang$String = QuartzScheduler_Stub.class$("java.lang.String")));
            $method_interrupt_29 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("interrupt", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_isClustered_30 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("isClustered", new Class[0]);
            $method_isInStandbyMode_31 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("isInStandbyMode", new Class[0]);
            $method_isShutdown_32 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("isShutdown", new Class[0]);
            $method_numJobsExecuted_33 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("numJobsExecuted", new Class[0]);
            $method_pauseAll_34 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("pauseAll", new Class[0]);
            $method_pauseJob_35 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("pauseJob", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_pauseJobs_36 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("pauseJobs", class$org$quartz$impl$matchers$GroupMatcher != null ? class$org$quartz$impl$matchers$GroupMatcher : (class$org$quartz$impl$matchers$GroupMatcher = QuartzScheduler_Stub.class$("org.quartz.impl.matchers.GroupMatcher")));
            $method_pauseTrigger_37 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("pauseTrigger", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_pauseTriggers_38 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("pauseTriggers", class$org$quartz$impl$matchers$GroupMatcher != null ? class$org$quartz$impl$matchers$GroupMatcher : (class$org$quartz$impl$matchers$GroupMatcher = QuartzScheduler_Stub.class$("org.quartz.impl.matchers.GroupMatcher")));
            $method_rescheduleJob_39 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("rescheduleJob", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")), class$org$quartz$Trigger != null ? class$org$quartz$Trigger : (class$org$quartz$Trigger = QuartzScheduler_Stub.class$("org.quartz.Trigger")));
            $method_resetTriggerFromErrorState_40 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("resetTriggerFromErrorState", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_resumeAll_41 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("resumeAll", new Class[0]);
            $method_resumeJob_42 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("resumeJob", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")));
            $method_resumeJobs_43 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("resumeJobs", class$org$quartz$impl$matchers$GroupMatcher != null ? class$org$quartz$impl$matchers$GroupMatcher : (class$org$quartz$impl$matchers$GroupMatcher = QuartzScheduler_Stub.class$("org.quartz.impl.matchers.GroupMatcher")));
            $method_resumeTrigger_44 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("resumeTrigger", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_resumeTriggers_45 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("resumeTriggers", class$org$quartz$impl$matchers$GroupMatcher != null ? class$org$quartz$impl$matchers$GroupMatcher : (class$org$quartz$impl$matchers$GroupMatcher = QuartzScheduler_Stub.class$("org.quartz.impl.matchers.GroupMatcher")));
            $method_runningSince_46 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("runningSince", new Class[0]);
            $method_scheduleJob_47 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("scheduleJob", class$org$quartz$JobDetail != null ? class$org$quartz$JobDetail : (class$org$quartz$JobDetail = QuartzScheduler_Stub.class$("org.quartz.JobDetail")), class$java$util$Set != null ? class$java$util$Set : (class$java$util$Set = QuartzScheduler_Stub.class$("java.util.Set")), Boolean.TYPE);
            $method_scheduleJob_48 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("scheduleJob", class$org$quartz$JobDetail != null ? class$org$quartz$JobDetail : (class$org$quartz$JobDetail = QuartzScheduler_Stub.class$("org.quartz.JobDetail")), class$org$quartz$Trigger != null ? class$org$quartz$Trigger : (class$org$quartz$Trigger = QuartzScheduler_Stub.class$("org.quartz.Trigger")));
            $method_scheduleJob_49 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("scheduleJob", class$org$quartz$Trigger != null ? class$org$quartz$Trigger : (class$org$quartz$Trigger = QuartzScheduler_Stub.class$("org.quartz.Trigger")));
            $method_scheduleJobs_50 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("scheduleJobs", class$java$util$Map != null ? class$java$util$Map : (class$java$util$Map = QuartzScheduler_Stub.class$("java.util.Map")), Boolean.TYPE);
            $method_shutdown_51 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("shutdown", new Class[0]);
            $method_shutdown_52 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("shutdown", Boolean.TYPE);
            $method_standby_53 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("standby", new Class[0]);
            $method_start_54 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("start", new Class[0]);
            $method_startDelayed_55 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("startDelayed", Integer.TYPE);
            $method_supportsPersistence_56 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("supportsPersistence", new Class[0]);
            $method_triggerJob_57 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("triggerJob", class$org$quartz$JobKey != null ? class$org$quartz$JobKey : (class$org$quartz$JobKey = QuartzScheduler_Stub.class$("org.quartz.JobKey")), class$org$quartz$JobDataMap != null ? class$org$quartz$JobDataMap : (class$org$quartz$JobDataMap = QuartzScheduler_Stub.class$("org.quartz.JobDataMap")));
            $method_triggerJob_58 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("triggerJob", class$org$quartz$spi$OperableTrigger != null ? class$org$quartz$spi$OperableTrigger : (class$org$quartz$spi$OperableTrigger = QuartzScheduler_Stub.class$("org.quartz.spi.OperableTrigger")));
            $method_unscheduleJob_59 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("unscheduleJob", class$org$quartz$TriggerKey != null ? class$org$quartz$TriggerKey : (class$org$quartz$TriggerKey = QuartzScheduler_Stub.class$("org.quartz.TriggerKey")));
            $method_unscheduleJobs_60 = (class$org$quartz$core$RemotableQuartzScheduler != null ? class$org$quartz$core$RemotableQuartzScheduler : (class$org$quartz$core$RemotableQuartzScheduler = QuartzScheduler_Stub.class$("org.quartz.core.RemotableQuartzScheduler"))).getMethod("unscheduleJobs", class$java$util$List != null ? class$java$util$List : (class$java$util$List = QuartzScheduler_Stub.class$("java.util.List")));
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new NoSuchMethodError("stub class initialization failed");
        }
    }

    public QuartzScheduler_Stub(RemoteRef remoteRef) {
        super(remoteRef);
    }

    public void addCalendar(String string, Calendar calendar, boolean bl, boolean bl2) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_addCalendar_0, new Object[]{string, calendar, bl ? Boolean.TRUE : Boolean.FALSE, bl2 ? Boolean.TRUE : Boolean.FALSE}, 8855052307177792680L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void addJob(JobDetail jobDetail, boolean bl) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_addJob_1, new Object[]{jobDetail, bl ? Boolean.TRUE : Boolean.FALSE}, -7729650160006632870L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void addJob(JobDetail jobDetail, boolean bl, boolean bl2) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_addJob_2, new Object[]{jobDetail, bl ? Boolean.TRUE : Boolean.FALSE, bl2 ? Boolean.TRUE : Boolean.FALSE}, 1129496936115180762L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean checkExists(JobKey jobKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_checkExists_3, new Object[]{jobKey}, -5409554300431077992L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean checkExists(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_checkExists_4, new Object[]{triggerKey}, 57742068790347073L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    static /* synthetic */ Class class$(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    public void clear() throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_clear_5, null, -7475254351993695499L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean deleteCalendar(String string) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_deleteCalendar_6, new Object[]{string}, 4621799193941576495L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean deleteJob(JobKey jobKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_deleteJob_7, new Object[]{jobKey}, -3057293324488607018L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean deleteJobs(List list) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_deleteJobs_8, new Object[]{list}, 7613446947728959209L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Calendar getCalendar(String string) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getCalendar_9, new Object[]{string}, 7476199188467217146L);
            return (Calendar)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public List getCalendarNames() throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getCalendarNames_10, null, -4042711865985645589L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public List getCurrentlyExecutingJobs() throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getCurrentlyExecutingJobs_11, null, 5767551841304860517L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public JobDetail getJobDetail(JobKey jobKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getJobDetail_12, new Object[]{jobKey}, -5890147489272798972L);
            return (JobDetail)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public List getJobGroupNames() throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getJobGroupNames_13, null, -8455486033245212483L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Set getJobKeys(GroupMatcher groupMatcher) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getJobKeys_14, new Object[]{groupMatcher}, 5516129892023529995L);
            return (Set)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Class getJobStoreClass() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getJobStoreClass_15, null, 6705397913929502666L);
            return (Class)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Set getPausedTriggerGroups() throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getPausedTriggerGroups_16, null, -3055688590637594456L);
            return (Set)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public SchedulerContext getSchedulerContext() throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getSchedulerContext_17, null, 2814359591403475563L);
            return (SchedulerContext)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getSchedulerInstanceId() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getSchedulerInstanceId_18, null, -2454925768252868567L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getSchedulerName() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getSchedulerName_19, null, 1038196595245667445L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Class getThreadPoolClass() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getThreadPoolClass_20, null, -706336661940287388L);
            return (Class)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int getThreadPoolSize() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getThreadPoolSize_21, null, 6528392066641712137L);
            return (Integer)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Trigger getTrigger(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getTrigger_22, new Object[]{triggerKey}, -8135458059745415503L);
            return (Trigger)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public List getTriggerGroupNames() throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getTriggerGroupNames_23, null, -1425625447055098000L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Set getTriggerKeys(GroupMatcher groupMatcher) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getTriggerKeys_24, new Object[]{groupMatcher}, -833881061725726505L);
            return (Set)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getTriggerState_25, new Object[]{triggerKey}, -5299675517853200699L);
            return (Trigger.TriggerState)((Object)object);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public List getTriggersOfJob(JobKey jobKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_getTriggersOfJob_26, new Object[]{jobKey}, 4987568461050139134L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getVersion() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getVersion_27, null, -8081107751519807347L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean interrupt(String string) throws RemoteException, UnableToInterruptJobException {
        try {
            Object object = this.ref.invoke(this, $method_interrupt_28, new Object[]{string}, 256262298724115780L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (UnableToInterruptJobException unableToInterruptJobException) {
            throw unableToInterruptJobException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean interrupt(JobKey jobKey) throws RemoteException, UnableToInterruptJobException {
        try {
            Object object = this.ref.invoke(this, $method_interrupt_29, new Object[]{jobKey}, -4185636327079289011L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (UnableToInterruptJobException unableToInterruptJobException) {
            throw unableToInterruptJobException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean isClustered() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_isClustered_30, null, 8772462407279794129L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean isInStandbyMode() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_isInStandbyMode_31, null, 809977841435240287L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean isShutdown() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_isShutdown_32, null, 6424449119484905518L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int numJobsExecuted() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_numJobsExecuted_33, null, 3699847707830503805L);
            return (Integer)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void pauseAll() throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_pauseAll_34, null, 5457255371237476599L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void pauseJob(JobKey jobKey) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_pauseJob_35, new Object[]{jobKey}, 8209397623379863913L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void pauseJobs(GroupMatcher groupMatcher) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_pauseJobs_36, new Object[]{groupMatcher}, 8348393716035813534L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void pauseTrigger(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_pauseTrigger_37, new Object[]{triggerKey}, -1556555911706012384L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void pauseTriggers(GroupMatcher groupMatcher) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_pauseTriggers_38, new Object[]{groupMatcher}, -7673129639132463315L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Date rescheduleJob(TriggerKey triggerKey, Trigger trigger) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_rescheduleJob_39, new Object[]{triggerKey, trigger}, -6542935860087805349L);
            return (Date)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_resetTriggerFromErrorState_40, new Object[]{triggerKey}, -8848809227421519492L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void resumeAll() throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_resumeAll_41, null, 6544465639644633234L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void resumeJob(JobKey jobKey) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_resumeJob_42, new Object[]{jobKey}, 85405606979760311L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void resumeJobs(GroupMatcher groupMatcher) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_resumeJobs_43, new Object[]{groupMatcher}, 7080691189565323939L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void resumeTrigger(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_resumeTrigger_44, new Object[]{triggerKey}, 1103652291697918174L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void resumeTriggers(GroupMatcher groupMatcher) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_resumeTriggers_45, new Object[]{groupMatcher}, 316892067472367515L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Date runningSince() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_runningSince_46, null, -1739625058868381113L);
            return (Date)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void scheduleJob(JobDetail jobDetail, Set set, boolean bl) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_scheduleJob_47, new Object[]{jobDetail, set, bl ? Boolean.TRUE : Boolean.FALSE}, -2860300690822357486L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_scheduleJob_48, new Object[]{jobDetail, trigger}, 4944457543332629245L);
            return (Date)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Date scheduleJob(Trigger trigger) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_scheduleJob_49, new Object[]{trigger}, -6865148385642356285L);
            return (Date)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void scheduleJobs(Map map, boolean bl) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_scheduleJobs_50, new Object[]{map, bl ? Boolean.TRUE : Boolean.FALSE}, 2404438458719160003L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void shutdown() throws RemoteException {
        try {
            this.ref.invoke(this, $method_shutdown_51, null, -7207851917985848402L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void shutdown(boolean bl) throws RemoteException {
        try {
            this.ref.invoke(this, $method_shutdown_52, new Object[]{bl ? Boolean.TRUE : Boolean.FALSE}, -7158426071079062438L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void standby() throws RemoteException {
        try {
            this.ref.invoke(this, $method_standby_53, null, 7161048918451732526L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void start() throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_start_54, null, -8025343665958530775L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void startDelayed(int n) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_startDelayed_55, new Object[]{new Integer(n)}, -1476976461109028800L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean supportsPersistence() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_supportsPersistence_56, null, -5767630451452602400L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void triggerJob(JobKey jobKey, JobDataMap jobDataMap) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_triggerJob_57, new Object[]{jobKey, jobDataMap}, -1585175841511357332L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void triggerJob(OperableTrigger operableTrigger) throws RemoteException, SchedulerException {
        try {
            this.ref.invoke(this, $method_triggerJob_58, new Object[]{operableTrigger}, 5598451830209081494L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean unscheduleJob(TriggerKey triggerKey) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_unscheduleJob_59, new Object[]{triggerKey}, -4592142908438852383L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean unscheduleJobs(List list) throws RemoteException, SchedulerException {
        try {
            Object object = this.ref.invoke(this, $method_unscheduleJobs_60, new Object[]{list}, 1385849655203364760L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (SchedulerException schedulerException) {
            throw schedulerException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }
}

