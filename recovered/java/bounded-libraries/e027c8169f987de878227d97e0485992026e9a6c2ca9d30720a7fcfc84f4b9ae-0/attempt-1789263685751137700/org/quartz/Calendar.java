/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.io.Serializable;

public interface Calendar
extends Serializable,
Cloneable {
    public static final int MONTH = 0;

    public void setBaseCalendar(Calendar var1);

    public Calendar getBaseCalendar();

    public boolean isTimeIncluded(long var1);

    public long getNextIncludedTime(long var1);

    public String getDescription();

    public void setDescription(String var1);

    public Object clone();
}

