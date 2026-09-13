/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.servlet.api.DeploymentInfo
 *  io.undertow.websockets.jsr.WebSocketDeploymentInfo
 *  org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer
 *  org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.websocket.servlet;

import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

public class UndertowWebSocketServletWebServerCustomizer
implements WebServerFactoryCustomizer<UndertowServletWebServerFactory>,
Ordered {
    public void customize(UndertowServletWebServerFactory factory) {
        WebsocketDeploymentInfoCustomizer customizer = new WebsocketDeploymentInfoCustomizer();
        factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer[]{customizer});
    }

    public int getOrder() {
        return 0;
    }

    private static class WebsocketDeploymentInfoCustomizer
    implements UndertowDeploymentInfoCustomizer {
        private WebsocketDeploymentInfoCustomizer() {
        }

        public void customize(DeploymentInfo deploymentInfo) {
            WebSocketDeploymentInfo info = new WebSocketDeploymentInfo();
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", (Object)info);
        }
    }
}

