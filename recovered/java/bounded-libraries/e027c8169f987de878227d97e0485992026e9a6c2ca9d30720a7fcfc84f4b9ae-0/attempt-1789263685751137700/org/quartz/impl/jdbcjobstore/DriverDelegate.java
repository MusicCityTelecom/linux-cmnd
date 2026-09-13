/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.FiredTriggerRecord;
import org.quartz.impl.jdbcjobstore.NoSuchDelegateException;
import org.quartz.impl.jdbcjobstore.SchedulerStateRecord;
import org.quartz.impl.jdbcjobstore.TriggerStatus;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.OperableTrigger;
import org.quartz.utils.Key;
import org.slf4j.Logger;

public interface DriverDelegate {
    public void initialize(Logger var1, String var2, String var3, String var4, ClassLoadHelper var5, boolean var6, String var7) throws NoSuchDelegateException;

    public int updateTriggerStatesFromOtherStates(Connection var1, String var2, String var3, String var4) throws SQLException;

    public List<TriggerKey> selectMisfiredTriggers(Connection var1, long var2) throws SQLException;

    public List<TriggerKey> selectMisfiredTriggersInState(Connection var1, String var2, long var3) throws SQLException;

    public boolean hasMisfiredTriggersInState(Connection var1, String var2, long var3, int var5, List<TriggerKey> var6) throws SQLException;

    public int countMisfiredTriggersInState(Connection var1, String var2, long var3) throws SQLException;

    public List<TriggerKey> selectMisfiredTriggersInGroupInState(Connection var1, String var2, String var3, long var4) throws SQLException;

    public List<OperableTrigger> selectTriggersForRecoveringJobs(Connection var1) throws SQLException, IOException, ClassNotFoundException;

    public int deleteFiredTriggers(Connection var1) throws SQLException;

    public int deleteFiredTriggers(Connection var1, String var2) throws SQLException;

    public int insertJobDetail(Connection var1, JobDetail var2) throws IOException, SQLException;

    public int updateJobDetail(Connection var1, JobDetail var2) throws IOException, SQLException;

    public List<TriggerKey> selectTriggerKeysForJob(Connection var1, JobKey var2) throws SQLException;

    public int deleteJobDetail(Connection var1, JobKey var2) throws SQLException;

    public boolean isJobNonConcurrent(Connection var1, JobKey var2) throws SQLException;

    public boolean jobExists(Connection var1, JobKey var2) throws SQLException;

    public int updateJobData(Connection var1, JobDetail var2) throws IOException, SQLException;

    public JobDetail selectJobDetail(Connection var1, JobKey var2, ClassLoadHelper var3) throws ClassNotFoundException, IOException, SQLException;

    public int selectNumJobs(Connection var1) throws SQLException;

    public List<String> selectJobGroups(Connection var1) throws SQLException;

    public Set<JobKey> selectJobsInGroup(Connection var1, GroupMatcher<JobKey> var2) throws SQLException;

    public int insertTrigger(Connection var1, OperableTrigger var2, String var3, JobDetail var4) throws SQLException, IOException;

    public int updateTrigger(Connection var1, OperableTrigger var2, String var3, JobDetail var4) throws SQLException, IOException;

    public boolean triggerExists(Connection var1, TriggerKey var2) throws SQLException;

    public int updateTriggerState(Connection var1, TriggerKey var2, String var3) throws SQLException;

    public int updateTriggerStateFromOtherState(Connection var1, TriggerKey var2, String var3, String var4) throws SQLException;

    public int updateTriggerStateFromOtherStates(Connection var1, TriggerKey var2, String var3, String var4, String var5, String var6) throws SQLException;

    public int updateTriggerGroupStateFromOtherStates(Connection var1, GroupMatcher<TriggerKey> var2, String var3, String var4, String var5, String var6) throws SQLException;

    public int updateTriggerGroupStateFromOtherState(Connection var1, GroupMatcher<TriggerKey> var2, String var3, String var4) throws SQLException;

    public int updateTriggerStatesForJob(Connection var1, JobKey var2, String var3) throws SQLException;

    public int updateTriggerStatesForJobFromOtherState(Connection var1, JobKey var2, String var3, String var4) throws SQLException;

    public int deleteTrigger(Connection var1, TriggerKey var2) throws SQLException;

    public int selectNumTriggersForJob(Connection var1, JobKey var2) throws SQLException;

