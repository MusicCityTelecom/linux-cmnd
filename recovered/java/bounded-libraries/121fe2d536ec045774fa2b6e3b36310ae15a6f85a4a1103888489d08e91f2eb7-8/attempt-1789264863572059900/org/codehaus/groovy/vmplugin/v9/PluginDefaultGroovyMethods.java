/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v9;

import org.codehaus.groovy.runtime.DefaultGroovyMethodsSupport;

public class PluginDefaultGroovyMethods
extends DefaultGroovyMethodsSupport {
    private PluginDefaultGroovyMethods() {
    }

    public static void info(System.Logger logger, String msg) {
        logger.log(System.Logger.Level.INFO, msg);
    }

    public static void info(System.Logger logger, String msg, Throwable thrown) {
        logger.log(System.Logger.Level.INFO, msg, thrown);
    }

    public static void info(System.Logger logger, String format, Object ... params) {
        logger.log(System.Logger.Level.INFO, format, params);
    }

    public static void trace(System.Logger logger, String msg) {
        logger.log(System.Logger.Level.TRACE, msg);
    }

    public static void trace(System.Logger logger, String msg, Throwable thrown) {
        logger.log(System.Logger.Level.TRACE, msg, thrown);
    }

    public static void trace(System.Logger logger, String format, Object ... params) {
        logger.log(System.Logger.Level.TRACE, format, params);
    }

    public static void warn(System.Logger logger, String msg) {
        logger.log(System.Logger.Level.WARNING, msg);
    }

    public static void warn(System.Logger logger, String msg, Throwable thrown) {
        logger.log(System.Logger.Level.WARNING, msg, thrown);
    }

    public static void warn(System.Logger logger, String format, Object ... params) {
        logger.log(System.Logger.Level.WARNING, format, params);
    }

    public static void error(System.Logger logger, String msg) {
        logger.log(System.Logger.Level.ERROR, msg);
    }

    public static void error(System.Logger logger, String msg, Throwable thrown) {
        logger.log(System.Logger.Level.ERROR, msg, thrown);
    }

    public static void error(System.Logger logger, String format, Object ... params) {
        logger.log(System.Logger.Level.ERROR, format, params);
    }

    public static void debug(System.Logger logger, String msg) {
        logger.log(System.Logger.Level.DEBUG, msg);
    }

    public static void debug(System.Logger logger, String msg, Throwable thrown) {
        logger.log(System.Logger.Level.DEBUG, msg, thrown);
    }

    public static void debug(System.Logger logger, String format, Object ... params) {
        logger.log(System.Logger.Level.DEBUG, format, params);
    }
}

