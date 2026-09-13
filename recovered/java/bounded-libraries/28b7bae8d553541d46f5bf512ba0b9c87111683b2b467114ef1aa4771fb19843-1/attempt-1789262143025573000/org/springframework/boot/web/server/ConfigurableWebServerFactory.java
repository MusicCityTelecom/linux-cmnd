/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import java.net.InetAddress;
import java.util.Set;
import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.boot.web.server.WebServerFactory;

public interface ConfigurableWebServerFactory
extends WebServerFactory,
ErrorPageRegistry {
    public void setPort(int var1);

    public void setAddress(InetAddress var1);

    public void setErrorPages(Set<? extends ErrorPage> var1);

    public void setSsl(Ssl var1);

    public void setSslStoreProvider(SslStoreProvider var1);

    public void setHttp2(Http2 var1);

    public void setCompression(Compression var1);

    public void setServerHeader(String var1);

    default public void setShutdown(Shutdown shutdown) {
    }
}

