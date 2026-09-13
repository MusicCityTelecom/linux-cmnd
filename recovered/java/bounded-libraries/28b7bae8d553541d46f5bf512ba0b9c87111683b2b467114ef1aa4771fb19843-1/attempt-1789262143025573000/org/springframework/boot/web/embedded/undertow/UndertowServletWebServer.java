/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.Handlers
 *  io.undertow.Undertow$Builder
 *  io.undertow.server.HttpHandler
 *  io.undertow.servlet.api.DeploymentManager
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.api.DeploymentManager;
import org.springframework.boot.web.embedded.undertow.DeploymentManagerHttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.util.StringUtils;

public class UndertowServletWebServer
extends UndertowWebServer {
    private final String contextPath;
    private final DeploymentManager manager;

    public UndertowServletWebServer(Undertow.Builder builder, Iterable<HttpHandlerFactory> httpHandlerFactories, String contextPath, boolean autoStart) {
        super(builder, httpHandlerFactories, autoStart);
        this.contextPath = contextPath;
        this.manager = this.findManager(httpHandlerFactories);
    }

    private DeploymentManager findManager(Iterable<HttpHandlerFactory> httpHandlerFactories) {
        for (HttpHandlerFactory httpHandlerFactory : httpHandlerFactories) {
            if (!(httpHandlerFactory instanceof DeploymentManagerHttpHandlerFactory)) continue;
            return ((DeploymentManagerHttpHandlerFactory)httpHandlerFactory).getDeploymentManager();
        }
        return null;
    }

    @Override
    protected HttpHandler createHttpHandler() {
        HttpHandler handler = super.createHttpHandler();
        if (StringUtils.hasLength((String)this.contextPath)) {
            handler = Handlers.path().addPrefixPath(this.contextPath, handler);
        }
        return handler;
    }

    @Override
    protected String getStartLogMessage() {
        String message = super.getStartLogMessage();
        if (StringUtils.hasText((String)this.contextPath)) {
            message = message + " with context path '" + this.contextPath + "'";
        }
        return message;
    }

    public DeploymentManager getDeploymentManager() {
        return this.manager;
    }
}

