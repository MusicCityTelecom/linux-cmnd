/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.message.internal.TracingInfo;

public abstract class TracingLogger {
    public static final String PROPERTY_NAME = TracingLogger.class.getName();
    private static final String HEADER_TRACING_PREFIX = "X-Jersey-Tracing-";
    public static final String HEADER_THRESHOLD = "X-Jersey-Tracing-Threshold";
    public static final String HEADER_ACCEPT = "X-Jersey-Tracing-Accept";
    public static final String HEADER_LOGGER = "X-Jersey-Tracing-Logger";
    private static final String HEADER_RESPONSE_FORMAT = "X-Jersey-Tracing-%03d";
    public static final Level DEFAULT_LEVEL = Level.TRACE;
    private static final String TRACING_LOGGER_NAME_PREFIX = "org.glassfish.jersey.tracing";
    private static final String DEFAULT_LOGGER_NAME_SUFFIX = "general";
    private static final TracingLogger EMPTY = new TracingLogger(){

        @Override
        public boolean isLogEnabled(Event event) {
            return false;
        }

        @Override
        public void log(Event event, Object ... args) {
        }

        @Override
        public void logDuration(Event event, long fromTimestamp, Object ... args) {
        }

        @Override
        public long timestamp(Event event) {
            return -1L;
        }

        @Override
        public void flush(MultivaluedMap<String, Object> headers) {
        }
    };

    public static TracingLogger getInstance(PropertiesDelegate propertiesDelegate) {
        if (propertiesDelegate == null) {
            return EMPTY;
        }
        TracingLogger tracingLogger = (TracingLogger)propertiesDelegate.getProperty(PROPERTY_NAME);
        return tracingLogger != null ? tracingLogger : EMPTY;
    }

    public static TracingLogger create(Level threshold, String loggerNameSuffix) {
        return new TracingLoggerImpl(threshold, loggerNameSuffix);
    }

    public static TracingLogger empty() {
        return EMPTY;
    }

    public abstract boolean isLogEnabled(Event var1);

    public abstract void log(Event var1, Object ... var2);

    public abstract void logDuration(Event var1, long var2, Object ... var4);

    public abstract long timestamp(Event var1);

    public abstract void flush(MultivaluedMap<String, Object> var1);

    public static interface Event {
        public String name();

        public String category();

        public Level level();

        public String messageFormat();
    }

    public static enum Level {
        SUMMARY,
        TRACE,
        VERBOSE;

    }

    private static final class TracingLoggerImpl
    extends TracingLogger {
        private final Logger logger;
        private final Level threshold;
        private final TracingInfo tracingInfo;

        public TracingLoggerImpl(Level threshold, String loggerNameSuffix) {
            this.threshold = threshold;
            this.tracingInfo = new TracingInfo();
            loggerNameSuffix = loggerNameSuffix != null ? loggerNameSuffix : TracingLogger.DEFAULT_LOGGER_NAME_SUFFIX;
            this.logger = Logger.getLogger("org.glassfish.jersey.tracing." + loggerNameSuffix);
        }

        @Override
        public boolean isLogEnabled(Event event) {
            return this.isEnabled(event.level());
        }

        @Override
        public void log(Event event, Object ... args) {
            this.logDuration(event, -1L, args);
        }

        @Override
        public void logDuration(Event event, long fromTimestamp, Object ... args) {
            if (this.isEnabled(event.level())) {
                long toTimestamp = fromTimestamp == -1L ? -1L : System.nanoTime();
                long duration = 0L;
                if (fromTimestamp != -1L && toTimestamp != -1L) {
                    duration = toTimestamp - fromTimestamp;
                }
                this.logImpl(event, duration, args);
            }
        }

        @Override
        public long timestamp(Event event) {
            if (this.isEnabled(event.level())) {
                return System.nanoTime();
            }
            return -1L;
        }

        @Override
        public void flush(MultivaluedMap<String, Object> headers) {
            String[] messages = this.tracingInfo.getMessages();
            for (int i = 0; i < messages.length; ++i) {
                headers.putSingle(String.format(TracingLogger.HEADER_RESPONSE_FORMAT, i), messages[i]);
            }
        }

        private void logImpl(Event event, long duration, Object ... messageArgs) {
            if (this.isEnabled(event.level())) {
                java.util.logging.Level loggingLevel;
                String[] messageArgsStr = new String[messageArgs.length];
                for (int i = 0; i < messageArgs.length; ++i) {
                    messageArgsStr[i] = TracingLoggerImpl.formatInstance(messageArgs[i]);
                }
                TracingInfo.Message message = new TracingInfo.Message(event, duration, messageArgsStr);
                this.tracingInfo.addMessage(message);
                switch (event.level()) {
                    case SUMMARY: {
                        loggingLevel = java.util.logging.Level.FINE;
                        break;
                    }
                    case TRACE: {
                        loggingLevel = java.util.logging.Level.FINER;
                        break;
                    }
                    case VERBOSE: {
                        loggingLevel = java.util.logging.Level.FINEST;
                        break;
                    }
                    default: {
                        loggingLevel = java.util.logging.Level.OFF;
                    }
                }
                if (this.logger.isLoggable(loggingLevel)) {
                    this.logger.log(loggingLevel, event.name() + ' ' + message.toString() + " [" + TracingInfo.formatDuration(duration) + " ms]");
                }
            }
        }

        private boolean isEnabled(Level level) {
            return this.threshold.ordinal() >= level.ordinal();
        }

        private static String formatInstance(Object instance) {
            StringBuilder textSB = new StringBuilder();
            if (instance == null) {
                textSB.append("null");
            } else if (instance instanceof Number || instance instanceof String || instance instanceof Method) {
                textSB.append(instance.toString());
            } else if (instance instanceof Response.StatusType) {
                textSB.append(TracingLoggerImpl.formatStatusInfo((Response.StatusType)instance));
            } else {
                textSB.append('[');
                TracingLoggerImpl.formatInstance(instance, textSB);
                if (instance.getClass().isAnnotationPresent(Priority.class)) {
                    textSB.append(" #").append(instance.getClass().getAnnotation(Priority.class).value());
                }
                if (instance instanceof WebApplicationException) {
                    TracingLoggerImpl.formatResponse(((WebApplicationException)instance).getResponse(), textSB);
                } else if (instance instanceof Response) {
                    TracingLoggerImpl.formatResponse((Response)instance, textSB);
                }
                textSB.append(']');
            }
            return textSB.toString();
        }

        private static void formatInstance(Object instance, StringBuilder textSB) {
            textSB.append(instance.getClass().getName()).append(" @").append(Integer.toHexString(System.identityHashCode(instance)));
        }

        private static void formatResponse(Response response, StringBuilder textSB) {
            textSB.append(" <").append(TracingLoggerImpl.formatStatusInfo(response.getStatusInfo())).append('|');
            if (response.hasEntity()) {
                TracingLoggerImpl.formatInstance(response.getEntity(), textSB);
            } else {
                textSB.append("-no-entity-");
            }
            textSB.append('>');
        }

        private static String formatStatusInfo(Response.StatusType statusInfo) {
            return String.valueOf(statusInfo.getStatusCode()) + '/' + (Object)((Object)statusInfo.getFamily()) + '|' + statusInfo.getReasonPhrase();
        }
    }
}

