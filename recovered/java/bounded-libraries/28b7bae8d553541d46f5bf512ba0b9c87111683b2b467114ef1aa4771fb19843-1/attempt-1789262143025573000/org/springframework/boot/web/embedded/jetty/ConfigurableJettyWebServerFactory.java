/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.util.thread.ThreadPool
 */
package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;

public interface ConfigurableJettyWebServerFactory
extends ConfigurableWebServerFactory {
    public void setAcceptors(int var1);

    public void setThreadPool(ThreadPool var1);

    public void setSelectors(int var1);

    public void setUseForwardHeaders(boolean var1);

    public void addServerCustomizers(JettyServerCustomizer ... var1);
}