    public JobDetail selectJobForTrigger(Connection var1, ClassLoadHelper var2, TriggerKey var3) throws ClassNotFoundException, SQLException;

    public JobDetail selectJobForTrigger(Connection var1, ClassLoadHelper var2, TriggerKey var3, boolean var4) throws ClassNotFoundException, SQLException;

    public List<OperableTrigger> selectTriggersForJob(Connection var1, JobKey var2) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException;

    public List<OperableTrigger> selectTriggersForCalendar(Connection var1, String var2) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException;

    public OperableTrigger selectTrigger(Connection var1, TriggerKey var2) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException;

    public JobDataMap selectTriggerJobDataMap(Connection var1, String var2, String var3) throws SQLException, ClassNotFoundException, IOException;

    public String selectTriggerState(Connection var1, TriggerKey var2) throws SQLException;

    public TriggerStatus selectTriggerStatus(Connection var1, TriggerKey var2) throws SQLException;

    public int selectNumTriggers(Connection var1) throws SQLException;

    public List<String> selectTriggerGroups(Connection var1) throws SQLException;

    public List<String> selectTriggerGroups(Connection var1, GroupMatcher<TriggerKey> var2) throws SQLException;

    public Set<TriggerKey> selectTriggersInGroup(Connection var1, GroupMatcher<TriggerKey> var2) throws SQLException;

    public List<TriggerKey> selectTriggersInState(Connection var1, String var2) throws SQLException;

    public int insertPausedTriggerGroup(Connection var1, String var2) throws SQLException;

    public int deletePausedTriggerGroup(Connection var1, String var2) throws SQLException;

    public int deletePausedTriggerGroup(Connection var1, GroupMatcher<TriggerKey> var2) throws SQLException;

    public int deleteAllPausedTriggerGroups(Connection var1) throws SQLException;

    public boolean isTriggerGroupPaused(Connection var1, String var2) throws SQLException;

    public Set<String> selectPausedTriggerGroups(Connection var1) throws SQLException;

    public boolean isExistingTriggerGroup(Connection var1, String var2) throws SQLException;

    public int insertCalendar(Connection var1, String var2, Calendar var3) throws IOException, SQLException;

    public int updateCalendar(Connection var1, String var2, Calendar var3) throws IOException, SQLException;

    public boolean calendarExists(Connection var1, String var2) throws SQLException;

    public Calendar selectCalendar(Connection var1, String var2) throws ClassNotFoundException, IOException, SQLException;

    public boolean calendarIsReferenced(Connection var1, String var2) throws SQLException;

    public int deleteCalendar(Connection var1, String var2) throws SQLException;

    public int selectNumCalendars(Connection var1) throws SQLException;

    public List<String> selectCalendars(Connection var1) throws SQLException;

    public long selectNextFireTime(Connection var1) throws SQLException;

    public Key<?> selectTriggerForFireTime(Connection var1, long var2) throws SQLException;

    public List<TriggerKey> selectTriggerToAcquire(Connection var1, long var2, long var4) throws SQLException;

    public List<TriggerKey> selectTriggerToAcquire(Connection var1, long var2, long var4, int var6) throws SQLException;

    public int insertFiredTrigger(Connection var1, OperableTrigger var2, String var3, JobDetail var4) throws SQLException;

    public int updateFiredTrigger(Connection var1, OperableTrigger var2, String var3, JobDetail var4) throws SQLException;

    public List<FiredTriggerRecord> selectFiredTriggerRecords(Connection var1, String var2, String var3) throws SQLException;

    public List<FiredTriggerRecord> selectFiredTriggerRecordsByJob(Connection var1, String var2, String var3) throws SQLException;

    public List<FiredTriggerRecord> selectInstancesFiredTriggerRecords(Connection var1, String var2) throws SQLException;

    public Set<String> selectFiredTriggerInstanceNames(Connection var1) throws SQLException;

    public int deleteFiredTrigger(Connection var1, String var2) throws SQLException;

    public int selectJobExecutionCount(Connection var1, JobKey var2) throws SQLException;

    public int insertSchedulerState(Connection var1, String var2, long var3, long var5) throws SQLException;

    public int deleteSchedulerState(Connection var1, String var2) throws SQLException;

    public int updateSchedulerState(Connection var1, String var2, long var3) throws SQLException;

    public List<SchedulerStateRecord> selectSchedulerStateRecords(Connection var1, String var2) throws SQLException;

    public void clearData(Connection var1) throws SQLException;
}

