/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  oracle.sql.BLOB
 */
package org.quartz.impl.jdbcjobstore.oracle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.sql.BLOB;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.impl.jdbcjobstore.TriggerPersistenceDelegate;
import org.quartz.spi.OperableTrigger;

public class OracleDelegate
extends StdJDBCDelegate {
    public static final String INSERT_ORACLE_JOB_DETAIL = "INSERT INTO {0}JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP, DESCRIPTION, JOB_CLASS_NAME, IS_DURABLE, IS_NONCONCURRENT, IS_UPDATE_DATA, REQUESTS_RECOVERY, JOB_DATA)  VALUES({1}, ?, ?, ?, ?, ?, ?, ?, ?, EMPTY_BLOB())";
    public static final String UPDATE_ORACLE_JOB_DETAIL = "UPDATE {0}JOB_DETAILS SET DESCRIPTION = ?, JOB_CLASS_NAME = ?, IS_DURABLE = ?, IS_NONCONCURRENT = ?, IS_UPDATE_DATA = ?, REQUESTS_RECOVERY = ?, JOB_DATA = EMPTY_BLOB()  WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?";
    public static final String UPDATE_ORACLE_JOB_DETAIL_BLOB = "UPDATE {0}JOB_DETAILS SET JOB_DATA = ?  WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?";
    public static final String SELECT_ORACLE_JOB_DETAIL_BLOB = "SELECT JOB_DATA FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ? FOR UPDATE";
    public static final String UPDATE_ORACLE_TRIGGER = "UPDATE {0}TRIGGERS SET JOB_NAME = ?, JOB_GROUP = ?, DESCRIPTION = ?, NEXT_FIRE_TIME = ?, PREV_FIRE_TIME = ?, TRIGGER_STATE = ?, TRIGGER_TYPE = ?, START_TIME = ?, END_TIME = ?, CALENDAR_NAME = ?, MISFIRE_INSTR = ?, PRIORITY = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?";
    public static final String SELECT_ORACLE_TRIGGER_JOB_DETAIL_BLOB = "SELECT JOB_DATA FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ? FOR UPDATE";
    public static final String UPDATE_ORACLE_TRIGGER_JOB_DETAIL_BLOB = "UPDATE {0}TRIGGERS SET JOB_DATA = ?  WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?";
    public static final String UPDATE_ORACLE_TRIGGER_JOB_DETAIL_EMPTY_BLOB = "UPDATE {0}TRIGGERS SET JOB_DATA = EMPTY_BLOB()  WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?";
    public static final String INSERT_ORACLE_CALENDAR = "INSERT INTO {0}CALENDARS (SCHED_NAME, CALENDAR_NAME, CALENDAR)  VALUES({1}, ?, EMPTY_BLOB())";
    public static final String SELECT_ORACLE_CALENDAR_BLOB = "SELECT CALENDAR FROM {0}CALENDARS WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ? FOR UPDATE";
    public static final String UPDATE_ORACLE_CALENDAR_BLOB = "UPDATE {0}CALENDARS SET CALENDAR = ?  WHERE SCHED_NAME = {1} AND CALENDAR_NAME = ?";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        Object obj = null;
        InputStream binaryInput = rs.getBinaryStream(colName);
        if (binaryInput != null) {
            try (ObjectInputStream in = new ObjectInputStream(binaryInput);){
                obj = in.readObject();
            }
        }
        return obj;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
        Blob dbBlob;
        int res;
        ResultSet rs;
        PreparedStatement ps;
        block4: {
            int n;
            ByteArrayOutputStream baos = this.serializeJobData(job.getJobDataMap());
            byte[] data = baos.toByteArray();
            ps = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp(INSERT_ORACLE_JOB_DETAIL));
                ps.setString(1, job.getKey().getName());
                ps.setString(2, job.getKey().getGroup());
                ps.setString(3, job.getDescription());
                ps.setString(4, job.getJobClass().getName());
                this.setBoolean(ps, 5, job.isDurable());
                this.setBoolean(ps, 6, job.isConcurrentExectionDisallowed());
                this.setBoolean(ps, 7, job.isPersistJobDataAfterExecution());
                this.setBoolean(ps, 8, job.requestsRecovery());
                ps.executeUpdate();
                ps.close();
                ps = conn.prepareStatement(this.rtp(SELECT_ORACLE_JOB_DETAIL_BLOB));
                ps.setString(1, job.getKey().getName());
                ps.setString(2, job.getKey().getGroup());
                rs = ps.executeQuery();
                res = 0;
                dbBlob = null;
                if (rs.next()) {
                    dbBlob = this.writeDataToBlob(rs, 1, data);
                    break block4;
                }
                n = res;
            }
            catch (Throwable throwable) {
                OracleDelegate.closeResultSet(rs);
                OracleDelegate.closeStatement(ps);
                throw throwable;
            }
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            return n;
        }
        rs.close();
        ps.close();
        ps = conn.prepareStatement(this.rtp(UPDATE_ORACLE_JOB_DETAIL_BLOB));
        ps.setBlob(1, dbBlob);
        ps.setString(2, job.getKey().getName());
        ps.setString(3, job.getKey().getGroup());
        int n = res = ps.executeUpdate();
        OracleDelegate.closeResultSet(rs);
        OracleDelegate.closeStatement(ps);
        return n;
    }

    @Override
    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            InputStream binaryInput = rs.getBinaryStream(colName);
            return binaryInput;
        }
        return this.getObjectFromBlob(rs, colName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
        int n;
        ByteArrayOutputStream baos = this.serializeJobData(job.getJobDataMap());
        byte[] data = baos.toByteArray();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp(UPDATE_ORACLE_JOB_DETAIL));
            ps.setString(1, job.getDescription());
            ps.setString(2, job.getJobClass().getName());
            this.setBoolean(ps, 3, job.isDurable());
            this.setBoolean(ps, 4, job.isConcurrentExectionDisallowed());
            this.setBoolean(ps, 5, job.isPersistJobDataAfterExecution());
            this.setBoolean(ps, 6, job.requestsRecovery());
            ps.setString(7, job.getKey().getName());
            ps.setString(8, job.getKey().getGroup());
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp(SELECT_ORACLE_JOB_DETAIL_BLOB));
            ps.setString(1, job.getKey().getName());
            ps.setString(2, job.getKey().getGroup());
            rs = ps.executeQuery();
            int res = 0;
            if (rs.next()) {
                Blob dbBlob = this.writeDataToBlob(rs, 1, data);
                ps2 = conn.prepareStatement(this.rtp(UPDATE_ORACLE_JOB_DETAIL_BLOB));
                ps2.setBlob(1, dbBlob);
                ps2.setString(2, job.getKey().getName());
                ps2.setString(3, job.getKey().getGroup());
                res = ps2.executeUpdate();
            }
            n = res;
        }
        catch (Throwable throwable) {
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            OracleDelegate.closeStatement(ps2);
            throw throwable;
        }
        OracleDelegate.closeResultSet(rs);
        OracleDelegate.closeStatement(ps);
        OracleDelegate.closeStatement(ps2);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public int insertTrigger(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        block11: {
            data = null;
            if (trigger.getJobDataMap().size() > 0) {
                data = this.serializeJobData(trigger.getJobDataMap()).toByteArray();
            }
            ps = null;
            rs = null;
            insertResult = 0;
            ps = conn.prepareStatement(this.rtp("INSERT INTO {0}TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, DESCRIPTION, NEXT_FIRE_TIME, PREV_FIRE_TIME, TRIGGER_STATE, TRIGGER_TYPE, START_TIME, END_TIME, CALENDAR_NAME, MISFIRE_INSTR, JOB_DATA, PRIORITY)  VALUES({1}, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
            ps.setString(1, trigger.getKey().getName());
            ps.setString(2, trigger.getKey().getGroup());
            ps.setString(3, trigger.getJobKey().getName());
            ps.setString(4, trigger.getJobKey().getGroup());
            ps.setString(5, trigger.getDescription());
            ps.setBigDecimal(6, new BigDecimal(String.valueOf(trigger.getNextFireTime().getTime())));
            prevFireTime = -1L;
            if (trigger.getPreviousFireTime() != null) {
                prevFireTime = trigger.getPreviousFireTime().getTime();
            }
            ps.setBigDecimal(7, new BigDecimal(String.valueOf(prevFireTime)));
            ps.setString(8, state);
            tDel = this.findTriggerPersistenceDelegate(trigger);
            type = "BLOB";
            if (tDel != null) {
                type = tDel.getHandledTriggerTypeDiscriminator();
            }
            ps.setString(9, type);
            ps.setBigDecimal(10, new BigDecimal(String.valueOf(trigger.getStartTime().getTime())));
            endTime = 0L;
            if (trigger.getEndTime() != null) {
                endTime = trigger.getEndTime().getTime();
            }
            ps.setBigDecimal(11, new BigDecimal(String.valueOf(endTime)));
            ps.setString(12, trigger.getCalendarName());
            ps.setInt(13, trigger.getMisfireInstruction());
            ps.setBinaryStream(14, (InputStream)null, 0);
            ps.setInt(15, trigger.getPriority());
            insertResult = ps.executeUpdate();
            if (data == null) ** GOTO lbl67
            ps.close();
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET JOB_DATA = EMPTY_BLOB()  WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setString(1, trigger.getKey().getName());
            ps.setString(2, trigger.getKey().getGroup());
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement(this.rtp("SELECT JOB_DATA FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ? FOR UPDATE"));
            ps.setString(1, trigger.getKey().getName());
            ps.setString(2, trigger.getKey().getGroup());
            rs = ps.executeQuery();
            dbBlob = null;
            if (rs.next()) {
                dbBlob = this.writeDataToBlob(rs, 1, data);
                break block11;
            }
            var16_14 = 0;
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            return var16_14;
        }
        try {
            rs.close();
            ps.close();
            ps = conn.prepareStatement(this.rtp("UPDATE {0}TRIGGERS SET JOB_DATA = ?  WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?"));
            ps.setBlob(1, dbBlob);
            ps.setString(2, trigger.getKey().getName());
            ps.setString(3, trigger.getKey().getGroup());
            ps.executeUpdate();
lbl67:
            // 2 sources

            if (tDel == null) {
                this.insertBlobTrigger(conn, trigger);
            } else {
                tDel.insertExtendedTriggerProperties(conn, trigger, state, jobDetail);
            }
        }
        finally {
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
        }
        return insertResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateTrigger(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        boolean updateJobData = trigger.getJobDataMap().isDirty();
        byte[] data = null;
        if (updateJobData && trigger.getJobDataMap().size() > 0) {
            data = this.serializeJobData(trigger.getJobDataMap()).toByteArray();
        }
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        int insertResult = 0;
        try {
            ps = conn.prepareStatement(this.rtp(UPDATE_ORACLE_TRIGGER));
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
            ps.setString(13, trigger.getKey().getName());
            ps.setString(14, trigger.getKey().getGroup());
            insertResult = ps.executeUpdate();
            if (updateJobData) {
                ps.close();
                ps = conn.prepareStatement(this.rtp(UPDATE_ORACLE_TRIGGER_JOB_DETAIL_EMPTY_BLOB));
                ps.setString(1, trigger.getKey().getName());
                ps.setString(2, trigger.getKey().getGroup());
                ps.executeUpdate();
                ps.close();
                ps = conn.prepareStatement(this.rtp(SELECT_ORACLE_TRIGGER_JOB_DETAIL_BLOB));
                ps.setString(1, trigger.getKey().getName());
                ps.setString(2, trigger.getKey().getGroup());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Blob dbBlob = this.writeDataToBlob(rs, 1, data);
                    ps2 = conn.prepareStatement(this.rtp(UPDATE_ORACLE_TRIGGER_JOB_DETAIL_BLOB));
                    ps2.setBlob(1, dbBlob);
                    ps2.setString(2, trigger.getKey().getName());
                    ps2.setString(3, trigger.getKey().getGroup());
                    ps2.executeUpdate();
                }
            }
            if (tDel == null) {
                this.updateBlobTrigger(conn, trigger);
            } else {
                tDel.updateExtendedTriggerProperties(conn, trigger, state, jobDetail);
            }
        }
        finally {
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            OracleDelegate.closeStatement(ps2);
        }
        return insertResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertCalendar(Connection conn, String calendarName, Calendar calendar) throws IOException, SQLException {
        ResultSet rs;
        PreparedStatement ps2;
        PreparedStatement ps;
        block3: {
            int n;
            ByteArrayOutputStream baos = this.serializeObject(calendar);
            ps = null;
            ps2 = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp(INSERT_ORACLE_CALENDAR));
                ps.setString(1, calendarName);
                ps.executeUpdate();
                ps.close();
                ps = conn.prepareStatement(this.rtp(SELECT_ORACLE_CALENDAR_BLOB));
                ps.setString(1, calendarName);
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                Blob dbBlob = this.writeDataToBlob(rs, 1, baos.toByteArray());
                ps2 = conn.prepareStatement(this.rtp(UPDATE_ORACLE_CALENDAR_BLOB));
                ps2.setBlob(1, dbBlob);
                ps2.setString(2, calendarName);
                n = ps2.executeUpdate();
            }
            catch (Throwable throwable) {
                OracleDelegate.closeResultSet(rs);
                OracleDelegate.closeStatement(ps);
                OracleDelegate.closeStatement(ps2);
                throw throwable;
            }
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            OracleDelegate.closeStatement(ps2);
            return n;
        }
        int n = 0;
        OracleDelegate.closeResultSet(rs);
        OracleDelegate.closeStatement(ps);
        OracleDelegate.closeStatement(ps2);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateCalendar(Connection conn, String calendarName, Calendar calendar) throws IOException, SQLException {
        ResultSet rs;
        PreparedStatement ps2;
        PreparedStatement ps;
        block3: {
            int n;
            ByteArrayOutputStream baos = this.serializeObject(calendar);
            ps = null;
            ps2 = null;
            rs = null;
            try {
                ps = conn.prepareStatement(this.rtp(SELECT_ORACLE_CALENDAR_BLOB));
                ps.setString(1, calendarName);
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                Blob dbBlob = this.writeDataToBlob(rs, 1, baos.toByteArray());
                ps2 = conn.prepareStatement(this.rtp(UPDATE_ORACLE_CALENDAR_BLOB));
                ps2.setBlob(1, dbBlob);
                ps2.setString(2, calendarName);
                n = ps2.executeUpdate();
            }
            catch (Throwable throwable) {
                OracleDelegate.closeResultSet(rs);
                OracleDelegate.closeStatement(ps);
                OracleDelegate.closeStatement(ps2);
                throw throwable;
            }
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            OracleDelegate.closeStatement(ps2);
            return n;
        }
        int n = 0;
        OracleDelegate.closeResultSet(rs);
        OracleDelegate.closeStatement(ps);
        OracleDelegate.closeStatement(ps2);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateJobData(Connection conn, JobDetail job) throws IOException, SQLException {
        int n;
        ByteArrayOutputStream baos = this.serializeJobData(job.getJobDataMap());
        byte[] data = baos.toByteArray();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(this.rtp(SELECT_ORACLE_JOB_DETAIL_BLOB));
            ps.setString(1, job.getKey().getName());
            ps.setString(2, job.getKey().getGroup());
            rs = ps.executeQuery();
            int res = 0;
            if (rs.next()) {
                Blob dbBlob = this.writeDataToBlob(rs, 1, data);
                ps2 = conn.prepareStatement(this.rtp(UPDATE_ORACLE_JOB_DETAIL_BLOB));
                ps2.setBlob(1, dbBlob);
                ps2.setString(2, job.getKey().getName());
                ps2.setString(3, job.getKey().getGroup());
                res = ps2.executeUpdate();
            }
            n = res;
        }
        catch (Throwable throwable) {
            OracleDelegate.closeResultSet(rs);
            OracleDelegate.closeStatement(ps);
            OracleDelegate.closeStatement(ps2);
            throw throwable;
        }
        OracleDelegate.closeResultSet(rs);
        OracleDelegate.closeStatement(ps);
        OracleDelegate.closeStatement(ps2);
        return n;
    }

    protected Blob writeDataToBlob(ResultSet rs, int column, byte[] data) throws SQLException {
        Blob blob = rs.getBlob(column);
        if (blob == null) {
            throw new SQLException("Driver's Blob representation is null!");
        }
        if (blob instanceof BLOB) {
            ((BLOB)blob).putBytes(1L, data);
            ((BLOB)blob).trim((long)data.length);
            return blob;
        }
        throw new SQLException("Driver's Blob representation is of an unsupported type: " + blob.getClass().getName());
    }
}

