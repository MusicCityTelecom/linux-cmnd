/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 */
package org.springframework.boot.logging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.util.Assert;

public class DeferredLog
implements Log {
    private volatile Log destination;
    private final Supplier<Log> destinationSupplier;
    private final Lines lines;

    public DeferredLog() {
        this.destinationSupplier = null;
        this.lines = new Lines();
    }

    DeferredLog(Supplier<Log> destination, Lines lines) {
        Assert.notNull(destination, (String)"Destination must not be null");
        this.destinationSupplier = destination;
        this.lines = lines;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isTraceEnabled() {
        Lines lines = this.lines;
        synchronized (lines) {
            return this.destination == null || this.destination.isTraceEnabled();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isDebugEnabled() {
        Lines lines = this.lines;
        synchronized (lines) {
            return this.destination == null || this.destination.isDebugEnabled();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isInfoEnabled() {
        Lines lines = this.lines;
        synchronized (lines) {
            return this.destination == null || this.destination.isInfoEnabled();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isWarnEnabled() {
        Lines lines = this.lines;
        synchronized (lines) {
            return this.destination == null || this.destination.isWarnEnabled();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isErrorEnabled() {
        Lines lines = this.lines;
        synchronized (lines) {
            return this.destination == null || this.destination.isErrorEnabled();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isFatalEnabled() {
        Lines lines = this.lines;
        synchronized (lines) {
            return this.destination == null || this.destination.isFatalEnabled();
        }
    }

    public void trace(Object message) {
        this.log(LogLevel.TRACE, message, null);
    }

    public void trace(Object message, Throwable t) {
        this.log(LogLevel.TRACE, message, t);
    }

    public void debug(Object message) {
        this.log(LogLevel.DEBUG, message, null);
    }

    public void debug(Object message, Throwable t) {
        this.log(LogLevel.DEBUG, message, t);
    }

    public void info(Object message) {
        this.log(LogLevel.INFO, message, null);
    }

    public void info(Object message, Throwable t) {
        this.log(LogLevel.INFO, message, t);
    }

    public void warn(Object message) {
        this.log(LogLevel.WARN, message, null);
    }

    public void warn(Object message, Throwable t) {
        this.log(LogLevel.WARN, message, t);
    }

    public void error(Object message) {
        this.log(LogLevel.ERROR, message, null);
    }

    public void error(Object message, Throwable t) {
        this.log(LogLevel.ERROR, message, t);
    }

    public void fatal(Object message) {
        this.log(LogLevel.FATAL, message, null);
    }

    public void fatal(Object message, Throwable t) {
        this.log(LogLevel.FATAL, message, t);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void log(LogLevel level, Object message, Throwable t) {
        Lines lines = this.lines;
        synchronized (lines) {
            if (this.destination != null) {
                DeferredLog.logTo(this.destination, level, message, t);
            } else {
                this.lines.add(this.destinationSupplier, level, message, t);
            }
        }
    }

    void switchOver() {
        this.destination = this.destinationSupplier.get();
    }

    public void switchTo(Class<?> destination) {
        this.switchTo(LogFactory.getLog(destination));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void switchTo(Log destination) {
        Lines lines = this.lines;
        synchronized (lines) {
            this.replayTo(destination);
            this.destination = destination;
        }
    }

    public void replayTo(Class<?> destination) {
        this.replayTo(LogFactory.getLog(destination));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void replayTo(Log destination) {
        Lines lines = this.lines;
        synchronized (lines) {
            for (Line line : this.lines) {
                DeferredLog.logTo(destination, line.getLevel(), line.getMessage(), line.getThrowable());
            }
            this.lines.clear();
        }
    }

    public static Log replay(Log source, Class<?> destination) {
        return DeferredLog.replay(source, LogFactory.getLog(destination));
    }

    public static Log replay(Log source, Log destination) {
        if (source instanceof DeferredLog) {
            ((DeferredLog)source).replayTo(destination);
        }
        return destination;
    }

    static void logTo(Log log, LogLevel level, Object message, Throwable throwable) {
        switch (level) {
            case TRACE: {
                log.trace(message, throwable);
                return;
            }
            case DEBUG: {
                log.debug(message, throwable);
                return;
            }
            case INFO: {
                log.info(message, throwable);
                return;
            }
            case WARN: {
                log.warn(message, throwable);
                return;
            }
            case ERROR: {
                log.error(message, throwable);
                return;
            }
            case FATAL: {
                log.fatal(message, throwable);
            }
        }
    }

    static class Line {
        private final Supplier<Log> destinationSupplier;
        private final LogLevel level;
        private final Object message;
        private final Throwable throwable;

        Line(Supplier<Log> destinationSupplier, LogLevel level, Object message, Throwable throwable) {
            this.destinationSupplier = destinationSupplier;
            this.level = level;
            this.message = message;
            this.throwable = throwable;
        }

        Log getDestination() {
            return this.destinationSupplier.get();
        }

        LogLevel getLevel() {
            return this.level;
        }

        Object getMessage() {
            return this.message;
        }

        Throwable getThrowable() {
            return this.throwable;
        }
    }

    static class Lines
    implements Iterable<Line> {
        private final List<Line> lines = new ArrayList<Line>();

        Lines() {
        }

        void add(Supplier<Log> destinationSupplier, LogLevel level, Object message, Throwable throwable) {
            this.lines.add(new Line(destinationSupplier, level, message, throwable));
        }

        void clear() {
            this.lines.clear();
        }

        @Override
        public Iterator<Line> iterator() {
            return this.lines.iterator();
        }
    }
}

