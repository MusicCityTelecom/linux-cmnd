/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.TriggerKey;
import org.quartz.spi.OperableTrigger;

public interface TriggerPersistenceDelegate {
    public void initialize(String var1, String var2);

    public boolean canHandleTriggerType(OperableTrigger var1);

    public String getHandledTriggerTypeDiscriminator();

    public int insertExtendedTriggerProperties(Connection var1, OperableTrigger var2, String var3, JobDetail var4) throws SQLException, IOException;

    public int updateExtendedTriggerProperties(Connection var1, OperableTrigger var2, String var3, JobDetail var4) throws SQLException, IOException;

    public int deleteExtendedTriggerProperties(Connection var1, TriggerKey var2) throws SQLException;

    public TriggerPropertyBundle loadExtendedTriggerProperties(Connection var1, TriggerKey var2) throws SQLException;

    public static class TriggerPropertyBundle {
        private ScheduleBuilder<?> sb;
        private String[] statePropertyNames;
        private Object[] statePropertyValues;

        public TriggerPropertyBundle(ScheduleBuilder<?> sb, String[] statePropertyNames, Object[] statePropertyValues) {
            this.sb = sb;
            this.statePropertyNames = statePropertyNames;
            this.statePropertyValues = statePropertyValues;
        }

        public ScheduleBuilder<?> getScheduleBuilder() {
            return this.sb;
        }

        public String[] getStatePropertyNames() {
            return this.statePropertyNames;
        }

        public Object[] getStatePropertyValues() {
            return this.statePropertyValues;
        }
    }
}

