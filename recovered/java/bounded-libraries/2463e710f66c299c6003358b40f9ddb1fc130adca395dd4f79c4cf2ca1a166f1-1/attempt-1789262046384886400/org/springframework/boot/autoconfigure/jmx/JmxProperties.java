/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.jmx;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.jmx")
public class JmxProperties {
    private boolean enabled = false;
    private boolean uniqueNames = false;
    private String server = "mbeanServer";
    private String defaultDomain;

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUniqueNames() {
        return this.uniqueNames;
    }

    public void setUniqueNames(boolean uniqueNames) {
        this.uniqueNames = uniqueNames;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDefaultDomain() {
        return this.defaultDomain;
    }

    public void setDefaultDomain(String defaultDomain) {
        this.defaultDomain = defaultDomain;
    }
}

