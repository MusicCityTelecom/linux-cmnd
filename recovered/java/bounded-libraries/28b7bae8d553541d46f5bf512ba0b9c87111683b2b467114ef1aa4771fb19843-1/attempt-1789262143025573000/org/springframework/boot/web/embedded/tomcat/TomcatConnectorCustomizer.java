/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.connector.Connector
 */
package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.connector.Connector;

@FunctionalInterface
public interface TomcatConnectorCustomizer {
    public void customize(Connector var1);
}

