/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.eclipse.jetty.server.Request
 *  org.eclipse.jetty.servlet.ErrorPageErrorHandler
 */
package org.springframework.boot.web.embedded.jetty;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;

class JettyEmbeddedErrorHandler
extends ErrorPageErrorHandler {
    private static final Set<String> HANDLED_HTTP_METHODS = new HashSet<String>(Arrays.asList("GET", "POST", "HEAD"));

    JettyEmbeddedErrorHandler() {
    }

    public boolean errorPageForMethod(String method) {
        return true;
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!HANDLED_HTTP_METHODS.contains(baseRequest.getMethod())) {
            baseRequest.setMethod("GET");
        }
        super.handle(target, baseRequest, request, response);
    }
}

