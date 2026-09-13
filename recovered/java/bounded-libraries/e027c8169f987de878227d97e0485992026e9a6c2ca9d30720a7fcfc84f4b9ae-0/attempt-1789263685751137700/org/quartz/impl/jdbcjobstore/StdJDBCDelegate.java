/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package org.quartz.impl.jdbcjobstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.jdbcjobstore.CalendarIntervalTriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.CronTriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.DailyTimeIntervalTriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.DriverDelegate;
import org.quartz.impl.jdbcjobstore.FiredTriggerRecord;
import org.quartz.impl.jdbcjobstore.NoSuchDelegateException;
import org.quartz.impl.jdbcjobstore.SchedulerStateRecord;
import org.quartz.impl.jdbcjobstore.SimpleTriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.StdJDBCConstants;
import org.quartz.impl.jdbcjobstore.TriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.TriggerStatus;
import org.quartz.impl.jdbcjobstore.Util;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.OperableTrigger;
import org.slf4j.Logger;

public class StdJDBCDelegate
implements DriverDelegate,
StdJDBCConstants {
    protected Logger logger = null;
    protected String tablePrefix = "QRTZ_";
    protected String instanceId;
    protected String schedName;
    protected boolean useProperties;
    protected ClassLoadHelper classLoadHelper;
    protected List<TriggerPersistenceDelegate> triggerPersistenceDelegates = new LinkedList<TriggerPersistenceDelegate>();
    private String schedNameLiteral = null;

    @Override
    public void initialize(Logger logger, String tablePrefix, String schedName, String instanceId, ClassLoadHelper classLoadHelper, boolean useProperties, String initString) throws NoSuchDelegateException {
        String[] settings;
        this.logger = logger;
        this.tablePrefix = tablePrefix;
        this.schedName = schedName;
        this.instanceId = instanceId;
        this.useProperties = useProperties;
        this.classLoadHelper = classLoadHelper;
        this.addDefaultTriggerPersistenceDelegates();
        if (initString == null) {
            return;
        }
        for (String setting : settings = initString.split("\\|")) {
            String[] parts = setting.split("=");
            String name = parts[0];
            if (parts.length == 1 || parts[1] == null || parts[1].equals("")) continue;
            if (name.equals("triggerPersistenceDelegateClasses")) {
                String[] trigDelegates;
                for (String trigDelClassName : trigDelegates = parts[1].split(",")) {
                    try {
                        Class<?> trigDelClass = classLoadHelper.loadClass(trigDelClassName);
                        this.addTriggerPersistenceDelegate((TriggerPersistenceDelegate)trigDelClass.newInstance());
                    }
                    catch (Exception e) {
                        throw new NoSuchDelegateException("Error instantiating TriggerPersistenceDelegate of type: " + trigDelClassName, e);
                    }
                }
                continue;
            }
            throw new NoSuchDelegateException("Unknown setting: '" + name + "'");
        }
    }

    protected void addDefaultTriggerPersistenceDelegates() {
        this.addTriggerPersistenceDelegate(new SimpleTriggerPersistenceDelegate());
        this.addTriggerPersistenceDelegate(new CronTriggerPersistenceDelegate());
        this.addTriggerPersistenceDelegate(new CalendarIntervalTriggerPersistenceDelegate());
        this.addTriggerPersistenceDelegate(new DailyTimeIntervalTriggerPersistenceDelegate());
    }

    protected boolean canUseProperties() {
        return this.useProperties;
    }

    public void addTriggerPersistenceDelegate(TriggerPersistenceDelegate delegate) {
        this.logger.debug("Adding TriggerPersistenceDelegate of type: " + delegate.getClass().getCanonicalName());
        delegate.initialize(this.tablePrefix, this.schedName);
        this.triggerPersistenceDelegates.add(delegate);
    }

    public TriggerPersistenceDelegate findTriggerPersistenceDelegate(OperableTrigger trigger) {
        for (TriggerPersistenceDelegate delegate : this.triggerPersistenceDelegates) {
            if (!delegate.canHandleTriggerType(trigger)) continue;
            return delegate;
        }
        return null;
    }

    public TriggerPersistenceDelegate findTriggerPersistenceDelegate(String discriminator) {
        for (TriggerPersistenceDelegate delegate : this.triggerPersistenceDelegates) {
            if (!delegate.getHandledTriggerTypeDiscriminator().equals(discriminator)) continue;
            return delegate;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerStatesFromOtherStates(Connection conn, String newState, String oldState1, String oldState2) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND (TRIGGER_STATE = ? OR TRIGGER_STATE = ?)"));
            ps.setString(1, newState);
            ps.setString(2, oldState1);
            ps.setString(3, oldState2);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerKey> selectMisfiredTriggers(Connection conn, long ts) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC"));
            ps.setBigDecimal(1, new BigDecimal(String.valueOf(ts)));
            rs = ps.executeQuery();
            LinkedList<TriggerKey> list = new LinkedList<TriggerKey>();
            while (rs.next()) {
                String triggerName = rs.getString("TRIGGER_NAME");
                String groupName = rs.getString("TRIGGER_GROUP");
                list.add(TriggerKey.triggerKey(triggerName, groupName));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerKey> selectTriggersInState(Connection conn, String state) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_STATE = ?"));
            ps.setString(1, state);
            rs = ps.executeQuery();
            LinkedList<TriggerKey> list = new LinkedList<TriggerKey>();
            while (rs.next()) {
                list.add(TriggerKey.triggerKey(rs.getString(1), rs.getString(2)));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerKey> selectMisfiredTriggersInState(Connection conn, String state, long ts) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? AND TRIGGER_STATE = ? ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC"));
            ps.setBigDecimal(1, new BigDecimal(String.valueOf(ts)));
            ps.setString(2, state);
            rs = ps.executeQuery();
            LinkedList<TriggerKey> list = new LinkedList<TriggerKey>();
            while (rs.next()) {
                String triggerName = rs.getString("TRIGGER_NAME");
                String groupName = rs.getString("TRIGGER_GROUP");
                list.add(TriggerKey.triggerKey(triggerName, groupName));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasMisfiredTriggersInState(Connection conn, String state1, long ts, int count, List<TriggerKey> resultList) throws SQLException {
        boolean bl;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? AND TRIGGER_STATE = ? ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC"));
            ps.setBigDecimal(1, new BigDecimal(String.valueOf(ts)));
            ps.setString(2, state1);
            rs = ps.executeQuery();
            boolean hasReachedLimit = false;
            while (rs.next() && !hasReachedLimit) {
                if (resultList.size() == count) {
                    hasReachedLimit = true;
                    continue;
                }
                String triggerName = rs.getString("TRIGGER_NAME");
                String groupName = rs.getString("TRIGGER_GROUP");
                resultList.add(TriggerKey.triggerKey(triggerName, groupName));
            }
            bl = hasReachedLimit;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    @Override
    public int countMisfiredTriggersInState(Connection conn, String state1, long ts) throws SQLException {
        block3: {
            int n;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT COUNT(TRIGGER_NAME) FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? AND TRIGGER_STATE = ?"));
                ps.setBigDecimal(1, new BigDecimal(String.valueOf(ts)));
                ps.setString(2, state1);
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                n = rs.getInt(1);
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return n;
        }
        throw new SQLException("No misfired trigger count returned.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerKey> selectMisfiredTriggersInGroupInState(Connection conn, String groupName, String state, long ts) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND NOT (MISFIRE_INSTR = -1) AND NEXT_FIRE_TIME < ? AND TRIGGER_GROUP = ? AND TRIGGER_STATE = ? ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC"));
            ps.setBigDecimal(1, new BigDecimal(String.valueOf(ts)));
            ps.setString(2, groupName);
            ps.setString(3, state);
            rs = ps.executeQuery();
            LinkedList<TriggerKey> list = new LinkedList<TriggerKey>();
            while (rs.next()) {
                String triggerName = rs.getString("TRIGGER_NAME");
                list.add(TriggerKey.triggerKey(triggerName, groupName));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> selectTriggersForRecoveringJobs(Connection conn) throws SQLException, IOException, ClassNotFoundException {
        LinkedList<OperableTrigger> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND INSTANCE_NAME = ? AND REQUESTS_RECOVERY = ?"));
            ps.setString(1, this.instanceId);
            this.setBoolean(ps, 2, true);
            rs = ps.executeQuery();
            long dumId = System.currentTimeMillis();
            LinkedList<OperableTrigger> list = new LinkedList<OperableTrigger>();
            while (rs.next()) {
                String jobName = rs.getString("JOB_NAME");
                String jobGroup = rs.getString("JOB_GROUP");
                String trigName = rs.getString("TRIGGER_NAME");
                String trigGroup = rs.getString("TRIGGER_GROUP");
                long firedTime = rs.getLong("FIRED_TIME");
                long scheduledTime = rs.getLong("SCHED_TIME");
                int priority = rs.getInt("PRIORITY");
                SimpleTriggerImpl rcvryTrig = new SimpleTriggerImpl("recover_" + this.instanceId + "_" + String.valueOf(dumId++), "RECOVERING_JOBS", new Date(scheduledTime));
                rcvryTrig.setJobName(jobName);
                rcvryTrig.setJobGroup(jobGroup);
                rcvryTrig.setPriority(priority);
                rcvryTrig.setMisfireInstruction(-1);
                JobDataMap jd = this.selectTriggerJobDataMap(conn, trigName, trigGroup);
                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME", trigName);
                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP", trigGroup);
                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_FIRETIME_IN_MILLISECONDS_AS_STRING", String.valueOf(firedTime));
                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS_AS_STRING", String.valueOf(scheduledTime));
                rcvryTrig.setJobDataMap(jd);
                list.add(rcvryTrig);
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteFiredTriggers(Connection conn) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1}"));
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteFiredTriggers(Connection conn, String theInstanceId) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND INSTANCE_NAME = ?"));
            ps.setString(1, theInstanceId);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearData(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}SIMPLE_TRIGGERS  WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}SIMPROP_TRIGGERS  WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}CRON_TRIGGERS WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}BLOB_TRIGGERS WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}TRIGGERS WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}CALENDARS WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}PAUSED_TRIGGER_GRPS WHERE SCHED_NAME = {1}"));
            ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
        ByteArrayOutputStream baos = this.serializeJobData(job.getJobDataMap());
        PreparedStatement ps = null;
        int insertResult = 0;
        try {
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP, DESCRIPTION, JOB_CLASS_NAME, IS_DURABLE, IS_NONCONCURRENT, IS_UPDATE_DATA, REQUESTS_RECOVERY, JOB_DATA)  VALUES({1}, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
            ps.setString(1, job.getKey().getName());
            ps.setString(2, job.getKey().getGroup());
            ps.setString(3, job.getDescription());
            ps.setString(4, job.getJobClass().getName());
            this.setBoolean(ps, 5, job.isDurable());
            this.setBoolean(ps, 6, job.isConcurrentExectionDisallowed());
            this.setBoolean(ps, 7, job.isPersistJobDataAfterExecution());
            this.setBoolean(ps, 8, job.requestsRecovery());
            this.setBytes(ps, 9, baos);
            insertResult = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return insertResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
        ByteArrayOutputStream baos = this.serializeJobData(job.getJobDataMap());
        PreparedStatement ps = null;
        int insertResult = 0;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}JOB_DETAILS SET DESCRIPTION = ?, JOB_CLASS_NAME = ?, IS_DURABLE = ?, IS_NONCONCURRENT = ?, IS_UPDATE_DATA = ?, REQUESTS_RECOVERY = ?, JOB_DATA = ?  WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, job.getDescription());
            ps.setString(2, job.getJobClass().getName());
            this.setBoolean(ps, 3, job.isDurable());
            this.setBoolean(ps, 4, job.isConcurrentExectionDisallowed());
            this.setBoolean(ps, 5, job.isPersistJobDataAfterExecution());
            this.setBoolean(ps, 6, job.requestsRecovery());
            this.setBytes(ps, 7, baos);
            ps.setString(8, job.getKey().getName());
            ps.setString(9, job.getKey().getGroup());
            insertResult = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return insertResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerKey> selectTriggerKeysForJob(Connection conn, JobKey jobKey) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, jobKey.getName());
            ps.setString(2, jobKey.getGroup());
            rs = ps.executeQuery();
            LinkedList<TriggerKey> list = new LinkedList<TriggerKey>();
            while (rs.next()) {
                String trigName = rs.getString("TRIGGER_NAME");
                String trigGroup = rs.getString("TRIGGER_GROUP");
                list.add(TriggerKey.triggerKey(trigName, trigGroup));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteJobDetail(Connection conn, JobKey jobKey) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Deleting job: " + jobKey);
            }
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, jobKey.getName());
            ps.setString(2, jobKey.getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isJobNonConcurrent(Connection conn, JobKey jobKey) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            boolean bl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT IS_NONCONCURRENT FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
                ps.setString(1, jobKey.getName());
                ps.setString(2, jobKey.getGroup());
                rs = ps.executeQuery();
                if (rs.next()) break block3;
                bl = false;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return bl;
        }
        boolean bl = this.getBoolean(rs, "IS_NONCONCURRENT");
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean jobExists(Connection conn, JobKey jobKey) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            boolean bl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT JOB_NAME FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
                ps.setString(1, jobKey.getName());
                ps.setString(2, jobKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                bl = true;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return bl;
        }
        boolean bl = false;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateJobData(Connection conn, JobDetail job) throws IOException, SQLException {
        int n;
        ByteArrayOutputStream baos = this.serializeJobData(job.getJobDataMap());
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}JOB_DETAILS SET JOB_DATA = ?  WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            this.setBytes(ps, 1, baos);
            ps.setString(2, job.getKey().getName());
            ps.setString(3, job.getKey().getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JobDetail selectJobDetail(Connection conn, JobKey jobKey, ClassLoadHelper loadHelper) throws ClassNotFoundException, IOException, SQLException {
        JobDetailImpl jobDetailImpl;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, jobKey.getName());
            ps.setString(2, jobKey.getGroup());
            rs = ps.executeQuery();
            JobDetailImpl job = null;
            if (rs.next()) {
                job = new JobDetailImpl();
                job.setName(rs.getString("JOB_NAME"));
                job.setGroup(rs.getString("JOB_GROUP"));
                job.setDescription(rs.getString("DESCRIPTION"));
                job.setJobClass(loadHelper.loadClass(rs.getString("JOB_CLASS_NAME"), Job.class));
                job.setDurability(this.getBoolean(rs, "IS_DURABLE"));
                job.setRequestsRecovery(this.getBoolean(rs, "REQUESTS_RECOVERY"));
                Map map = null;
                map = this.canUseProperties() ? this.getMapFromProperties(rs) : (Map)this.getObjectFromBlob(rs, "JOB_DATA");
                if (null != map) {
                    job.setJobDataMap(new JobDataMap(map));
                }
            }
            jobDetailImpl = job;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return jobDetailImpl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<?, ?> getMapFromProperties(ResultSet rs) throws ClassNotFoundException, IOException, SQLException {
        InputStream is = (InputStream)this.getJobDataFromBlob(rs, "JOB_DATA");
        if (is == null) {
            return null;
        }
        Properties properties = new Properties();
        if (is != null) {
            try {
                properties.load(is);
            }
            finally {
                is.close();
            }
        }
        Map<?, ?> map = this.convertFromProperty(properties);
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int selectNumJobs(Connection conn) throws SQLException {
        int n;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int count = 0;
            ps = conn.prepareStatement(this.rtp("SELECT COUNT(JOB_NAME)  FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> selectJobGroups(Connection conn) throws SQLException {
        LinkedList<String> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT DISTINCT(JOB_GROUP) FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            LinkedList<String> list = new LinkedList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<JobKey> selectJobsInGroup(Connection conn, GroupMatcher<JobKey> matcher) throws SQLException {
        HashSet<JobKey> hashSet;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (this.isMatcherEquals(matcher)) {
                ps = conn.prepareStatement(this.rtp("SELECT JOB_NAME, JOB_GROUP FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_GROUP = ?"));
                ps.setString(1, this.toSqlEqualsClause(matcher));
            } else {
                ps = conn.prepareStatement(this.rtp("SELECT JOB_NAME, JOB_GROUP FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_GROUP LIKE ?"));
                ps.setString(1, this.toSqlLikeClause(matcher));
            }
            rs = ps.executeQuery();
            LinkedList<JobKey> list = new LinkedList<JobKey>();
            while (rs.next()) {
                list.add(JobKey.jobKey(rs.getString(1), rs.getString(2)));
            }
            hashSet = new HashSet<JobKey>(list);
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return hashSet;
    }

    protected boolean isMatcherEquals(GroupMatcher<?> matcher) {
        return matcher.getCompareWithOperator().equals((Object)StringMatcher.StringOperatorName.EQUALS);
    }

    protected String toSqlEqualsClause(GroupMatcher<?> matcher) {
        return matcher.getCompareToValue();
    }

    protected String toSqlLikeClause(GroupMatcher<?> matcher) {
        String groupName;
        switch (matcher.getCompareWithOperator()) {
            case EQUALS: {
                groupName = matcher.getCompareToValue();
                break;
            }
            case CONTAINS: {
                groupName = "%" + matcher.getCompareToValue() + "%";
                break;
            }
            case ENDS_WITH: {
                groupName = "%" + matcher.getCompareToValue();
                break;
            }
            case STARTS_WITH: {
                groupName = matcher.getCompareToValue() + "%";
                break;
            }
            case ANYTHING: {
                groupName = "%";
                break;
            }
            default: {
                throw new UnsupportedOperationException("Don't know how to translate " + (Object)((Object)matcher.getCompareWithOperator()) + " into SQL");
            }
        }
        return groupName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertTrigger(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        int insertResult;
        PreparedStatement ps;
        block9: {
            ByteArrayOutputStream baos = null;
            if (trigger.getJobDataMap().size() > 0) {
                baos = this.serializeJobData(trigger.getJobDataMap());
            }
            ps = null;
            insertResult = 0;
            try {
                ps = conn.prepareStatement(this.rtp("INSERT INTO {0}TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, DESCRIPTION, NEXT_FIRE_TIME, PREV_FIRE_TIME, TRIGGER_STATE, TRIGGER_TYPE, START_TIME, END_TIME, CALENDAR_NAME, MISFIRE_INSTR, JOB_DATA, PRIORITY)  VALUES({1}, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
                ps.setString(1, trigger.getKey().getName());
                ps.setString(2, trigger.getKey().getGroup());
                ps.setString(3, trigger.getJobKey().getName());
                ps.setString(4, trigger.getJobKey().getGroup());
                ps.setString(5, trigger.getDescription());
                if (trigger.getNextFireTime() != null) {
                    ps.setBigDecimal(6, new BigDecimal(String.valueOf(trigger.getNextFireTime().getTime())));
                } else {
                    ps.setBigDecimal(6, null);
                }
                long prevFireTime = -1L;
                if (trigger.getPreviousFireTime() != null) {
                    prevFireTime = trigger.getPreviousFireTime().getTime();
                }
                ps.setBigDecimal(7, new BigDecimal(String.valueOf(prevFireTime)));
                ps.setString(8, state);
                TriggerPersistenceDelegate tDel = this.findTriggerPersistenceDelegate(trigger);
                String type = "BLOB";
                if (tDel != null) {
                    type = tDel.getHandledTriggerTypeDiscriminator();
                }
                ps.setString(9, type);
                ps.setBigDecimal(10, new BigDecimal(String.valueOf(trigger.getStartTime().getTime())));
                long endTime = 0L;
                if (trigger.getEndTime() != null) {
                    endTime = trigger.getEndTime().getTime();
                }
                ps.setBigDecimal(11, new BigDecimal(String.valueOf(endTime)));
                ps.setString(12, trigger.getCalendarName());
                ps.setInt(13, trigger.getMisfireInstruction());
                this.setBytes(ps, 14, baos);
                ps.setInt(15, trigger.getPriority());
                insertResult = ps.executeUpdate();
                if (tDel == null) {
                    this.insertBlobTrigger(conn, trigger);
                    break block9;
                }
                tDel.insertExtendedTriggerProperties(conn, trigger, state, jobDetail);
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
        }
        StdJDBCDelegate.closeStatement(ps);
        return insertResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int insertBlobTrigger(Connection conn, OperableTrigger trigger) throws SQLException, IOException {
        int n;
        PreparedStatement ps = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(trigger);
            oos.close();
            byte[] buf = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(buf);
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}BLOB_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, BLOB_DATA)  VALUES({1}, ?, ?, ?)"));
            ps.setString(1, trigger.getKey().getName());
            ps.setString(2, trigger.getKey().getGroup());
            ps.setBinaryStream(3, (InputStream)is, buf.length);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTrigger(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        int insertResult;
        PreparedStatement ps;
        block10: {
            boolean updateJobData = trigger.getJobDataMap().isDirty();
            ByteArrayOutputStream baos = null;
            if (updateJobData) {
                baos = this.serializeJobData(trigger.getJobDataMap());
            }
            ps = null;
            insertResult = 0;
            try {
                ps = updateJobData ? conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET JOB_NAME = ?, JOB_GROUP = ?, DESCRIPTION = ?, NEXT_FIRE_TIME = ?, PREV_FIRE_TIME = ?, TRIGGER_STATE = ?, TRIGGER_TYPE = ?, START_TIME = ?, END_TIME = ?, CALENDAR_NAME = ?, MISFIRE_INSTR = ?, PRIORITY = ?, JOB_DATA = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?")) : conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET JOB_NAME = ?, JOB_GROUP = ?, DESCRIPTION = ?, NEXT_FIRE_TIME = ?, PREV_FIRE_TIME = ?, TRIGGER_STATE = ?, TRIGGER_TYPE = ?, START_TIME = ?, END_TIME = ?, CALENDAR_NAME = ?, MISFIRE_INSTR = ?, PRIORITY = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
                ps.setString(1, trigger.getJobKey().getName());
                ps.setString(2, trigger.getJobKey().getGroup());
                ps.setString(3, trigger.getDescription());
                long nextFireTime = -1L;
                if (trigger.getNextFireTime() != null) {
                    nextFireTime = trigger.getNextFireTime().getTime();
                }
                ps.setBigDecimal(4, new BigDecimal(String.valueOf(nextFireTime)));
                long prevFireTime = -1L;
                if (trigger.getPreviousFireTime() != null) {
                    prevFireTime = trigger.getPreviousFireTime().getTime();
                }
                ps.setBigDecimal(5, new BigDecimal(String.valueOf(prevFireTime)));
                ps.setString(6, state);
                TriggerPersistenceDelegate tDel = this.findTriggerPersistenceDelegate(trigger);
                String type = "BLOB";
                if (tDel != null) {
                    type = tDel.getHandledTriggerTypeDiscriminator();
                }
                ps.setString(7, type);
                ps.setBigDecimal(8, new BigDecimal(String.valueOf(trigger.getStartTime().getTime())));
                long endTime = 0L;
                if (trigger.getEndTime() != null) {
                    endTime = trigger.getEndTime().getTime();
                }
                ps.setBigDecimal(9, new BigDecimal(String.valueOf(endTime)));
                ps.setString(10, trigger.getCalendarName());
                ps.setInt(11, trigger.getMisfireInstruction());
                ps.setInt(12, trigger.getPriority());
                if (updateJobData) {
                    this.setBytes(ps, 13, baos);
                    ps.setString(14, trigger.getKey().getName());
                    ps.setString(15, trigger.getKey().getGroup());
                } else {
                    ps.setString(13, trigger.getKey().getName());
                    ps.setString(14, trigger.getKey().getGroup());
                }
                insertResult = ps.executeUpdate();
                if (tDel == null) {
                    this.updateBlobTrigger(conn, trigger);
                    break block10;
                }
                tDel.updateExtendedTriggerProperties(conn, trigger, state, jobDetail);
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
        }
        StdJDBCDelegate.closeStatement(ps);
        return insertResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int updateBlobTrigger(Connection conn, OperableTrigger trigger) throws SQLException, IOException {
        int n;
        PreparedStatement ps = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(trigger);
            oos.close();
            byte[] buf = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(buf);
            ps = conn.prepareStatement(this.rtp("UPDATE {0}BLOB_TRIGGERS SET BLOB_DATA = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setBinaryStream(1, (InputStream)is, buf.length);
            ps.setString(2, trigger.getKey().getName());
            ps.setString(3, trigger.getKey().getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            if (os != null) {
                os.close();
            }
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        if (os != null) {
            os.close();
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean triggerExists(Connection conn, TriggerKey triggerKey) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            boolean bl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
                ps.setString(1, triggerKey.getName());
                ps.setString(2, triggerKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                bl = true;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return bl;
        }
        boolean bl = false;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerState(Connection conn, TriggerKey triggerKey, String state) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, state);
            ps.setString(2, triggerKey.getName());
            ps.setString(3, triggerKey.getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerStateFromOtherStates(Connection conn, TriggerKey triggerKey, String newState, String oldState1, String oldState2, String oldState3) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ? AND (TRIGGER_STATE = ? OR TRIGGER_STATE = ? OR TRIGGER_STATE = ?)"));
            ps.setString(1, newState);
            ps.setString(2, triggerKey.getName());
            ps.setString(3, triggerKey.getGroup());
            ps.setString(4, oldState1);
            ps.setString(5, oldState2);
            ps.setString(6, oldState3);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerGroupStateFromOtherStates(Connection conn, GroupMatcher<TriggerKey> matcher, String newState, String oldState1, String oldState2, String oldState3) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND TRIGGER_GROUP LIKE ? AND (TRIGGER_STATE = ? OR TRIGGER_STATE = ? OR TRIGGER_STATE = ?)"));
            ps.setString(1, newState);
            ps.setString(2, this.toSqlLikeClause(matcher));
            ps.setString(3, oldState1);
            ps.setString(4, oldState2);
            ps.setString(5, oldState3);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerStateFromOtherState(Connection conn, TriggerKey triggerKey, String newState, String oldState) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ? AND TRIGGER_STATE = ?"));
            ps.setString(1, newState);
            ps.setString(2, triggerKey.getName());
            ps.setString(3, triggerKey.getGroup());
            ps.setString(4, oldState);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerGroupStateFromOtherState(Connection conn, GroupMatcher<TriggerKey> matcher, String newState, String oldState) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND TRIGGER_GROUP LIKE ? AND TRIGGER_STATE = ?"));
            ps.setString(1, newState);
            ps.setString(2, this.toSqlLikeClause(matcher));
            ps.setString(3, oldState);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerStatesForJob(Connection conn, JobKey jobKey, String state) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, state);
            ps.setString(2, jobKey.getName());
            ps.setString(3, jobKey.getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTriggerStatesForJobFromOtherState(Connection conn, JobKey jobKey, String state, String oldState) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET TRIGGER_STATE = ? WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ? AND TRIGGER_STATE = ?"));
            ps.setString(1, state);
            ps.setString(2, jobKey.getName());
            ps.setString(3, jobKey.getGroup());
            ps.setString(4, oldState);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int deleteBlobTrigger(Connection conn, TriggerKey triggerKey) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}BLOB_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteTrigger(Connection conn, TriggerKey triggerKey) throws SQLException {
        int n;
        PreparedStatement ps = null;
        this.deleteTriggerExtension(conn, triggerKey);
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    protected void deleteTriggerExtension(Connection conn, TriggerKey triggerKey) throws SQLException {
        for (TriggerPersistenceDelegate tDel : this.triggerPersistenceDelegates) {
            if (tDel.deleteExtendedTriggerProperties(conn, triggerKey) <= 0) continue;
            return;
        }
        this.deleteBlobTrigger(conn, triggerKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int selectNumTriggersForJob(Connection conn, JobKey jobKey) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            int n;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT COUNT(TRIGGER_NAME) FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
                ps.setString(1, jobKey.getName());
                ps.setString(2, jobKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                n = rs.getInt(1);
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return n;
        }
        int n = 0;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    @Override
    public JobDetail selectJobForTrigger(Connection conn, ClassLoadHelper loadHelper, TriggerKey triggerKey) throws ClassNotFoundException, SQLException {
        return this.selectJobForTrigger(conn, loadHelper, triggerKey, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JobDetail selectJobForTrigger(Connection conn, ClassLoadHelper loadHelper, TriggerKey triggerKey, boolean loadJobClass) throws ClassNotFoundException, SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block5: {
            JobDetailImpl jobDetailImpl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT J.JOB_NAME, J.JOB_GROUP, J.IS_DURABLE, J.JOB_CLASS_NAME, J.REQUESTS_RECOVERY FROM {0}TRIGGERS T, {0}JOB_DETAILS J WHERE T.SCHED_NAME = {1} AND J.SCHED_NAME = {1} AND T.TRIGGER_NAME = ? AND T.TRIGGER_GROUP = ? AND T.JOB_NAME = J.JOB_NAME AND T.JOB_GROUP = J.JOB_GROUP"));
                ps.setString(1, triggerKey.getName());
                ps.setString(2, triggerKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block5;
                JobDetailImpl job = new JobDetailImpl();
                job.setName(rs.getString(1));
                job.setGroup(rs.getString(2));
                job.setDurability(this.getBoolean(rs, 3));
                if (loadJobClass) {
                    job.setJobClass(loadHelper.loadClass(rs.getString(4), Job.class));
                }
                job.setRequestsRecovery(this.getBoolean(rs, 5));
                jobDetailImpl = job;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return jobDetailImpl;
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("No job for trigger '" + triggerKey + "'.");
        }
        JobDetail jobDetail = null;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return jobDetail;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> selectTriggersForJob(Connection conn, JobKey jobKey) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException {
        LinkedList<OperableTrigger> trigList = new LinkedList<OperableTrigger>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, jobKey.getName());
            ps.setString(2, jobKey.getGroup());
            rs = ps.executeQuery();
            while (rs.next()) {
                OperableTrigger t = this.selectTrigger(conn, TriggerKey.triggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP")));
                if (t == null) continue;
                trigList.add(t);
            }
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return trigList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> selectTriggersForCalendar(Connection conn, String calName) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException {
        LinkedList<OperableTrigger> trigList = new LinkedList<OperableTrigger>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?"));
            ps.setString(1, calName);
            rs = ps.executeQuery();
            while (rs.next()) {
                trigList.add(this.selectTrigger(conn, TriggerKey.triggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP"))));
            }
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return trigList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public OperableTrigger selectTrigger(Connection conn, TriggerKey triggerKey) throws SQLException, ClassNotFoundException, IOException, JobPersistenceException {
        OperableTrigger operableTrigger;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            OperableTrigger trigger = null;
            ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            rs = ps.executeQuery();
            if (rs.next()) {
                String jobName = rs.getString("JOB_NAME");
                String jobGroup = rs.getString("JOB_GROUP");
                String description = rs.getString("DESCRIPTION");
                long nextFireTime = rs.getLong("NEXT_FIRE_TIME");
                long prevFireTime = rs.getLong("PREV_FIRE_TIME");
                String triggerType = rs.getString("TRIGGER_TYPE");
                long startTime = rs.getLong("START_TIME");
                long endTime = rs.getLong("END_TIME");
                String calendarName = rs.getString("CALENDAR_NAME");
                int misFireInstr = rs.getInt("MISFIRE_INSTR");
                int priority = rs.getInt("PRIORITY");
                Map map = null;
                map = this.canUseProperties() ? this.getMapFromProperties(rs) : (Map)this.getObjectFromBlob(rs, "JOB_DATA");
                Date nft = null;
                if (nextFireTime > 0L) {
                    nft = new Date(nextFireTime);
                }
                Date pft = null;
                if (prevFireTime > 0L) {
                    pft = new Date(prevFireTime);
                }
                Date startTimeD = new Date(startTime);
                Date endTimeD = null;
                if (endTime > 0L) {
                    endTimeD = new Date(endTime);
                }
                if (triggerType.equals("BLOB")) {
                    rs.close();
                    rs = null;
                    ps.close();
                    ps = null;
                    ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}BLOB_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
                    ps.setString(1, triggerKey.getName());
                    ps.setString(2, triggerKey.getGroup());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        trigger = (OperableTrigger)this.getObjectFromBlob(rs, "BLOB_DATA");
                    }
                } else {
                    TriggerPersistenceDelegate tDel = this.findTriggerPersistenceDelegate(triggerType);
                    if (tDel == null) {
                        throw new JobPersistenceException("No TriggerPersistenceDelegate for trigger discriminator type: " + triggerType);
                    }
                    TriggerPersistenceDelegate.TriggerPropertyBundle triggerProps = null;
                    try {
                        triggerProps = tDel.loadExtendedTriggerProperties(conn, triggerKey);
                    }
                    catch (IllegalStateException isex) {
                        if (this.isTriggerStillPresent(ps)) {
                            throw isex;
                        }
                        OperableTrigger operableTrigger2 = null;
                        StdJDBCDelegate.closeResultSet(rs);
                        StdJDBCDelegate.closeStatement(ps);
                        return operableTrigger2;
                    }
                    TriggerBuilder<?> tb = TriggerBuilder.newTrigger().withDescription(description).withPriority(priority).startAt(startTimeD).endAt(endTimeD).withIdentity(triggerKey).modifiedByCalendar(calendarName).withSchedule(triggerProps.getScheduleBuilder()).forJob(JobKey.jobKey(jobName, jobGroup));
                    if (null != map) {
                        tb.usingJobData(new JobDataMap(map));
                    }
                    trigger = (OperableTrigger)tb.build();
                    trigger.setMisfireInstruction(misFireInstr);
                    trigger.setNextFireTime(nft);
                    trigger.setPreviousFireTime(pft);
                    this.setTriggerStateProperties(trigger, triggerProps);
                }
            }
            operableTrigger = trigger;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return operableTrigger;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean isTriggerStillPresent(PreparedStatement ps) throws SQLException {
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            boolean bl = rs.next();
            return bl;
        }
        finally {
            StdJDBCDelegate.closeResultSet(rs);
        }
    }

    private void setTriggerStateProperties(OperableTrigger trigger, TriggerPersistenceDelegate.TriggerPropertyBundle props) throws JobPersistenceException {
        if (props.getStatePropertyNames() == null) {
            return;
        }
        Util.setBeanProps(trigger, props.getStatePropertyNames(), props.getStatePropertyValues());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JobDataMap selectTriggerJobDataMap(Connection conn, String triggerName, String groupName) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs;
        PreparedStatement ps;
        block2: {
            JobDataMap jobDataMap;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT JOB_DATA FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
                ps.setString(1, triggerName);
                ps.setString(2, groupName);
                rs = ps.executeQuery();
                if (!rs.next()) break block2;
                Map map = null;
                map = this.canUseProperties() ? this.getMapFromProperties(rs) : (Map)this.getObjectFromBlob(rs, "JOB_DATA");
                rs.close();
                ps.close();
                if (null == map) break block2;
                jobDataMap = new JobDataMap(map);
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return jobDataMap;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return new JobDataMap();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String selectTriggerState(Connection conn, TriggerKey triggerKey) throws SQLException {
        String string;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String state = null;
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_STATE FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            rs = ps.executeQuery();
            state = rs.next() ? rs.getString("TRIGGER_STATE") : "DELETED";
            string = state.intern();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TriggerStatus selectTriggerStatus(Connection conn, TriggerKey triggerKey) throws SQLException {
        TriggerStatus triggerStatus;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            TriggerStatus status = null;
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_STATE, NEXT_FIRE_TIME, JOB_NAME, JOB_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            rs = ps.executeQuery();
            if (rs.next()) {
                String state = rs.getString("TRIGGER_STATE");
                long nextFireTime = rs.getLong("NEXT_FIRE_TIME");
                String jobName = rs.getString("JOB_NAME");
                String jobGroup = rs.getString("JOB_GROUP");
                Date nft = null;
                if (nextFireTime > 0L) {
                    nft = new Date(nextFireTime);
                }
                status = new TriggerStatus(state, nft);
                status.setKey(triggerKey);
                status.setJobKey(JobKey.jobKey(jobName, jobGroup));
            }
            triggerStatus = status;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return triggerStatus;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int selectNumTriggers(Connection conn) throws SQLException {
        int n;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int count = 0;
            ps = conn.prepareStatement(this.rtp("SELECT COUNT(TRIGGER_NAME)  FROM {0}TRIGGERS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> selectTriggerGroups(Connection conn) throws SQLException {
        LinkedList<String> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT DISTINCT(TRIGGER_GROUP) FROM {0}TRIGGERS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            LinkedList<String> list = new LinkedList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> selectTriggerGroups(Connection conn, GroupMatcher<TriggerKey> matcher) throws SQLException {
        LinkedList<String> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT DISTINCT(TRIGGER_GROUP) FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP LIKE ?"));
            ps.setString(1, this.toSqlLikeClause(matcher));
            rs = ps.executeQuery();
            LinkedList<String> list = new LinkedList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<TriggerKey> selectTriggersInGroup(Connection conn, GroupMatcher<TriggerKey> matcher) throws SQLException {
        HashSet<TriggerKey> hashSet;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (this.isMatcherEquals(matcher)) {
                ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP = ?"));
                ps.setString(1, this.toSqlEqualsClause(matcher));
            } else {
                ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP LIKE ?"));
                ps.setString(1, this.toSqlLikeClause(matcher));
            }
            rs = ps.executeQuery();
            HashSet<TriggerKey> keys = new HashSet<TriggerKey>();
            while (rs.next()) {
                keys.add(TriggerKey.triggerKey(rs.getString(1), rs.getString(2)));
            }
            hashSet = keys;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return hashSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertPausedTriggerGroup(Connection conn, String groupName) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            int rows;
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}PAUSED_TRIGGER_GRPS (SCHED_NAME, TRIGGER_GROUP) VALUES({1}, ?)"));
            ps.setString(1, groupName);
            n = rows = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deletePausedTriggerGroup(Connection conn, String groupName) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            int rows;
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}PAUSED_TRIGGER_GRPS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP LIKE ?"));
            ps.setString(1, groupName);
            n = rows = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deletePausedTriggerGroup(Connection conn, GroupMatcher<TriggerKey> matcher) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            int rows;
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}PAUSED_TRIGGER_GRPS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP LIKE ?"));
            ps.setString(1, this.toSqlLikeClause(matcher));
            n = rows = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteAllPausedTriggerGroups(Connection conn) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            int rows;
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}PAUSED_TRIGGER_GRPS WHERE SCHED_NAME = {1}"));
            n = rows = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isTriggerGroupPaused(Connection conn, String groupName) throws SQLException {
        boolean bl;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_GROUP FROM {0}PAUSED_TRIGGER_GRPS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP = ?"));
            ps.setString(1, groupName);
            rs = ps.executeQuery();
            bl = rs.next();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isExistingTriggerGroup(Connection conn, String groupName) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            boolean bl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT COUNT(TRIGGER_NAME)  FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP = ?"));
                ps.setString(1, groupName);
                rs = ps.executeQuery();
                if (rs.next()) break block3;
                bl = false;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return bl;
        }
        boolean bl = rs.getInt(1) > 0;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertCalendar(Connection conn, String calendarName, Calendar calendar) throws IOException, SQLException {
        int n;
        ByteArrayOutputStream baos = this.serializeObject(calendar);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}CALENDARS (SCHED_NAME, CALENDAR_NAME, CALENDAR)  VALUES({1}, ?, ?)"));
            ps.setString(1, calendarName);
            this.setBytes(ps, 2, baos);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateCalendar(Connection conn, String calendarName, Calendar calendar) throws IOException, SQLException {
        int n;
        ByteArrayOutputStream baos = this.serializeObject(calendar);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}CALENDARS SET CALENDAR = ?  WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?"));
            this.setBytes(ps, 1, baos);
            ps.setString(2, calendarName);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean calendarExists(Connection conn, String calendarName) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            boolean bl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT CALENDAR_NAME FROM {0}CALENDARS WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?"));
                ps.setString(1, calendarName);
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                bl = true;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return bl;
        }
        boolean bl = false;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Calendar selectCalendar(Connection conn, String calendarName) throws ClassNotFoundException, IOException, SQLException {
        Calendar calendar;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String selCal = this.rtp("SELECT * FROM {0}CALENDARS WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?");
            ps = conn.prepareStatement(selCal);
            ps.setString(1, calendarName);
            rs = ps.executeQuery();
            Calendar cal = null;
            if (rs.next()) {
                cal = (Calendar)this.getObjectFromBlob(rs, "CALENDAR");
            }
            if (null == cal) {
                this.logger.warn("Couldn't find calendar with name '" + calendarName + "'.");
            }
            calendar = cal;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return calendar;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean calendarIsReferenced(Connection conn, String calendarName) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            boolean bl;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT CALENDAR_NAME FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?"));
                ps.setString(1, calendarName);
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                bl = true;
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return bl;
        }
        boolean bl = false;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteCalendar(Connection conn, String calendarName) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}CALENDARS WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?"));
            ps.setString(1, calendarName);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int selectNumCalendars(Connection conn) throws SQLException {
        int n;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int count = 0;
            ps = conn.prepareStatement(this.rtp("SELECT COUNT(CALENDAR_NAME)  FROM {0}CALENDARS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> selectCalendars(Connection conn) throws SQLException {
        LinkedList<String> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT CALENDAR_NAME FROM {0}CALENDARS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            LinkedList<String> list = new LinkedList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            linkedList = list;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long selectNextFireTime(Connection conn) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            long l;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT MIN(NEXT_FIRE_TIME) AS ALIAS_NXT_FR_TM FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_STATE = ? AND NEXT_FIRE_TIME >= 0"));
                ps.setString(1, "WAITING");
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                l = rs.getLong("ALIAS_NXT_FR_TM");
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return l;
        }
        long l = 0L;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TriggerKey selectTriggerForFireTime(Connection conn, long fireTime) throws SQLException {
        ResultSet rs;
        PreparedStatement ps;
        block3: {
            TriggerKey triggerKey;
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_STATE = ? AND NEXT_FIRE_TIME = ?"));
                ps.setString(1, "WAITING");
                ps.setBigDecimal(2, new BigDecimal(String.valueOf(fireTime)));
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                triggerKey = new TriggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP"));
            }
            catch (Throwable throwable) {
                StdJDBCDelegate.closeResultSet(rs);
                StdJDBCDelegate.closeStatement(ps);
                throw throwable;
            }
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            return triggerKey;
        }
        TriggerKey triggerKey = null;
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return triggerKey;
    }

    @Override
    public List<TriggerKey> selectTriggerToAcquire(Connection conn, long noLaterThan, long noEarlierThan) throws SQLException {
        return this.selectTriggerToAcquire(conn, noLaterThan, noEarlierThan, 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerKey> selectTriggerToAcquire(Connection conn, long noLaterThan, long noEarlierThan, int maxCount) throws SQLException {
        LinkedList<TriggerKey> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList<TriggerKey> nextTriggers = new LinkedList<TriggerKey>();
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_NAME, TRIGGER_GROUP, NEXT_FIRE_TIME, PRIORITY FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_STATE = ? AND NEXT_FIRE_TIME <= ? AND (MISFIRE_INSTR = -1 OR (MISFIRE_INSTR != -1 AND NEXT_FIRE_TIME >= ?)) ORDER BY NEXT_FIRE_TIME ASC, PRIORITY DESC"));
            if (maxCount < 1) {
                maxCount = 1;
            }
            ps.setMaxRows(maxCount);
            ps.setFetchSize(maxCount);
            ps.setString(1, "WAITING");
            ps.setBigDecimal(2, new BigDecimal(String.valueOf(noLaterThan)));
            ps.setBigDecimal(3, new BigDecimal(String.valueOf(noEarlierThan)));
            rs = ps.executeQuery();
            while (rs.next() && nextTriggers.size() < maxCount) {
                nextTriggers.add(TriggerKey.triggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP")));
            }
            linkedList = nextTriggers;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertFiredTrigger(Connection conn, OperableTrigger trigger, String state, JobDetail job) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}FIRED_TRIGGERS (SCHED_NAME, ENTRY_ID, TRIGGER_NAME, TRIGGER_GROUP, INSTANCE_NAME, FIRED_TIME, SCHED_TIME, STATE, JOB_NAME, JOB_GROUP, IS_NONCONCURRENT, REQUESTS_RECOVERY, PRIORITY) VALUES({1}, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
            ps.setString(1, trigger.getFireInstanceId());
            ps.setString(2, trigger.getKey().getName());
            ps.setString(3, trigger.getKey().getGroup());
            ps.setString(4, this.instanceId);
            ps.setBigDecimal(5, new BigDecimal(String.valueOf(System.currentTimeMillis())));
            ps.setBigDecimal(6, new BigDecimal(String.valueOf(trigger.getNextFireTime().getTime())));
            ps.setString(7, state);
            if (job != null) {
                ps.setString(8, trigger.getJobKey().getName());
                ps.setString(9, trigger.getJobKey().getGroup());
                this.setBoolean(ps, 10, job.isConcurrentExectionDisallowed());
                this.setBoolean(ps, 11, job.requestsRecovery());
            } else {
                ps.setString(8, null);
                ps.setString(9, null);
                this.setBoolean(ps, 10, false);
                this.setBoolean(ps, 11, false);
            }
            ps.setInt(12, trigger.getPriority());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateFiredTrigger(Connection conn, OperableTrigger trigger, String state, JobDetail job) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}FIRED_TRIGGERS SET INSTANCE_NAME = ?, FIRED_TIME = ?, SCHED_TIME = ?, STATE = ?, JOB_NAME = ?, JOB_GROUP = ?, IS_NONCONCURRENT = ?, REQUESTS_RECOVERY = ? WHERE SCHED_NAME = {1} AND ENTRY_ID = ?"));
            ps.setString(1, this.instanceId);
            ps.setBigDecimal(2, new BigDecimal(String.valueOf(System.currentTimeMillis())));
            ps.setBigDecimal(3, new BigDecimal(String.valueOf(trigger.getNextFireTime().getTime())));
            ps.setString(4, state);
            if (job != null) {
                ps.setString(5, trigger.getJobKey().getName());
                ps.setString(6, trigger.getJobKey().getGroup());
                this.setBoolean(ps, 7, job.isConcurrentExectionDisallowed());
                this.setBoolean(ps, 8, job.requestsRecovery());
            } else {
                ps.setString(5, null);
                ps.setString(6, null);
                this.setBoolean(ps, 7, false);
                this.setBoolean(ps, 8, false);
            }
            ps.setString(9, trigger.getFireInstanceId());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FiredTriggerRecord> selectFiredTriggerRecords(Connection conn, String triggerName, String groupName) throws SQLException {
        LinkedList<FiredTriggerRecord> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            LinkedList<FiredTriggerRecord> lst = new LinkedList<FiredTriggerRecord>();
            if (triggerName != null) {
                ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
                ps.setString(1, triggerName);
                ps.setString(2, groupName);
            } else {
                ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_GROUP = ?"));
                ps.setString(1, groupName);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                FiredTriggerRecord rec = new FiredTriggerRecord();
                rec.setFireInstanceId(rs.getString("ENTRY_ID"));
                rec.setFireInstanceState(rs.getString("STATE"));
                rec.setFireTimestamp(rs.getLong("FIRED_TIME"));
                rec.setScheduleTimestamp(rs.getLong("SCHED_TIME"));
                rec.setPriority(rs.getInt("PRIORITY"));
                rec.setSchedulerInstanceId(rs.getString("INSTANCE_NAME"));
                rec.setTriggerKey(TriggerKey.triggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP")));
                if (!rec.getFireInstanceState().equals("ACQUIRED")) {
                    rec.setJobDisallowsConcurrentExecution(this.getBoolean(rs, "IS_NONCONCURRENT"));
                    rec.setJobRequestsRecovery(rs.getBoolean("REQUESTS_RECOVERY"));
                    rec.setJobKey(JobKey.jobKey(rs.getString("JOB_NAME"), rs.getString("JOB_GROUP")));
                }
                lst.add(rec);
            }
            linkedList = lst;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FiredTriggerRecord> selectFiredTriggerRecordsByJob(Connection conn, String jobName, String groupName) throws SQLException {
        LinkedList<FiredTriggerRecord> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            LinkedList<FiredTriggerRecord> lst = new LinkedList<FiredTriggerRecord>();
            if (jobName != null) {
                ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
                ps.setString(1, jobName);
                ps.setString(2, groupName);
            } else {
                ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND JOB_GROUP = ?"));
                ps.setString(1, groupName);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                FiredTriggerRecord rec = new FiredTriggerRecord();
                rec.setFireInstanceId(rs.getString("ENTRY_ID"));
                rec.setFireInstanceState(rs.getString("STATE"));
                rec.setFireTimestamp(rs.getLong("FIRED_TIME"));
                rec.setScheduleTimestamp(rs.getLong("SCHED_TIME"));
                rec.setPriority(rs.getInt("PRIORITY"));
                rec.setSchedulerInstanceId(rs.getString("INSTANCE_NAME"));
                rec.setTriggerKey(TriggerKey.triggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP")));
                if (!rec.getFireInstanceState().equals("ACQUIRED")) {
                    rec.setJobDisallowsConcurrentExecution(this.getBoolean(rs, "IS_NONCONCURRENT"));
                    rec.setJobRequestsRecovery(rs.getBoolean("REQUESTS_RECOVERY"));
                    rec.setJobKey(JobKey.jobKey(rs.getString("JOB_NAME"), rs.getString("JOB_GROUP")));
                }
                lst.add(rec);
            }
            linkedList = lst;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FiredTriggerRecord> selectInstancesFiredTriggerRecords(Connection conn, String instanceName) throws SQLException {
        LinkedList<FiredTriggerRecord> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            LinkedList<FiredTriggerRecord> lst = new LinkedList<FiredTriggerRecord>();
            ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND INSTANCE_NAME = ?"));
            ps.setString(1, instanceName);
            rs = ps.executeQuery();
            while (rs.next()) {
                FiredTriggerRecord rec = new FiredTriggerRecord();
                rec.setFireInstanceId(rs.getString("ENTRY_ID"));
                rec.setFireInstanceState(rs.getString("STATE"));
                rec.setFireTimestamp(rs.getLong("FIRED_TIME"));
                rec.setScheduleTimestamp(rs.getLong("SCHED_TIME"));
                rec.setSchedulerInstanceId(rs.getString("INSTANCE_NAME"));
                rec.setTriggerKey(TriggerKey.triggerKey(rs.getString("TRIGGER_NAME"), rs.getString("TRIGGER_GROUP")));
                if (!rec.getFireInstanceState().equals("ACQUIRED")) {
                    rec.setJobDisallowsConcurrentExecution(this.getBoolean(rs, "IS_NONCONCURRENT"));
                    rec.setJobRequestsRecovery(rs.getBoolean("REQUESTS_RECOVERY"));
                    rec.setJobKey(JobKey.jobKey(rs.getString("JOB_NAME"), rs.getString("JOB_GROUP")));
                }
                rec.setPriority(rs.getInt("PRIORITY"));
                lst.add(rec);
            }
            linkedList = lst;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> selectFiredTriggerInstanceNames(Connection conn) throws SQLException {
        HashSet<String> hashSet;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            HashSet<String> instanceNames = new HashSet<String>();
            ps = conn.prepareStatement(this.rtp("SELECT DISTINCT INSTANCE_NAME FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            while (rs.next()) {
                instanceNames.add(rs.getString("INSTANCE_NAME"));
            }
            hashSet = instanceNames;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return hashSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteFiredTrigger(Connection conn, String entryId) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND ENTRY_ID = ?"));
            ps.setString(1, entryId);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int selectJobExecutionCount(Connection conn, JobKey jobKey) throws SQLException {
        int n;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp("SELECT COUNT(TRIGGER_NAME) FROM {0}FIRED_TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?"));
            ps.setString(1, jobKey.getName());
            ps.setString(2, jobKey.getGroup());
            rs = ps.executeQuery();
            n = rs.next() ? rs.getInt(1) : 0;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertSchedulerState(Connection conn, String theInstanceId, long checkInTime, long interval) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}SCHEDULER_STATE (SCHED_NAME, INSTANCE_NAME, LAST_CHECKIN_TIME, CHECKIN_INTERVAL) VALUES({1}, ?, ?, ?)"));
            ps.setString(1, theInstanceId);
            ps.setLong(2, checkInTime);
            ps.setLong(3, interval);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteSchedulerState(Connection conn, String theInstanceId) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("DELETE FROM {0}SCHEDULER_STATE WHERE SCHED_NAME = {1} AND INSTANCE_NAME = ?"));
            ps.setString(1, theInstanceId);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateSchedulerState(Connection conn, String theInstanceId, long checkInTime) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(this.rtp("UPDATE {0}SCHEDULER_STATE SET LAST_CHECKIN_TIME = ? WHERE SCHED_NAME = {1} AND INSTANCE_NAME = ?"));
            ps.setLong(1, checkInTime);
            ps.setString(2, theInstanceId);
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<SchedulerStateRecord> selectSchedulerStateRecords(Connection conn, String theInstanceId) throws SQLException {
        LinkedList<SchedulerStateRecord> linkedList;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            LinkedList<SchedulerStateRecord> lst = new LinkedList<SchedulerStateRecord>();
            if (theInstanceId != null) {
                ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}SCHEDULER_STATE WHERE SCHED_NAME = {1} AND INSTANCE_NAME = ?"));
                ps.setString(1, theInstanceId);
            } else {
                ps = conn.prepareStatement(this.rtp("SELECT * FROM {0}SCHEDULER_STATE WHERE SCHED_NAME = {1}"));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                SchedulerStateRecord rec = new SchedulerStateRecord();
                rec.setSchedulerInstanceId(rs.getString("INSTANCE_NAME"));
                rec.setCheckinTimestamp(rs.getLong("LAST_CHECKIN_TIME"));
                rec.setCheckinInterval(rs.getLong("CHECKIN_INTERVAL"));
                lst.add(rec);
            }
            linkedList = lst;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return linkedList;
    }

    protected final String rtp(String query) {
        return Util.rtp(query, this.tablePrefix, this.getSchedulerNameLiteral());
    }

    protected String getSchedulerNameLiteral() {
        if (this.schedNameLiteral == null) {
            this.schedNameLiteral = "'" + this.schedName + "'";
        }
        return this.schedNameLiteral;
    }

    protected ByteArrayOutputStream serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (null != obj) {
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(obj);
            out.flush();
        }
        return baos;
    }

    protected ByteArrayOutputStream serializeJobData(JobDataMap data) throws IOException {
        if (this.canUseProperties()) {
            return this.serializeProperties(data);
        }
        try {
            return this.serializeObject(data);
        }
        catch (NotSerializableException e) {
            throw new NotSerializableException("Unable to serialize JobDataMap for insertion into database because the value of property '" + this.getKeyOfNonSerializableValue(data) + "' is not serializable: " + e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object getKeyOfNonSerializableValue(Map<?, ?> data) {
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            ByteArrayOutputStream baos = null;
            try {
                baos = this.serializeObject(entry.getValue());
            }
            catch (IOException e) {
                Object obj = entry.getKey();
                return obj;
            }
            finally {
                if (baos == null) continue;
                try {
                    baos.close();
                }
                catch (IOException iOException) {}
            }
        }
        return null;
    }

    private ByteArrayOutputStream serializeProperties(JobDataMap data) throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        if (null != data) {
            Properties properties = this.convertToProperty(data.getWrappedMap());
            properties.store(ba, "");
        }
        return ba;
    }

    protected Map<?, ?> convertFromProperty(Properties properties) throws IOException {
        return new HashMap<Object, Object>(properties);
    }

    protected Properties convertToProperty(Map<?, ?> data) throws IOException {
        Properties properties = new Properties();
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            String val;
            Object key = entry.getKey();
            String string = val = entry.getValue() == null ? "" : entry.getValue();
            if (!(key instanceof String)) {
                throw new IOException("JobDataMap keys/values must be Strings when the 'useProperties' property is set.  offending Key: " + key);
            }
            if (!(val instanceof String)) {
                throw new IOException("JobDataMap values must be Strings when the 'useProperties' property is set.  Key of offending value: " + key);
            }
            properties.put(key, val);
        }
        return properties;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        InputStream binaryInput;
        Object obj = null;
        Blob blobLocator = rs.getBlob(colName);
        if (!(blobLocator == null || blobLocator.length() == 0L || null == (binaryInput = blobLocator.getBinaryStream()) || binaryInput instanceof ByteArrayInputStream && ((ByteArrayInputStream)binaryInput).available() == 0)) {
            try (ObjectInputStream in = new ObjectInputStream(binaryInput);){
                obj = in.readObject();
            }
        }
        return obj;
    }

    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            Blob blobLocator = rs.getBlob(colName);
            if (blobLocator != null) {
                InputStream binaryInput = blobLocator.getBinaryStream();
                return binaryInput;
            }
            return null;
        }
        return this.getObjectFromBlob(rs, colName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> selectPausedTriggerGroups(Connection conn) throws SQLException {
        HashSet<String> hashSet;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashSet<String> set = new HashSet<String>();
        try {
            ps = conn.prepareStatement(this.rtp("SELECT TRIGGER_GROUP FROM {0}PAUSED_TRIGGER_GRPS WHERE SCHED_NAME = {1}"));
            rs = ps.executeQuery();
            while (rs.next()) {
                String groupName = rs.getString("TRIGGER_GROUP");
                set.add(groupName);
            }
            hashSet = set;
        }
        catch (Throwable throwable) {
            StdJDBCDelegate.closeResultSet(rs);
            StdJDBCDelegate.closeStatement(ps);
            throw throwable;
        }
        StdJDBCDelegate.closeResultSet(rs);
        StdJDBCDelegate.closeStatement(ps);
        return hashSet;
    }

    protected static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
    }

    protected static void closeStatement(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
    }

    protected void setBoolean(PreparedStatement ps, int index, boolean val) throws SQLException {
        ps.setBoolean(index, val);
    }

    protected boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        return rs.getBoolean(columnName);
    }

    protected boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBoolean(columnIndex);
    }

    protected void setBytes(PreparedStatement ps, int index, ByteArrayOutputStream baos) throws SQLException {
        ps.setBytes(index, baos == null ? new byte[]{} : baos.toByteArray());
    }
}

