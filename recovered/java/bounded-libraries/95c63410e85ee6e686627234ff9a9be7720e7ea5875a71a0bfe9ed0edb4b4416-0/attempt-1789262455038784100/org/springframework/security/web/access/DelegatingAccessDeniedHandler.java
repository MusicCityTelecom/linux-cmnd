/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.Assert;

public final class DelegatingAccessDeniedHandler
implements AccessDeniedHandler {
    private final LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers;
    private final AccessDeniedHandler defaultHandler;

    public DelegatingAccessDeniedHandler(LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers, AccessDeniedHandler defaultHandler) {
        Assert.notEmpty(handlers, (String)"handlers cannot be null or empty");
        Assert.notNull((Object)defaultHandler, (String)"defaultHandler cannot be null");
        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        for (Map.Entry<Class<? extends AccessDeniedException>, AccessDeniedHandler> entry : this.handlers.entrySet()) {
            Class<? extends AccessDeniedException> handlerClass = entry.getKey();
            if (!handlerClass.isAssignableFrom(((Object)((Object)accessDeniedException)).getClass())) continue;
            AccessDeniedHandler handler = entry.getValue();
            handler.handle(request, response, accessDeniedException);
            return;
        }
        this.defaultHandler.handle(request, response, accessDeniedException);
    }
}

