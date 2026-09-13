/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.embedded.undertow;

import java.io.File;
import java.util.Collection;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;

public interface ConfigurableUndertowWebServerFactory
extends ConfigurableWebServerFactory {
    public void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> var1);

    public void addBuilderCustomizers(UndertowBuilderCustomizer ... var1);

    public void setBufferSize(Integer var1);

    public void setIoThreads(Integer var1);

    public void setWorkerThreads(Integer var1);

    public void setUseDirectBuffers(Boolean var1);

    public void setAccessLogDirectory(File var1);

    public void setAccessLogPattern(String var1);

    public void setAccessLogPrefix(String var1);

    public void setAccessLogSuffix(String var1);

    public void setAccessLogEnabled(boolean var1);

    public void setAccessLogRotate(boolean var1);

    public void setUseForwardHeaders(boolean var1);
}

