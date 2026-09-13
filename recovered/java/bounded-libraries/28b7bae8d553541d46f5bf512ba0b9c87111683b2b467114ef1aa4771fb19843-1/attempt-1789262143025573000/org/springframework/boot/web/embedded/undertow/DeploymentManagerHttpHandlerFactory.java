/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.server.HttpHandler
 *  io.undertow.server.HttpServerExchange
 *  io.undertow.servlet.api.DeploymentManager
 *  javax.servlet.ServletException
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.api.DeploymentManager;
import java.io.Closeable;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.boot.web.embedded.undertow.HttpHandlerFactory;
import org.springframework.util.Assert;

class DeploymentManagerHttpHandlerFactory
implements HttpHandlerFactory {
    private final DeploymentManager deploymentManager;

    DeploymentManagerHttpHandlerFactory(DeploymentManager deploymentManager) {
        this.deploymentManager = deploymentManager;
    }

    @Override
    public HttpHandler getHandler(HttpHandler next) {
        Assert.state((next == null ? 1 : 0) != 0, (String)"DeploymentManagerHttpHandlerFactory must be first");
        return new DeploymentManagerHandler(this.deploymentManager);
    }

    DeploymentManager getDeploymentManager() {
        return this.deploymentManager;
    }

    static class DeploymentManagerHandler
    implements HttpHandler,
    Closeable {
        private final DeploymentManager deploymentManager;
        private final HttpHandler handler;

        DeploymentManagerHandler(DeploymentManager deploymentManager) {
            this.deploymentManager = deploymentManager;
            try {
                this.handler = deploymentManager.start();
            }
            catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void handleRequest(HttpServerExchange exchange) throws Exception {
            this.handler.handleRequest(exchange);
        }

        @Override
        public void close() throws IOException {
            try {
                this.deploymentManager.stop();
                this.deploymentManager.undeploy();
            }
            catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        }

        DeploymentManager getDeploymentManager() {
            return this.deploymentManager;
        }
    }
}

