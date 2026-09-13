/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.Marker
 *  org.slf4j.helpers.FormattingTuple
 *  org.slf4j.helpers.MessageFormatter
 *  org.slf4j.spi.LocationAwareLogger
 */
package org.eclipse.jetty.util.log;

import org.eclipse.jetty.util.log.Slf4jLog;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

class JettyAwareLogger
implements Logger {
    private static final int DEBUG = 10;
    private static final int ERROR = 40;
    private static final int INFO = 20;
    private static final int TRACE = 0;
    private static final int WARN = 30;
    private static final String FQCN = Slf4jLog.class.getName();
    private final LocationAwareLogger _logger;

    public JettyAwareLogger(LocationAwareLogger logger) {
        this._logger = logger;
    }

    public String getName() {
        return this._logger.getName();
    }

    public boolean isTraceEnabled() {
        return this._logger.isTraceEnabled();
    }

    public void trace(String msg) {
        this.log(null, 0, msg, null, null);
    }

    public void trace(String format, Object arg) {
        this.log(null, 0, format, new Object[]{arg}, null);
    }

    public void trace(String format, Object arg1, Object arg2) {
        this.log(null, 0, format, new Object[]{arg1, arg2}, null);
    }

    public void trace(String format, Object[] argArray) {
        this.log(null, 0, format, argArray, null);
    }

    public void trace(String msg, Throwable t) {
        this.log(null, 0, msg, null, t);
    }

    public boolean isTraceEnabled(Marker marker) {
        return this._logger.isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg) {
        this.log(marker, 0, msg, null, null);
    }

    public void trace(Marker marker, String format, Object arg) {
        this.log(marker, 0, format, new Object[]{arg}, null);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        this.log(marker, 0, format, new Object[]{arg1, arg2}, null);
    }

    public void trace(Marker marker, String format, Object[] argArray) {
        this.log(marker, 0, format, argArray, null);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        this.log(marker, 0, msg, null, t);
    }

    public boolean isDebugEnabled() {
        return this._logger.isDebugEnabled();
    }

    public void debug(String msg) {
        this.log(null, 10, msg, null, null);
    }

    public void debug(String format, Object arg) {
        this.log(null, 10, format, new Object[]{arg}, null);
    }

    public void debug(String format, Object arg1, Object arg2) {
        this.log(null, 10, format, new Object[]{arg1, arg2}, null);
    }

    public void debug(String format, Object[] argArray) {
        this.log(null, 10, format, argArray, null);
    }

    public void debug(String msg, Throwable t) {
        this.log(null, 10, msg, null, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return this._logger.isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        this.log(marker, 10, msg, null, null);
    }

    public void debug(Marker marker, String format, Object arg) {
        this.log(marker, 10, format, new Object[]{arg}, null);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        this.log(marker, 10, format, new Object[]{arg1, arg2}, null);
    }

    public void debug(Marker marker, String format, Object[] argArray) {
        this.log(marker, 10, format, argArray, null);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        this.log(marker, 10, msg, null, t);
    }

    public boolean isInfoEnabled() {
        return this._logger.isInfoEnabled();
    }

    public void info(String msg) {
        this.log(null, 20, msg, null, null);
    }

    public void info(String format, Object arg) {
        this.log(null, 20, format, new Object[]{arg}, null);
    }

    public void info(String format, Object arg1, Object arg2) {
        this.log(null, 20, format, new Object[]{arg1, arg2}, null);
    }

    public void info(String format, Object[] argArray) {
        this.log(null, 20, format, argArray, null);
    }

    public void info(String msg, Throwable t) {
        this.log(null, 20, msg, null, t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return this._logger.isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        this.log(marker, 20, msg, null, null);
    }

    public void info(Marker marker, String format, Object arg) {
        this.log(marker, 20, format, new Object[]{arg}, null);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        this.log(marker, 20, format, new Object[]{arg1, arg2}, null);
    }

    public void info(Marker marker, String format, Object[] argArray) {
        this.log(marker, 20, format, argArray, null);
    }

    public void info(Marker marker, String msg, Throwable t) {
        this.log(marker, 20, msg, null, t);
    }

    public boolean isWarnEnabled() {
        return this._logger.isWarnEnabled();
    }

    public void warn(String msg) {
        this.log(null, 30, msg, null, null);
    }

    public void warn(String format, Object arg) {
        this.log(null, 30, format, new Object[]{arg}, null);
    }

    public void warn(String format, Object[] argArray) {
        this.log(null, 30, format, argArray, null);
    }

    public void warn(String format, Object arg1, Object arg2) {
        this.log(null, 30, format, new Object[]{arg1, arg2}, null);
    }

    public void warn(String msg, Throwable t) {
        this.log(null, 30, msg, null, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return this._logger.isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        this.log(marker, 30, msg, null, null);
    }

    public void warn(Marker marker, String format, Object arg) {
        this.log(marker, 30, format, new Object[]{arg}, null);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        this.log(marker, 30, format, new Object[]{arg1, arg2}, null);
    }

    public void warn(Marker marker, String format, Object[] argArray) {
        this.log(marker, 30, format, argArray, null);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        this.log(marker, 30, msg, null, t);
    }

    public boolean isErrorEnabled() {
        return this._logger.isErrorEnabled();
    }

    public void error(String msg) {
        this.log(null, 40, msg, null, null);
    }

    public void error(String format, Object arg) {
        this.log(null, 40, format, new Object[]{arg}, null);
    }

    public void error(String format, Object arg1, Object arg2) {
        this.log(null, 40, format, new Object[]{arg1, arg2}, null);
    }

    public void error(String format, Object[] argArray) {
        this.log(null, 40, format, argArray, null);
    }

    public void error(String msg, Throwable t) {
        this.log(null, 40, msg, null, t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return this._logger.isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        this.log(marker, 40, msg, null, null);
    }

    public void error(Marker marker, String format, Object arg) {
        this.log(marker, 40, format, new Object[]{arg}, null);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        this.log(marker, 40, format, new Object[]{arg1, arg2}, null);
    }

    public void error(Marker marker, String format, Object[] argArray) {
        this.log(marker, 40, format, argArray, null);
    }

    public void error(Marker marker, String msg, Throwable t) {
        this.log(marker, 40, msg, null, t);
    }

    public String toString() {
        return this._logger.toString();
    }

    private void log(Marker marker, int level, String msg, Object[] argArray, Throwable t) {
        if (argArray == null) {
            this._logger.log(marker, FQCN, level, msg, null, t);
        } else {
            int loggerLevel;
            int n = this._logger.isTraceEnabled() ? 0 : (this._logger.isDebugEnabled() ? 10 : (this._logger.isInfoEnabled() ? 20 : (loggerLevel = this._logger.isWarnEnabled() ? 30 : 40)));
            if (loggerLevel <= level) {
                FormattingTuple ft = MessageFormatter.arrayFormat((String)msg, (Object[])argArray);
                this._logger.log(marker, FQCN, level, ft.getMessage(), null, t);
            }
        }
    }
}

