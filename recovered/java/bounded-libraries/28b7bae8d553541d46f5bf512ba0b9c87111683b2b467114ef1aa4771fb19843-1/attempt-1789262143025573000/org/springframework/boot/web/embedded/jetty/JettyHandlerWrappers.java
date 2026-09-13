/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.eclipse.jetty.http.HttpMethod
 *  org.eclipse.jetty.server.Request
 *  org.eclipse.jetty.server.handler.HandlerWrapper
 *  org.eclipse.jetty.server.handler.gzip.GzipHandler
 */
package org.springframework.boot.web.embedded.jetty;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.springframework.boot.web.server.Compression;

final class JettyHandlerWrappers {
    private JettyHandlerWrappers() {
    }

    static HandlerWrapper createGzipHandlerWrapper(Compression compression) {
        GzipHandler handler = new GzipHandler();
        handler.setMinGzipSize((int)compression.getMinResponseSize().toBytes());
        handler.setIncludedMimeTypes(compression.getMimeTypes());
        for (HttpMethod httpMethod : HttpMethod.values()) {
            handler.addIncludedMethods(new String[]{httpMethod.name()});
        }
        if (compression.getExcludedUserAgents() != null) {
            try {
                handler.setExcludedAgentPatterns(compression.getExcludedUserAgents());
            }
            catch (NoSuchMethodError noSuchMethodError) {
                // empty catch block
            }
        }
        return handler;
    }

    static HandlerWrapper createServerHeaderHandlerWrapper(String header) {
        return new ServerHeaderHandler(header);
    }

    private static class ServerHeaderHandler
    extends HandlerWrapper {
        private static final String SERVER_HEADER = "server";
        private final String value;

        ServerHeaderHandler(String value) {
            this.value = value;
        }

        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            if (!response.getHeaderNames().contains(SERVER_HEADER)) {
                response.setHeader(SERVER_HEADER, this.value);
            }
            super.handle(target, baseRequest, request, response);
        }
    }
}

