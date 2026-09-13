/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.StdJDBCConstants;
import org.quartz.impl.jdbcjobstore.TriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.Util;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.OperableTrigger;

public class CronTriggerPersistenceDelegate
implements TriggerPersistenceDelegate,
StdJDBCConstants {
    protected String tablePrefix;
    protected String schedNameLiteral;

    @Override
    public void initialize(String theTablePrefix, String schedName) {
        this.tablePrefix = theTablePrefix;
        this.schedNameLiteral = "'" + schedName + "'";
    }

    @Override
    public String getHandledTriggerTypeDiscriminator() {
        return "CRON";
    }

    @Override
    public boolean canHandleTriggerType(OperableTrigger trigger) {
        return trigger instanceof CronTriggerImpl && !((CronTriggerImpl)trigger).hasAdditionalProperties();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteExtendedTriggerProperties(Connection conn, TriggerKey triggerKey) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Util.rtp("DELETE FROM {0}CRON_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
            ps.setString(1, triggerKey.getName());
            ps.setString(2, triggerKey.getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            Util.closeStatement(ps);
            throw throwable;
        }
        Util.closeStatement(ps);
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int insertExtendedTriggerProperties(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        int n;
        CronTrigger cronTrigger = (CronTrigger)((Object)trigger);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Util.rtp("INSERT INTO {0}CRON_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, CRON_EXPRESSION, TIME_ZONE_ID)  VALUES({1}, ?, ?, ?, ?)", this.tablePrefix, this.schedNameLiteral));
            ps.setString(1, trigger.getKey().getName());
            ps.setString(2, trigger.getKey().getGroup());
            ps.setString(3, cronTrigger.getCronExpression());
            ps.setString(4, cronTrigger.getTimeZone().getID());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            Util.closeStatement(ps);
            throw throwable;
        }
        Util.closeStatement(ps);
        return n;
    }

    @Override
    public TriggerPersistenceDelegate.TriggerPropertyBundle loadExtendedTriggerProperties(Connection conn, TriggerKey triggerKey) throws SQLException {
        block4: {
            TriggerPersistenceDelegate.TriggerPropertyBundle triggerPropertyBundle;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(Util.rtp("SELECT * FROM {0}CRON_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
                ps.setString(1, triggerKey.getName());
                ps.setString(2, triggerKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block4;
                String cronExpr = rs.getString("CRON_EXPRESSION");
                String timeZoneId = rs.getString("TIME_ZONE_ID");
                CronScheduleBuilder cb = CronScheduleBuilder.cronSchedule(cronExpr);
                if (timeZoneId != null) {
                    cb.inTimeZone(TimeZone.getTimeZone(timeZoneId));
                }
                triggerPropertyBundle = new TriggerPersistenceDelegate.TriggerPropertyBundle(cb, null, null);
            }
            catch (Throwable throwable) {
                Util.closeResultSet(rs);
                Util.closeStatement(ps);
                throw throwable;
            }
            Util.closeResultSet(rs);
            Util.closeStatement(ps);
            return triggerPropertyBundle;
        }
        throw new IllegalStateException("No record found for selection of Trigger with key: '" + triggerKey + "' and statement: " + Util.rtp("SELECT * FROM {0}CRON_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateExtendedTriggerProperties(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        int n;
        CronTrigger cronTrigger = (CronTrigger)((Object)trigger);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Util.rtp("UPDATE {0}CRON_TRIGGERS SET CRON_EXPRESSION = ?, TIME_ZONE_ID = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
            ps.setString(1, cronTrigger.getCronExpression());
            ps.setString(2, cronTrigger.getTimeZone().getID());
            ps.setString(3, trigger.getKey().getName());
            ps.setString(4, trigger.getKey().getGroup());
            n = ps.executeUpdate();
        }
        catch (Throwable throwable) {
            Util.closeStatement(ps);
            throw throwable;
        }
        Util.closeStatement(ps);
        return n;
    }
}

