/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.logging;

import java.util.Comparator;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.util.Assert;

class LoggerConfigurationComparator
implements Comparator<LoggerConfiguration> {
    private final String rootLoggerName;

    LoggerConfigurationComparator(String rootLoggerName) {
        Assert.notNull((Object)rootLoggerName, (String)"RootLoggerName must not be null");
        this.rootLoggerName = rootLoggerName;
    }

    @Override
    public int compare(LoggerConfiguration o1, LoggerConfiguration o2) {
        if (this.rootLoggerName.equals(o1.getName())) {
            return -1;
        }
        if (this.rootLoggerName.equals(o2.getName())) {
            return 1;
        }
        return o1.getName().compareTo(o2.getName());
    }
}

