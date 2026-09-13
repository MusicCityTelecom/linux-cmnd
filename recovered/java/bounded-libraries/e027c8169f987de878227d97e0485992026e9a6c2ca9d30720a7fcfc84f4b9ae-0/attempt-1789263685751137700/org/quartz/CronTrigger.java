/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.TimeZone;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public interface CronTrigger
extends Trigger {
    public static final long serialVersionUID = -8644953146451592766L;
    public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
    public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

    public String getCronExpression();

    public TimeZone getTimeZone();

    public String getExpressionSummary();

    public TriggerBuilder<CronTrigger> getTriggerBuilder();
}

