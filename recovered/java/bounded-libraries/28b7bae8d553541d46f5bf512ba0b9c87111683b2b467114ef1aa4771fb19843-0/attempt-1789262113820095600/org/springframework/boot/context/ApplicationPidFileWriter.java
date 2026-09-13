/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationListener
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.boot.system.SystemProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

public class ApplicationPidFileWriter
implements ApplicationListener<SpringApplicationEvent>,
Ordered {
    private static final Log logger = LogFactory.getLog(ApplicationPidFileWriter.class);
    private static final String DEFAULT_FILE_NAME = "application.pid";
    private static final List<Property> FILE_PROPERTIES;
    private static final List<Property> FAIL_ON_WRITE_ERROR_PROPERTIES;
    private static final AtomicBoolean created;
    private int order = -2147483635;
    private final File file;
    private Class<? extends SpringApplicationEvent> triggerEventType = ApplicationPreparedEvent.class;

    public ApplicationPidFileWriter() {
        this(new File(DEFAULT_FILE_NAME));
    }

    public ApplicationPidFileWriter(String filename) {
        this(new File(filename));
    }

    public ApplicationPidFileWriter(File file) {
        Assert.notNull((Object)file, (String)"File must not be null");
        this.file = file;
    }

    public void setTriggerEventType(Class<? extends SpringApplicationEvent> triggerEventType) {
        Assert.notNull(triggerEventType, (String)"Trigger event type must not be null");
        this.triggerEventType = triggerEventType;
    }

    public void onApplicationEvent(SpringApplicationEvent event) {
        if (this.triggerEventType.isInstance((Object)event) && created.compareAndSet(false, true)) {
            try {
                this.writePidFile(event);
            }
            catch (Exception ex) {
                String message = String.format("Cannot create pid file %s", this.file);
                if (this.failOnWriteError(event)) {
                    throw new IllegalStateException(message, ex);
                }
                logger.warn((Object)message, (Throwable)ex);
            }
        }
    }

    private void writePidFile(SpringApplicationEvent event) throws IOException {
        File pidFile = this.file;
        String override = this.getProperty(event, FILE_PROPERTIES);
        if (override != null) {
            pidFile = new File(override);
        }
        new ApplicationPid().write(pidFile);
        pidFile.deleteOnExit();
    }

    private boolean failOnWriteError(SpringApplicationEvent event) {
        String value = this.getProperty(event, FAIL_ON_WRITE_ERROR_PROPERTIES);
        return Boolean.parseBoolean(value);
    }

    private String getProperty(SpringApplicationEvent event, List<Property> candidates) {
        for (Property candidate : candidates) {
            String value = candidate.getValue(event);
            if (value == null) continue;
            return value;
        }
        return null;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    protected static void reset() {
        created.set(false);
    }

    static {
        ArrayList<Property> properties = new ArrayList<Property>();
        properties.add(new SpringProperty("spring.pid.", "file"));
        properties.add(new SpringProperty("spring.", "pidfile"));
        properties.add(new SystemProperty("PIDFILE"));
        FILE_PROPERTIES = Collections.unmodifiableList(properties);
        properties = new ArrayList();
        properties.add(new SpringProperty("spring.pid.", "fail-on-write-error"));
        properties.add(new SystemProperty("PID_FAIL_ON_WRITE_ERROR"));
        FAIL_ON_WRITE_ERROR_PROPERTIES = Collections.unmodifiableList(properties);
        created = new AtomicBoolean();
    }

    private static class SystemProperty
    implements Property {
        private final String[] properties;

        SystemProperty(String name) {
            this.properties = new String[]{name.toUpperCase(Locale.ENGLISH), name.toLowerCase(Locale.ENGLISH)};
        }

        @Override
        public String getValue(SpringApplicationEvent event) {
            return SystemProperties.get(this.properties);
        }
    }

    private static class SpringProperty
    implements Property {
        private final String prefix;
        private final String key;

        SpringProperty(String prefix, String key) {
            this.prefix = prefix;
            this.key = key;
        }

        @Override
        public String getValue(SpringApplicationEvent event) {
            Environment environment = this.getEnvironment(event);
            if (environment == null) {
                return null;
            }
            return environment.getProperty(this.prefix + this.key);
        }

        private Environment getEnvironment(SpringApplicationEvent event) {
            if (event instanceof ApplicationEnvironmentPreparedEvent) {
                return ((ApplicationEnvironmentPreparedEvent)event).getEnvironment();
            }
            if (event instanceof ApplicationPreparedEvent) {
                return ((ApplicationPreparedEvent)event).getApplicationContext().getEnvironment();
            }
            if (event instanceof ApplicationReadyEvent) {
                return ((ApplicationReadyEvent)event).getApplicationContext().getEnvironment();
            }
            return null;
        }
    }

    private static interface Property {
        public String getValue(SpringApplicationEvent var1);
    }
}

