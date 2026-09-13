/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Undertow$Builder
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.http.server.reactive.UndertowHttpHandlerAdapter
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Undertow;
import java.io.File;
import java.util.Collection;
import java.util.List;
import org.springframework.boot.web.embedded.undertow.ConfigurableUndertowWebServerFactory;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowWebServerFactoryDelegate;
import org.springframework.boot.web.reactive.server.AbstractReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.UndertowHttpHandlerAdapter;

public class UndertowReactiveWebServerFactory
extends AbstractReactiveWebServerFactory
implements ConfigurableUndertowWebServerFactory {
    private UndertowWebServerFactoryDelegate delegate = new UndertowWebServerFactoryDelegate();

    public UndertowReactiveWebServerFactory() {
    }

    public UndertowReactiveWebServerFactory(int port) {
        super(port);
    }

    @Override
    public void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers) {
        this.delegate.setBuilderCustomizers(customizers);
    }

    @Override
    public void addBuilderCustomizers(UndertowBuilderCustomizer ... customizers) {
        this.delegate.addBuilderCustomizers(customizers);
    }

    public Collection<UndertowBuilderCustomizer> getBuilderCustomizers() {
        return this.delegate.getBuilderCustomizers();
    }

    @Override
    public void setBufferSize(Integer bufferSize) {
        this.delegate.setBufferSize(bufferSize);
    }

    @Override
    public void setIoThreads(Integer ioThreads) {
        this.delegate.setIoThreads(ioThreads);
    }

    @Override
    public void setWorkerThreads(Integer workerThreads) {
        this.delegate.setWorkerThreads(workerThreads);
    }

    @Override
    public void setUseDirectBuffers(Boolean directBuffers) {
        this.delegate.setUseDirectBuffers(directBuffers);
    }

    @Override
    public void setUseForwardHeaders(boolean useForwardHeaders) {
        this.delegate.setUseForwardHeaders(useForwardHeaders);
    }

    protected final boolean isUseForwardHeaders() {
        return this.delegate.isUseForwardHeaders();
    }

    @Override
    public void setAccessLogDirectory(File accessLogDirectory) {
        this.delegate.setAccessLogDirectory(accessLogDirectory);
    }

    @Override
    public void setAccessLogPattern(String accessLogPattern) {
        this.delegate.setAccessLogPattern(accessLogPattern);
    }

    @Override
    public void setAccessLogPrefix(String accessLogPrefix) {
        this.delegate.setAccessLogPrefix(accessLogPrefix);
    }

    @Override
    public void setAccessLogSuffix(String accessLogSuffix) {
        this.delegate.setAccessLogSuffix(accessLogSuffix);
    }

    public boolean isAccessLogEnabled() {
        return this.delegate.isAccessLogEnabled();
    }

    @Override
    public void setAccessLogEnabled(boolean accessLogEnabled) {
        this.delegate.setAccessLogEnabled(accessLogEnabled);
    }

    @Override
    public void setAccessLogRotate(boolean accessLogRotate) {
        this.delegate.setAccessLogRotate(accessLogRotate);
    }

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        Undertow.Builder builder = this.delegate.createBuilder(this);
        List<HttpHandlerFactory> httpHandlerFactories = this.delegate.createHttpHandlerFactories(this, next -> new UndertowHttpHandlerAdapter(httpHandler));
        return new UndertowWebServer(builder, httpHandlerFactories, this.getPort() >= 0);
    }
}

