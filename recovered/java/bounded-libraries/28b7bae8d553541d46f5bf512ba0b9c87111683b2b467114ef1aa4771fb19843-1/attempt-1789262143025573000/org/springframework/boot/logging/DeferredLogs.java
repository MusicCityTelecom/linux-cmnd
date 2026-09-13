/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.boot.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.boot.logging.DeferredLogFactory;

public class DeferredLogs
implements DeferredLogFactory {
    private final DeferredLog.Lines lines = new DeferredLog.Lines();
    private final List<DeferredLog> loggers = new ArrayList<DeferredLog>();

    @Override
    public Log getLog(Class<?> destination) {
        return this.getLog(() -> LogFactory.getLog((Class)destination));
    }

    @Override
    public Log getLog(Log destination) {
        return this.getLog(() -> destination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Log getLog(Supplier<Log> destination) {
        DeferredLog.Lines lines = this.lines;
        synchronized (lines) {
            DeferredLog logger = new DeferredLog(destination, this.lines);
            this.loggers.add(logger);
            return logger;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void switchOverAll() {
        DeferredLog.Lines lines = this.lines;
        synchronized (lines) {
            for (DeferredLog.Line line : this.lines) {
                DeferredLog.logTo(line.getDestination(), line.getLevel(), line.getMessage(), line.getThrowable());
            }
            for (DeferredLog logger : this.loggers) {
                logger.switchOver();
            }
            this.lines.clear();
        }
    }
}

