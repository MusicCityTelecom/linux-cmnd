/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.neo4j.driver.Logger
 *  org.neo4j.driver.Logging
 */
package org.springframework.boot.autoconfigure.neo4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.Logger;
import org.neo4j.driver.Logging;

class Neo4jSpringJclLogging
implements Logging {
    private static final String AUTOMATIC_PREFIX = "org.neo4j.driver.";

    Neo4jSpringJclLogging() {
    }

    public Logger getLog(String name) {
        String requestedLog = name;
        if (!requestedLog.startsWith(AUTOMATIC_PREFIX)) {
            requestedLog = AUTOMATIC_PREFIX + name;
        }
        Log springJclLog = LogFactory.getLog((String)requestedLog);
        return new SpringJclLogger(springJclLog);
    }

    private static final class SpringJclLogger
    implements Logger {
        private final Log delegate;

        SpringJclLogger(Log delegate) {
            this.delegate = delegate;
        }

        public void error(String message, Throwable cause) {
            this.delegate.error((Object)message, cause);
        }

        public void info(String format, Object ... params) {
            this.delegate.info((Object)String.format(format, params));
        }

        public void warn(String format, Object ... params) {
            this.delegate.warn((Object)String.format(format, params));
        }

        public void warn(String message, Throwable cause) {
            this.delegate.warn((Object)message, cause);
        }

        public void debug(String format, Object ... params) {
            if (this.isDebugEnabled()) {
                this.delegate.debug((Object)String.format(format, params));
            }
        }

        public void debug(String message, Throwable throwable) {
            if (this.isDebugEnabled()) {
                this.delegate.debug((Object)message, throwable);
            }
        }

        public void trace(String format, Object ... params) {
            if (this.isTraceEnabled()) {
                this.delegate.trace((Object)String.format(format, params));
            }
        }

        public boolean isTraceEnabled() {
            return this.delegate.isTraceEnabled();
        }

        public boolean isDebugEnabled() {
            return this.delegate.isDebugEnabled();
        }
    }
}

