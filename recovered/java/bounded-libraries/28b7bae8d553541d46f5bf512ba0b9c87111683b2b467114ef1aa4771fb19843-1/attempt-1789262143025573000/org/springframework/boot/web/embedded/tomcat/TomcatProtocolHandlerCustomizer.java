/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.coyote.ProtocolHandler
 */
package org.springframework.boot.web.embedded.tomcat;

import org.apache.coyote.ProtocolHandler;

@FunctionalInterface
public interface TomcatProtocolHandlerCustomizer<T extends ProtocolHandler> {
    public void customize(T var1);
}

