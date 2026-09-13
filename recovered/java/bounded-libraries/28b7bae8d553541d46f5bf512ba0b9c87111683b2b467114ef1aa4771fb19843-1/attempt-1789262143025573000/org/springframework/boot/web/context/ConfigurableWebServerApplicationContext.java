/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.springframework.boot.web.context;

import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public interface ConfigurableWebServerApplicationContext
extends ConfigurableApplicationContext,
WebServerApplicationContext {
    public void setServerNamespace(String var1);
}

