/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.session.FlushMode
 *  org.springframework.session.SaveMode
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.session.FlushMode;
import org.springframework.session.SaveMode;

@ConfigurationProperties(prefix="spring.session.redis")
public class RedisSessionProperties {
    private static final String DEFAULT_CLEANUP_CRON = "0 * * * * *";
    private String namespace = "spring:session";
    private FlushMode flushMode = FlushMode.ON_SAVE;
    private SaveMode saveMode = SaveMode.ON_SET_ATTRIBUTE;
    private ConfigureAction configureAction = ConfigureAction.NOTIFY_KEYSPACE_EVENTS;
    private String cleanupCron = "0 * * * * *";

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public FlushMode getFlushMode() {
        return this.flushMode;
    }

    public void setFlushMode(FlushMode flushMode) {
        this.flushMode = flushMode;
    }

    public SaveMode getSaveMode() {
        return this.saveMode;
    }

    public void setSaveMode(SaveMode saveMode) {
        this.saveMode = saveMode;
    }

    public String getCleanupCron() {
        return this.cleanupCron;
    }

    public void setCleanupCron(String cleanupCron) {
        this.cleanupCron = cleanupCron;
    }

    public ConfigureAction getConfigureAction() {
        return this.configureAction;
    }

    public void setConfigureAction(ConfigureAction configureAction) {
        this.configureAction = configureAction;
    }

    public static enum ConfigureAction {
        NOTIFY_KEYSPACE_EVENTS,
        NONE;

    }
}

