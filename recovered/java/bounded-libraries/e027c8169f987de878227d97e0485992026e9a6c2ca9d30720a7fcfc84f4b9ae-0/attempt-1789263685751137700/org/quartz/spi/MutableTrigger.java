/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import java.util.Date;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface MutableTrigger
extends Trigger {
    public void setKey(TriggerKey var1);

    public void setJobKey(JobKey var1);

    public void setDescription(String var1);

    public void setCalendarName(String var1);

    public void setJobDataMap(JobDataMap var1);

    public void setPriority(int var1);

    public void setStartTime(Date var1);

    public void setEndTime(Date var1);

    public void setMisfireInstruction(int var1);

    public Object clone();
}

