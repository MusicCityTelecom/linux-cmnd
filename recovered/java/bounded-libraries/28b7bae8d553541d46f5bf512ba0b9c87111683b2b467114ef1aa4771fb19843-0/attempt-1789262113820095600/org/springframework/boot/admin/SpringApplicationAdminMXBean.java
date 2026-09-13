/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.admin;

public interface SpringApplicationAdminMXBean {
    public boolean isReady();

    public boolean isEmbeddedWebApplication();

    public String getProperty(String var1);

    public void shutdown();
}

