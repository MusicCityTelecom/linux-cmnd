/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.JobKey;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class DB2v6Delegate
extends StdJDBCDelegate {
    public static final String SELECT_NUM_JOBS = "SELECT COUNT(*) FROM {0}JOB_DETAILS WHERE SCHED_NAME = {1}";
    public static final String SELECT_NUM_TRIGGERS_FOR_JOB = "SELECT COUNT(*) FROM {0}TRIGGERS WHERE SCHED_NAME = {1} AND JOB_NAME = ? AND JOB_GROUP = ?";
    public static final String SELECT_NUM_TRIGGERS = "SELECT COUNT(*) FROM {0}TRIGGERS WHERE SCHED_NAME = {1}";
    public static final String SELECT_NUM_CALENDARS = "SELECT COUNT(*) FROM {0}CALENDARS WHERE SCHED_NAME = {1}";

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
            ps = conn.prepareStatement(this.rtp(SELECT_NUM_JOBS));
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            DB2v6Delegate.closeResultSet(rs);
            DB2v6Delegate.closeStatement(ps);
            throw throwable;
        }
        DB2v6Delegate.closeResultSet(rs);
        DB2v6Delegate.closeStatement(ps);
        return n;
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
                ps = conn.prepareStatement(this.rtp(SELECT_NUM_TRIGGERS_FOR_JOB));
                ps.setString(1, jobKey.getName());
                ps.setString(2, jobKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                n = rs.getInt(1);
            }
            catch (Throwable throwable) {
                DB2v6Delegate.closeResultSet(rs);
                DB2v6Delegate.closeStatement(ps);
                throw throwable;
            }
            DB2v6Delegate.closeResultSet(rs);
            DB2v6Delegate.closeStatement(ps);
            return n;
        }
        int n = 0;
        DB2v6Delegate.closeResultSet(rs);
        DB2v6Delegate.closeStatement(ps);
        return n;
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
            ps = conn.prepareStatement(this.rtp(SELECT_NUM_TRIGGERS));
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            DB2v6Delegate.closeResultSet(rs);
            DB2v6Delegate.closeStatement(ps);
            throw throwable;
        }
        DB2v6Delegate.closeResultSet(rs);
        DB2v6Delegate.closeStatement(ps);
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
            ps = conn.prepareStatement(this.rtp(SELECT_NUM_CALENDARS));
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            n = count;
        }
        catch (Throwable throwable) {
            DB2v6Delegate.closeResultSet(rs);
            DB2v6Delegate.closeStatement(ps);
            throw throwable;
        }
        DB2v6Delegate.closeResultSet(rs);
        DB2v6Delegate.closeStatement(ps);
        return n;
    }
}

