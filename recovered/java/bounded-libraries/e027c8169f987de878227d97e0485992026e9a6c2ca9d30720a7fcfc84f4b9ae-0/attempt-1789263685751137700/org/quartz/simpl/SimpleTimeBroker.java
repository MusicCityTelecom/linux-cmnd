/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.util.Date;
import org.quartz.SchedulerConfigException;
import org.quartz.spi.TimeBroker;

public class SimpleTimeBroker
implements TimeBroker {
    @Override
    public Date getCurrentTime() {
        return new Date();
    }

    @Override
    public void initialize() throws SchedulerConfigException {
    }

    @Override
    public void shutdown() {
    }
}

