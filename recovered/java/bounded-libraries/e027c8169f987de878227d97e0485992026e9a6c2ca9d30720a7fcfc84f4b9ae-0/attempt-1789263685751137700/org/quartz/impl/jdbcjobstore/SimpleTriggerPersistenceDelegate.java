/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.StdJDBCConstants;
import org.quartz.impl.jdbcjobstore.TriggerPersistenceDelegate;
import org.quartz.impl.jdbcjobstore.Util;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.OperableTrigger;

public class SimpleTriggerPersistenceDelegate
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
        return "SIMPLE";
    }

    @Override
    public boolean canHandleTriggerType(OperableTrigger trigger) {
        return trigger instanceof SimpleTriggerImpl && !((SimpleTriggerImpl)trigger).hasAdditionalProperties();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int deleteExtendedTriggerProperties(Connection conn, TriggerKey triggerKey) throws SQLException {
        int n;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Util.rtp("DELETE FROM {0}SIMPLE_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
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
        SimpleTrigger simpleTrigger = (SimpleTrigger)((Object)trigger);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Util.rtp("INSERT INTO {0}SIMPLE_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, REPEAT_COUNT, REPEAT_INTERVAL, TIMES_TRIGGERED)  VALUES({1}, ?, ?, ?, ?, ?)", this.tablePrefix, this.schedNameLiteral));
            ps.setString(1, trigger.getKey().getName());
            ps.setString(2, trigger.getKey().getGroup());
            ps.setInt(3, simpleTrigger.getRepeatCount());
            ps.setBigDecimal(4, new BigDecimal(String.valueOf(simpleTrigger.getRepeatInterval())));
            ps.setInt(5, simpleTrigger.getTimesTriggered());
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
        block3: {
            TriggerPersistenceDelegate.TriggerPropertyBundle triggerPropertyBundle;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = conn.prepareStatement(Util.rtp("SELECT * FROM {0}SIMPLE_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
                ps.setString(1, triggerKey.getName());
                ps.setString(2, triggerKey.getGroup());
                rs = ps.executeQuery();
                if (!rs.next()) break block3;
                int repeatCount = rs.getInt("REPEAT_COUNT");
                long repeatInterval = rs.getLong("REPEAT_INTERVAL");
                int timesTriggered = rs.getInt("TIMES_TRIGGERED");
                SimpleScheduleBuilder sb = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(repeatCount).withIntervalInMilliseconds(repeatInterval);
                String[] statePropertyNames = new String[]{"timesTriggered"};
                Object[] statePropertyValues = new Object[]{timesTriggered};
                triggerPropertyBundle = new TriggerPersistenceDelegate.TriggerPropertyBundle(sb, statePropertyNames, statePropertyValues);
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
        throw new IllegalStateException("No record found for selection of Trigger with key: '" + triggerKey + "' and statement: " + Util.rtp("SELECT * FROM {0}SIMPLE_TRIGGERS WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateExtendedTriggerProperties(Connection conn, OperableTrigger trigger, String state, JobDetail jobDetail) throws SQLException, IOException {
        int n;
        SimpleTrigger simpleTrigger = (SimpleTrigger)((Object)trigger);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Util.rtp("UPDATE {0}SIMPLE_TRIGGERS SET REPEAT_COUNT = ?, REPEAT_INTERVAL = ?, TIMES_TRIGGERED = ? WHERE SCHED_NAME = {1} AND TRIGGER_NAME = ? AND TRIGGER_GROUP = ?", this.tablePrefix, this.schedNameLiteral));
            ps.setInt(1, simpleTrigger.getRepeatCount());
            ps.setBigDecimal(2, new BigDecimal(String.valueOf(simpleTrigger.getRepeatInterval())));
            ps.setInt(3, simpleTrigger.getTimesTriggered());
            ps.setString(4, simpleTrigger.getKey().getName());
            ps.setString(5, simpleTrigger.getKey().getGroup());
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

