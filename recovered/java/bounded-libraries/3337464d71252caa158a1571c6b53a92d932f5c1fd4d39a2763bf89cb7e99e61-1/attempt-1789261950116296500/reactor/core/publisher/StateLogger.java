/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.util.Logger;

class StateLogger {
    final Logger logger;

    StateLogger(Logger logger) {
        this.logger = logger;
    }

    void log(String instance, String action, long initialState, long committedState) {
        this.log(instance, action, initialState, committedState, false);
    }

    void log(String instance, String action, long initialState, long committedState, boolean logStackTrace) {
        if (logStackTrace) {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", instance, action, action, Thread.currentThread().getId(), StateLogger.formatState(initialState, 64), StateLogger.formatState(committedState, 64)), new RuntimeException());
        } else {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", instance, action, Thread.currentThread().getId(), StateLogger.formatState(initialState, 64), StateLogger.formatState(committedState, 64)));
        }
    }

    void log(String instance, String action, int initialState, int committedState) {
        this.log(instance, action, initialState, committedState, false);
    }

    void log(String instance, String action, int initialState, int committedState, boolean logStackTrace) {
        if (logStackTrace) {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", instance, action, action, Thread.currentThread().getId(), StateLogger.formatState(initialState, 32), StateLogger.formatState(committedState, 32)), new RuntimeException());
        } else {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", instance, action, Thread.currentThread().getId(), StateLogger.formatState(initialState, 32), StateLogger.formatState(committedState, 32)));
        }
    }

    static String formatState(long state, int size) {
        String defaultFormat = Long.toBinaryString(state);
        StringBuilder formatted = new StringBuilder();
        int toPrepend = size - defaultFormat.length();
        for (int i = 0; i < size; ++i) {
            if (i != 0 && i % 4 == 0) {
                formatted.append("_");
            }
            if (i < toPrepend) {
                formatted.append("0");
                continue;
            }
            formatted.append(defaultFormat.charAt(i - toPrepend));
        }
        formatted.insert(0, "0b");
        return formatted.toString();
    }
}

