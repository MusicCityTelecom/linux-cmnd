/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.boot.web.server.CertificateFileSslStoreProvider;
import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.Assert;

public abstract class AbstractConfigurableWebServerFactory
implements ConfigurableWebServerFactory {
    private int port = 8080;
    private InetAddress address;
    private Set<ErrorPage> errorPages = new LinkedHashSet<ErrorPage>();
    private Ssl ssl;
    private SslStoreProvider sslStoreProvider;
    private Http2 http2;
    private Compression compression;
    private String serverHeader;
    private Shutdown shutdown = Shutdown.IMMEDIATE;

    public AbstractConfigurableWebServerFactory() {
    }

    public AbstractConfigurableWebServerFactory(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Set<ErrorPage> getErrorPages() {
        return this.errorPages;
    }

    @Override
    public void setErrorPages(Set<? extends ErrorPage> errorPages) {
        Assert.notNull(errorPages, (String)"ErrorPages must not be null");
        this.errorPages = new LinkedHashSet<ErrorPage>(errorPages);
    }

    @Override
    public void addErrorPages(ErrorPage ... errorPages) {
        Assert.notNull((Object)errorPages, (String)"ErrorPages must not be null");
        this.errorPages.addAll(Arrays.asList(errorPages));
    }

    public Ssl getSsl() {
        return this.ssl;
    }

    @Override
    public void setSsl(Ssl ssl) {
        this.ssl = ssl;
    }

    public SslStoreProvider getSslStoreProvider() {
        return this.sslStoreProvider;
    }

    @Override
    public void setSslStoreProvider(SslStoreProvider sslStoreProvider) {
        this.sslStoreProvider = sslStoreProvider;
    }

    public Http2 getHttp2() {
        return this.http2;
    }

    @Override
    public void setHttp2(Http2 http2) {
        this.http2 = http2;
    }

    public Compression getCompression() {
        return this.compression;
    }

    @Override
    public void setCompression(Compression compression) {
        this.compression = compression;
    }

    public String getServerHeader() {
        return this.serverHeader;
    }

    @Override
    public void setServerHeader(String serverHeader) {
        this.serverHeader = serverHeader;
    }

    @Override
    public void setShutdown(Shutdown shutdown) {
        this.shutdown = shutdown;
    }

    public Shutdown getShutdown() {
        return this.shutdown;
    }

    public final SslStoreProvider getOrCreateSslStoreProvider() {
        if (this.sslStoreProvider != null) {
            return this.sslStoreProvider;
        }
        return CertificateFileSslStoreProvider.from(this.ssl);
    }

    protected final File createTempDir(String prefix) {
        try {
            File tempDir = Files.createTempDirectory(prefix + "." + this.getPort() + ".", new FileAttribute[0]).toFile();
            tempDir.deleteOnExit();
            return tempDir;
        }
        catch (IOException ex) {
            throw new WebServerException("Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex);
        }
    }
}

