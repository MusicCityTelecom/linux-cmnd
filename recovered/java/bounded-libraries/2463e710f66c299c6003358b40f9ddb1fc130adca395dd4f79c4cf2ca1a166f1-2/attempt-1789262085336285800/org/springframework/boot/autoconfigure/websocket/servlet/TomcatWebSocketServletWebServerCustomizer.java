/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContainerInitializer
 *  org.apache.tomcat.websocket.server.WsSci
 *  org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
 *  org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.autoconfigure.websocket.servlet;

import javax.servlet.ServletContainerInitializer;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

public class TomcatWebSocketServletWebServerCustomizer
implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>,
Ordered {
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addContextCustomizers(new TomcatContextCustomizer[]{context -> context.addServletContainerInitializer((ServletContainerInitializer)new WsSci(), null)});
    }

    public int getOrder() {
        return 0;
    }
}

