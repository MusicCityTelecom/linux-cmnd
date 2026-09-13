/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.Valve
 */
package org.springframework.boot.web.embedded.tomcat;

import java.io.File;
import java.nio.charset.Charset;
import org.apache.catalina.Valve;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;

public interface ConfigurableTomcatWebServerFactory
extends ConfigurableWebServerFactory {
    public void setBaseDirectory(File var1);

    public void setBackgroundProcessorDelay(int var1);

    public void addEngineValves(Valve ... var1);

    public void addConnectorCustomizers(TomcatConnectorCustomizer ... var1);

    public void addContextCustomizers(TomcatContextCustomizer ... var1);

    public void addProtocolHandlerCustomizers(TomcatProtocolHandlerCustomizer<?> ... var1);

    public void setUriEncoding(Charset var1);
}

