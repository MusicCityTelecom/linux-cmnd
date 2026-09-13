/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Handlers
 *  io.undertow.Undertow
 *  io.undertow.Undertow$Builder
 *  io.undertow.UndertowOptions
 *  io.undertow.server.HttpHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.web.embedded.undertow.AccessLogHttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.CompressionHttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.SslBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.server.AbstractConfigurableWebServerFactory;
import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.Ssl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

class UndertowWebServerFactoryDelegate {
    private Set<UndertowBuilderCustomizer> builderCustomizers = new LinkedHashSet<UndertowBuilderCustomizer>();
    private Integer bufferSize;
    private Integer ioThreads;
    private Integer workerThreads;
    private Boolean directBuffers;
    private File accessLogDirectory;
    private String accessLogPattern;
    private String accessLogPrefix;
    private String accessLogSuffix;
    private boolean accessLogEnabled = false;
    private boolean accessLogRotate = true;
    private boolean useForwardHeaders;

    UndertowWebServerFactoryDelegate() {
    }

    void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        this.builderCustomizers = new LinkedHashSet<UndertowBuilderCustomizer>(customizers);
    }

    void addBuilderCustomizers(UndertowBuilderCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        this.builderCustomizers.addAll(Arrays.asList(customizers));
    }

    Collection<UndertowBuilderCustomizer> getBuilderCustomizers() {
        return this.builderCustomizers;
    }

    void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    void setIoThreads(Integer ioThreads) {
        this.ioThreads = ioThreads;
    }

    void setWorkerThreads(Integer workerThreads) {
        this.workerThreads = workerThreads;
    }

    void setUseDirectBuffers(Boolean directBuffers) {
        this.directBuffers = directBuffers;
    }

    void setAccessLogDirectory(File accessLogDirectory) {
        this.accessLogDirectory = accessLogDirectory;
    }

    void setAccessLogPattern(String accessLogPattern) {
        this.accessLogPattern = accessLogPattern;
    }

    void setAccessLogPrefix(String accessLogPrefix) {
        this.accessLogPrefix = accessLogPrefix;
    }

    String getAccessLogPrefix() {
        return this.accessLogPrefix;
    }

    void setAccessLogSuffix(String accessLogSuffix) {
        this.accessLogSuffix = accessLogSuffix;
    }

    void setAccessLogEnabled(boolean accessLogEnabled) {
        this.accessLogEnabled = accessLogEnabled;
    }

    boolean isAccessLogEnabled() {
        return this.accessLogEnabled;
    }

    void setAccessLogRotate(boolean accessLogRotate) {
        this.accessLogRotate = accessLogRotate;
    }

    void setUseForwardHeaders(boolean useForwardHeaders) {
        this.useForwardHeaders = useForwardHeaders;
    }

    boolean isUseForwardHeaders() {
        return this.useForwardHeaders;
    }

    Undertow.Builder createBuilder(AbstractConfigurableWebServerFactory factory) {
        Http2 http2;
        Ssl ssl = factory.getSsl();
        InetAddress address = factory.getAddress();
        int port = factory.getPort();
        Undertow.Builder builder = Undertow.builder();
        if (this.bufferSize != null) {
            builder.setBufferSize(this.bufferSize.intValue());
        }
        if (this.ioThreads != null) {
            builder.setIoThreads(this.ioThreads.intValue());
        }
        if (this.workerThreads != null) {
            builder.setWorkerThreads(this.workerThreads.intValue());
        }
        if (this.directBuffers != null) {
            builder.setDirectBuffers(this.directBuffers.booleanValue());
        }
        if ((http2 = factory.getHttp2()) != null) {
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, (Object)http2.isEnabled());
        }
        if (ssl != null && ssl.isEnabled()) {
            new SslBuilderCustomizer(factory.getPort(), address, ssl, factory.getOrCreateSslStoreProvider()).customize(builder);
        } else {
            builder.addHttpListener(port, address != null ? address.getHostAddress() : "0.0.0.0");
        }
        builder.setServerOption(UndertowOptions.SHUTDOWN_TIMEOUT, (Object)0);
        for (UndertowBuilderCustomizer customizer : this.builderCustomizers) {
            customizer.customize(builder);
        }
        return builder;
    }

    List<HttpHandlerFactory> createHttpHandlerFactories(AbstractConfigurableWebServerFactory webServerFactory, HttpHandlerFactory ... initialHttpHandlerFactories) {
        List<HttpHandlerFactory> factories = UndertowWebServerFactoryDelegate.createHttpHandlerFactories(webServerFactory.getCompression(), this.useForwardHeaders, webServerFactory.getServerHeader(), webServerFactory.getShutdown(), initialHttpHandlerFactories);
        if (this.isAccessLogEnabled()) {
            factories.add(new AccessLogHttpHandlerFactory(this.accessLogDirectory, this.accessLogPattern, this.accessLogPrefix, this.accessLogSuffix, this.accessLogRotate));
        }
        return factories;
    }

    static List<HttpHandlerFactory> createHttpHandlerFactories(Compression compression, boolean useForwardHeaders, String serverHeader, Shutdown shutdown, HttpHandlerFactory ... initialHttpHandlerFactories) {
        ArrayList<HttpHandlerFactory> factories = new ArrayList<HttpHandlerFactory>(Arrays.asList(initialHttpHandlerFactories));
        if (compression != null && compression.getEnabled()) {
            factories.add(new CompressionHttpHandlerFactory(compression));
        }
        if (useForwardHeaders) {
            factories.add(Handlers::proxyPeerAddress);
        }
        if (StringUtils.hasText((String)serverHeader)) {
            factories.add(next -> Handlers.header((HttpHandler)next, (String)"Server", (String)serverHeader));
        }
        if (shutdown == Shutdown.GRACEFUL) {
            factories.add(Handlers::gracefulShutdown);
        }
        return factories;
    }
}

